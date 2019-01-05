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
import com.example.aflah.tracki_master.Data.remote.API.ApiClient;
import com.example.aflah.tracki_master.Data.remote.API.ApiInterface;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchNameProduct;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchNameStore;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements NavigationActivity.OnCubeaconUpdated,AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener, View.OnClickListener, HomeContract.view {

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

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        tokoTerdekatAdapter = new TokoTerdekatAdapter(getContext(), rmdup);
        recyclerView.setAdapter(tokoTerdekatAdapter);

        return view;
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
               Intent intent = new Intent(getActivity(),TokoActivity.class);
               startActivity(intent);
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
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (flagSearch == 0){
            Call<ResponseSearchNameStore> getNama = apiInterface.getSearchNamesStore();
            getNama.enqueue(new Callback<ResponseSearchNameStore>() {
                @Override
                public void onResponse(Call<ResponseSearchNameStore> call, Response<ResponseSearchNameStore> response) {
                    String[] namaToko = new String[response.body().getSearchNamesStore().size()];
                    for (int i = 0; i < response.body().getSearchNamesStore().size(); i++) {
                        namaToko[i] = response.body().getSearchNamesStore().get(i).getName();
                        hashMapSearch.put(namaToko[i],response.body().getSearchNamesStore().get(i));
                    }
                    autoCompleteAdaptor = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,namaToko);
                    autoCompleteTextView.setAdapter(autoCompleteAdaptor);
                    autoCompleteTextView.setThreshold(0);
                    autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                            if(i == EditorInfo.IME_ACTION_SEARCH){
                                Intent intent = new Intent(getActivity(), HasilSearchStoreActivity.class);
                                intent.putExtra("search",String.valueOf(autoCompleteTextView.getText()));
                                autoCompleteTextView.setText("");
                                getActivity().startActivity(intent);
                            }
                            return false;
                        }
                    });
                    autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String selected = (String) adapterView.getItemAtPosition(i);
                            int pos = Arrays.asList(namaToko).indexOf(selected);
                            SearchName tokoPilihan = hashMapSearch.get(namaToko[pos]);
                            Intent intent = new Intent(getActivity(),TokoActivity.class);
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
            Call<ResponseSearchNameProduct> getNama = apiInterface.getSearchNamesProduct();
            getNama.enqueue(new Callback<ResponseSearchNameProduct>() {
                @Override
                public void onResponse(Call<ResponseSearchNameProduct> call, Response<ResponseSearchNameProduct> response) {
                    String[] namaProduk = new String[response.body().getSearchNamesProduct().size()];
                    for (int i = 0; i < response.body().getSearchNamesProduct().size(); i++) {
                        namaProduk[i] = response.body().getSearchNamesProduct().get(i).getName();
                        hashMapSearch.put(namaProduk[i],response.body().getSearchNamesProduct().get(i));
                    }
                    autoCompleteAdaptor = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,namaProduk);
                    autoCompleteTextView.setAdapter(autoCompleteAdaptor);
                    autoCompleteTextView.setThreshold(0);
                    autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                            if(i == EditorInfo.IME_ACTION_SEARCH){
                                Intent intent = new Intent(getActivity(), HasilSearchProductActivity.class);
                                intent.putExtra("search",String.valueOf(autoCompleteTextView.getText()));
                                autoCompleteTextView.setText("");
                                getActivity().startActivity(intent);
                            }
                            return false;
                        }
                    });
                    autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String selected = (String) adapterView.getItemAtPosition(i);
                            int pos = Arrays.asList(namaProduk).indexOf(selected);
                            SearchName produkPilihan = hashMapSearch.get(namaProduk[pos]);
                            Intent intent = new Intent(getActivity(),DetailMenuActivity.class);
                            intent.putExtra("idProduk",Integer.valueOf(produkPilihan.getId()));
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
