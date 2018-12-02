package com.example.aflah.tracki_master.DetailTokoFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aflah.tracki_master.Adapter.ListMakananAdapter;
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
 * {@link MakananFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MakananFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakananFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView textViewNoMakanan;

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
        return fragment;
    }

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

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseDetailToko> getData = apiRequest.getStoreByID(idToko);
        getData.enqueue(new Callback<ResponseDetailToko>() {
            @Override
            public void onResponse(Call<ResponseDetailToko> call, Response<ResponseDetailToko> response) {

                int temp=0;
                for (int i = 0;i<response.body().getStore().getProducts().size();i++){
                    if (response.body().getStore().getProducts().get(i).getCategory().getId() == 1){
                        temp =1;
                    }
                }
                if (temp==1){
                    textViewNoMakanan.setVisibility(View.GONE);
                }

                recyclerView.setAdapter(new ListMakananAdapter(getContext(), response.body().getStore()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<ResponseDetailToko> call, Throwable t) {
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
