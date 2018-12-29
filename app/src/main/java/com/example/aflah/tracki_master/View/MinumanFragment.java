package com.example.aflah.tracki_master.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.Adapter.ListMinumanAdapter;
import com.example.aflah.tracki_master.Contract.MinumanContract;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.Presenter.MinumanPresenter;
import com.example.aflah.tracki_master.R;

import java.util.ArrayList;
import java.util.List;

public class MinumanFragment extends Fragment implements MinumanContract.view {

    RecyclerView recyclerView;
    TextView textViewNoMinuman;
    ListMinumanAdapter adapter;
    List<Product> listMinuman = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    private MinumanPresenter presenter = new MinumanPresenter(Injection.provideProductRepository(), this);

    public MinumanFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_minuman, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_minuman);
        textViewNoMinuman = (TextView) view.findViewById(R.id.tv_minumanTidakAda_fragmenMinuman);

        int idToko = getActivity().getIntent().getExtras().getInt("idTokoTerdekat");
        presenter.getAllMinuman(idToko);

        return view;
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
    public void showDataList(List<Product> listMinuman) {
        this.listMinuman.addAll(listMinuman);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showIfNoMinuman() {
        textViewNoMinuman.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFailure(String errMsg) {
        Toast.makeText(getContext(), "Ada error : " + errMsg, Toast.LENGTH_LONG).show();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
