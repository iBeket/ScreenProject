package com.example.milos.cfcscreen;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Milos on 23-Oct-17.
 */

public class CfcAdapter extends ArrayAdapter<CFCModel> {
    private Context context;
    private ArrayList<CFCModel> cfcModel;
    private int resource;
    private LayoutInflater inflater;

    public CfcAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<CFCModel> cfcModel) {
        super(context, resource, cfcModel);
        this.context = context;
        this.resource = resource;
        this.cfcModel = cfcModel;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cfcModel.size();
    }
    @Nullable
    @Override
    public CFCModel getItem(int position) {
        return super.getItem(position);
    }

    /**
     * This method returns the row related view,
     * first, checks if the actual view (convertView) is set
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//Initialize the helper class
        ViewHolder viewHolder = null;
         /*
          If convertView is not set, inflate the row layout and get its views' references
          then set the helper class as a tag for the convertView
        */
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.letterTv = convertView.findViewById(R.id.letterTv);
            viewHolder.titleCfc =  convertView.findViewById(R.id.title_cfc);
            viewHolder.locationCfc =  convertView.findViewById(R.id.location_cfc);
            viewHolder.imageCfc =  convertView.findViewById(R.id.imageCfc);
            viewHolder.linear = convertView.findViewById(R.id.listid);
            viewHolder.switchCompat = convertView.findViewById(R.id.switch1);

            convertView.setTag(viewHolder);
        }/*
          If convertView already exists, just get the tag and set it in the viewHolder attribute
        */
        else {
            viewHolder = (CfcAdapter.ViewHolder) convertView.getTag();
        }
        //Populate the row's layout
        CFCModel cfcModel = getItem(position);
       /* viewHolder.titleCfc.setText(cfcModel.getTitle());
        viewHolder.locationCfc.setText(cfcModel.getLocation());
        viewHolder.letterTv.setText(cfcModel.getTitle().substring(0, 1).toUpperCase());*/

        if ((position > 0) && (!cfcModel.getTitle().substring(0, 1).equalsIgnoreCase(this.cfcModel.get(position - 1).getTitle().substring(0, 1)))) {
            viewHolder.letterTv.setVisibility(View.VISIBLE);
            viewHolder.letterTv.setText(cfcModel.getTitle().substring(0, 1).toUpperCase());
            viewHolder.titleCfc.setText(cfcModel.getTitle().substring(0, 1).toUpperCase() + cfcModel.getTitle().substring(1));
            viewHolder.locationCfc.setText(cfcModel.getLocation().substring(0, 1).toUpperCase() + cfcModel.getLocation().substring(1));
            //   letter = obj.title.substring(0, 1);
        } else if (position == 0) {
            viewHolder.letterTv.setVisibility(View.VISIBLE);
            viewHolder.letterTv.setText(cfcModel.getTitle().substring(0, 1).toUpperCase());
            viewHolder.titleCfc.setText(cfcModel.getTitle().substring(0, 1).toUpperCase() + cfcModel.getTitle().substring(1));
            viewHolder.locationCfc.setText(cfcModel.getLocation().substring(0, 1).toUpperCase() + cfcModel.getLocation().substring(1));
        } else {
            viewHolder.letterTv.setVisibility(View.GONE);
            viewHolder.letterTv.setText("");
            viewHolder.titleCfc.setText(cfcModel.getTitle().substring(0, 1).toUpperCase() + cfcModel.getTitle().substring(1));
            viewHolder.locationCfc.setText(cfcModel.getLocation().substring(0, 1).toUpperCase() + cfcModel.getLocation().substring(1));
        }

        Picasso.with(getContext())
                .load(cfcModel.getImagecfc().substring(0, 4) + "s" + cfcModel.getImagecfc().substring(4))
                .into(viewHolder.imageCfc);

        return convertView;
    }

    public class ViewHolder {
        /* This is an helper class used to save
        *  each component of the listView row layout */
        public TextView letterTv;
        public TextView titleCfc;
        public TextView locationCfc;
        public ImageView imageCfc;
        public LinearLayout linear;
        public SwitchCompat switchCompat;
    }
}
