package in.softment.periodangels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import in.softment.periodangels.Model.UserModel;
import in.softment.periodangels.Organiser.OrganisationHomeActivity;
import in.softment.periodangels.Organiser.SetupOrganisation;
import in.softment.periodangels.Utils.Services;
import in.softment.periodangels.Volunteer.SetupVolunteerActivity;
import in.softment.periodangels.Volunteer.VolunteerHomeActivity;

public class UserSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);
        SharedPreferences sharedPreferences = getSharedPreferences("PERIOD_DB", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        findViewById(R.id.user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("userType","user").apply();
                Intent intent = new Intent(UserSelectionActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        findViewById(R.id.volunteer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("userType","volunteer").apply();

                if (UserModel.data.isVolunteer()) {
                    Services.getCurrentVolunteers(UserSelectionActivity.this, FirebaseAuth.getInstance().getCurrentUser().getUid(), true);
                }
                else {
                    Intent intent;
                    intent = new Intent(UserSelectionActivity.this, SetupVolunteerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });

        findViewById(R.id.organisation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("userType","organiser").apply();

                if (UserModel.data.isOrganizer()) {
                    Services.getCurrentOrganiser(UserSelectionActivity.this, FirebaseAuth.getInstance().getCurrentUser().getUid(), true);
                }
                else {
                    Intent intent;
                    intent = new Intent(UserSelectionActivity.this, SetupOrganisation.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


            }
        });
    }
}
