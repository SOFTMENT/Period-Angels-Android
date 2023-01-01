package in.softment.periodangels.Organiser;

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
import android.os.Bundle;
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
import in.softment.periodangels.R;
import in.softment.periodangels.SignInActivity;
import in.softment.periodangels.UserSelectionActivity;
import in.softment.periodangels.Utils.ProgressHud;
import in.softment.periodangels.Utils.Services;
import in.softment.periodangels.Volunteer.SetupVolunteerActivity;
import in.softment.periodangels.Volunteer.VolunteerHomeActivity;

public class OrganisationHomeActivity extends AppCompatActivity {

    private TextView name, phone, address, typeName;
    private ImageView typeImage;
    private CardView settings;
    private CheckBox sanitaryCheck, tamponsCheck, menstrualCheck, plasticCheck, reusableCheck;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation_home);
        sharedPreferences = getSharedPreferences("PERIOD_DB", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        typeName = findViewById(R.id.typeName);
        typeImage = findViewById(R.id.typeImage);
        settings = findViewById(R.id.settings);

        sanitaryCheck = findViewById(R.id.sanitarypadsCheck);
        tamponsCheck = findViewById(R.id.tamponsCheck);
        menstrualCheck = findViewById(R.id.menstrualcupCheck);
        plasticCheck = findViewById(R.id.plasticfreeCheck);
        reusableCheck = findViewById(R.id.reusableCheck);

        loadUI(OrganiserModel.data);

    }



    public void loadUI(OrganiserModel organiserModel) {

        tamponsCheck.setChecked(false);
        menstrualCheck.setChecked(false);
        sanitaryCheck.setChecked(false);
        plasticCheck.setChecked(false);
        reusableCheck.setChecked(false);
        ArrayList<String> products = organiserModel.getProducts();

        for(String product : products) {
            if(product.equalsIgnoreCase("tampons")) {
                tamponsCheck.setChecked(true);
            }
            if(product.equalsIgnoreCase("menstrual")) {
                menstrualCheck.setChecked(true);
            }
            if(product.equalsIgnoreCase("sanitary")) {
                sanitaryCheck.setChecked(true);
            }
            if(product.equalsIgnoreCase("plastic")) {
                plasticCheck.setChecked(true);
            }
            if (product.equalsIgnoreCase("reusable")) {
                reusableCheck.setChecked(true);
            }

        }


        name.setText(organiserModel.name);
        phone.setText(organiserModel.phoneNumber);
        address.setText(organiserModel.fullAddress);
        if (organiserModel.getType().equalsIgnoreCase("public")) {
            typeName.setText("Public");
            typeImage.setImageResource(R.drawable.building);
        }
        else if (organiserModel.getType().equalsIgnoreCase("business")) {
            typeName.setText("Business");
            typeImage.setImageResource(R.drawable.shop);
        }
        else if (organiserModel.getType().equalsIgnoreCase("charity")) {

            typeName.setText("Charity");
            typeImage.setImageResource(R.drawable.charity);
        }
        else if (organiserModel.getType().equalsIgnoreCase("other")) {

            typeName.setText("Other");
            typeImage.setImageResource(R.drawable.other);
        }


        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressHud.show(OrganisationHomeActivity.this,"");
                ArrayList<String> list = new ArrayList<>();
                if (sanitaryCheck.isChecked()) {
                    list.add("sanitary");
                }
                if (tamponsCheck.isChecked()) {
                    list.add("tampons");
                }
                if (menstrualCheck.isChecked()) {
                    list.add("menstrual");
                }
                if (plasticCheck.isChecked()) {
                    list.add("plastic");
                }
                if (reusableCheck.isChecked()) {
                    list.add("reusable");
                }

                OrganiserModel.data.setProducts(list);

                FirebaseFirestore.getInstance().collection("Organisers").document(OrganiserModel.data.getUid()).set(OrganiserModel.data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                            ProgressHud.dialog.dismiss();
                            if (task.isSuccessful()) {
                                Services.showCenterToast(OrganisationHomeActivity.this,"Saved");
                            }
                            else {
                                Services.showDialog(OrganisationHomeActivity.this,"ERROR",task.getException().getLocalizedMessage());
                            }
                    }
                });
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(OrganisationHomeActivity.this, settings);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.organiser_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.availability) {
                            startActivity(new Intent(OrganisationHomeActivity.this, ManageAvailabilityActivity.class));
                        }
                        else if (menuItem.getItemId() == R.id.switchtouser){
                            editor.putString("userType","user").apply();

                            Services.getCurrentUserData(OrganisationHomeActivity.this, FirebaseAuth.getInstance().getCurrentUser().getUid(), true);
                        }
                        else if (menuItem.getItemId() == R.id.switchtovolunteer){
                            editor.putString("userType","volunteer").apply();

                            if (UserModel.data.isVolunteer()) {
                                Services.getCurrentVolunteers(OrganisationHomeActivity.this, FirebaseAuth.getInstance().getCurrentUser().getUid(), true);
                            }
                            else {
                                Intent intent;
                                intent = new Intent(OrganisationHomeActivity.this, SetupVolunteerActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                        }
                        else if (menuItem.getItemId() == R.id.logout) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(OrganisationHomeActivity.this);
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
                                    finish();
                                    Intent intent = new Intent(OrganisationHomeActivity.this, SignInActivity.class);
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

