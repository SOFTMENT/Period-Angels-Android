package in.softment.periodangels.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.softment.periodangels.Model.OrganiserModel;
import in.softment.periodangels.R;
import in.softment.periodangels.ShowOrganisationDetails;
import in.softment.periodangels.Utils.Services;

public class OrganisationsAdapter extends RecyclerView.Adapter<OrganisationsAdapter.ViewHolder> {

    Context context;
    ArrayList<OrganiserModel> organiserModels;
    public OrganisationsAdapter(Context context, ArrayList<OrganiserModel> organiserModels) {
        this.context = context;
        this.organiserModels = organiserModels;
    }

    @NonNull
    @Override
    public OrganisationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.organisation_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrganisationsAdapter.ViewHolder holder, int position) {
            holder.setIsRecyclable(false);
            OrganiserModel organiserModel = organiserModels.get(position);
            holder.name.setText(organiserModel.getName());
            if (organiserModel.getType().equalsIgnoreCase("public")) {
            holder.typeName.setText("Public");
            holder.typeImage.setImageResource(R.drawable.building);
        }
        else if (organiserModel.getType().equalsIgnoreCase("business")) {
                holder.typeName.setText("Business");
                holder.typeImage.setImageResource(R.drawable.shop);
        }
        else if (organiserModel.getType().equalsIgnoreCase("charity")) {
                holder.typeName.setText("Charity");
                holder.typeImage.setImageResource(R.drawable.charity);
        }
            else if (organiserModel.getType().equalsIgnoreCase("other")) {
                holder.typeName.setText("Other");
                holder.typeImage.setImageResource(R.drawable.charity);
            }




            for (String product : organiserModel.getProducts()) {
                if (product.equalsIgnoreCase("sanitary")) {
                    holder.periodPadsLL.setVisibility(View.VISIBLE);
                }
                else if (product.equalsIgnoreCase("tampons")) {
                    holder.tamponsLL.setVisibility(View.VISIBLE);
                }
                else if (product.equalsIgnoreCase("menstrual")) {
                    holder.menstrualLL.setVisibility(View.VISIBLE);
                }
                else if (product.equalsIgnoreCase("plastic")) {
                    holder.plasticFreeLL.setVisibility(View.VISIBLE);
                }
                else if (product.equalsIgnoreCase("reusable")) {
                    holder.reusableLL.setVisibility(View.VISIBLE);
                }
            }
            float distanceBetween = Services.getDistanceBetween(organiserModel.getLatitude(), organiserModel.getLongitude());
            float km = distanceBetween / 1000;
             holder.distance.setText(String.format("%.2f",km)+" KM");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowOrganisationDetails.class);
                    intent.putExtra("organiserModel",organiserModel);
                    context.startActivity(intent);
                }
            });



    }

    @Override
    public int getItemCount() {
        return organiserModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, typeName, distance;
        ImageView typeImage;
        LinearLayout periodPadsLL, tamponsLL, plasticFreeLL, menstrualLL, reusableLL;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            typeName = itemView.findViewById(R.id.typeName);
            typeImage = itemView.findViewById(R.id.typeImage);
            distance = itemView.findViewById(R.id.distance);
            periodPadsLL = itemView.findViewById(R.id.periodPadsLL);
            tamponsLL = itemView.findViewById(R.id.tamponsLL);
            plasticFreeLL = itemView.findViewById(R.id.plasticFreeLL);
            menstrualLL = itemView.findViewById(R.id.menstrualLL);
            reusableLL = itemView.findViewById(R.id.reusableLL);
        }
    }
}
