package in.softment.periodangels.Organiser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.Map;

import in.softment.periodangels.Model.OrganiserModel;
import in.softment.periodangels.R;
import in.softment.periodangels.Utils.ProgressHud;

public class ManageAvailabilityActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_availability);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        OrganiserModel organiserModel = OrganiserModel.data;
        if (organiserModel == null || organiserModel.getUid().isEmpty()) {
            finish();
        }

        EditText mondayStartTime = findViewById(R.id.mondayStartTime);
        EditText mondayEndTime = findViewById(R.id.mondayEndTime);
        CheckBox mondayCheck = findViewById(R.id.mondayCheck);
        mondayStartTime.setText(organiserModel.getMondayStartTime());
        mondayEndTime.setText(organiserModel.getMondayEndTime());
        mondayCheck.setChecked(organiserModel.isMondayAvailable());

        EditText tuesdayStartTime = findViewById(R.id.tuesdayStartTime);
        EditText tuesdayEndTime = findViewById(R.id.tuesdayEndTime);
        CheckBox tuesdayCheck = findViewById(R.id.tuesdayCheck);
        tuesdayStartTime.setText(organiserModel.getTuesdayStartTime());
        tuesdayEndTime.setText(organiserModel.getTuesdayEndTime());
        tuesdayCheck.setChecked(organiserModel.isTuesdayAvailable());

        EditText wednesdayStartTime = findViewById(R.id.wednesdayStartTime);
        EditText wednesdayEndTime = findViewById(R.id.wednesdayEndTime);
        CheckBox wednesdayCheck = findViewById(R.id.wedbesdayCheck);
        wednesdayStartTime.setText(organiserModel.getWednesdayStartTime());
        wednesdayEndTime.setText(organiserModel.getWednesdayEndTime());
        wednesdayCheck.setChecked(organiserModel.isWednesdayAvailable());

        EditText thursdayStartTime = findViewById(R.id.thursdayStartTime);
        EditText thursdayEndTime = findViewById(R.id.thursdayEndTime);
        CheckBox thursdayCheck = findViewById(R.id.thursdayCheck);
        thursdayStartTime.setText(organiserModel.getThursdayStartTime());
        thursdayEndTime.setText(organiserModel.getThursdayEndTime());
        thursdayCheck.setChecked(organiserModel.isThursdayAvailable());

        EditText fridayStartTime = findViewById(R.id.fridayStartTime);
        EditText fridayEndTime = findViewById(R.id.fridayEndTime);
        CheckBox fridayCheck = findViewById(R.id.fridayCheck);
        fridayStartTime.setText(organiserModel.getFridayStartTime());
        fridayEndTime.setText(organiserModel.getFridayEndTime());
        fridayCheck.setChecked(organiserModel.isFridayAvailable());


        EditText saturdayStartTime = findViewById(R.id.saturdayStartTime);
        EditText saturdayEndTime = findViewById(R.id.saturdayEndTime);
        CheckBox saturdayCheck = findViewById(R.id.saturdayCheck);
        saturdayStartTime.setText(organiserModel.getSaturdayStartTime());
        saturdayEndTime.setText(organiserModel.getSaturdayEndTime());
        saturdayCheck.setChecked(organiserModel.isSaturdayAvailable());


        EditText sundayStartTime = findViewById(R.id.sundayStartTime);
        EditText sundayEndTime = findViewById(R.id.sundayEndTime);
        CheckBox sundayCheck = findViewById(R.id.sundayCheck);
        sundayStartTime.setText(organiserModel.getSundayStartTime());
        sundayEndTime.setText(organiserModel.getSundayEndTime());
        sundayCheck.setChecked(organiserModel.isSundayAvailable());

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        mondayStartTime.setFocusable(false);
        mondayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        mondayStartTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.mondayStartTime = mondayStartTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

       mondayEndTime.setFocusable(false);
       mondayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        mondayEndTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.mondayEndTime = mondayEndTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });


        tuesdayStartTime.setFocusable(false);
        tuesdayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        tuesdayStartTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.tuesdayStartTime = tuesdayStartTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        tuesdayEndTime.setFocusable(false);
        tuesdayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        tuesdayEndTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.tuesdayEndTime = tuesdayEndTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });


        wednesdayStartTime.setFocusable(false);
        wednesdayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        wednesdayStartTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.wednesdayStartTime = wednesdayStartTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        wednesdayEndTime.setFocusable(false);
        wednesdayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        wednesdayEndTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.wednesdayEndTime = wednesdayEndTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        thursdayStartTime.setFocusable(false);
        thursdayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        thursdayStartTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.thursdayStartTime = thursdayStartTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        thursdayEndTime.setFocusable(false);
        thursdayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        thursdayEndTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.thursdayEndTime = thursdayEndTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        fridayStartTime.setFocusable(false);
        fridayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        fridayStartTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.fridayStartTime = fridayStartTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        fridayEndTime.setFocusable(false);
        fridayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        fridayEndTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.fridayEndTime = fridayEndTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        saturdayStartTime.setFocusable(false);
        saturdayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        saturdayStartTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.saturdayStartTime = saturdayStartTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        saturdayEndTime.setFocusable(false);
        saturdayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        saturdayEndTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.saturdayEndTime =saturdayEndTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        sundayStartTime.setFocusable(false);
        sundayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        sundayStartTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.sundayStartTime = sundayStartTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        sundayEndTime.setFocusable(false);
        sundayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ManageAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPM = hourOfDay >= 12 ? "PM" : "AM";
                        int mHour = hourOfDay >= 13 ? (hourOfDay - 12) :  hourOfDay;
                        sundayEndTime.setText(String.format("%02d",mHour)+":"+String.format("%02d",minute)+" "+amPM);
                        organiserModel.sundayEndTime = sundayEndTime.getText().toString();
                        updateOnFirebase(organiserModel);

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        mondayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               organiserModel.mondayAvailable = isChecked;
               updateOnFirebase(organiserModel);
            }
        });

        tuesdayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                organiserModel.tuesdayAvailable = isChecked;
                updateOnFirebase(organiserModel);
            }
        });

        mondayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                organiserModel.mondayAvailable = isChecked;
                updateOnFirebase(organiserModel);
            }
        });

        tuesdayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                organiserModel.tuesdayAvailable = isChecked;
                updateOnFirebase(organiserModel);
            }
        });

        wednesdayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                organiserModel.wednesdayAvailable = isChecked;
                updateOnFirebase(organiserModel);
            }
        });

        thursdayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                organiserModel.thursdayAvailable = isChecked;
                updateOnFirebase(organiserModel);
            }
        });

        fridayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                organiserModel.fridayAvailable = isChecked;
                updateOnFirebase(organiserModel);
            }
        });

        saturdayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                organiserModel.saturdayAvailable = isChecked;
                updateOnFirebase(organiserModel);
            }
        });

        sundayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                organiserModel.sundayAvailable = isChecked;
                updateOnFirebase(organiserModel);
            }
        });


    }

    public void updateOnFirebase(OrganiserModel organiserModel) {

        FirebaseFirestore.getInstance().collection("Organisers").document(organiserModel.getUid()).set(organiserModel, SetOptions.merge());

    }

}
