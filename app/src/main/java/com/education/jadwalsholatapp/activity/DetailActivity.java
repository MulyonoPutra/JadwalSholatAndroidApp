package com.education.jadwalsholatapp.activity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import com.education.jadwalsholatapp.R;
import com.education.jadwalsholatapp.activity.MainActivity;
import com.education.jadwalsholatapp.adapter.CityListAdapter;
import com.education.jadwalsholatapp.fragment.DatePickerFragment;
import com.education.jadwalsholatapp.listener.OnClickCityListener;
import com.education.jadwalsholatapp.model.ResponseCityModel;
import com.education.jadwalsholatapp.model.ResponseDetailModel;
import com.education.jadwalsholatapp.retrofit.JadwalSholatService;
import com.education.jadwalsholatapp.retrofit.RetrofitUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView cityNameText;
    private TextView shubuhText;
    private TextView dzuhurText;
    private TextView asharText;
    private TextView magribText;
    private TextView isyaText;
    private TextView dateText;
    private LinearLayout jadwalContainer;
    private ProgressBar progressBar;
    private String cityId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /* Receive data from Main Activity */
        cityId = getIntent().getStringExtra("city_id");
        String name = getIntent().getStringExtra("city_name");

        cityNameText = findViewById(R.id.city_name);
        shubuhText = findViewById(R.id.shubuh_time);
        dzuhurText = findViewById(R.id.dzuhur_time);
        asharText = findViewById(R.id.ashar_time);
        magribText = findViewById(R.id.magrib_time);
        isyaText = findViewById(R.id.isya_time);
        dateText = findViewById(R.id.date_text);
        jadwalContainer = findViewById(R.id.jadwal_container);
        progressBar = findViewById(R.id.progress_bar);

        Button button = (Button) findViewById(R.id.buttonDate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        cityNameText.setText(name);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        dateText.setText(dateString);
        requestData(cityId, dateString);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        Date date = new Date();
        date.setTime(calendar.getTimeInMillis());
        String dateString = dateFormat.format(date);

        dateText.setText(dateString);
        requestData(cityId, dateString);
    }


    private void requestData(String cityId, final String dateString) {
        progressBar.setVisibility(View.VISIBLE);
        jadwalContainer.setVisibility(View.GONE);
        JadwalSholatService mApiInterface = RetrofitUtils.getClient().create(JadwalSholatService.class);
        Call<ResponseDetailModel> cityListCall = mApiInterface.getJadwalSholat(cityId, dateString);
        cityListCall.enqueue(new Callback<ResponseDetailModel>() {
            @Override
            public void onResponse(Call<ResponseDetailModel> call, Response<ResponseDetailModel> response) {
                ResponseDetailModel responseDetailModel = response.body();
                shubuhText.setText(responseDetailModel.getJadwal().getData().getSubuh());
                dzuhurText.setText(responseDetailModel.getJadwal().getData().getDzuhur());
                asharText.setText(responseDetailModel.getJadwal().getData().getAshar());
                magribText.setText(responseDetailModel.getJadwal().getData().getMaghrib());
                isyaText.setText(responseDetailModel.getJadwal().getData().getIsya());
                progressBar.setVisibility(View.GONE);
                jadwalContainer.setVisibility(View.VISIBLE);

                try {
                    Date currrenTime = new Date();

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                    Date subuhDate = dateFormat.parse(dateString+" "+responseDetailModel.getJadwal().getData().getSubuh());
                    Date dzuhurDate = dateFormat.parse(dateString+" "+responseDetailModel.getJadwal().getData().getDzuhur());
                    Date asharDate = dateFormat.parse(dateString+" "+responseDetailModel.getJadwal().getData().getAshar());
                    Date magribDate = dateFormat.parse(dateString+" "+responseDetailModel.getJadwal().getData().getMaghrib());
                    Date isyaDate = dateFormat.parse(dateString+" "+responseDetailModel.getJadwal().getData().getIsya());

                    if (currrenTime.before(subuhDate)){
                        shubuhText.setTextColor(Color.RED);
                    }else if(currrenTime.before(dzuhurDate)){
                        dzuhurText.setTextColor(Color.RED);
                    }else if(currrenTime.before(asharDate)){
                        asharText.setTextColor(Color.RED);
                    }else if(currrenTime.before(magribDate)){
                        magribText.setTextColor(Color.RED);
                    }else if(currrenTime.before(isyaDate)){
                        isyaText.setTextColor(Color.RED);
                    }else{
                        shubuhText.setTextColor(Color.RED);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailModel> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }


}
