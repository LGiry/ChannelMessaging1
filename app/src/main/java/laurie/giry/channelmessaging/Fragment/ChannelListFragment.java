package laurie.giry.channelmessaging.Fragment;

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

import laurie.giry.channelmessaging.Channel;
import laurie.giry.channelmessaging.ChannelActivity;
import laurie.giry.channelmessaging.ChannelAdaptater;
import laurie.giry.channelmessaging.ChannelResponse;
import laurie.giry.channelmessaging.OnWsRequestListener;
import laurie.giry.channelmessaging.R;
import laurie.giry.channelmessaging.WsRequest;

/**
 * Created by Wibou on 07/03/2016.
 */
public class ChannelListFragment extends AppCompatActivity implements AdapterView.OnItemClickListener, OnWsRequestListener {
    private static final int REQUEST_GET_CHANNELS = 0;
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
        WsRequest connectionRqt = new WsRequest(REQUEST_GET_CHANNELS, "http://www.raphaelbischof.fr/messaging/?function=getchannels", values);
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
    public void OnSuccess(int mRequestCode, String result) {
        Gson gson = new Gson();

        ChannelResponse channelRep = gson.fromJson(result, ChannelResponse.class);
        List<Channel> channels = channelRep.getResponse();

        lvMyListView.setAdapter(new ChannelAdaptater(channels, this));
    }

    @Override
    public void OnError(int mRequestCode) {}
}
