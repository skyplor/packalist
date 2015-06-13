package com.skypayjm.thack.packalist.util;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.volley.Response;
import com.skypayjm.thack.packalist.model.Airport;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by user on 6/13/2015.
 */
public class AmadeusListener implements Response.Listener<JSONArray> {
    private ArrayAdapter<String> arrayAdapter;
    private AmadeusJSONParser parser;
    private AutoCompleteTextView v;

    public AmadeusListener(AutoCompleteTextView v, ArrayAdapter arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
        parser = new AmadeusJSONParser();
        this.v = v;
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            List<Airport> list = parser.unmarshal(response.toString());
            for (Airport type : list)
                arrayAdapter.add(type.toString());
            v.setAdapter(arrayAdapter);
        } catch (Exception e) {
            throw new RuntimeException("Failed!", e);
        }
    }
}
