package me.shelly.projectshelly;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class FirebaseHandler implements Executor {

    // [START declare_auth]
    private FirebaseAuth mAuth;
    public FirebaseHandler() {
        mAuth = FirebaseAuth.getInstance();
    }
    // [END declare_auth]

    public void signIn(String email, String password, Listener<Boolean> completeListener, Listener<Exception> errorListener) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.i("signInWithEmail:success", "signInWithEmail:success: " + task.isSuccessful());
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e("signInWithEmail:failure:", "signInWithEmail:failure:", task.getException());
                        errorListener.onListen(task.getException());
                    }

                    completeListener.onListen(task.isSuccessful());
                });
        // [END sign_in_with_email]
    }

    public void createAccount(String email, String password, Listener<Boolean> completeListener, Listener<Exception> errorListener) {
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
                            errorListener.onListen(task.getException());
                        }

                        completeListener.onListen(task.isSuccessful());
                    }
                });
        // [END create_user_with_email]
    }

    public void resetPassword(String email, Listener<Boolean> completeListener, Listener<Exception> errorListener) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        completeListener.onListen(task.isSuccessful());
                        errorListener.onListen(task.getException());
                    }
                });
    }

    public void extractUserDetails(String uid, Listener<ReadWriteUserDetails> listener) {
        ReadWriteUserDetails userDetails = new ReadWriteUserDetails();

        // Extract the details from user
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("RegisteredUsers");
        referenceProfile.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
                    userDetails.setUserName(readUserDetails.getUserName());
                    userDetails.setFavColor(readUserDetails.getFavColor());
                    userDetails.setBirthday(readUserDetails.getBirthday());

                    listener.onListen(userDetails);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }

}
