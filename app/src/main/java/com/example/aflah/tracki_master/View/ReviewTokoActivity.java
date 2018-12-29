package com.example.aflah.tracki_master.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aflah.tracki_master.Adapter.ReviewAdapter;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.Reviewer;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Contract.ReviewTokoContract;
import com.example.aflah.tracki_master.Presenter.ReviewTokoPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewTokoActivity extends AppCompatActivity implements View.OnClickListener, ReviewTokoContract.view {

    CircleImageView circleImageView;
    TextView namaToko, tipeToko;
    RecyclerView recyclerView;
    ReviewAdapter reviewAdapter;
    RatingBar ratingBarResult, ratingBarSend;
    Button btnReview;
    EditText et_isiKomentar;
    double ratingTokoSend;
    String komentarToko, userToken;
    int idToko;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    List<Reviewer> reviewerList = new ArrayList<>();

    private ReviewTokoPresenter presenter = new ReviewTokoPresenter(Injection.provideReviewRepository(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_toko);
        initViews();

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        userToken = sharedPreferences.getString("tokenLogin", "");
        idToko = getIntent().getExtras().getInt("idToko");

        presenter.getReview(idToko);
        initAdapter();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(ReviewTokoActivity.this, 1));
        reviewAdapter = new ReviewAdapter(ReviewTokoActivity.this, reviewerList);
        recyclerView.setAdapter(reviewAdapter);
    }

    private void initViews() {
        circleImageView = (CircleImageView) findViewById(R.id.imgToko_review);
        namaToko = (TextView) findViewById(R.id.tv_namaToko_review);
        tipeToko = (TextView) findViewById(R.id.tv_tipeToko_review);
        ratingBarResult = (RatingBar) findViewById(R.id.ratingTokoResult_review);
        ratingBarSend =(RatingBar) findViewById(R.id.ratingBarToko_review);
        et_isiKomentar = (EditText) findViewById(R.id.et_komentarToko_review);
        btnReview = (Button) findViewById(R.id.btn_kirim_review);
        recyclerView = (RecyclerView) findViewById(R.id.recycerview_review);
        btnReview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_kirim_review){
            ratingTokoSend = (double) ratingBarSend.getRating();
            komentarToko = et_isiKomentar.getText().toString();
            presenter.sendReview(userToken, idToko, ratingTokoSend, komentarToko);
            this.reviewerList.clear();
            presenter.getReview(idToko);
        }
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setTitle("Harap tunggu");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        ratingBarSend.setRating(0.00f);
        et_isiKomentar.getText().clear();
        progressDialog.dismiss();
    }

    @Override
    public void showListReview(Store store) {
        this.reviewerList.addAll(store.getReviewers());
        namaToko.setText(store.getName());
        tipeToko.setText(store.getStore_type().getName());
        ratingBarResult.setRating((float)store.getRating());
        Picasso.get().load(store.getLogo()).fit().into(circleImageView);
        reviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(ReviewTokoActivity.this, "Ada error : " + error, Toast.LENGTH_LONG).show();
    }
}
