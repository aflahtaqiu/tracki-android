package com.example.aflah.tracki_master.NavbarFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aflah.tracki_master.Adapter.ListPromotionAdapter;
import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.Response.ResponseRedeemPromotion;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoFragment extends Fragment {

    RecyclerView recyclerViewPromo;
    private ListPromotionAdapter listPromotionAdapter;
    List<Promotion> promotionList;

    private OnFragmentInteractionListener mListener;

    public PromoFragment() {
    }

    public static PromoFragment newInstance(String param1, String param2) {
        PromoFragment fragment = new PromoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_promo, container, false);
        promotionList = new ArrayList<>();

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseRedeemPromotion> getAllPromo = apiRequest.getPromotions();
        getAllPromo.enqueue(new Callback<ResponseRedeemPromotion>() {
            @Override
            public void onResponse(Call<ResponseRedeemPromotion> call, Response<ResponseRedeemPromotion> response) {
                for (Promotion promotion : response.body().getPromotions()){
                    promotionList.add(promotion);
                }
                listPromotionAdapter = new ListPromotionAdapter(getContext(), promotionList);
                recyclerViewPromo = view.findViewById(R.id.recycerview_promo);
                recyclerViewPromo.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewPromo.setAdapter(listPromotionAdapter);
            }

            @Override
            public void onFailure(Call<ResponseRedeemPromotion> call, Throwable t) {

            }
        });
        return view;
    }

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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
