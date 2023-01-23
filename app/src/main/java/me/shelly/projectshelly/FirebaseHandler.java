package me.shelly.projectshelly;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class FirebaseHandler implements Executor {

    // [START declare_auth]
    private FirebaseAuth mAuth;
    public FirebaseHandler() {
        mAuth = FirebaseAuth.getInstance();
    }
    // [END declare_auth]

    public void signIn(String email, String password, CompleteListener<Boolean> completeListener, ErrorListener<Exception> errorListener) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("signInWithEmail:success", "signInWithEmail:success: " + task.isSuccessful());
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("signInWithEmail:failure:", "signInWithEmail:failure:", task.getException());
                            errorListener.onError(task.getException());
                        }

                        completeListener.onComplete(task.isSuccessful());
                    }
                });
        // [END sign_in_with_email]
    }

    public void createAccount(String email, String password, CompleteListener<Boolean> completeListener, ErrorListener<Exception> errorListener) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("createUserWithEmail:success", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("createUserWithEmail:failure:", "createUserWithEmail:failure:", task.getException());
                            errorListener.onError(task.getException());
                        }

                        completeListener.onComplete(task.isSuccessful());
                    }
                });
        // [END create_user_with_email]
    }

    public void resetPassword(String email, CompleteListener<Boolean> completeListener, ErrorListener<Exception> errorListener) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        completeListener.onComplete(task.isSuccessful());
                        errorListener.onError(task.getException());
                    }
                });
    }

    public FirebaseAuth getmAuth() {
        return FirebaseAuth.getInstance();
    }

    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
