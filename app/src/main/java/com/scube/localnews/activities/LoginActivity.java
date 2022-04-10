package com.scube.localnews.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.scube.localnews.NewsApp;
import com.scube.localnews.R;
import com.scube.localnews.utils.AlertBox;
import com.scube.localnews.utils.Helper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {
    NewsApp newsApp;
    Button login_button;
    ProgressDialog pdialog;
    EditText uidEt,pwdEt;
    TextInputLayout uidtextInputLayout,pwdtextInputLayout2;
    TextView signup,foregotpwd;
    String  TAG="LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(Helper.isNetworkAvailable(LoginActivity.this)) {
            initViews();
            pdialog = AlertBox.intPDialog(LoginActivity.this);
        }else{
            Helper.showErrorScreen(LoginActivity.this,"LoginActivity");
        }
    }

    private void initViews() {
        newsApp=(NewsApp) getApplication();
        uidEt=(EditText)findViewById(R.id.uidet);
        pwdEt=(EditText)findViewById(R.id.pwdet);
        login_button=(Button)findViewById(R.id.login_button);
        uidtextInputLayout=(TextInputLayout)findViewById(R.id.emailTextInputLayout);
        pwdtextInputLayout2=(TextInputLayout)findViewById(R.id.pwdtextInputLayout2);
        login_button.setOnClickListener(this);
        signup=(TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);
        foregotpwd=findViewById(R.id.foregotpwd);
        foregotpwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.login_button) {
            if (Helper.isNetworkAvailable(LoginActivity.this)) {
               /* Intent i = new Intent(LoginActivity.this, home.class);
                startActivity(i);*/
                if(validate()){
                    pdialog.show();
                    FirebaseAuth mAuth=newsApp.getmAuth();
                 mAuth.signInWithEmailAndPassword(uidEt.getText().toString(),pwdEt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         pdialog.cancel();
                         if (task.isSuccessful()) {
                             // Sign in success, update UI with the signed-in user's information
                             Log.d(TAG, "signInWithEmail:success");
                             FirebaseUser user = mAuth.getCurrentUser();
                             updateUI(user);
                         } else {
                             // If sign in fails, display a message to the user.
                             Log.w(TAG, "signInWithEmail:failure", task.getException());
                             Toast.makeText(LoginActivity.this, "Authentication failed.",
                                     Toast.LENGTH_SHORT).show();
                             AlertBox.showDismisableAlertDialog(LoginActivity.this,"Login Failed please provide valid email password!");
                         }
                     }
                 });
                }
            }else{
                Helper.showErrorScreen(LoginActivity.this,"LoginActivity");
            }
        }
        if(v.getId()==R.id.signup){
            if (Helper.isNetworkAvailable(LoginActivity.this)) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }else{
                Helper.showErrorScreen(LoginActivity.this,"LoginActivity");
            }
        }
        if(v.getId()==R.id.foregotpwd){
            if (Helper.isNetworkAvailable(LoginActivity.this)) {
                Intent i = new Intent(LoginActivity.this, ForegotPassword.class);
                startActivity(i);
            }else{
                Helper.showErrorScreen(LoginActivity.this,"LoginActivity");
            }
        }
    }

    private void updateUI(FirebaseUser user) {
        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    private boolean validate() {
        boolean isValid=true;
        if(uidEt.getText().toString().equalsIgnoreCase("")||uidEt.getText().toString().length()==0){
            uidtextInputLayout.setError("Please enter username");
            isValid= false;
        }else {
            uidtextInputLayout.setErrorEnabled(false);
        }

        if(pwdEt.getText().toString().equalsIgnoreCase("")||pwdEt.getText().toString().length()==0){
            pwdtextInputLayout2.setError("Please enter password");

            isValid= false;
        }else {
            pwdtextInputLayout2.setErrorEnabled(false);
        }
        return isValid;
    }
}