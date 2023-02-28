package me.shelly.projectshelly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseHandler firebaseHandler;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //

        firebaseHandler = new FirebaseHandler();
        auth = firebaseHandler.getAuth();

        EditText getPassword = findViewById(R.id.getPassword);
        EditText getEmail = findViewById(R.id.getEmail);
        Button registerBtn = findViewById(R.id.buttonRegister);
        Button loginBtn = findViewById(R.id.buttonLogin);

        loginBtn.setOnClickListener(v -> firebaseHandler.signIn(getEmail.getText().toString(), getPassword.getText().toString(), success -> {
            if (success) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Login completed!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                });
            }
        }, error -> {
            runOnUiThread(() -> Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());
        }));

        registerBtn.setOnClickListener(view -> firebaseHandler.createAccount(getEmail.getText().toString(), getPassword.getText().toString(), success -> {
            if (success) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Register completed! Please log in.", Toast.LENGTH_SHORT).show());
            }
        }, error -> {
            runOnUiThread(() -> Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show());
        }));


    }
}