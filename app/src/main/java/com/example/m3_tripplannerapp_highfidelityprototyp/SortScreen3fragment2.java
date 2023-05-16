package com.example.m3_tripplannerapp_highfidelityprototyp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SortScreen3fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SortScreen3fragment2 extends Fragment {

    private TextView shownText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SortScreen3fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sortScreen3fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static SortScreen3fragment2 newInstance(String param1) {
        SortScreen3fragment2 fragment = new SortScreen3fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static void setArgParam1(String argParam1) {
        ARG_PARAM1 = argParam1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sort_screen3fragment2, container, false);

        shownText = v.findViewById(R.id.screen3frag2textView);
        shownText.setText(ARG_PARAM1);
        // Inflate the layout for this fragment
        Log.d("malte2" , "2. ist this null? : " + (this == null));
        Log.d("malte2" , "2. ist this.getArguments() null? : " + (getArguments() == null));

        this.getArguments();
        return v;
    }
}