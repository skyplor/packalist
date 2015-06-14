package com.skypayjm.thack.packalist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;

import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.EditText;
import com.rey.material.widget.RadioButton;
import com.skypayjm.thack.packalist.Events.PackItemsAvailableEvent;
import com.skypayjm.thack.packalist.model.Airport;
import com.skypayjm.thack.packalist.model.PackItem;
import com.skypayjm.thack.packalist.util.AmadeusJSONParser;
import com.skypayjm.thack.packalist.util.VolleyRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity {

    @ViewById
    Button fromDate, toDate;

    @ViewById
    EditText fromCntry, toCntry;

    @ViewById
    View snackbarPosition2;

    @ViewById
    RadioButton male, female;

    @ViewById
    CheckBox party, backpacking, beach, business;

    private SimpleDateFormat dateFormatter;
    private EventBus eventBus;

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

    @Click
    public void fromDate() {
        showDatePicker(fromDate);
    }

    @Click
    public void toDate() {
        showDatePicker(toDate);
    }

    @AfterViews
    public void init() {
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        fromCntry.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handleTextChange(fromCntry);
            }
        });
        toCntry.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handleTextChange(toCntry);
            }

        });
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) female.setChecked(false);
            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) male.setChecked(false);
            }
        });
    }

    public void handleTextChange(EditText v) {

        String jsonConnectionLink = "http://api.sandbox.amadeus.com/v1.2/airports/autocomplete?apikey=" + getResources().getString(R.string.amadeusAPI) + "&term=" + v.getText().toString();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line);
        VolleyRequest<Airport> request = new VolleyRequest<>(this, jsonConnectionLink, snackbarPosition2, v, arrayAdapter, Airport.class);
        PackalistApplication_.getInstance().addToRequestQueue(request);
    }

    @Click
    public void search() {
        String origin = fromCntry.getText().toString();
        String destination = toCntry.getText().toString();
        String departureDate = fromDate.getText().toString();
        String returnDate = toDate.getText().toString();
        boolean isMale = male.isChecked();
        boolean isFemale = female.isChecked();
        boolean isParty = party.isChecked();
        boolean isBackpacking = backpacking.isChecked();
        boolean isBeach = beach.isChecked();
        boolean isBusiness = business.isChecked();
        if (checkValid(origin, destination, departureDate, returnDate, isMale, isFemale, isParty, isBackpacking, isBeach, isBusiness)) {
            // Search the DB for packlists that have similar profile
            // If nothing in the DB, we ask user to create one
            searchDBPackList(origin, destination, departureDate, returnDate, isMale, isFemale, isParty, isBackpacking, isBeach, isBusiness);
            startPackListActivity();
        }
    }

    private void startPackListActivity() {
        Intent packListIntent = new Intent(SearchActivity.this, PackListActivity_.class);
        startActivity(packListIntent);
        this.finish();
    }

    private boolean searchDBPackList(String origin, String destination, String departureDate, String returnDate, boolean isMale, boolean isFemale, boolean isParty, boolean isBackpacking, boolean isBeach, boolean isBusiness) {
        EventBus.getDefault().postSticky(producePackItems());
        if (producePackItems().getPItems().isEmpty())
            return false;
        return true;
    }

    private List<PackItem> parseJSON(String jsonArray) {

        AmadeusJSONParser<PackItem> parser = new AmadeusJSONParser(PackItem.class);
        List<PackItem> list = parser.unmarshalList(jsonArray);
        return list;
    }

    public PackItemsAvailableEvent producePackItems() {
        String jsonArray = "[{\"name\": \"shoes\",\"quantity\": 1,\"uom\": \"pairs\"},{\"name\": \"t-shirts\",\"quantity\": 7,\"uom\": \"pieces\"}]";
        List<PackItem> items = parseJSON(jsonArray);
        return new PackItemsAvailableEvent(items);
    }

    private boolean checkValid(String origin, String destination, String departureDate, String returnDate, boolean isMale, boolean isFemale, boolean isParty, boolean isBackpacking, boolean isBeach, boolean isBusiness) {
        return !origin.isEmpty() && !destination.isEmpty() && isDateValid(departureDate, returnDate) && (isMale || isFemale) && (isParty || isBackpacking || isBeach || isBusiness);
    }

    private boolean isDateValid(String departureDate, String returnDate) {
        if (!departureDate.equals("Departure Date") && !returnDate.equals("Return Date")) {
            try {
                Date date1 = dateFormatter.parse(departureDate);
                Date date2 = dateFormatter.parse(returnDate);
                return date2.compareTo(date1) > 0;

            } catch (ParseException e) {
                // execution will come here if the String that is given
                // does not match the expected format.
                e.printStackTrace();
            }
        }
        return false;
    }

    private void showDatePicker(final Button v) {

        Dialog.Builder builder;
        builder = new DatePickerDialog.Builder() {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                String date = dialog.getFormattedDate(dateFormatter);
                super.onPositiveActionClicked(fragment);
                v.setText(date);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("OK").negativeAction("CANCEL");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
    }
}
