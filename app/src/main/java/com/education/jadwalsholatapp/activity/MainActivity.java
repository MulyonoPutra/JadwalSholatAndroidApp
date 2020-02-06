package com.education.jadwalsholatapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.education.jadwalsholatapp.R;
import com.education.jadwalsholatapp.adapter.CityListAdapter;
import com.education.jadwalsholatapp.listener.OnClickCityListener;
import com.education.jadwalsholatapp.model.CityModel;
import com.education.jadwalsholatapp.model.ResponseCityModel;
import com.education.jadwalsholatapp.retrofit.JadwalSholatService;
import com.education.jadwalsholatapp.retrofit.RetrofitUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnClickCityListener {

    private CityListAdapter cityListAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.list_city);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        JadwalSholatService mApiInterface = RetrofitUtils.getClient().create(JadwalSholatService.class);

        Call<ResponseCityModel> cityListCall = mApiInterface.getListCity();
        cityListCall.enqueue(new Callback<ResponseCityModel>() {
            @Override
            public void onResponse(Call<ResponseCityModel> call, Response<ResponseCityModel> response) {

                // specify an adapter (see also next example)
                cityListAdapter = new CityListAdapter(response.body().getKota());
                recyclerView.setAdapter(cityListAdapter);
                cityListAdapter.setCityClickListener(MainActivity.this);
            }

            @Override
            public void onFailure(Call<ResponseCityModel> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });

    }


    @Override
    public void onCityClicked(String id, String name) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("city_id",id); /* melempar data ke Detail Activity */
        intent.putExtra("city_name", name);
        startActivity(intent);
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }
}
