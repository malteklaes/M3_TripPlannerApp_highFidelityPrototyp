package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SortScreen3fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SortScreen3fragment1 extends Fragment {


    private TextView shownText;

    private static String Data_CONNECTION_INFO = "";
    private static List<DataConnection> DATA_CONNECTION = new ArrayList<>();


    /**
     * general constructor
     */
    public SortScreen3fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param dataConnection Parameter 1.
     * @return A new instance of fragment sortScreen3fragment1.
     */
    public static SortScreen3fragment1 newInstance(String dataConnection) {
        SortScreen3fragment1 fragment = new SortScreen3fragment1();
        Bundle args = new Bundle();
        args.putString(Data_CONNECTION_INFO, dataConnection);
        fragment.setArguments(args);
        return fragment;
    }


    public static void setDATACONNECTION(List<DataConnection> argParam) {
        DATA_CONNECTION = argParam;
    }

    /**
     * manaages main action to either control showing list-results or nothing at all
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sort_screen3fragment1, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.RecyclerViewFrag1);

        if(DATA_CONNECTION != null && DATA_CONNECTION.size() > 0) {
            SortScreen3RecylerViewAdapter adapter = new SortScreen3RecylerViewAdapter(getActivity(), DATA_CONNECTION);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            ((sortScreen3) getActivity()).setResultFrag1Data(new DataConnection("", "", "", "", false));
        } else {
            shownText = v.findViewById(R.id.screen3frag1textView);
            shownText.setText("nothing found!");
        }
        return v;
    }


}