package com.example.aflah.tracki_master.DetailTokoFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aflah.tracki_master.Adapter.ListMinumanAdapter;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MinumanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MinumanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MinumanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    TextView textViewNoMinuman;

    private OnFragmentInteractionListener mListener;

    public MinumanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MinumanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MinumanFragment newInstance(String param1, String param2) {
        MinumanFragment fragment = new MinumanFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_minuman, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_minuman);
        textViewNoMinuman = (TextView) view.findViewById(R.id.tv_minumanTidakAda_fragmenMinuman);

        int idToko = getActivity().getIntent().getExtras().getInt("idTokoTerdekat");

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseDetailToko> getData = apiRequest.getStoreByID(idToko);
        getData.enqueue(new Callback<ResponseDetailToko>() {
            @Override
            public void onResponse(Call<ResponseDetailToko> call, Response<ResponseDetailToko> response) {

;
                int temp=0;
                for (int i = 0;i<response.body().getStore().getProducts().size();i++){
                    if (response.body().getStore().getProducts().get(i).getCategory().getId() == 2){
                        Log.v("idProductMInuman",response.body().getStore().getProducts().get(i).toString() + " " +response.body().getStore().getProducts().get(i).getCategory().toString());
                        temp =1;
                    }
                }
                if (temp==1){
                    textViewNoMinuman.setVisibility(View.GONE);
                }

                recyclerView.setAdapter(new ListMinumanAdapter(getContext(), response.body().getStore()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<ResponseDetailToko> call, Throwable t) {
                Log.i("RETRO ", " onFailure " + t.getMessage());
            }
        });

        return view;
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
