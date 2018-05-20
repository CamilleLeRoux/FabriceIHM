package fr.unice.polytech.dipn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.unice.polytech.dipn.DataBase.Incident;

/**
 * Created by Margoulax on 29/04/2018.
 */

public class IncidentAdapter extends RecyclerView.Adapter<IncidentViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Incident incident);
    }
    public interface OnItemLongClickListener {
        boolean onItemLongClick(Incident incident);
    }

    private List<Incident> incidentList;

    private Context cont;

    public IncidentAdapter(List<Incident> incidents ) {
        incidentList = incidents;

    }

    @Override
    public int getItemCount() {
        if (incidentList != null)
            return incidentList.size();
        else return 0;
    }

    @Override
    public IncidentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incident_preview, parent, false);
        IncidentViewHolder ivh = new IncidentViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(IncidentViewHolder holder, final int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            Intent intent = new Intent(v.getContext(), IncidentDetails.class);

            intent.putExtra("Incident", incidentList.get(position));

            v.getContext().startActivity(intent);            }

        });

        Incident a = incidentList.get(position);

        holder.getDate().setText(a.getDate());
        holder.getTitle().setText(a.getTitle());

        switch(a.getImportance()) {
            case 1:
                holder.getIcon().setImageResource(R.drawable.emergency1);
                break;
            case 2:
                holder.getIcon().setImageResource(R.drawable.emergency2);
                break;
            case 3:
                holder.getIcon().setImageResource(R.drawable.emergency3);
                break;
        }
        //holder.bind(a, shortListener, longListener);
    }

    void setIncident(List<Incident> incident){
        incidentList = incident;
        notifyDataSetChanged();
    }
}
