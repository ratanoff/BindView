package ru.ratanov.bindview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;

import ru.ratanov.bindview.annotation.CVV;
import ru.ratanov.bindview.annotation.CardHolder;
import ru.ratanov.bindview.annotation.CardNumber;
import ru.ratanov.bindview.annotation.ExpDate;
import ru.ratanov.bindview.annotation.PayButtton;
import ru.ratanov.bindview.validator.Validator;
import ru.ratanov.bindview_api.BindView;

public class MainActivity extends AppCompatActivity {

    @CardNumber
    @BindView(R.id.card_number_input)
    EditText cardNumberInput;

    @ExpDate
    @BindView(R.id.exp_date_input)
    EditText expDateInput;

    @CVV
    @BindView(R.id.cvv_input)
    EditText cvvInput;

    @CardHolder
    @BindView(R.id.card_holder_input)
    EditText cardHolderInput;

    @PayButtton
    @BindView(R.id.pay_button)
    Button payButton;

    Validator validator = new Validator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        validator.init(this);


    }


    public void count(View view) {
        int counter = 0;
        for (Field field : MainActivity.class.getDeclaredFields()) {


            if (field.isAnnotationPresent(PayButtton.class)) {
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
    }
}
