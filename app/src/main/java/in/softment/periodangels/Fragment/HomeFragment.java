package in.softment.periodangels.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import in.softment.periodangels.Adapter.OrganisationsAdapter;
import in.softment.periodangels.Interface.OrganisersListener;
import in.softment.periodangels.MainActivity;
import in.softment.periodangels.Model.OrganiserModel;
import in.softment.periodangels.NotificationActivity;
import in.softment.periodangels.R;
import in.softment.periodangels.Utils.Constants;
import in.softment.periodangels.Utils.ProgressHud;
import in.softment.periodangels.Utils.Services;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayout no_organisations_available;
    private OrganisationsAdapter organisationsAdapter;
    private ArrayList<OrganiserModel> organiserModels;
    private SearchView searchView;
    private ArrayList<OrganiserModel> tempOrganiserModel;
    private MainActivity mainActivity;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        view.findViewById(R.id.notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NotificationActivity.class));
            }
        });

        no_organisations_available = view.findViewById(R.id.no_organizers_available);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        organiserModels = new ArrayList<>();
        tempOrganiserModel = new ArrayList<>();
        organisationsAdapter = new OrganisationsAdapter(getContext(), organiserModels);
        recyclerView.setAdapter(organisationsAdapter);

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                organiserModels.clear();
                if (newText.isEmpty()) {
                    organiserModels.addAll(tempOrganiserModel);

                }
                else {
                    for (OrganiserModel model : tempOrganiserModel) {
                        if (model.getName().toLowerCase().contains(newText.toLowerCase()) || model.getFullAddress().toLowerCase().contains(newText.toLowerCase())) {
                                organiserModels.add(model);
                        }
                    }
                }
                organisationsAdapter.notifyDataSetChanged();
                return false;
            }
        });

        view.findViewById(R.id.filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.filters_layout,null);
                builder.setView(view1);
                CheckBox periodPadsCheck = view1.findViewById(R.id.sanitaryPadsCheck);
                CheckBox tamponsCheck = view1.findViewById(R.id.tamponsCheck);
                CheckBox menstrualCupCheck = view1.findViewById(R.id.menstrualCupCheck);
                CheckBox plasticCheck = view1.findViewById(R.id.plasticFreeCheck);
                CheckBox reusableCheck = view1.findViewById(R.id.reusableCheck);

                periodPadsCheck.setChecked(Constants.Filter.period_pads);
                tamponsCheck.setChecked(Constants.Filter.tampons);
                menstrualCupCheck.setChecked(Constants.Filter.menstrual_cup);
                plasticCheck.setChecked(Constants.Filter.plastic_free);
                reusableCheck.setChecked(Constants.Filter.reusable_products);



                AlertDialog alertDialog = builder.create();
                view1.findViewById(R.id.filter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Constants.Filter.period_pads = periodPadsCheck.isChecked();
                        Constants.Filter.tampons = tamponsCheck.isChecked();
                        Constants.Filter.menstrual_cup = menstrualCupCheck.isChecked();
                        Constants.Filter.plastic_free = plasticCheck.isChecked();
                        Constants.Filter.reusable_products = reusableCheck.isChecked();



                       startGetOrganisersProcess();
                        alertDialog.dismiss();

                    }
                });
                alertDialog.show();

            }
        });
        return view;
    }
    public void startGetOrganisersProcess(){

        Services.getAllOrganisers(mainActivity,new OrganisersListener() {
            @Override
            public void onCallback(ArrayList<OrganiserModel> orModels) {
                ProgressHud.dialog.dismiss();
                organiserModels.clear();
                organiserModels.addAll(orModels);
                tempOrganiserModel.addAll(orModels);
                if (organiserModels.size() > 0) {
                    no_organisations_available.setVisibility(View.GONE);
                }
                else {
                    no_organisations_available.setVisibility(View.VISIBLE);
                }

                organisationsAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = ((MainActivity)context);
        ((MainActivity)context).initializeHomeFragment(this);
    }
}
