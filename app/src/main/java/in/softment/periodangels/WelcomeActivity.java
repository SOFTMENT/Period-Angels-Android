package in.softment.periodangels;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import in.softment.periodangels.Model.UserModel;
import in.softment.periodangels.Organiser.OrganisationHomeActivity;
import in.softment.periodangels.Organiser.SetupOrganisation;
import in.softment.periodangels.Utils.Services;
import in.softment.periodangels.Volunteer.SetupVolunteerActivity;
import in.softment.periodangels.Volunteer.VolunteerHomeActivity;

public class WelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        FirebaseMessaging.getInstance().subscribeToTopic("periodangels");


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            Services.continueToLogin(WelcomeActivity.this,FirebaseAuth.getInstance().getCurrentUser().getUid());

        }
        else {
            Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


    }


}
