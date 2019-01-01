package com.example.aflah.tracki_master.NavbarFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aflah.tracki_master.View.AboutTrackiActivity;
import com.example.aflah.tracki_master.Adapter.ListSavePromoAdapter;
import com.example.aflah.tracki_master.View.LoginActivity;
import com.example.aflah.tracki_master.View.EditProfilActivity;
import com.example.aflah.tracki_master.Model.Promotion;
import com.example.aflah.tracki_master.Model.Response.ResponseLogout;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.Model.User;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.NavigationActivity;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {

    CircleImageView imgAvatar;
    TextView tvUserName, tvNoPromo;
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
    Uri selectedImage;
    private int GALLERY = 1, CAMERA = 2;


    public AccountFragment() {
    }

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
        tvNoPromo = view.findViewById(R.id.announceNoPromo_accountFragment);
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
        Call<ResponseUserById> getSavedPromo = apiRequest.getSavedPromo(userLogin.getId());
        getSavedPromo.enqueue(new Callback<ResponseUserById>() {
            @Override
            public void onResponse(Call<ResponseUserById> call, Response<ResponseUserById> response) {
                for (Promotion promotion : response.body().getUnused_promotions()){
                    promotions.add(promotion);
                }
                recyclerView = view.findViewById(R.id.recycerview_promoSaved);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                if (promotions.size() != 0)
                    tvNoPromo.setVisibility(View.INVISIBLE);
                listSavePromoAdapter = new ListSavePromoAdapter(getContext(), promotions, userToken,recyclerView, tvNoPromo);
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
                getActivity().startActivity(new Intent(getActivity(), AboutTrackiActivity.class));
                break;
//            case R.id.item_help:
//                Log.v("itemSelected", "help");
//                break;
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
                getActivity().startActivity(new Intent(getActivity(), EditProfilActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void MyCustomDialog(){
        Mydialog = new Dialog(getActivity());
        Mydialog.setContentView(R.layout.popup_normal);

        picGaleri = (TextView)Mydialog.findViewById(R.id.imgGaleri);
        picCamera = (TextView)Mydialog.findViewById(R.id.imgCamera);

        picGaleri.setEnabled(true);
        picCamera.setEnabled(true);

        picGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY);

            }
        });

        picCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(getActivity(), new String[] {android.Manifest.permission.CAMERA}, 70);
                }
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAMERA);
                }
            }
        });

        Mydialog.show();
    }
    
    private Bitmap getResizedBitmap(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = 200;
            height = (int) (width / bitmapRatio);
        } else {
            height = 200;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == GALLERY || requestCode == CAMERA) && resultCode == RESULT_OK && null != data) {
            try {
                selectedImage = data.getData();
                File file = new File(getContext().getCacheDir(), "fotoProfil");
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                file.createNewFile();
                if(requestCode == GALLERY){
                    Bitmap foto = getResizedBitmap(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),selectedImage));
                    foto.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                }else{
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    Bitmap foto = getResizedBitmap(imageBitmap);
                    foto.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                }
                byte[] bitmapdata = bos.toByteArray();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();

                RequestBody requestFile = RequestBody.create(MultipartBody.FORM,file);
                MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("foto",file.getName(),requestFile);

                RequestBody requestMethod = RequestBody.create(MultipartBody.FORM,"PUT");

                ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                Call<ResponseUserById> updatefoto = apiRequest.updateProfilPicture(userLogin.getId(),userToken,multipartBody,requestMethod);
                updatefoto.enqueue(new Callback<ResponseUserById>() {
                    @Override
                    public void onResponse(Call<ResponseUserById> call, Response<ResponseUserById> response) {
                        User userLogin =  response.body().getUser();

                        Gson gson = new Gson();
                        String json = gson.toJson(userLogin);
                        SharedPreferences.Editor editor = getContext().getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                        editor.putString("userLogin", json);
                        editor.apply();
                        editor.commit();
                        Mydialog.dismiss();
                        Picasso.get().load(userLogin.getFoto()).fit().into(imgAvatar);

                    }

                    @Override
                    public void onFailure(Call<ResponseUserById> call, Throwable t) {
                    }
                });

            } catch (Exception e) { }
        }
    }
}
