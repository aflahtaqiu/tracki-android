package com.example.aflah.tracki_master.NavbarFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.aflah.tracki_master.Adapter.TokoTerdekatAdapter;
import com.example.aflah.tracki_master.DetaiMenu.DetailMenuActivity;
import com.example.aflah.tracki_master.DetailTokoActivity;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchNameProduct;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchNameStore;
import com.example.aflah.tracki_master.Model.Response.ResponseTokoByUID;
import com.example.aflah.tracki_master.Model.SearchName;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.NavigationActivity;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.eyro.cubeacon.CBBeacon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements NavigationActivity.OnCubeaconUpdated,AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private List<Map<String, String>> data;
    private List<CBBeacon> beacons;
    private SimpleAdapter adapter;
    private TokoTerdekatAdapter tokoTerdekatAdapter;
    private  SwipeRefreshLayout mySwipeRefreshLayout;
    HashMap<String,SearchName> toko;
    String[] from;
    int[] to;
    String[] spinnerItem = new String[]{"Toko", "Produk"};

    ViewPager viewPager;

    ArrayAdapter<String> autoCompleteAdaptor;
    List<Store> stores;
    HashMap<String,Store> rmdup;
    ImageView imageViewUndetectStore;

    int beaconCount;
    int flagSearch = 0;

    TextView textViewTokoTerdekat;
    AppCompatSpinner spinnerSearch;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textViewTokoTerdekat = (TextView) view.findViewById(R.id.tv_tokoTerdekat_tokoTerdekat);
        imageViewUndetectStore = (ImageView) view.findViewById(R.id.iv_undetect_store);
//        imageViewUndetectStore.setVisibility(View.INVISIBLE);
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.edit_search);
        spinnerSearch = (AppCompatSpinner) view.findViewById(R.id.spinnerSearch);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.item_spinner, spinnerItem);
        spinnerSearch.setAdapter(spinnerAdapter);
        spinnerSearch.setOnItemSelectedListener(this);

        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);

                if (flagSearch == 0){
                    Call<ResponseSearchNameStore> getNama;
                    getNama = apiRequest.getSearchNamesStore();
                    getNama.enqueue(new Callback<ResponseSearchNameStore>() {
                        @Override
                        public void onResponse(Call<ResponseSearchNameStore> call, Response<ResponseSearchNameStore> response) {
                            toko = new HashMap<>();
                            String[] namaToko = new String[response.body().getSearchNamesStore().size()];
                            for (int i = 0; i < response.body().getSearchNamesStore().size(); i++) {
                                namaToko[i] = response.body().getSearchNamesStore().get(i).getName();
                                toko.put(namaToko[i],response.body().getSearchNamesStore().get(i));
                            }
                            autoCompleteAdaptor = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,namaToko);
                            autoCompleteTextView.setAdapter(autoCompleteAdaptor);
                            autoCompleteTextView.setThreshold(0);
                            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    String selected = (String) adapterView.getItemAtPosition(i);
                                    int pos = Arrays.asList(namaToko).indexOf(selected);
                                    SearchName tokoPilihan =toko.get(namaToko[pos]);
                                    Intent intent = new Intent(getActivity(),DetailTokoActivity.class);
                                    intent.putExtra("idTokoTerdekat",Integer.valueOf(tokoPilihan.getId()));
                                    autoCompleteTextView.setText("");
                                    startActivity(intent);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<ResponseSearchNameStore> call, Throwable t) {

                        }
                    });
                }else {
                    Call<ResponseSearchNameProduct> getNama;
                    getNama = apiRequest.getSearchNamesProduct();
                    getNama.enqueue(new Callback<ResponseSearchNameProduct>() {
                        @Override
                        public void onResponse(Call<ResponseSearchNameProduct> call, Response<ResponseSearchNameProduct> response) {
                            toko = new HashMap<>();
                            String[] namaToko = new String[response.body().getSearchNamesProduct().size()];
                            for (int i = 0; i < response.body().getSearchNamesProduct().size(); i++) {
                                namaToko[i] = response.body().getSearchNamesProduct().get(i).getName();
                                toko.put(namaToko[i],response.body().getSearchNamesProduct().get(i));
                            }
                            autoCompleteAdaptor = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,namaToko);
                            autoCompleteTextView.setAdapter(autoCompleteAdaptor);
                            autoCompleteTextView.setThreshold(0);
                            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    String selected = (String) adapterView.getItemAtPosition(i);
                                    int pos = Arrays.asList(namaToko).indexOf(selected);
                                    SearchName tokoPilihan =toko.get(namaToko[pos]);
                                    Intent intent = new Intent(getActivity(),DetailMenuActivity.class);
                                    intent.putExtra("idProduk",Integer.valueOf(tokoPilihan.getId()));
                                    autoCompleteTextView.setText("");
                                    startActivity(intent);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<ResponseSearchNameProduct> call, Throwable t) {

                        }
                    });
                }


            }
        });
        stores = new ArrayList<>();
        rmdup = new HashMap<>();


        mySwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mySwipeRefreshLayout.setRefreshing(true);
                rmdup.clear();
                textViewTokoTerdekat.setText("Tidak terdeksi toko");
                imageViewUndetectStore.setVisibility(View.VISIBLE);
                tokoTerdekatAdapter.notifyDataSetChanged();
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });

        from = new String[]{"title", "subtitle"};
        to = new int[]{android.R.id.text1, android.R.id.text2};
        data = new ArrayList<>();
        adapter = new SimpleAdapter(getContext(), data, android.R.layout.simple_list_item_2, from, to);

