package com.example.aflah.tracki_master.NavbarFragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.aflah.tracki_master.Adapter.TokoTerdekatAdapter;
import com.example.aflah.tracki_master.DetailTokoActivity;
import com.example.aflah.tracki_master.Model.ResponseTokoTerdekat;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.NavigationActivity;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.eyro.cubeacon.CBBeacon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class HomeFragment extends Fragment implements NavigationActivity.OnCubeaconUpdated,AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = HomeFragment.class.getSimpleName();
    private ListView listView;
    private List<Map<String, String>> data;
    private List<CBBeacon> beacons;
    private SimpleAdapter adapter;
    private TokoTerdekatAdapter tokoTerdekatAdapter;
    private Map map;
    String[] from;
    int[] to;


    List<Store> stores;
    HashMap<String,Store> rmdup;

    int beaconCount;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        stores = new ArrayList<>();
        rmdup = new HashMap<>();



        // Inflate the layout for this fragment

        navigationActivity = (NavigationActivity) getActivity();
        navigationActivity.getSupportActionBar().show();

        //listView = view.findViewById(R.id.listview);

        from = new String[]{"title", "subtitle"};
        to = new int[]{android.R.id.text1, android.R.id.text2};
        data = new ArrayList<>();
        adapter = new SimpleAdapter(getContext(), data, android.R.layout.simple_list_item_2, from, to);
        //listView.setAdapter(adapter);
        //listView.setOnItemClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycerview_tokoTerdekat);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        tokoTerdekatAdapter = new TokoTerdekatAdapter(getContext(), rmdup);
        recyclerView.setAdapter(tokoTerdekatAdapter);

        return view;
    }

//    public void loadJSON(int uid){
//
//        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
//        Call<ResponseTokoTerdekat> getStore = apiRequest.getStoreByUID(String.valueOf(uid));
//        getStore.enqueue(new Callback<ResponseTokoTerdekat>() {
//            @Override
//            public void onResponse(Call<ResponseTokoTerdekat> call, Response<ResponseTokoTerdekat> response) {
//                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//                recyclerView.setAdapter(new TokoTerdekatAdapter(getContext(), response.body().getStores()));
//            }
//
//            @Override
//            public void onFailure(Call<ResponseTokoTerdekat> call, Throwable t) {
//                Log.i("onFailure", t.getMessage());
//
//            }
//        });
//    }

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
        Log.d("debug",cbBeacons.size() + "");
        data = new ArrayList<>();
        beacons = new ArrayList<>();
        data = cbBeacons;
        beacons = list;
        beaconCount = cbBeacons.size();

        if (getActivity() != null){
            Log.d("Debug","Enggak nulll");
            //adapter = new SimpleAdapter(getContext(), data, android.R.layout.simple_list_item_2, from, to);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //listView.setAdapter(adapter);
                    //tokoTerdekatAdapter.notifyDataSetChanged();

                    ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);

//                    stores.clear();
                    List<Store> newStores = new ArrayList<>();
                    for (int i = 0 ;i<cbBeacons.size(); i++ ){
                        final int tole = i;
                        Call<ResponseTokoTerdekat> getData = apiRequest.getStoreByUID(String.valueOf(beacons.get(i).getMajor()));
                        getData.enqueue(new Callback<ResponseTokoTerdekat>() {
                            @Override
                            public void onResponse(Call<ResponseTokoTerdekat> call, Response<ResponseTokoTerdekat> response) {
                                for (Store store : response.body().getStores()) {
//                                    if (!stores.contains(store)){
//                                        stores.add(store);
//                                    }
                                    rmdup.put(String.valueOf(store.getId()),store);

//                                    stores.add(rmdup.get(store.getUid()));
//                                    Log.v("debug",  " isi respine " + response.body().getStores().size());
//                                    Log.v("debuger",  " isi stores " + rmdup.toString());

                                }
                                Log.v("debuggerMas", " isi response" + response.body().getStores().size());
                                tokoTerdekatAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<ResponseTokoTerdekat> call, Throwable t) {

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
