package in.softment.periodangels.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import in.softment.periodangels.BuildConfig;
import in.softment.periodangels.ContactUsActivity;
import in.softment.periodangels.MainActivity;
import in.softment.periodangels.Model.UserModel;
import in.softment.periodangels.NotificationActivity;
import in.softment.periodangels.Organiser.OrganisationHomeActivity;
import in.softment.periodangels.Organiser.SetupOrganisation;
import in.softment.periodangels.R;
import in.softment.periodangels.SignInActivity;
import in.softment.periodangels.UserSelectionActivity;
import in.softment.periodangels.Utils.ProgressHud;
import in.softment.periodangels.Utils.Services;
import in.softment.periodangels.Volunteer.SetupVolunteerActivity;
import in.softment.periodangels.Volunteer.VolunteerHomeActivity;


public class ProfileFragment extends Fragment {


    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PERIOD_DB", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        TextView name = view.findViewById(R.id.name);
        TextView email = view.findViewById(R.id.email);

        name.setText(UserModel.data.getFullName());
        email.setText(UserModel.data.getEmail());

        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.logout();

            }
        });

        //Organisation
        view.findViewById(R.id.organisation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("userType","organiser").apply();

                if (UserModel.data.isOrganizer()) {
                    Services.getCurrentOrganiser(getContext(), FirebaseAuth.getInstance().getCurrentUser().getUid(),true);
                }
                else {
                    Intent intent;
                    intent = new Intent(getContext(), SetupOrganisation.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


            }
        });

        view.findViewById(R.id.shareApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Period Angels");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        view.findViewById(R.id.volunteer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("userType","volunteer").apply();

                if (UserModel.data.isVolunteer()) {
                  Services.getCurrentVolunteers(getContext(), FirebaseAuth.getInstance().getCurrentUser().getUid(),true);
                }
                else {
                    Intent intent;
                    intent = new Intent(getContext(), SetupVolunteerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });

        //Notification
        view.findViewById(R.id.notification).
                setOnClickListener(view12 -> startActivity(new Intent(getContext(), NotificationActivity.class)));

        view.findViewById(R.id.rateApp).setOnClickListener(view13 -> {
            Uri uri = Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID);
            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(myAppLinkToMarket);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), " unable to find market app", Toast.LENGTH_LONG).show();
            }
        });


        view.findViewById(R.id.privacy_policy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://softment.in/privacy-policy/"));
                startActivity(browserIntent);
            }
        });




        view.findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ContactUsActivity.class));
            }
        });

        //VersionCode
        TextView version = view.findViewById(R.id.version);
        String versionName = BuildConfig.VERSION_NAME;
        version.setText(versionName);

        //DeleteAccount
        view.findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                builder.setCancelable(false);
                builder.setTitle("Delete Account?");
                builder.setMessage("Are you sure you want to delete account?");
                builder.setNegativeButton("Delete Account", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProgressHud.show(getContext(),"Deleting ...");
                        FirebaseFirestore.getInstance().collection("Users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                ProgressHud.dialog.dismiss();
                                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            editor.putString("userType","").apply();
                                            startActivity(new Intent(getContext(), SignInActivity.class));
                                            requireActivity().finish();
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                                            builder.setTitle("LOGIN AGAIN");
                                            builder.setMessage("Please login and try again delete account.");
                                            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    startActivity(new Intent(getContext(), SignInActivity.class));
                                                    requireActivity().finish();
                                                }
                                            });

                                            builder.show();

                                        }
                                    }
                                });
                            }
                        });

                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();


            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = ((MainActivity)context);
    }
}
