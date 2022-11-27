package com.education.jadwalsholatapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.education.jadwalsholatapp.R;
import com.education.jadwalsholatapp.listener.OnClickCityListener;
import com.education.jadwalsholatapp.model.CityModel;

import java.util.ArrayList;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {

    private OnClickCityListener onClickCityListener;
    private ArrayList<CityModel> cityList;


    // Provide a suitable constructor (depends on the kind of cityModelArrayList)
    public CityListAdapter(ArrayList<CityModel> cityModelArrayList) {
        cityList = cityModelArrayList;
    }

    public void setCityClickListener(OnClickCityListener onClickCityListener){
        this.onClickCityListener = onClickCityListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CityListAdapter.CityViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        /* create a new view */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_city, parent, false);
        CityViewHolder viewHolder = new CityViewHolder(v);
        return viewHolder;
    }

        /* Provide a reference to the views for each data item
        Complex data items may need more than one view per item, and
        you provide access to all the views for a data item in a view holder */
    public class CityViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textCity;

        public CityViewHolder(View v) {
            super(v);
            textCity = v.findViewById(R.id.text_city);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CityModel cityModel = cityList.get(getAdapterPosition());
                    onClickCityListener.onCityClicked(cityModel.getId(), cityModel.getNama());
                }
            });
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textCity.setText(cityList.get(position).getNama());
    }

    // Return the size of your cityModelArrayList (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cityList.size();
    }


}
