package com.example.aflah.tracki_master.NavbarFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aflah.tracki_master.AboutTrackiActivity;
import com.example.aflah.tracki_master.Adapter.TokoFavoritAdapter;
import com.example.aflah.tracki_master.Auth.LoginActivity;
import com.example.aflah.tracki_master.EditProfilActivity;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchNameProduct;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchNameStore;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.SearchName;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CircleImageView imgAvatar;
    TextView tvUserName;
    SharedPreferences sharedPreferences;
    String json;
    UserLogin userLogin;
    Gson gson = new Gson();
    String userToken;
    List<Store> stores;
    RecyclerView recyclerView;
    TokoFavoritAdapter tokoFavoritAdapter;
    Button btnEditProfile;

    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        sharedPreferences = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        json = sharedPreferences.getString("userLogin", "");
        userLogin= gson.fromJson(json, UserLogin.class);
        userToken = sharedPreferences.getString("tokenLogin", "");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        imgAvatar = view.findViewById(R.id.imgProfile);
        tvUserName = view.findViewById(R.id.tv_userName);
        btnEditProfile = (Button) view.findViewById(R.id.btn_edit);

        Picasso.get().load(userLogin.getFoto()).fit().into(imgAvatar);
        tvUserName.setText(userLogin.getName());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycerview_tokoFavorit);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(view.getContext(), EditProfilActivity.class));
            }
        });

        stores = new ArrayList<>();
        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseUserById> getTokoFav = apiRequest.getTokoFavorit(userLogin.getId());
        getTokoFav.enqueue(new Callback<ResponseUserById>() {
            @Override
            public void onResponse(Call<ResponseUserById> call, Response<ResponseUserById> response) {
                for (int i =0; i < response.body().getUser().getStores().size();i++){
                    stores.add(response.body().getUser().getStores().get(i));
                }
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                tokoFavoritAdapter = new TokoFavoritAdapter(getContext(), stores);
                recyclerView.setAdapter(tokoFavoritAdapter);
            }

            @Override
            public void onFailure(Call<ResponseUserById> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_setting, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.item_about:
                Log.v("itemSelected", "about");
                getActivity().startActivity(new Intent(getActivity(), AboutTrackiActivity.class));
                break;
            case R.id.item_help:
                Log.v("itemSelected", "help");
                break;
            case R.id.item_logout:
                SharedPreferences.Editor editor = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                editor.putString("tokenLogin", "");
                editor.putString("userLogin", "");
                editor.apply();
                editor.commit();
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
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
