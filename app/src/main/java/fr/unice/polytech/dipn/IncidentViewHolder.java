package fr.unice.polytech.dipn;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by François on 29/04/2018.
 */

public class IncidentViewHolder extends RecyclerView.ViewHolder {

    final TextView date;
    final TextView title;
    final ImageView icon;

    public IncidentViewHolder(View view) {
        super(view);
        this.date = view.findViewById(R.id.cardDate);
        this.title = view.findViewById(R.id.cardTitle);
        this.icon = view.findViewById(R.id.icon);
    }

    public TextView getDate() {
        return date;
    }

    public TextView getTitle() {
        return title;
    }

    public ImageView getIcon() {
        return icon;
    }

}
