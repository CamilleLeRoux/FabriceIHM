package fr.unice.polytech.dipn;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import fr.unice.polytech.dipn.DataBase.Incident;

/**
 * Created by Margoulax on 29/04/2018.
 */

public class IncidentAdapter extends RecyclerView.Adapter<IncidentViewHolder> {

    List<Incident> incidentList;

    public IncidentAdapter(List<Incident> incidentList) {
        this.incidentList = incidentList;
    }

    @Override
    public int getItemCount() {
        return incidentList.size();
    }

    @Override
    public IncidentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incident_preview, parent, false);
        IncidentViewHolder ivh = new IncidentViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(IncidentViewHolder holder, int position) {

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
    }
}
