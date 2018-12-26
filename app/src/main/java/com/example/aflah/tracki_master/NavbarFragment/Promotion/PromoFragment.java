package com.example.aflah.tracki_master.NavbarFragment.Promotion;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.R;

import java.util.ArrayList;
import java.util.List;

public class PromoFragment extends Fragment implements PromotionContract.view {

    RecyclerView recyclerViewPromo;
    ProgressDialog progressDialog;
    private ListPromotionAdapter listPromotionAdapter;
    List<Promotion> promotionList = new ArrayList<>();

    private PromotionPresenter presenter = new PromotionPresenter(Injection.providePromotionRepository(), this);

    private OnFragmentInteractionListener mListener;

    public PromoFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promo, container, false);
        recyclerViewPromo = view.findViewById(R.id.recycerview_promo);
        presenter.getListDataPromotions();
        initAdapter();
        return view;
    }

    public void initAdapter(){
        listPromotionAdapter = new ListPromotionAdapter(getContext(), promotionList);
        recyclerViewPromo.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewPromo.setAdapter(listPromotionAdapter);
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

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.setTitle("Harap tunggu");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showDataList(List<Promotion> promotionList) {
        this.promotionList.addAll(promotionList);
        listPromotionAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFailureMessage(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}