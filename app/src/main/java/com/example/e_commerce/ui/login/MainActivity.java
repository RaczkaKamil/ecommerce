package com.example.e_commerce.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.e_commerce.R;
import com.example.e_commerce.ui.order_list.Main2Activity;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v->{
            TextView login = findViewById(R.id.editTextTextPersonName);
            TextView pass = findViewById(R.id.editTextTextPersonName2);
            if((login.getText().toString().equals("admin") && pass.getText().toString().equals("admin")) || (login.getText().length()==0 && pass.getText().length()==0)){
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
            }else{
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                ConstraintLayout constraint = findViewById(R.id.constraint);
                Snackbar snackbar = Snackbar
                        .make(constraint, "Podano niepoprawne dane logowania", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        });
    }
}