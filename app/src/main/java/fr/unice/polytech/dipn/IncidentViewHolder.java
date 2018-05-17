package fr.unice.polytech.dipn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fr.unice.polytech.dipn.DataBase.Incident;

/**
 * Created by Margoulax on 29/04/2018.
 */

public class IncidentViewHolder extends RecyclerView.ViewHolder {

    final TextView date;
    final TextView title;
    final ImageView icon;
    final ImageView backward;
    final ImageView forward;
    final ImageView suppr;

    public IncidentViewHolder(View view) {
        super(view);
        this.date = view.findViewById(R.id.cardDate);
        this.title = view.findViewById(R.id.cardTitle);
        this.icon = view.findViewById(R.id.icon);
        this.backward = view.findViewById(R.id.backwardButton);
        this.forward = view.findViewById(R.id.forwardButton);
        this.suppr = view.findViewById(R.id.supprButton);
    }

    public TextView getDate() {
        return date;
    }

    public void bind(final Incident incident, final IncidentAdapter.OnItemClickListener shortListener, final IncidentAdapter.OnItemLongClickListener longListener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortListener.onItemClick(incident);
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return longListener.onItemLongClick(incident);
            }
        });
    }

    public TextView getTitle() {
        return title;
    }

    public ImageView getIcon() {
        return icon;
    }

    public ImageView getBackward() {
        return backward;
    }

    public ImageView getForward() {
        return forward;
    }

    public ImageView getSuppr() {
        return suppr;
    }
}
