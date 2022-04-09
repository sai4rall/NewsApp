package com.scube.localnews.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.scube.localnews.NewsApp;
import com.scube.localnews.R;
import com.scube.localnews.model.Users;
import com.scube.localnews.utils.AlertBox;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    NewsApp newsApp;
    EditText emailEt,pwdEt,confirmPwdEt,nameEt;
    TextInputLayout nametextInputLayout,emailInputLayout,passwordInputLayout,confirmPasswordInputLayout;
    Button signUpBtn;
    String  TAG="RegisterActivity";
    ProgressDialog pdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

    }

    private void initViews() {
        pdialog = AlertBox.intPDialog(RegisterActivity.this);
        newsApp=(NewsApp) getApplication();
        emailEt=findViewById(R.id.signup_email_et);
        pwdEt=findViewById(R.id.signup_pwdet);
        confirmPwdEt=findViewById(R.id.signup_confirm_pwdet);
        nameEt=findViewById(R.id.signup_name_et);
        signUpBtn=findViewById(R.id.signup_button);
        signUpBtn.setOnClickListener(this);
        nametextInputLayout=findViewById(R.id.nametextInputLayout);
        emailInputLayout=findViewById(R.id.emailTextInputLayout);
        passwordInputLayout=findViewById(R.id.pwdInputLayout);
        confirmPasswordInputLayout=findViewById(R.id.confirm_pwdInputLayout);
    }

    @Override
    public void onClick(View v) {
    if(validate()){
        pdialog.show();
        FirebaseAuth mAuth=newsApp.getmAuth();
        mAuth.createUserWithEmailAndPassword(emailEt.getText().toString(),pwdEt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pdialog.cancel();
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    pdialog.cancel();
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    }

    private void updateUI(FirebaseUser user) {
        Intent mainActivity=new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(mainActivity);
    }

    private boolean validate() {
        boolean isValid=true;
        if(emailEt.getText().toString().equalsIgnoreCase("")||emailEt.getText().toString().length()==0){
            emailInputLayout.setError("Please enter Email");
            isValid= false;
        }else if(!isValidEmail(emailEt.getText().toString())){
            emailInputLayout.setError("Please enter valid Email");
            isValid= false;
        }
        else {
            emailInputLayout.setErrorEnabled(false);
        }

        if(pwdEt.getText().toString().equalsIgnoreCase("")||pwdEt.getText().toString().length()==0){
            passwordInputLayout.setError("Please enter password");

            isValid= false;
        }else if(pwdEt.getText().toString().length()<6){
            passwordInputLayout.setError("password should be at least six digits");
            isValid= false;
        }
        else if(pwdEt.getText().toString().length()>10){
            passwordInputLayout.setError("password should be at max ten digits");
            isValid= false;
        }
        else {
            passwordInputLayout.setErrorEnabled(false);
        }
        if(confirmPwdEt.getText().toString().equalsIgnoreCase("")||confirmPwdEt.getText().toString().length()==0){
            confirmPasswordInputLayout.setError("Please enter confirm password");

            isValid= false;
        }
    else if(confirmPwdEt.getText().toString().length()<6){
        confirmPasswordInputLayout.setError("password should be at least six characters");
        isValid= false;
        }
        else if(confirmPwdEt.getText().toString().length()>10){
        confirmPasswordInputLayout.setError("password should be at max ten characters");
        isValid= false;
        }else if(!pwdEt.getText().toString().contentEquals(confirmPwdEt.getText().toString())){
            confirmPasswordInputLayout.setError("password and confirm password should match");
        }
        else {
            confirmPasswordInputLayout.setErrorEnabled(false);
        }
        if(nameEt.getText().toString().equalsIgnoreCase("")||nameEt.getText().toString().length()==0){
            nametextInputLayout.setError("Please enter name");
            isValid= false;
        }else if(nameEt.getText().toString().length()<3){
            nametextInputLayout.setError("name should be at least 3 characters");
            isValid= false;
        }
        else if(nameEt.getText().toString().length()>12){
            nametextInputLayout.setError("name should be at max 12 characters");
            isValid= false;
        }
        else {
            nametextInputLayout.setErrorEnabled(false);
        }
        return isValid;
    }

    private boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}