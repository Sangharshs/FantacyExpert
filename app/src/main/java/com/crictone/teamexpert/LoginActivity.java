package com.crictone.teamexpert;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.crictone.teamexpert.util.Ex;
import com.crictone.teamexpert.util.SaveSharedPreference;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static com.crictone.teamexpert.util.ApiConfig.API_Forgot_Pass;
import static com.crictone.teamexpert.util.ApiConfig.GAMES_;
import static com.crictone.teamexpert.util.Ex.isValidMail;
import static com.crictone.teamexpert.util.Utility.USER_NAME;

public class LoginActivity extends AppCompatActivity {

    TextView textView,skip,forgotPassword;
    EditText password_edittext,username_edittext;
    String username, password,email;
    Button login;
    RelativeLayout progressLayout;
    private ProgressDialog progressDialog;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView = findViewById(R.id.register);
        skip = findViewById(R.id.skip);

        username_edittext = findViewById(R.id.username_edittext);
        password_edittext = findViewById(R.id.password_edittext);
        login = findViewById(R.id.login);
        forgotPassword = findViewById(R.id.forgotPassword);
        progressBar = findViewById(R.id.progressLayout);
        progressDialog = new ProgressDialog(LoginActivity.this);
        if(SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        Intent getInt = new Intent();
        String a = getIntent().getStringExtra("sa");

        if(a != null)
        if(a.equals("s")) {
            if (restorePrefData()) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                savePrefsData();
                finish();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_edittext.getText().toString();
                password = password_edittext.getText().toString();
                //Start ProgressBar first (Set visibility VISIBLE)

                if(!username.equals("") && !password.equals(""))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;
                            PutData putData = new PutData(GAMES_+"login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    progressBar.setVisibility(View.GONE);
                                    //End ProgressBar (Set visibility to GONE)
                                    Log.i("PutData", result);
                                    if (result.equals("Login Success")) {
                                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString(USER_NAME, username);
                                        editor.apply();

                                        SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK |FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "All Fields Are Require.", Toast.LENGTH_SHORT).show();
            }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_forgetpassword);
                dialog.getWindow().setLayout(ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
                final EditText editText_forgetPassword = dialog.findViewById(R.id.editText_forget_password);
                Button buttonForgetPassword = dialog.findViewById(R.id.button_forgetPassword);
                buttonForgetPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String stringForgetPassword = editText_forgetPassword.getText().toString();
                        editText_forgetPassword.setError(null);
                        if (!isValidMail(stringForgetPassword) || stringForgetPassword.isEmpty()) {
                            editText_forgetPassword.requestFocus();
                            editText_forgetPassword.setError(getResources().getString(R.string.please_enter_email));
                        } else {
                            if (Ex.isConnectionEnable(LoginActivity.this)) {
                                forgetPassword(stringForgetPassword);
                            } else {
                               // Toast.makeText(LoginActivity.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
//
//    public void Validate_form() {
//        username_edittext.setError(null);
//        password_edittext.setError(null);
//
//        if (!isValidMail(username) || username.isEmpty()) {
//            password_edittext.requestFocus();
//            username_edittext.setError(getResources().getString(R.string.app_name));
//        } else if (password.equals("") || password.isEmpty()) {
//            password_edittext.requestFocus();
//            //password_edittext.setError(getResources().getString(R.string.PasswordEditTest_error));
//        } else {
//           username_edittext.clearFocus();
//            password_edittext.clearFocus();
//
//            username_edittext.setText("");
//            password_edittext.setText("");
//
//            if (Ex.isConnectionEnable(LoginActivity.this)) {
//                login(email,password);
//            } else {
//              //  Toast.makeText(this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    public void forgetPassword(String sendEmail_forget_password) {

        progressDialog.show();
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);

        String forgetPassword_url = API_Forgot_Pass + "&email=" + sendEmail_forget_password;

        Log.e("fURL",forgetPassword_url);
       AsyncHttpClient client = new AsyncHttpClient();
        client.get(forgetPassword_url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("spin");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String msg = object.getString("msg");
                        String success = object.getString("success");
Log.e("fURL1",msg + success);
                        if (success.equals("1")) {
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                    }

                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            progressDialog.dismiss();
            }

        });
    }
    public void ShowHidePass(View view) {
        if(view.getId()==R.id.show_pass_btn){
            if(password_edittext.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.hide_password);
                //Show Password
                password_edittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.show_password);
                //Hide Password
                password_edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;



    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.apply();


    }
}