package laurie.giry.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ChannelActivity extends AppCompatActivity implements OnWsRequestListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView userName = (TextView) findViewById(R.id.userName);

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

        int channelId = getIntent().getIntExtra("ChannelId", -1);

        WsRequest connectionRqt = new WsRequest("http://www.raphaelbischof.fr/messaging/?function=getmessages", values, channelId);
        connectionRqt.setOnWsRequestListener(this);
        connectionRqt.execute();
    }

    @Override
    public void OnSuccess(String result) {

    }

    @Override
    public void OnError() {

    }
}
