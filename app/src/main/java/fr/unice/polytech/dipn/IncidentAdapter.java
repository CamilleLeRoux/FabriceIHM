package fr.unice.polytech.dipn;

import android.content.Context;
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
    private final LayoutInflater mInflater;
    private final OnItemClickListener shortListener;
    private final OnItemLongClickListener longListener;
    private Context cont;
    private Incident incident;
    private int position;

    public IncidentAdapter(Context context, OnItemClickListener shortListener, OnItemLongClickListener longListener) {
        mInflater = LayoutInflater.from(context);
        this.shortListener = shortListener;
        this.longListener = longListener;
        this.cont = context;
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

        Incident a = incidentList.get(position);
        this.position = position;


        holder.getDate().setText(a.getDate());
        holder.getTitle().setText(a.getTitle());

        switch (a.getImportance()) {
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
        if (a.getToShow()) {
            holder.getBackward().setVisibility(View.VISIBLE);
            holder.getForward().setVisibility(View.VISIBLE);
//            holder.getSuppr().setVisibility(View.VISIBLE);
            holder.getIcon().setVisibility(View.INVISIBLE);
            holder.getDate().setVisibility(View.INVISIBLE);
            holder.getTitle().setVisibility(View.INVISIBLE);
        } else {
            holder.getBackward().setVisibility(View.INVISIBLE);
            holder.getForward().setVisibility(View.INVISIBLE);
//            holder.getSuppr().setVisibility(View.INVISIBLE);
            holder.getIcon().setVisibility(View.VISIBLE);
            holder.getDate().setVisibility(View.VISIBLE);
            holder.getTitle().setVisibility(View.VISIBLE);
        }
        holder.getBackward().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Incident incident = incidentList.get(position);
                System.out.println("Try to backward " + incident);
                if (incident.getAdvancement() > 1) {
                    incident.setAdvancement(incident.getAdvancement() - 1);
                    incidentList.remove(incident);
                    incident.changeShow();
                    setIncident(incidentList);
                    System.out.println("Backward succeed");
                }
            }
        });
        holder.getForward().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Incident incident = incidentList.get(position);
                System.out.println("Try to forward " + incident);
                if (incident.getAdvancement() < 3) {
                    incident.setAdvancement(incident.getAdvancement() + 1);
                    incidentList.remove(incident);
                    incident.changeShow();
                    setIncident(incidentList);
                    System.out.println("Forward succeed");
                }

            }
        });
//        holder.getSuppr().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO suppr l'incident de la base de données
//                incident.changeShow();
//                incidentList.remove(incident);
//                setIncident(incidentList);
//            }
//        });
        holder.bind(a, shortListener, longListener);
    }

    void setIncident(List<Incident> incident) {
        incidentList = incident;
        notifyDataSetChanged();
    }
}
