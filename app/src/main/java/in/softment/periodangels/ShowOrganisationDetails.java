package in.softment.periodangels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import in.softment.periodangels.Model.OrganiserModel;
import in.softment.periodangels.Utils.Services;

public class ShowOrganisationDetails extends AppCompatActivity {
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_organisation_details);

        OrganiserModel organiserModel = (OrganiserModel) getIntent().getSerializableExtra("organiserModel");
        if (organiserModel == null) {
            finish();
        }
        TextView name = findViewById(R.id.name);
        TextView phone = findViewById(R.id.phone);
        TextView address = findViewById(R.id.address);
        TextView distance = findViewById(R.id.distance);
        TextView typeName = findViewById(R.id.typeName);
        TextView mondayTime = findViewById(R.id.mondayTime);
        TextView tuesdayTime = findViewById(R.id.tuesdayTime);
        TextView wednesdayTime = findViewById(R.id.wednesdayTime);
        TextView thursdayTime = findViewById(R.id.thursdayTime);
        TextView fridayTime = findViewById(R.id.fridayTime);
        TextView saturdayTime = findViewById(R.id.saturdayTime);
        TextView sundayTime = findViewById(R.id.sundayTime);

        ImageView typeImage = findViewById(R.id.typeImage);
        LinearLayout periodPadsLL = findViewById(R.id.periodPadsLL);
        LinearLayout tamponsLL = findViewById(R.id.tamponsLL);
        LinearLayout plasticFreeLL = findViewById(R.id.plasticLL);
        LinearLayout menstrualLL = findViewById(R.id.menstrualLL);
        LinearLayout reusableLL = findViewById(R.id.reusableLL);

        name.setText(organiserModel.getName());
        phone.setText(organiserModel.getPhoneNumber());
        address.setText(organiserModel.getFullAddress());
        float distanceBetween = Services.getDistanceBetween(organiserModel.getLatitude(), organiserModel.getLongitude());
        float km = distanceBetween / 1000;
        distance.setText(String.format("%.2f",km)+" KM");

        if (organiserModel.mondayAvailable) {
            mondayTime.setText(organiserModel.getMondayStartTime()+" To "+organiserModel.getMondayEndTime());
        }
        else {
            mondayTime.setText("Closed");
        }
        if (organiserModel.tuesdayAvailable) {
            tuesdayTime.setText(organiserModel.getTuesdayStartTime()+" To "+organiserModel.getTuesdayEndTime());
        }
        else {
            tuesdayTime.setText("Closed");
        }
        if (organiserModel.wednesdayAvailable) {
            wednesdayTime.setText(organiserModel.getWednesdayStartTime()+" To "+organiserModel.getWednesdayEndTime());
        }
        else {
            wednesdayTime.setText("Closed");
        }
        if (organiserModel.thursdayAvailable) {
            thursdayTime.setText(organiserModel.getThursdayStartTime()+" To "+organiserModel.getThursdayEndTime());
        }
        else {
            thursdayTime.setText("Closed");
        }
        if (organiserModel.fridayAvailable) {
            fridayTime.setText(organiserModel.getFridayStartTime()+" To "+organiserModel.getFridayEndTime());
        }
        else {
            fridayTime.setText("Closed");
        }
        if (organiserModel.saturdayAvailable) {
            saturdayTime.setText(organiserModel.getSaturdayStartTime()+" To "+organiserModel.getSaturdayEndTime());
        }
        else {
            saturdayTime.setText("Closed");
        }
        if (organiserModel.sundayAvailable) {
            sundayTime.setText(organiserModel.getSundayStartTime()+" To "+organiserModel.getSundayEndTime());
        }
        else {
            sundayTime.setText("Closed");
        }

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

        for (String product : organiserModel.getProducts()) {
            if (product.equalsIgnoreCase("sanitary")) {
                periodPadsLL.setVisibility(View.VISIBLE);
            }
            else if (product.equalsIgnoreCase("tampons")) {
                tamponsLL.setVisibility(View.VISIBLE);
            }
            else if (product.equalsIgnoreCase("menstrual")) {
                menstrualLL.setVisibility(View.VISIBLE);
            }
            else if (product.equalsIgnoreCase("plastic")) {
                plasticFreeLL.setVisibility(View.VISIBLE);
            }
            else if (product.equalsIgnoreCase("reusable")) {
                reusableLL.setVisibility(View.VISIBLE);
            }
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;

                LatLng mLocation = new LatLng(organiserModel.getLatitude(), organiserModel.getLongitude());

                MarkerOptions marker = new MarkerOptions();
                marker.position(mLocation);
                mMap.addMarker(marker);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 16F));

            }
        });

        //BACK
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
