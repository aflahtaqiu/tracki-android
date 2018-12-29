package com.example.aflah.tracki_master.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.Adapter.ListMakananAdapter;
import com.example.aflah.tracki_master.Contract.MakananContract;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.Presenter.MakananPresenter;
import com.example.aflah.tracki_master.R;

import java.util.ArrayList;
import java.util.List;

public class MakananFragment extends Fragment implements MakananContract.view {

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    TextView textViewNoMakanan;
    List<Product> listMakanan = new ArrayList<>();
    ListMakananAdapter adapter;

    private MakananPresenter presenter = new MakananPresenter(Injection.provideProductRepository(), this);

    public MakananFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_makanan, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_makanan);
        textViewNoMakanan = (TextView) view.findViewById(R.id.tv_makananTidakAda_fragmenMakanan);

        int idToko = getActivity().getIntent().getExtras().getInt("idTokoTerdekat");

        presenter.getAllMakanan(idToko);
        initAdapter();

        return view;
    }

    private void initAdapter() {
        adapter = new ListMakananAdapter(getContext(), listMakanan);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.smoothScrollToPosition(0);
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
    public void showDataList(List<Product> listMakanan) {
        this.listMakanan.addAll(listMakanan);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showIfNoMakanan() {
        textViewNoMakanan.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFailure(String errMsg) {
        Toast.makeText(getContext(), "Ada error :" + errMsg, Toast.LENGTH_LONG).show();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
