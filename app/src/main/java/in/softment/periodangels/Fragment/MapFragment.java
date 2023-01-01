package in.softment.periodangels.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import in.softment.periodangels.MainActivity;
import in.softment.periodangels.Model.OrganiserModel;
import in.softment.periodangels.R;
import in.softment.periodangels.ShowOrganisationDetails;


public class MapFragment extends Fragment  {


    private GoogleMap mMap;

    private Context context;
    ArrayList<OrganiserModel> organiserModels;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_map, container, false);

        organiserModels = new ArrayList<>();
       com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment)((MainActivity)context).getFragmentManager()
               .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        int position = (int)(marker.getTag());
                        Intent intent = new Intent(context, ShowOrganisationDetails.class);
                        intent.putExtra("organiserModel",organiserModels.get(position));
                        context.startActivity(intent);
                        return false;
                    }
                });

            }
        });


        return view;
    }

    public void showLocationsOnMap(ArrayList<OrganiserModel> organiserModels) {
        mMap.clear();
        if (mMap != null) {
            this.organiserModels.addAll(organiserModels);
            for (int i=0 ; i<organiserModels.size(); i++) {
                OrganiserModel organiserModel = organiserModels.get(i);
                LatLng mLocation = new LatLng(organiserModel.getLatitude(), organiserModel.getLongitude());
                MarkerOptions marker = new MarkerOptions();
                marker.position(mLocation);
                marker.title(organiserModel.getName());

                Marker m = mMap.addMarker(marker);
                m.setTag(i);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 14F));
            }

        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        ((MainActivity)context).initializeMapFragment(this);
    }
}
