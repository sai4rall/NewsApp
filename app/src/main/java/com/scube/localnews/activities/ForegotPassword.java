package com.scube.localnews.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.scube.localnews.NewsApp;
import com.scube.localnews.R;
import com.scube.localnews.utils.AlertBox;

public class ForegotPassword extends AppCompatActivity implements View.OnClickListener {
    EditText foregotpwd_email_address;
    TextInputLayout f_emailTextInputLayout;
    Button resetPassword;
    ProgressDialog pdialog;
    NewsApp newsApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foregot_password);
        initViews();
    }

    private void initViews() {
        newsApp=(NewsApp)getApplication();
        foregotpwd_email_address=findViewById(R.id.foregotpwd_email_address);
        f_emailTextInputLayout=findViewById(R.id.f_emailTextInputLayout);
        resetPassword=findViewById(R.id.resetPassword);
        resetPassword.setOnClickListener(this);
        pdialog= AlertBox.intPDialog(ForegotPassword.this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.resetPassword){
            pdialog.show();
            FirebaseAuth mAuth=newsApp.getmAuth();
            mAuth.sendPasswordResetEmail(foregotpwd_email_address.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pdialog.dismiss();
                            if(task.isSuccessful()) {
                                AlertBox.showDismisableAlertDialog(ForegotPassword.this, "Email sent with Reset Instruction!")
                                        .setOnDismissListener(dialog -> {
                                            Intent intent = new Intent(ForegotPassword.this, LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            AlertBox.showDismisableAlertDialog(ForegotPassword.this,"Email Address not fount");

                        }
                    });




        }
    }
}