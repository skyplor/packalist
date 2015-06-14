package com.skypayjm.thack.packalist.util;

import android.widget.ArrayAdapter;

import com.android.volley.Response;
import com.rey.material.widget.EditText;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by user on 6/13/2015.
 */
public class AmadeusListener<T> implements Response.Listener<JSONArray> {
    private ArrayAdapter<String> arrayAdapter;
    private AmadeusJSONParser<T> parser;
    private EditText v;

    public AmadeusListener(EditText v, ArrayAdapter arrayAdapter, Class<T> clazz) {
        this.arrayAdapter = arrayAdapter;
        parser = new AmadeusJSONParser<>(clazz);
        this.v = v;
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            List<T> list = parser.unmarshalList(response.toString());
            for (T type : list)
                arrayAdapter.add(type.toString());
            v.setAdapter(arrayAdapter);
        } catch (Exception e) {
            throw new RuntimeException("Failed!", e);
        }
    }
}
