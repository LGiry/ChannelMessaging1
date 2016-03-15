package laurie.giry.channelmessaging;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import laurie.giry.channelmessaging.Fragment.ChannelListFragment;
import laurie.giry.channelmessaging.Fragment.MessageFragment;

public class ChannelListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ChannelListFragment fragA = (ChannelListFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentA_channelList);
        MessageFragment fragB = (MessageFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentB_channel);
        if(fragB == null || !fragB.isInLayout()){
            Intent myIntent = new Intent(getApplicationContext(),ChannelActivity.class);
            myIntent.putExtra("ChannelId", id);
            startActivity(myIntent);
        } else {
            fragB.OnChannelClick(id);
        }
    }
}