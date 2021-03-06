package fr.unice.polytech.dipn;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.dipn.DataBase.Incident;
import fr.unice.polytech.dipn.DataBase.IncidentViewModel;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Margoulax on 29/04/2018.
 */


public class IncidentPreviewFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView recyclerView;
    private IncidentAdapter incidentAdapter;
    private IncidentViewModel incidentViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private OnFragmentInteractionListener mListener;
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
    public static IncidentPreviewFragment newInstance(int sectionNumber) {
        IncidentPreviewFragment fragment = new IncidentPreviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        System.out.println("Loading Fragment " + sectionNumber);
        fragment.setArguments(args);
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
        List<Incident> incidentList;
        /*incidentList = Data.getInstance().getDataFilteredByLevel(getArguments().getInt(ARG_SECTION_NUMBER));
        final IncidentAdapter adapter = new IncidentAdapter(incidentList);
        RecyclerView recyclerView = view.findViewById(R.id.incidentView);
        recyclerView.setAdapter(adapter);*/
        final RecyclerView recyclerView = view.findViewById(R.id.incidentView);
        final IncidentAdapter adapter = new IncidentAdapter(getContext(), new IncidentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Incident incident) {
                if (!incident.getToShow()) {
                    Intent intent = new Intent(getContext(), IncidentDetails.class);
                    intent.putExtra("Incident", incident);
                    startActivityForResult(intent, 0);
                }
            }
        }, new IncidentAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(Incident incident) {
                System.out.println("Long click on " + incident);
                if(Instance.getInstance().getSession().equals("admin") || Instance.getInstance().getSession().equals("technic")) {
                    incident.changeShow();
                    refresh();
                }
                return incident.getAdvancement() < 3;
            }
        });
        this.incidentAdapter = adapter;

        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);

        refresh();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            refresh();
        } else {
            isVisible = false;
            //Fragment stored in cache: DO NOTHING
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void refresh() {
        this.incidentViewModel = Instance.getInstance().getIncidentViewModel();
        incidentViewModel.getAllIncident().observe(this, new Observer<List<Incident>>() {
            @Override
            public void onChanged(@Nullable final List<Incident> incidents) {
                // Update the cached copy of the words in the adapter.
                ArrayList<Incident> filtered = new ArrayList<Incident>();
                System.out.println(incidents);
                for (Incident i : incidents) {
                    if (i.getAdvancement() == getArguments().getInt(ARG_SECTION_NUMBER)) {
                        filtered.add(i);
                    }
                }
                incidentAdapter.setIncident(filtered);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

}