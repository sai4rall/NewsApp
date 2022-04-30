package com.scube.localnews.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.scube.localnews.NewsApp;
import com.scube.localnews.R;
import com.scube.localnews.config.Constants;
import com.scube.localnews.service.FcmReceiverService;
import com.scube.localnews.services.LocalNewsAppbackgroundService;
import com.scube.localnews.utils.AlertBox;
import com.scube.localnews.utils.Helper;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener{
    NewsApp newsApp;
    Button login_button;
    FirebaseAuth mAuth;
    ProgressDialog pdialog;
    EditText uidEt, pwdEt;
    TextInputLayout uidtextInputLayout, pwdtextInputLayout2;
    TextView signup, foregotpwd;
    String TAG="LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Helper.isNetworkAvailable(LoginActivity.this)) {
            initViews();
            pdialog=AlertBox.intPDialog(LoginActivity.this);
        } else{
            Helper.showErrorScreen(LoginActivity.this, "LoginActivity");
        }
    }

    private void initViews(){

        newsApp=(NewsApp) getApplication();
        mAuth=newsApp.getmAuth();

        uidEt=(EditText) findViewById(R.id.uidet);
        pwdEt=(EditText) findViewById(R.id.pwdet);
        login_button=(Button) findViewById(R.id.login_button);
        uidtextInputLayout=(TextInputLayout) findViewById(R.id.emailTextInputLayout);
        pwdtextInputLayout2=(TextInputLayout) findViewById(R.id.pwdtextInputLayout2);
        login_button.setOnClickListener(this);
        signup=(TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);
        foregotpwd=findViewById(R.id.foregotpwd);
        foregotpwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.login_button:
                onLoginClick();
                break;
            case R.id.signup:
                onSignUpClick();
                break;
            case R.id.foregotpwd:
                onForegotPwdClick();
                break;
            default:
                break;
        }

    }

    private void onForegotPwdClick(){
        if (Helper.isNetworkAvailable(LoginActivity.this)) {
            Intent i=new Intent(LoginActivity.this, ForegotPassword.class);
            startActivity(i);
        } else{
            Helper.showErrorScreen(LoginActivity.this, "LoginActivity");
        }
    }

    private void onSignUpClick(){
        if (Helper.isNetworkAvailable(LoginActivity.this)) {
            Intent i=new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        } else{
            Helper.showErrorScreen(LoginActivity.this, "LoginActivity");
        }
    }

    private void onLoginClick(){
        if (Helper.isNetworkAvailable(LoginActivity.this)) {
            if (validate()) {
                pdialog.show();
                mAuth.signInWithEmailAndPassword(uidEt.getText().toString(), pwdEt.getText().toString())
                        .addOnCompleteListener(this);
            }
        } else{
            Helper.showErrorScreen(LoginActivity.this, "LoginActivity");
        }
    }

    private void updateUI(FirebaseUser user){
        FcmReceiverService.subscribeTopic(getApplicationContext(), Constants.TOPIC_TO_SUBSCRIBE);
        Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private boolean validate(){
        boolean isValid=true;
        if (uidEt.getText().toString().equalsIgnoreCase("") || uidEt.getText().toString().length() == 0) {
            uidtextInputLayout.setError("Please enter username");
            isValid=false;
        } else{
            uidtextInputLayout.setErrorEnabled(false);
        }

        if (pwdEt.getText().toString().equalsIgnoreCase("") || pwdEt.getText().toString().length() == 0) {
            pwdtextInputLayout2.setError("Please enter password");

            isValid=false;
        } else{
            pwdtextInputLayout2.setErrorEnabled(false);
        }
        return isValid;
    }

    @Override
    public void onComplete(@NonNull Task task){
        pdialog.cancel();
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithEmail:success");
            FirebaseUser user=mAuth.getCurrentUser();
            updateUI(user);
            startService();
            startsvc();
        } else{
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithEmail:failure", task.getException());
            Toast.makeText(LoginActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
            AlertBox.showDismisableAlertDialog(LoginActivity.this, "Login Failed please provide valid email password!");
        }
    }
    private void startService()
    {

        Intent myIntent = new Intent(this, LocalNewsAppbackgroundService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
        Log.e("TAG", "++++++++++222222++++++++");
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        // calendar.setTimeInMillis(System.currentTimeMillis());
        //calendar.add(Calendar.SECOND, 10);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }


    }

    private void startsvc()
    {
        Intent intent = new Intent(this, LocalNewsAppbackgroundService.class);
        startService(intent);
    }
}