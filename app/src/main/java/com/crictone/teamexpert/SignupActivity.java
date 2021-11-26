package com.crictone.teamexpert;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import static com.crictone.teamexpert.util.ApiConfig.GAMES_;

public class SignupActivity extends AppCompatActivity {

    EditText password_edittext, username_edittext, email_edittext, contact_number_edittext, reference_code_edittext;
    String password, username, email, contact_number, reference_code;
    Button signUp;
    ProgressBar progressLayout;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username_edittext = findViewById(R.id.username_edittext);
        email_edittext = findViewById(R.id.editText_email);
        contact_number_edittext = findViewById(R.id.contact_number_edittext);
        password_edittext = findViewById(R.id.password_edittext);
        reference_code_edittext = findViewById(R.id.reference_code);
        signUp = findViewById(R.id.signUp);
        progressLayout = findViewById(R.id.progressLayout);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_edittext.getText().toString();
                email = email_edittext.getText().toString();
                contact_number = contact_number_edittext.getText().toString();
                password = password_edittext.getText().toString();
                reference_code = reference_code_edittext.getText().toString();

                //  Toast.makeText(SignupActivity.this, username + email + contact_number + password , Toast.LENGTH_SHORT).show();

                //Start ProgressBar first (Set visibility VISIBLE)
                if(email_edittext.getText().toString().trim().matches(emailPattern)) {

                    if (!username.equals("") && !contact_number.equals("") && !email.equals("") && !password.equals("")) {
                        progressLayout.setVisibility(View.VISIBLE);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[5];
                                field[0] = "username";
                                field[1] = "email";
                                field[2] = "phone_number";
                                field[3] = "password";
                                field[4] = "refer_code";
                                //Creating array for data
                                String[] data = new String[5];
                                data[0] = username;
                                data[1] = email;
                                data[2] = contact_number;
                                data[3] = password;
                                data[4] = reference_code;

                                Log.d("DataL", username + email + contact_number + password);

                                PutData putData = new PutData(GAMES_ + "signup.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        //End ProgressBar (Set visibility to GONE)
                                        progressLayout.setVisibility(View.GONE);
                                        Log.i("PutData", String.valueOf(putData));
                                        if (result.equals("Sign Up Success")) {
                                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                        } else {
                                            Toast.makeText(SignupActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                // End Write and Read data with URL
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "All Fields Are Require.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SignupActivity.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                    email_edittext.setError("Invalid Email");
                }
            }


        });


    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void ShowHidePass(View view) {
        if (view.getId() == R.id.show_pass_btn) {

            if (password_edittext.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.hide_password);
                //Show Password
                password_edittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.show_password);
                //Hide Password
                password_edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}