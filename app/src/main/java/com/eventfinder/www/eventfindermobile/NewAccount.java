package com.eventfinder.www.eventfindermobile;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.RadioGroup;

public class NewAccount extends AppCompatActivity {
    // global error message used in a toast
    CharSequence err_text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        final ConstraintLayout mainLayout = findViewById(R.id.main_layout);

        Button accountbtn = (Button)findViewById(R.id.account);

        accountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                // context and duration used in toast
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                if (hasValidInput(mainLayout)) {
                    startActivity(new Intent(NewAccount.this, HomeScreenActivity.class));
                } else { // show error messages
                    Toast.makeText(context, err_text,duration).show();
                }
            }
        });
    }

    //
    // function that validates/checks if input is empty
    // currently hardcoded to being ConstraintLayout
    private boolean hasValidInput(ConstraintLayout mainLayout)
    {
        boolean validInput = true;

        // loop through all children
        for (int i = 0; i < mainLayout.getChildCount(); i++)
        {
            // if edittext object
            if (mainLayout.getChildAt(i) instanceof EditText)
            {
                EditText child = (EditText)mainLayout.getChildAt(i);
                // if empty add to error message
                if ("".equals(child.getText().toString()))
                {
                    validInput = false;
                    err_text = err_text + child.getHint().toString() + " cannot be blank!\n";
                }
            }
            // if male/female not really sure should it be a button or toggle group?

            if (mainLayout.getChildAt(i) instanceof  RadioGroup)
            {
                RadioGroup child = (RadioGroup)mainLayout.getChildAt(i);
                // radioGroup is empty
                if (child.getCheckedRadioButtonId() == -1)
                {
                    err_text = err_text + "An account type must be selected.\n";
                }
            }
        }
        return validInput;
    }
}