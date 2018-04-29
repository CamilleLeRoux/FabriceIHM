package fr.unice.polytech.dipn;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by François on 29/04/2018.
 */

public class IncidentViewHolder extends RecyclerView.ViewHolder {

    final TextView author;
    final TextView title;

    public IncidentViewHolder(View view) {
        super(view);
        this.author = view.findViewById(R.id.cardAuthor);
        this.title = view.findViewById(R.id.cardTitle);
    }

    public TextView getAuthor() {
        return author;
    }

    public TextView getTitle() {
        return title;
    }

}
