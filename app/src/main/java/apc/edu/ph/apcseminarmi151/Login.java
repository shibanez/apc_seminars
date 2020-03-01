package apc.edu.ph.apcseminarmi151;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText emailAddressEditText;
    private EditText passwordEditText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailAddressEditText =  (EditText) findViewById(R.id.emailAddress);
        passwordEditText =  (EditText) findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();

    }
    public void login(View v) {
        //startActivity(new Intent(Login.this,SeminarListActivity.class));
        String email = emailAddressEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (validate(email, password)) {
            final ProgressDialog progressDialog = ProgressDialog.show(Login.this, "Please Wait...", "Processing", true);
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful()) {
                        Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Login.this, SeminarListActivity.class);
                        startActivity(i);
                    }
                    else {
                        Log.e("Error", task.getException().toString());
                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private boolean validate(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email/password must not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void register(View v) {
        startActivity(new Intent(Login.this, Register.class));
    }

}
