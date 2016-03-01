package laurie.giry.channelmessaging;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ChannelListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnWsRequestListener {
    private ListView lvMyListView;
    private String[] listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

        lvMyListView = (ListView)findViewById(R.id.channelList);
        lvMyListView.setOnItemClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SharedPreferences settings = getSharedPreferences("PrefAccessToken", 0);
        String accessToken = settings.getString("AccessToken", "");

        List<NameValuePair> values = new ArrayList<NameValuePair>(1);
        if(accessToken.length() != 0){
            values.add(new BasicNameValuePair("accesstoken", accessToken));
        }
        WsRequest connectionRqt = new WsRequest("http://www.raphaelbischof.fr/messaging/?function=getchannels", values);
        connectionRqt.setOnWsRequestListener(this);
        connectionRqt.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getApplicationContext(),ChannelActivity.class);
        myIntent.putExtra("ChannelId", id);
        startActivity(myIntent);
    }

    @Override
    public void OnSuccess(String result) {
        Gson gson = new Gson();

        ChannelResponse channelRep = gson.fromJson(result, ChannelResponse.class);
        List<Channel> channels = channelRep.getResponse();

        lvMyListView.setAdapter(new ChannelAdaptater(channels, this));
    }

    @Override
    public void OnError() {

    }
}
