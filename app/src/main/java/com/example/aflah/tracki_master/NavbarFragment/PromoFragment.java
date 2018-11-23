package com.example.aflah.tracki_master.NavbarFragment;

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

import com.example.aflah.tracki_master.Adapter.ListPromoAdapter;
import com.example.aflah.tracki_master.Model.Response.ResponseTokoByUID;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PromoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PromoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PromoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    HashMap<String, Store> rmdup;
    RecyclerView recyclerViewPromo;
    private ListPromoAdapter listPromoAdapter;

    private OnFragmentInteractionListener mListener;

    public PromoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PromoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PromoFragment newInstance(String param1, String param2) {
        PromoFragment fragment = new PromoFragment();
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

        View view = inflater.inflate(R.layout.fragment_promo, container, false);
        rmdup = new HashMap<>();

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseTokoByUID> getStoreByUID = apiRequest.getStoreByUID(1);
        getStoreByUID.enqueue(new Callback<ResponseTokoByUID>() {
            @Override
            public void onResponse(Call<ResponseTokoByUID> call, Response<ResponseTokoByUID> response) {

                for (Store store : response.body().getStores()){
                    if (store.getPromotions() == null){
                        store.setPromotions(new ArrayList<>());
                    }
                    rmdup.put(String.valueOf(store.getId()), store);
                }
                listPromoAdapter = new ListPromoAdapter(getContext(), rmdup);
                recyclerViewPromo = view.findViewById(R.id.recycerview_promo);
                recyclerViewPromo.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewPromo.setAdapter(listPromoAdapter);

//                listPromoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseTokoByUID> call, Throwable t) {

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
