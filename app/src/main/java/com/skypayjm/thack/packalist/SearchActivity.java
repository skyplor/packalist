package com.skypayjm.thack.packalist;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@EActivity(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity {

    @ViewById
    EditText fromDate, toDate;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @FocusChange
    public void fromDate(View hello, boolean hasFocus) {
        if (hasFocus && !fromDatePickerDialog.isShowing()) fromDatePickerDialog.show();
    }

    @Click
    public void fromDate() {
        if (!fromDatePickerDialog.isShowing()) fromDatePickerDialog.show();
    }

    @FocusChange
    public void toDate(View hello, boolean hasFocus) {
        if (hasFocus && !toDatePickerDialog.isShowing()) toDatePickerDialog.show();
    }

    @Click
    public void toDate() {
        if (!toDatePickerDialog.isShowing()) toDatePickerDialog.show();
    }

    @AfterViews
    public void init() {
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        setDateTimeField();
    }

    @ViewById
    AutoCompleteTextView fromCntry, toCntry;

    @ViewById
    View snackbarPosition2;

    @TextChange
    public void fromCntry() {

        String jsonConnectionLink = "http://api.sandbox.amadeus.com/v1.2/airports/autocomplete?apikey=" + getResources().getString(R.string.amadeusAPI) + "&term=" + fromCntry.getText().toString();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line);
        VolleyAirportRequest request = new VolleyAirportRequest(this, jsonConnectionLink, snackbarPosition2, fromCntry, arrayAdapter);
        PackalistApplication_.getInstance().addToRequestQueue(request);
    }

    @TextChange
    public void toCntry() {

        String jsonConnectionLink = "http://api.sandbox.amadeus.com/v1.2/airports/autocomplete?apikey=" + getResources().getString(R.string.amadeusAPI) + "&term=" + toCntry.getText().toString();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line);
        VolleyAirportRequest request = new VolleyAirportRequest(this, jsonConnectionLink, snackbarPosition2, toCntry, arrayAdapter);
        PackalistApplication_.getInstance().addToRequestQueue(request);
    }

    @Click
    public void search() {
        String origin = fromCntry.getText().toString();
        String destination = toCntry.getText().toString();
        String departureDate = fromDate.getText().toString();
        String returnDate = toDate.getText().toString();
        // Search the DB for packlists that have similar profile
        searchDBPackList();
    }

    private void searchDBPackList() {
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
