package ru.ratanov.bindview.validator;

import java.lang.annotation.Annotation;

public class ButtonBuilder {

    public static String getClassAnnotationValue(Class classType, Class annotationType, String attributeName) {
        String value = null;

        Annotation annotation = classType.getAnnotation(annotationType);
        if (annotation != null) {
            try {
                value = (String) annotation.annotationType().getMethod(attributeName).invoke(annotation);
            } catch (Exception ex) {
            }
        }

        return value;
    }
}
