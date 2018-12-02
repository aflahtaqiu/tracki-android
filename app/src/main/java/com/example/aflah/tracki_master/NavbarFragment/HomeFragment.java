package com.example.aflah.tracki_master.NavbarFragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.aflah.tracki_master.Adapter.CarouselHomeAdapter;
import com.example.aflah.tracki_master.Adapter.TokoTerdekatAdapter;
import com.example.aflah.tracki_master.DetailTokoActivity;
import com.example.aflah.tracki_master.Model.Advertisement;
import com.example.aflah.tracki_master.Model.Advertisements;
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
import java.util.TimerTask;

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
public class HomeFragment extends Fragment implements NavigationActivity.OnCubeaconUpdated,AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = HomeFragment.class.getSimpleName();
    private List<Map<String, String>> data;
    private List<CBBeacon> beacons;
    private SimpleAdapter adapter;
    private TokoTerdekatAdapter tokoTerdekatAdapter;
    private  SwipeRefreshLayout mySwipeRefreshLayout;
    HashMap<String,SearchName> toko;
    String[] from;
    int[] to;

    ViewPager viewPager;

    ArrayAdapter<String> autoCompleteAdaptor;
    List<Store> stores;
    HashMap<String,Store> rmdup;

    int beaconCount;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textViewTokoTerdekat, textViewNoTokoTerdekat;

    private NavigationActivity navigationActivity;

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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textViewTokoTerdekat = (TextView) view.findViewById(R.id.tv_tokoTerdekat_tokoTerdekat);
        textViewNoTokoTerdekat = (TextView) view.findViewById(R.id.tvNoTokoTerdekat_tokoTerdekat);
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.edit_search);


        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
                Call<ResponseSearchNameStore> getNamaToko = apiRequest.getSearchNamesStore();
                getNamaToko.enqueue(new Callback<ResponseSearchNameStore>() {
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
//
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerCarousel(), 2000, 4000);

        return view;
    }

    public class TimerCarousel extends TimerTask{

        @Override
        public void run() {
            HomeFragment.this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0)
                        viewPager.setCurrentItem(1, true);
                    else if (viewPager.getCurrentItem() == 1)
                        viewPager.setCurrentItem(2, true);
                    else
                        viewPager.setCurrentItem(0,true);
                }
            });
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();

        inflater.inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (item != null){
            searchView = (SearchView) item.getActionView();
        }

        if (searchView != null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    Log.i("onQueryTextSubmit", query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    Log.i("onQueryTextChange", newText);
                    return false;
                }
            };

            searchView.setOnQueryTextListener(queryTextListener);
        }

        super.onCreateOptionsMenu(menu, inflater);
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
                    //listView.setAdapter(adapter);
                    //tokoTerdekatAdapter.notifyDataSetChanged();

                    ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);

                    for (int i = 0 ;i<cbBeacons.size(); i++ ){
                        final int tole = i;
                        Call<ResponseTokoByUID> getData = apiRequest.getStoreByUID(beacons.get(i).getMajor());
                        getData.enqueue(new Callback<ResponseTokoByUID>() {
                            @Override
                            public void onResponse(Call<ResponseTokoByUID> call, Response<ResponseTokoByUID> response) {
                                for (Store store : response.body().getStores()) {
                                    rmdup.put(String.valueOf(store.getId()),store);

                                }
                                if (rmdup.size() != 0){
                                    textViewNoTokoTerdekat.setText("");
                                    textViewTokoTerdekat.setText("Toko Terdekat");
                                }
                                else{
                                    textViewTokoTerdekat.setText("");
                                    textViewNoTokoTerdekat.setText("Tidak terdeteksi toko disekitar Anda");
                                }
                                tokoTerdekatAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<ResponseTokoByUID> call, Throwable t) {

                            }
                        });
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
