package com.example.aflah.tracki_master.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.example.aflah.tracki_master.Adapter.DetailTokoAdapter;
import com.example.aflah.tracki_master.Contract.DetailTokoContract;
import com.example.aflah.tracki_master.Presenter.DetailTokoPresenter;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailTokoFragment extends Fragment implements DetailTokoContract.view {

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    String userToken;
    SharedPreferences sharedPreferences;
    SweetAlertDialog sweetAlertDialogProgress;
    DetailTokoAdapter adapter;
    Store store = new Store();

    private DetailTokoPresenter presenter = new DetailTokoPresenter(Injection.provideStoreRepository(), this);

    public DetailTokoFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        userToken = sharedPreferences.getString("tokenLogin", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_toko, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_detailtoko);
        TextView textView = (TextView) view.findViewById(R.id.tv_phoneToko_detailToko);

        int idToko = getActivity().getIntent().getExtras().getInt("idTokoTerdekat");
        presenter.getStoreDetail(idToko);

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
    public void showProgress() {
        sweetAlertDialogProgress = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialogProgress.getProgressHelper().setBarColor(Color.parseColor("#B40037"));
        sweetAlertDialogProgress.getProgressHelper().setRimColor(Color.parseColor("#B40037"));
        sweetAlertDialogProgress.setTitleText("Loading");
        sweetAlertDialogProgress.setCancelable(false);
        sweetAlertDialogProgress.setCanceledOnTouchOutside(true);
        sweetAlertDialogProgress.show();
    }

    @Override
    public void hideProgress() {
        sweetAlertDialogProgress.dismiss();
    }

    @Override
    public void showDataList(ResponseDetailToko responseDetailToko) {
        this.store = responseDetailToko.getStore();
        adapter = new DetailTokoAdapter(getContext(), store, userToken);
        adapter.notifyDataSetChanged();
        initAdapter();
    }

    private void initAdapter() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void showError(String errMsg) {
        Toast.makeText(getContext(), "Ada error : " + errMsg, Toast.LENGTH_LONG).show();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
