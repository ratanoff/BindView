package ru.ratanov.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Names;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner7;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import ru.ratanov.bindview_api.BindView;

/**
 * @author Daniel Serdyukov
 */
public class BindViewVisitor extends ElementScanner7<Void, Void> {

    private final CodeBlock.Builder mFindViewById = CodeBlock.builder();

    private final Trees mTrees;

    private final Messager mLogger;

    private final Filer mFiler;

    private final TypeElement mOriginElement;

    private final TreeMaker mTreeMaker;

    private final Names mNames;

    public BindViewVisitor(ProcessingEnvironment env, TypeElement element) {
        super();
        mTrees = Trees.instance(env);
        mLogger = env.getMessager();
        mFiler = env.getFiler();
        mOriginElement = element;
        final JavacProcessingEnvironment javacEnv = (JavacProcessingEnvironment) env;
        mTreeMaker = TreeMaker.instance(javacEnv.getContext());
        mNames = Names.instance(javacEnv.getContext());
    }

    @Override
    public Void visitVariable(VariableElement e, Void aVoid) {
        ((JCTree) mTrees.getTree(e)).accept(new TreeTranslator() {
            @Override
            public void visitVarDef(JCTree.JCVariableDecl jcVariableDecl) {
                super.visitVarDef(jcVariableDecl);
                jcVariableDecl.mods.flags &= ~Flags.PRIVATE;
            }
        });
        final BindView bindView = e.getAnnotation(BindView.class);
        mFindViewById.addStatement("(($T) this).$L = ($T) findViewById($L)",
                ClassName.get(mOriginElement), e.getSimpleName(), ClassName.get(e.asType()), bindView.value());
        return super.visitVariable(e, aVoid);
    }

    public void brewJava() {
        final TypeSpec typeSpec = TypeSpec.classBuilder(mOriginElement.getSimpleName() + "$$Proxy")
                .addModifiers(Modifier.ABSTRACT)
                .superclass(ClassName.get(mOriginElement.getSuperclass()))
                .addOriginatingElement(mOriginElement)
                .addMethod(MethodSpec.methodBuilder("setContentView")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(TypeName.INT, "layoutResId")
                        .addStatement("super.setContentView(layoutResId)")
                        .addCode(mFindViewById.build())
                        .build())
                .build();
        final JavaFile javaFile = JavaFile.builder(mOriginElement.getEnclosingElement().toString(), typeSpec)
                .addFileComment("Generated by DroidCon processor, do not modify")
                .build();
        try {
            final JavaFileObject sourceFile = mFiler.createSourceFile(
                    javaFile.packageName + "." + typeSpec.name, mOriginElement);
            try (final Writer writer = new BufferedWriter(sourceFile.openWriter())) {
                javaFile.writeTo(writer);
            }
            JCTree.JCExpression selector = mTreeMaker.Ident(mNames.fromString(javaFile.packageName));
            selector = mTreeMaker.Select(selector, mNames.fromString(typeSpec.name));
            ((JCTree.JCClassDecl) mTrees.getTree(mOriginElement)).extending = selector;
        } catch (IOException e) {
            mLogger.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), mOriginElement);
        }
    }

}