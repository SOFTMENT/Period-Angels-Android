package in.softment.periodangels.Volunteer;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import in.softment.periodangels.Model.OrganiserModel;
import in.softment.periodangels.Model.UserModel;
import in.softment.periodangels.Model.VolunteerModel;
import in.softment.periodangels.Organiser.OrganisationHomeActivity;
import in.softment.periodangels.Organiser.SetupOrganisation;
import in.softment.periodangels.R;
import in.softment.periodangels.UserSelectionActivity;
import in.softment.periodangels.Utils.ProgressHud;
import in.softment.periodangels.Utils.Services;

public class SetupVolunteerActivity extends AppCompatActivity {

    private String apiKey = "AIzaSyBJV3l6BYFNTps4akbzU1tVtKEGAFpIJ_k";
    private AutoCompleteTextView fullAddress;
    private EditText organisationName, buildingNo, streetAddress,cityName, postalCode, phoneNumber;
    private String TAG = "SOFTMENTLOCATION";
    private LinearLayout addreesLL;
    private String country = "",state = "";
    private Double latitude = 0.0,longitude = 0.0;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_volunteer_acticity);

        if (!Places.isInitialized()) {
            Places.initialize(this,apiKey);
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(SetupVolunteerActivity.this, UserSelectionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        PlacesClient placesClient = Places.createClient(this);
        fullAddress = findViewById(R.id.fullAddress);
        fullAddress.setFocusable(false);
        fullAddress.setOnClickListener(v -> startAutocompleteIntent());

        organisationName = findViewById(R.id.name);
        buildingNo = findViewById(R.id.buildingNo);
        streetAddress = findViewById(R.id.streetAddress);
        cityName = findViewById(R.id.cityName);
        postalCode = findViewById(R.id.postalCode);
        addreesLL = findViewById(R.id.addressLL);
        phoneNumber = findViewById(R.id.businessPhoneNumber);


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

       

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName = organisationName.getText().toString().trim();
                String sBuildingNo = buildingNo.getText().toString().trim();
                String sStreetAddress = streetAddress.getText().toString().trim();
                String sCityName = cityName.getText().toString().trim();
                String sPostalCode = postalCode.getText().toString().trim();
                String sPhoneNumber = phoneNumber.getText().toString().trim();

                if (sName.isEmpty()) {
                    Services.showCenterToast(SetupVolunteerActivity.this,"Enter Organisation Name");
                    return;
                }
                if (sBuildingNo.isEmpty()) {
                    Services.showCenterToast(SetupVolunteerActivity.this,"Enter Building Number");
                    return;
                }
                if (sStreetAddress.isEmpty()) {
                    Services.showCenterToast(SetupVolunteerActivity.this,"Enter Street Address");
                    return;
                }
                if (sCityName.isEmpty()) {
                    Services.showCenterToast(SetupVolunteerActivity.this,"Enter City Name");
                    return;
                }
                if (sPostalCode.isEmpty()) {
                    Services.showCenterToast(SetupVolunteerActivity.this,"Enter Postal Code");
                    return;
                }
                if (sPhoneNumber.isEmpty()) {
                    Services.showCenterToast(SetupVolunteerActivity.this,"Enter Phone Number");
                }
                else {
                    ProgressHud.show(SetupVolunteerActivity.this,"Creating Profile ...");

                    String fulladdress = sBuildingNo+" "+ sStreetAddress+" "+sCityName+" "+state+" "+sPostalCode+" "+country;
                    VolunteerModel organiserModel = new VolunteerModel();
                    organiserModel.name  =  sName;
                    organiserModel.fullAddress = fulladdress;
                    organiserModel.emailId = UserModel.data.getEmail();
                    organiserModel.registeredAt = new Date();
                    organiserModel.uid = UserModel.data.getUid();
                    organiserModel.phoneNumber = sPhoneNumber;
                    organiserModel.latitude = latitude;
                    organiserModel.longitude = longitude;
                    organiserModel.hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(latitude, longitude));

                    WriteBatch writeBatch = FirebaseFirestore.getInstance().batch();
                    DocumentReference createOrganisationRef = FirebaseFirestore.getInstance().collection("Volunteers").document(organiserModel.getUid());
                    writeBatch.set(createOrganisationRef,organiserModel);

                    DocumentReference updateUserRef = FirebaseFirestore.getInstance().collection("Users").document(organiserModel.getUid());
                    UserModel.data.setVolunteer(true);
                    writeBatch.set(updateUserRef,UserModel.data, SetOptions.merge());

                    writeBatch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            ProgressHud.dialog.dismiss();;
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(SetupVolunteerActivity.this, VolunteerHomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Services.showDialog(SetupVolunteerActivity.this,"ERROR",task.getException().getMessage());
                            }
                        }
                    });

                }


            }
        });
    }

    private void startAutocompleteIntent() {

        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS_COMPONENTS,
                Place.Field.LAT_LNG, Place.Field.VIEWPORT);

        // Build the autocomplete intent with field, country, and type filters applied
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setTypeFilter(TypeFilter.ADDRESS)
                .build(this);
        startAutocomplete.launch(intent);
    }


    private final ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (ActivityResultCallback<ActivityResult>) result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Place place = Autocomplete.getPlaceFromIntent(intent);

                        fillInAddress(place);
                    }

                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    // The user canceled the operation.

                }
            });

    private void fillInAddress(Place place) {

        addreesLL.setVisibility(View.VISIBLE);
        latitude = Objects.requireNonNull(place.getLatLng()).latitude;
        longitude = place.getLatLng().longitude;


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
                mMap.addMarker(new
                        MarkerOptions().position(place.getLatLng()).title("Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            }
        });

        AddressComponents components = place.getAddressComponents();
        StringBuilder address1 = new StringBuilder();
        // Get each component of the address from the place details,
        // and then fill-in the corresponding field on the form.
        // Possible AddressComponent types are documented at https://goo.gle/32SJPM1
        if (components != null) {
            for (AddressComponent component : components.asList()) {
                String type = component.getTypes().get(0);
                switch (type) {
                    case "street_number": {
                        address1.insert(0, component.getName());
                        break;
                    }
                    case "route": {
                        address1.append(" ");
                        address1.append(component.getShortName());
                        break;
                    }
                    case "postal_code":
                        postalCode.setText(component.getName());
                        postalCode.setFocusable(false);
                        break;


                    case "locality":
                        cityName.setText(component.getName());
                        cityName.setFocusable(false);
                        break;

                    case "administrative_area_level_1": {
                        state = component.getName();

                        break;
                    }

                    case "country":
                        country = component.getName();
                        break;
                }
            }
            fullAddress.setText(address1);
        }



    }

}
