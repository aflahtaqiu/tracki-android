package com.example.aflah.tracki_master.DetailToko;

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

import com.example.aflah.tracki_master.Adapter.DetailTokoAdapter;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTokoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    String userToken;
    SharedPreferences sharedPreferences;
    SweetAlertDialog sweetAlertDialogProgress;

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
        sweetAlertDialogProgress = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialogProgress.getProgressHelper().setBarColor(Color.parseColor("#B40037"));
        sweetAlertDialogProgress.getProgressHelper().setRimColor(Color.parseColor("#B40037"));
        sweetAlertDialogProgress.setTitleText("Loading");
        sweetAlertDialogProgress.setCancelable(false);
        sweetAlertDialogProgress.setCanceledOnTouchOutside(true);
        sweetAlertDialogProgress.show();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_detailtoko);
        TextView textView = (TextView) view.findViewById(R.id.tv_phoneToko_detailToko);

        int idToko = getActivity().getIntent().getExtras().getInt("idTokoTerdekat");

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseDetailToko> getData = apiRequest.getStoreByID(idToko);
        getData.enqueue(new Callback<ResponseDetailToko>() {
            @Override
            public void onResponse(Call<ResponseDetailToko> call, Response<ResponseDetailToko> response) {
                recyclerView.setAdapter(new DetailTokoAdapter(getContext(), response.body().getStore(), userToken));
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.smoothScrollToPosition(0);
                sweetAlertDialogProgress.dismiss();
            }
            @Override
            public void onFailure(Call<ResponseDetailToko> call, Throwable t) {
            }
        });

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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
