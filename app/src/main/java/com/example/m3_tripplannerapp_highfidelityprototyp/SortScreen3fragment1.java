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

    private static String ARG_PARAM1 = "param1";
    private static List<DataConnection> ARG_PARAM = new ArrayList<>();




    public SortScreen3fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment sortScreen3fragment1.
     */
    public static SortScreen3fragment1 newInstance(String param1) {
        SortScreen3fragment1 fragment = new SortScreen3fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static void setArgParam1(String argParam1) {
        ARG_PARAM1 = argParam1;
    }

    public static void setArgParam(List<DataConnection> argParam) {
        ARG_PARAM = argParam;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sort_screen3fragment1, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.RecyclerViewFrag1);

        if(ARG_PARAM != null && ARG_PARAM.size() > 0) {
            SortScreen3RecylerViewAdapter adapter = new SortScreen3RecylerViewAdapter(getActivity(), ARG_PARAM);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            ((sortScreen3) getActivity()).setResultFrag1Data(new DataConnection("", "", "", ""));
        } else {
            shownText = v.findViewById(R.id.screen3frag1textView);
            shownText.setText("nothing found!");
        }
        return v;
    }


}