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

public class Register extends AppCompatActivity {


    private EditText register_username;
    private EditText register_password;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_username =  (EditText) findViewById(R.id.register_username);
        register_password =  (EditText) findViewById(R.id.register_password);
        firebaseAuth = FirebaseAuth.getInstance();

    }
    public void registerUser(View v) {
        // startActivity(new Intent(Register.this,SeminarListActivity.class));
        final ProgressDialog progressDialog = ProgressDialog.show(Register.this, "Please Wait...", "Processing", true);
        (firebaseAuth.createUserWithEmailAndPassword(register_username.getText().toString(), register_password.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful()) {
                    Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Register.this, Login.class);
                    startActivity(i);
                }
                else {
                    Log.e("Error", task.getException().toString());
                    Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
