package in.softment.periodangels.Volunteer;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;

import in.softment.periodangels.MainActivity;
import in.softment.periodangels.Model.OrganiserModel;
import in.softment.periodangels.Model.UserModel;
import in.softment.periodangels.Model.VolunteerModel;
import in.softment.periodangels.Organiser.OrganisationHomeActivity;
import in.softment.periodangels.Organiser.SetupOrganisation;
import in.softment.periodangels.R;
import in.softment.periodangels.SignInActivity;
import in.softment.periodangels.UserSelectionActivity;
import in.softment.periodangels.Utils.ProgressHud;
import in.softment.periodangels.Utils.Services;

public class VolunteerHomeActivity extends AppCompatActivity {

    private TextView name, phone, address;
    private CardView settings;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home);
        sharedPreferences = getSharedPreferences("PERIOD_DB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        settings = findViewById(R.id.settings);



        findViewById(R.id.periodAngelsPack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1Fr7JmBK79kJWmF6GVKYiqS9PeRCel09i?usp=sharing"));
                startActivity(browserIntent);
            }
        });

        loadUI(VolunteerModel.data);


    }



    public void loadUI(VolunteerModel volunteerModel) {




        name.setText(volunteerModel.name);
        phone.setText(volunteerModel.phoneNumber);
        address.setText(volunteerModel.fullAddress);

        findViewById(R.id.switchToOrganisation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Services.getCurrentUserData(VolunteerHomeActivity.this,FirebaseAuth.getInstance().getCurrentUser().getUid(), true);

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(VolunteerHomeActivity.this, settings);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.volunteer_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.manageproducts) {
                            startActivity(new Intent(VolunteerHomeActivity.this, ManageProductsActivity.class));
                        }
                        else if (menuItem.getItemId() == R.id.switchtouser){
                            editor.putString("userType","user").apply();
                            Services.getCurrentUserData(VolunteerHomeActivity.this, FirebaseAuth.getInstance().getCurrentUser().getUid(), true);
                        }
                        else if (menuItem.getItemId() == R.id.switchToOrganisation){
                            editor.putString("userType","organiser").apply();

                            if (UserModel.data.isOrganizer()) {
                                Services.getCurrentOrganiser(VolunteerHomeActivity.this, FirebaseAuth.getInstance().getCurrentUser().getUid(), true);
                            }
                            else {
                                Intent intent;
                                intent = new Intent(VolunteerHomeActivity.this, SetupOrganisation.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                        }
                        else if (menuItem.getItemId() == R.id.logout) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(VolunteerHomeActivity.this);
                            builder.setCancelable(false);
                            builder.setTitle("Logout");
                            builder.setMessage("Are you sure you want to logout?");
                            builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    SharedPreferences sharedPreferences = getSharedPreferences("PERIOD_DB", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putString("userType","").apply();

                                    FirebaseAuth.getInstance().signOut();

                                            Intent intent = new Intent(VolunteerHomeActivity.this, SignInActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);




                                }
                            });
                            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.show();
                        }
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });
    }
}

