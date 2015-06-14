package com.skypayjm.thack.packalist;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.rey.material.widget.TextView;
import com.skypayjm.thack.packalist.Events.PackItemsAvailableEvent;
import com.skypayjm.thack.packalist.model.PackItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_pack_list)
public class PackListActivity extends AppCompatActivity {
    List<PackItem> packItems;

    @ViewById
    TextView packListText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pack_list, menu);
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

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @AfterViews
    public void init() {
        packItems = ((PackItemsAvailableEvent) EventBus.getDefault().removeStickyEvent(PackItemsAvailableEvent.class)).getPItems();
        if (packItems == null || packItems.isEmpty()) {
            packListText.setText("Can't find any existing packing list that matches your needs. Would you like to create a pack list instead?");
        } else {
            packListText.setText("Name: " + packItems.get(0).getName() + ", x" + packItems.get(0).getQuantity() + " " + packItems.get(0).getUom());
        }
    }

//    public void onEvent(PackItemsAvailableEvent event) {
//        this.packItems = event.getPItems();
//    }
}
