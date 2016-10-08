package com.mlabs.bbm.firstandroidapp_morningclass;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = (Button) findViewById(R.id.login);
        TextView show = (TextView) findViewById(R.id.show);
        TextView signup = (TextView) findViewById(R.id.btnReg);


        final DataBaseAdapter db = new DataBaseAdapter(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText emailAdd = (EditText) findViewById(R.id.Email);
                EditText pword = (EditText) findViewById(R.id.Pw);

                //if (checkLogin(emailAdd.getText().toString(), pword.getText().toString()) == true) {
                if (db.validateUserFromEmail(emailAdd.getText().toString(), pword.getText().toString()) == true
                        || db.validateUserFromUName(emailAdd.getText().toString(), pword.getText().toString()) == true) {
                    //Toast.makeText(getApplicationContext(), "Connecting...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, TouchActivity.class);
                    startActivity(intent);

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            MainActivity.this).create();
                    alertDialog.setMessage("Invalid Email Address/Password");
                    alertDialog.show();
                }

            }
        });

        show.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        EditText showPword = (EditText) findViewById(R.id.Pw);
                        showPword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                    case MotionEvent.ACTION_UP:
                        EditText hidePword = (EditText) findViewById(R.id.Pw);
                        hidePword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                return true;
            }

        });

        signup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent signup = new Intent(MainActivity.this, SignUp.class);
                startActivity(signup);
            }
        });
    }

    public boolean checkLogin(String emailAdd, String pword) {
        String regexEmail = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";

        Pattern p = Pattern.compile(regexEmail);
        Matcher m = p.matcher(emailAdd);

        if (pword.length() >= 8 && m.matches()) {
            return true;
        } else {
            return false;
        }
    }

        @Override
        public void onBackPressed(){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
    }
}
