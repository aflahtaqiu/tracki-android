package com.example.aflah.tracki_master.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.Adapter.TokoTerdekatAdapter;
import com.example.aflah.tracki_master.Contract.HomeContract;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.SearchName;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.Presenter.HomePresenter;
import com.example.aflah.tracki_master.R;
import com.eyro.cubeacon.CBBeacon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements HomeContract.view, NavigationActivity.OnCubeaconUpdated, AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener, View.OnClickListener, TextView.OnEditorActionListener {

    private SwipeRefreshLayout mySwipeRefreshLayout;
    ImageView imageViewUndetectStore;
    TextView textViewTokoTerdekat;
    AppCompatSpinner spinnerSearch;
    RecyclerView recyclerView;
    AutoCompleteTextView autoCompleteTextView;

    private List<CBBeacon> beacons;
    private TokoTerdekatAdapter tokoTerdekatAdapter;
    HashMap<String,SearchName> hashMapSearch = new HashMap<>();
    ArrayAdapter<String> autoCompleteAdaptor;
    HashMap<String,Store> rmdup = new HashMap<>();

    String[] spinnerItem = new String[]{"Toko", "Produk"};
    String[] arraysOfNames;
    int flagSearch = 0;

    private HomePresenter presenter = new HomePresenter(Injection.provideStoreRepository(), Injection.provideProductRepository(), this);

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);
        spinnerHandle();
        initNearestStoreAdapter();

        return view;
    }

    private void initNearestStoreAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        tokoTerdekatAdapter = new TokoTerdekatAdapter(getContext(), rmdup);
        recyclerView.setAdapter(tokoTerdekatAdapter);
    }

    private void spinnerHandle() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.item_spinner, spinnerItem);
        spinnerSearch.setAdapter(spinnerAdapter);
        spinnerSearch.setOnItemSelectedListener(this);
    }

    private void initViews(View view) {
        textViewTokoTerdekat = (TextView) view.findViewById(R.id.tv_tokoTerdekat_tokoTerdekat);
        imageViewUndetectStore = (ImageView) view.findViewById(R.id.iv_undetect_store);
        autoCompleteTextView = view.findViewById(R.id.edit_search);
        spinnerSearch = (AppCompatSpinner) view.findViewById(R.id.spinnerSearch);
        mySwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycerview_tokoTerdekat);

        mySwipeRefreshLayout.setOnRefreshListener(this);
        autoCompleteTextView.setOnClickListener(this);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        rmdup.clear();
        mListener = null;
    }

    @Override
    public void setData(final List<Map<String, String>> cbBeacons, List<CBBeacon> list) {
        beacons = new ArrayList<>();
        beacons = list;

        if (getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (cbBeacons.size() != 0){
                        for (int i = 0 ;i<cbBeacons.size(); i++ ){
                            presenter.getNearestStore(beacons.get(i).getMajor());
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selected = (String) parent.getItemAtPosition(position);
        int pos = Arrays.asList(arraysOfNames).indexOf(selected);
        SearchName itemSelected = hashMapSearch.get(arraysOfNames[pos]);
        Intent intentSearch;
        if (flagSearch == 0){
            intentSearch = new Intent(getActivity(),TokoActivity.class);
            intentSearch.putExtra("idTokoTerdekat",Integer.valueOf(itemSelected.getId()));
        } else {
            intentSearch = new Intent(getActivity(),DetailMenuActivity.class);
            intentSearch.putExtra("idProduk",Integer.valueOf(itemSelected.getId()));
        }
        autoCompleteTextView.setText("");
        startActivity(intentSearch);
    }

    @Override
    public void onRefresh() {
        mySwipeRefreshLayout.setRefreshing(true);
        rmdup.clear();
        textViewTokoTerdekat.setText("Tidak terdeteksi toko");
        imageViewUndetectStore.setVisibility(View.VISIBLE);
        tokoTerdekatAdapter.notifyDataSetChanged();
        mySwipeRefreshLayout.setRefreshing(false);
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
    public void onNothingSelected(AdapterView<?> parent) { }

    @Override
    public void onClick(View v) {
        if (flagSearch == 0) presenter.getSearchStoreByInput();
        else presenter.getSearchProductByInput();
    }

    @Override
    public void showNearestStore(List<Store> storeList) {
        for (Store store : storeList){
            rmdup.put(String.valueOf(store.getId()), store);
        }
        tokoTerdekatAdapter.notifyDataSetChanged();

        textViewTokoTerdekat.setText("Toko Terdekat");
        imageViewUndetectStore.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showFailure(String errMsg) {
        Toast.makeText(getContext(), "Ada error : " + errMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAutoCompleteText(String[] arraysOfName, List<SearchName> searchNameList) {
        for (int i =0;i<arraysOfName.length; i++){
            hashMapSearch.put(arraysOfName[i], searchNameList.get(i));
        }

        arraysOfNames = Arrays.copyOf(arraysOfName, arraysOfName.length);
        initAutoCompleteAdapter(arraysOfName);

        autoCompleteTextView.setOnEditorActionListener(this);
        autoCompleteTextView.setOnItemClickListener(this);
    }

    private void initAutoCompleteAdapter(String[] arraysOfName) {
        autoCompleteAdaptor = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,arraysOfName);
        autoCompleteTextView.setAdapter(autoCompleteAdaptor);
        autoCompleteTextView.setThreshold(0);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH){
            Intent intent;
            if (flagSearch == 0)
                intent = new Intent(getActivity(), HasilSearchStoreActivity.class);
            else
                intent = new Intent(getActivity(), HasilSearchProductActivity.class);
            intent.putExtra("search",String.valueOf(autoCompleteTextView.getText()));
            autoCompleteTextView.setText("");
            getActivity().startActivity(intent);
        }
        return false;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
