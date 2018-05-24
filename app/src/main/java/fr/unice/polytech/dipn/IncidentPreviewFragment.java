package fr.unice.polytech.dipn;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.dipn.DataBase.Incident;
import fr.unice.polytech.dipn.DataBase.IncidentViewModel;

import static android.app.Activity.RESULT_OK;
import static okhttp3.internal.Internal.instance;

/**
 * Created by Margoulax on 29/04/2018.
 */


public class IncidentPreviewFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView recyclerView;
    private IncidentAdapter incidentAdapter;
    private static IncidentViewModel incidentViewModel;
    private List<Incident> articleList;
    IncidentAdapter mAdapter;

   static IncidentPreviewFragment fragment;

    private static String INCIDENT_VIEW_MODEL = "ivm";
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    protected boolean isVisible;

    public IncidentPreviewFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsGridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncidentPreviewFragment newInstance(int sectionNumber, IncidentViewModel ivm) {
        fragment = new IncidentPreviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable(INCIDENT_VIEW_MODEL, ivm);
        System.out.println("Loading Fragment "+sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public static IncidentPreviewFragment getInstance() {
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_incident_recycler, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("incident");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot snapshot) {

                articleList = new ArrayList<>();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Incident climate = postSnapshot.getValue(Incident.class);

                    articleList.add(climate);

                }



                mAdapter = new IncidentAdapter(articleList);

                setupRecyclerView();



            }





            @Override

            public void onCancelled(DatabaseError firebaseError) {

                System.out.println("firebase error :" + firebaseError.getDetails());

            }

        });

    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.incidentView);



        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(mAdapter);
    }

    public List<Incident> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Incident> articleList) {
        this.articleList = articleList;
    }
}