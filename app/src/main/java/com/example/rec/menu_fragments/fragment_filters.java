package com.example.rec.menu_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.rec.R;

import java.util.ArrayList;
import java.util.List;

public class fragment_filters extends Fragment {

    ImageView backBtn;

    Spinner Area;
    Spinner Site;
    EditText Demand;
    Spinner Location;

    String locationStr;
    int demand;
    String siteType;
    String areaStr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filters, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.fragment_settings_actionbarlayout);

        View viewX = ((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView();

        backBtn = (ImageView) viewX.findViewById(R.id.imgBack);

        TextView textView =(TextView)viewX.findViewById(R.id.heading);
        textView.setText("Filters");

        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_home()).commit();
            }
        });

        demand=0;
        //saqib code
        final TextView buyRent = ((AppCompatActivity)getActivity()).findViewById(R.id.edtbuyRent);
        final Switch buyrent = ((AppCompatActivity)getActivity()).findViewById(R.id.buyRentSwitch);
        buyrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buyrent.isChecked()){
                    buyRent.setText("Rent");
                }

                else {
                    buyRent.setText("Buy");
                }
            }
        });

        Area = ((AppCompatActivity)getActivity()).findViewById(R.id.spinnerArea);
        Location = ((AppCompatActivity)getActivity()).findViewById(R.id.spinnerLocation);
        Site = ((AppCompatActivity)getActivity()).findViewById(R.id.spinnerSitetype);
        Demand = ((AppCompatActivity)getActivity()).findViewById(R.id.edtDemand);

        List<String> list = new ArrayList<String>();
        list.add("Johar Town");
        list.add("Faisal Town");
        list.add("DHA");
        list.add("Model Town");
        list.add("Garden Town");
        list.add("Shalimar Town");
        list.add("Iqbal Town");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(((AppCompatActivity)getActivity()),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Location.setAdapter(dataAdapter);


        List<String> list1 = new ArrayList<String>();
        list1.add("House");
        list1.add("Shop");
        list1.add("Plaza");
        list1.add("Petrol Pump");
        list1.add("Flat/Apartment");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(((AppCompatActivity)getActivity()),
                android.R.layout.simple_spinner_item, list1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Site.setAdapter(dataAdapter1);

        List<String> list2 = new ArrayList<String>();
        list2.add("5 Marla");
        list2.add("10 Marla");
        list2.add("1 Kanal");
        list2.add("2 Kanal");

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(((AppCompatActivity)getActivity()),
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Area.setAdapter(dataAdapter2);

        Button btn = ((AppCompatActivity)getActivity()).findViewById(R.id.btnFilterSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaStr = Area.getSelectedItem().toString();
                locationStr = Location.getSelectedItem().toString();
                if(Demand!=null)
                {
                    demand = Integer.parseInt(Demand.getText().toString());
                }
                siteType = Site.getSelectedItem().toString();

//                Toast.makeText(getBaseContext(), "Location: " + locationStr, Toast.LENGTH_LONG).show();
                //               Toast.makeText(getBaseContext(), "Demand: " + String.valueOf(demand), Toast.LENGTH_LONG).show();
                //              Toast.makeText(getBaseContext(), "Site: " + siteType, Toast.LENGTH_LONG).show();
                //             Toast.makeText(getBaseContext(), "Area: " + areaStr, Toast.LENGTH_LONG).show();

                ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_home()).commit();
            }
        });

    }
}
