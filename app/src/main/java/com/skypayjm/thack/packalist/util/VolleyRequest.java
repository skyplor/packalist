package com.skypayjm.thack.packalist.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.rey.material.widget.EditText;

/**
 * Created by user on 6/13/2015.
 */
public class VolleyRequest<T> extends JsonArrayRequest {
    public VolleyRequest(Context context, String url, final View snackbarPosition, EditText view, ArrayAdapter arrayAdapter, Class<T> clazz) {
        super(
                url,
                new AmadeusListener<T>(view, arrayAdapter, clazz), new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(snackbarPosition, error.toString(), Snackbar.LENGTH_LONG).show();
                    }
                }
        );
    }
}
