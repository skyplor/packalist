package com.skypayjm.thack.packalist;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.skypayjm.thack.packalist.util.AmadeusListener;

/**
 * Created by user on 6/13/2015.
 */
public class VolleyAirportRequest extends JsonArrayRequest {
    public VolleyAirportRequest(Context context, String url, final View snackbarPosition, AutoCompleteTextView view, ArrayAdapter arrayAdapter) {
        super(
                url,
                new AmadeusListener(view, arrayAdapter), new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(snackbarPosition, error.toString(), Snackbar.LENGTH_LONG).show();
                    }
                }
        );
    }
}
