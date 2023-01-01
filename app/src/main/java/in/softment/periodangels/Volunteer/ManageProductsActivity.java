package in.softment.periodangels.Volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import in.softment.periodangels.Model.VolunteerModel;
import in.softment.periodangels.R;
import in.softment.periodangels.Utils.ProgressHud;
import in.softment.periodangels.Utils.Services;

public class ManageProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);


        //Manage Products
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        VolunteerModel volunteerModel = VolunteerModel.data;

        EditText pads = findViewById(R.id.padsET);
        EditText menstrual = findViewById(R.id.menstrualET);
        EditText reusable = findViewById(R.id.reusableET);
        EditText tampons = findViewById(R.id.tamponsET);
        EditText plasticFree = findViewById(R.id.plasticFreeET);

        pads.setText(volunteerModel.getPeriodPads()+"");
        menstrual.setText(volunteerModel.getMenstrualCup()+"");
        reusable.setText(volunteerModel.getReusableProducts()+"");
        tampons.setText(volunteerModel.getTampons()+"");
        plasticFree.setText(volunteerModel.getPlasticFree()+"");



        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sPads = pads.getText().toString();
                String sMenstrual = menstrual.getText().toString();
                String sReusable = reusable.getText().toString();
                String sTampons = tampons.getText().toString();
                String sPlasticFree = plasticFree.getText().toString();

                if (sPads.isEmpty()) {
                    Services.showCenterToast(ManageProductsActivity.this,"Enter Period Products");
                    return;
                }
                if (sMenstrual.isEmpty()) {
                    Services.showCenterToast(ManageProductsActivity.this,"Enter MenstrualCup");
                    return;
                }
                if (sReusable.isEmpty()) {
                    Services.showCenterToast(ManageProductsActivity.this,"Enter Reusable Products");
                    return;
                }
                if (sTampons.isEmpty()) {
                    Services.showCenterToast(ManageProductsActivity.this,"Enter Tampons");
                    return;
                }
                if (sPlasticFree.isEmpty()) {
                    Services.showCenterToast(ManageProductsActivity.this,"Enter Plastic Free");
                }
                else {
                    ProgressHud.show(ManageProductsActivity.this,"");
                    volunteerModel.periodPads = Integer.parseInt(sPads);
                    volunteerModel.menstrualCup = Integer.parseInt(sMenstrual);
                    volunteerModel.reusableProducts = Integer.parseInt(sReusable);
                    volunteerModel.tampons = Integer.parseInt(sTampons);
                    volunteerModel.plasticFree = Integer.parseInt(sPlasticFree);

                    FirebaseFirestore.getInstance().collection("Volunteers").document(volunteerModel.getUid()).set(volunteerModel, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            ProgressHud.dialog.dismiss();
                            if (task.isSuccessful()) {
                                Services.showCenterToast(ManageProductsActivity.this,"Saved");
                            }
                            else {
                                Services.showDialog(ManageProductsActivity.this,"ERROR",task.getException().getLocalizedMessage());
                            }
                        }
                    });
                }
            }
        });

    }
}
