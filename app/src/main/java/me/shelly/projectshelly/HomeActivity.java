package me.shelly.projectshelly;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import me.shelly.projectshelly.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ImageView imageView;

    private FirebaseHandler firebaseHandler;
    private FirebaseAuth auth;

    private NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initializing Firebase Values
        firebaseHandler = new FirebaseHandler();
        auth = firebaseHandler.getAuth();

        // Setting up the drawer viewer on top left

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        //Showing what item is already clicked
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // Setting username and email on navigation drawer
        navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            View headerView = navigationView.getHeaderView(0);

            FirebaseUser user = auth.getCurrentUser();
            String uid = user.getUid();

            TextView textViewEmail = headerView.findViewById(R.id.text_email_address);
            TextView textViewUsername = headerView.findViewById(R.id.text_username);

            firebaseHandler.extractUserDetails(uid, userDetails -> {
                textViewEmail.setText(auth.getCurrentUser().getEmail());
                textViewUsername.setText(userDetails.getUserName());
            });
        }

        imageView = findViewById(R.id.imageview_profile);

    }

    // Checking what item clicked and changing fragments
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_pomodoro:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimerFragment()).commit();
                break;
//            case R.id.nav_calendar:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserSettingsFragment()).commit();
//                break;
            case R.id.nav_logout:
                auth.signOut(); // Sign out user
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                Intent loginActivity = new Intent(HomeActivity.this, MainActivity.class);
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginActivity);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void onClickProfilePicture(View view) {
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfilePictureFragment());
//    }

    //Checking back pressed
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}