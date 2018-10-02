package com.example.aflah.tracki_master.View.Toko;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aflah.tracki_master.View.Toko.DetailTokoActivity;

import java.util.zip.Inflater;

import com.example.aflah.tracki_master.Adapter.RecyclerviewMakananAdapter;
import com.example.aflah.tracki_master.Model.Makanan;
import com.example.aflah.tracki_master.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MakananFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MakananFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MakananFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private List<Makanan> makananList;

    private OnFragmentInteractionListener mListener;

    public MakananFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakananFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakananFragment newInstance(String param1, String param2) {
        MakananFragment fragment = new MakananFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        makananList = new ArrayList<>();

        makananList.add(new Makanan("Makanan 1", "Rp. Makanan1", R.mipmap.logotracki));
        makananList.add(new Makanan("Makanan 2", "Rp. Makanan1", R.mipmap.logotracki));
        makananList.add(new Makanan("Makanan 3", "Rp. Makanan1", R.mipmap.logotracki));
        makananList.add(new Makanan("Makanan 4", "Rp. Makanan1", R.mipmap.logotracki));
        makananList.add(new Makanan("Makanan 5", "Rp. Makanan1", R.mipmap.logotracki));
        makananList.add(new Makanan("Makanan 6", "Rp. Makanan1", R.mipmap.logotracki));
        makananList.add(new Makanan("Makanan 7", "Rp. Makanan1", R.mipmap.logotracki));
        makananList.add(new Makanan("Makanan 8", "Rp. Makanan1", R.mipmap.logotracki));
        makananList.add(new Makanan("Makanan 9", "Rp. Makanan1", R.mipmap.logotracki));
        makananList.add(new Makanan("Makanan 0", "Rp. Makanan1", R.mipmap.logotracki));
        makananList.add(new Makanan("Makanan 10", "Rp. Makanan1", R.mipmap.logotracki));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_home,container,false);
//        Button openToko = (Button) view.findViewById(R.id.button);
//        openToko.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(),SplashScreen.class);
//                getActivity().startActivity(intent);
//            }
//        });
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_makanan, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewMakanan);
        RecyclerviewMakananAdapter recyclerviewMakananAdapter = new RecyclerviewMakananAdapter(getContext(), makananList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerviewMakananAdapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button openToko = (Button) view.findViewById(R.id.btnDetailToko);
        openToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),DetailTokoActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void onClick(View view) {

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

}
