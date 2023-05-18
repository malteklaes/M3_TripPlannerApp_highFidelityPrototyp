package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.os.Bundle;

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
 * Use the {@link SortScreen3fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SortScreen3fragment2 extends Fragment {

    private TextView shownText;

    private static String Data_CONNECTION_INFO = "";
    private static List<DataConnection> DATA_CONNECTION = new ArrayList<>();
    private static boolean isOneWay = false;


    public SortScreen3fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param dataConnection Parameter 1.
     * @return A new instance of fragment sortScreen3fragment2.
     */
    public static SortScreen3fragment2 newInstance(String dataConnection) {
        SortScreen3fragment2 fragment = new SortScreen3fragment2();
        Bundle args = new Bundle();
        args.putString(Data_CONNECTION_INFO, dataConnection);
        fragment.setArguments(args);
        return fragment;
    }



    public static void setDATACONNECTION(List<DataConnection> argParam) {
        DATA_CONNECTION = argParam;
    }

    public static void setIsOneWay(boolean isOneWay) {
        SortScreen3fragment2.isOneWay = isOneWay;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sort_screen3fragment2, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.RecyclerViewFrag2);

        if(DATA_CONNECTION != null && DATA_CONNECTION.size() > 0) {
            SortScreen3RecylerViewAdapter adapter = new SortScreen3RecylerViewAdapter(getActivity(), DATA_CONNECTION);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            ((sortScreen3) getActivity()).setResultFrag2Data(new DataConnection("", "", "", ""));
        } else {
            shownText = v.findViewById(R.id.screen3frag2textView);
            if(isOneWay){
                shownText.setText("only one way was chosen!");
            } else {
                shownText.setText("nothing found!");
            }
        }
        return v;
    }
}