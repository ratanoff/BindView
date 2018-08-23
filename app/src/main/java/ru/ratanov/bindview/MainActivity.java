package ru.ratanov.bindview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import ru.ratanov.bindview.annotation.CardNumber;
import ru.ratanov.bindview.annotation.Counter;
import ru.ratanov.bindview.annotation.MyButton;
import ru.ratanov.bindview.validator.Validator;
import ru.ratanov.bindview_api.BindView;

public class MainActivity extends AppCompatActivity {

    @CardNumber
    @BindView(R.id.card_input)
    EditText cardInput;

    @Counter
    @BindView(R.id.test_textView)
    TextView textView;

    @Counter
    @MyButton
    Button countButton;

    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        validator = new Validator();
        validator.init(this);

        textView.setText("BindView is working!");

        countButton = findViewById(R.id.count_button);
        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count(view);
            }
        });

    }


    public void count(View view) {
        int counter = 0;
        for (Field field : MainActivity.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Counter.class)) {
                counter++;
            }

            if (field.isAnnotationPresent(MyButton.class)) {
                Toast.makeText(this, "class = " + field.getType() + "\nname = " + field.getName(), Toast.LENGTH_SHORT).show();

                try {
                    Button button = (Button) field.get(this);
                    button.setText("Reflection");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException("You can't mark <" + field.getClass() + "> with (@Button) annotation");
                }


            }
        }

        textView.setText(String.valueOf(counter));
    }
}
