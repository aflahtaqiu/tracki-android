package com.example.aflah.tracki_master.NavbarFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.AboutTrackiActivity;
import com.example.aflah.tracki_master.Adapter.ListSavePromoAdapter;
import com.example.aflah.tracki_master.Auth.LoginActivity;
import com.example.aflah.tracki_master.EditProfilActivity;
import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.Response.ResponseLogout;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.NavigationActivity;
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

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    CircleImageView imgAvatar;
    TextView tvUserName;
    SharedPreferences sharedPreferences;
    String json;
    UserLogin userLogin;
    Gson gson = new Gson();
    String userToken;
    List<Store> stores;
    List<Promotion> promotions;
    RecyclerView recyclerView;
    ListSavePromoAdapter listSavePromoAdapter;
    Dialog Mydialog;
    TextView picAvatar,picGaleri, picCamera;
    Toolbar toolbarAccount;
    private int GALLERY = 1, CAMERA = 2;

    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
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
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        toolbarAccount = (Toolbar) view.findViewById(R.id.toolbar_account);

        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.setSupportActionBar(toolbarAccount);

        Picasso.get().load(userLogin.getFoto()).fit().into(imgAvatar);
        tvUserName.setText(userLogin.getName());

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCustomDialog();
            }
        });

        stores = new ArrayList<>();
        promotions = new ArrayList<>();

        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseUserById> getTokoFav = apiRequest.getSavedPromo(userLogin.getId());
        getTokoFav.enqueue(new Callback<ResponseUserById>() {
            @Override
            public void onResponse(Call<ResponseUserById> call, Response<ResponseUserById> response) {
                for (Promotion promotion : response.body().getUnused_promotions()){
                    promotions.add(promotion);
                }
                listSavePromoAdapter = new ListSavePromoAdapter(getContext(), promotions, userToken);
                recyclerView = view.findViewById(R.id.recycerview_promoSaved);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(listSavePromoAdapter);
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
                ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                Call<ResponseLogout> responseLogoutCall = apiRequest.sendLogout();
                responseLogoutCall.enqueue(new Callback<ResponseLogout>() {
                    @Override
                    public void onResponse(Call<ResponseLogout> call, Response<ResponseLogout> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseLogout> call, Throwable t) {

                    }
                });
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.btn_edit:
                Log.v("itemSelected", "edit profile");
                getActivity().startActivity(new Intent(getActivity(), EditProfilActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void MyCustomDialog(){
        Mydialog = new Dialog(getActivity());
        Mydialog.setContentView(R.layout.popup_normal);

        picAvatar = (TextView)Mydialog.findViewById(R.id.imgAvatar);
        picGaleri = (TextView)Mydialog.findViewById(R.id.imgGaleri);
        picCamera = (TextView)Mydialog.findViewById(R.id.imgCamera);

        picAvatar.setEnabled(true);
        picGaleri.setEnabled(true);
        picCamera.setEnabled(true);

        picAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pilih default avatar", Toast.LENGTH_LONG).show();
            }
        });

        picGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY);
            }
        });

        picCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA);
            }
        });

        Mydialog.show();
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
