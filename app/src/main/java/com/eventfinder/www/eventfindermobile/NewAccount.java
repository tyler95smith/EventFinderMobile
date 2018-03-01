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

public class NewAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        final ConstraintLayout mainLayout = findViewById(R.id.main_layout);

        Button accountbtn = (Button)findViewById(R.id.account);

        accountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validInput = true;
                Context context = getApplicationContext();
                CharSequence err_text = "";
                int duration = Toast.LENGTH_SHORT;

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
                    // account type should be a toggle group with one selected by default
                }
                if (validInput) {
                    startActivity(new Intent(NewAccount.this, HomeScreenActivity.class));
                } else { // show error messages
                    Toast.makeText(context, err_text,duration).show();
                }
            }
        });
    }
}