//        viewPager = (ViewPager) view.findViewById(R.id.viewPager_carousel_Home);

//        List<Advertisement> advertisementList = new ArrayList<>();
//        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
//        Call<Advertisements> getIklans = apiRequest.getAdvertisements();
//        getIklans.enqueue(new Callback<Advertisements>() {
//            @Override
//            public void onResponse(Call<Advertisements> call, Response<Advertisements> response) {
//                for (int i = 0; i< response.body().getAdvertisements().size() ;i++){
//                    advertisementList.add(response.body().getAdvertisements().get(i));
//                }
//                CarouselHomeAdapter carouselHomeAdapter = new CarouselHomeAdapter(getContext(), advertisementList);
//                viewPager.setAdapter(carouselHomeAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<Advertisements> call, Throwable t) {
//
//            }
//        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycerview_tokoTerdekat);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        tokoTerdekatAdapter = new TokoTerdekatAdapter(getContext(), rmdup);
        recyclerView.setAdapter(tokoTerdekatAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        }
//        else{
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
    @Override
    public void onDetach() {
        super.onDetach();
        rmdup.clear();
        mListener = null;
    }


    @Override
    public void setData(final List<Map<String, String>> cbBeacons, List<CBBeacon> list) {
        data = new ArrayList<>();
        beacons = new ArrayList<>();
        data = cbBeacons;
        beacons = list;
        beaconCount = cbBeacons.size();

        if (getActivity() != null){

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);

                    if (cbBeacons.size() != 0){
                        for (int i = 0 ;i<cbBeacons.size(); i++ ){
                            final int tole = i;
                            Call<ResponseTokoByUID> getData = apiRequest.getStoreByUID(beacons.get(i).getMajor());
                            getData.enqueue(new Callback<ResponseTokoByUID>() {
                                @Override
                                public void onResponse(Call<ResponseTokoByUID> call, Response<ResponseTokoByUID> response) {
                                    for (Store store : response.body().getStores()) {
                                        rmdup.put(String.valueOf(store.getId()),store);
                                    }
                                    if (rmdup.size() !=0 ){
                                        textViewTokoTerdekat.setText("Toko terdekat");
                                        imageViewUndetectStore.setVisibility(View.INVISIBLE);
                                    }
                                    tokoTerdekatAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<ResponseTokoByUID> call, Throwable t) {

                                }
                            });
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               CBBeacon beacon = this.beacons.get(position);
               Intent intent = new Intent(getActivity(),DetailTokoActivity.class);
               startActivity(intent);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                flagSearch = 0;
                break;
            case 1:
                flagSearch = 1;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
