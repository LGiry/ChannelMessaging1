package laurie.giry.channelmessaging;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ChannelActivity extends AppCompatActivity implements View.OnClickListener, OnWsRequestListener {

    private static final int REQUEST_GET_MESSAGES = 1;
    private static final int REQUEST_SEND_MESSAGES = 0;
    private ListView lstMesasge;
    private Button send;
    private EditText message;
    private Handler handler;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstMesasge =(ListView) findViewById(R.id.lvMessage);
        message = (EditText) findViewById(R.id.etMessage);
        send = (Button) findViewById(R.id.btnSend);
        send.setOnClickListener(this);

        handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                SharedPreferences settings = getSharedPreferences("PrefAccessToken", 0);
                String accessToken = settings.getString("AccessToken", "");
                List<NameValuePair> values = new ArrayList<NameValuePair>(1);
                if(accessToken.length() != 0){
                    values.add(new BasicNameValuePair("accesstoken", accessToken));
                }
                long channelId = getIntent().getLongExtra("ChannelId", -1);
                values.add(new BasicNameValuePair("channelid", "" + channelId));

                final WsRequest connectionRqt = new WsRequest(REQUEST_GET_MESSAGES, "http://www.raphaelbischof.fr/messaging/?function=getmessages", values);
                connectionRqt.setOnWsRequestListener(ChannelActivity.this);
                connectionRqt.execute();

                handler.postDelayed(this, 2000);
            }
        };
        handler.post(r);
    }

    @Override
    public void OnSuccess(int mRequestCode, String result) {
        Gson gson = new Gson();

        if(mRequestCode == REQUEST_GET_MESSAGES){
            MessageResponse messageRsp = gson.fromJson(result, MessageResponse.class);
            List<Message> messages = messageRsp.messages;

            lstMesasge.setAdapter(new MessageAdapter(messages, this));
        }else{



        }
    }

    @Override
    public void OnError(int mRequestCode) {

    }

    @Override
    public void onClick(View v) {
        String msg = message.getText().toString();

        SharedPreferences settings = getSharedPreferences("PrefAccessToken", 0);
        String accessToken = settings.getString("AccessToken", "");
        List<NameValuePair> values = new ArrayList<NameValuePair>(1);
        if(accessToken.length() != 0){
            values.add(new BasicNameValuePair("accesstoken", accessToken));
        }
        long channelId = getIntent().getLongExtra("ChannelId", -1);
        values.add(new BasicNameValuePair("channelid", "" + channelId));
        values.add(new BasicNameValuePair("message", msg));

        WsRequest connectionRqt = new WsRequest(REQUEST_SEND_MESSAGES, "http://www.raphaelbischof.fr/messaging/?function=sendmessage", values);
        connectionRqt.setOnWsRequestListener(this);
        connectionRqt.execute();

    }
}
