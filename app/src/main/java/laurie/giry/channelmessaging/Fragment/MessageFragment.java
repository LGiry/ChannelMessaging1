package laurie.giry.channelmessaging.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import laurie.giry.channelmessaging.ChannelActivity;
import laurie.giry.channelmessaging.Message;
import laurie.giry.channelmessaging.MessageAdapter;
import laurie.giry.channelmessaging.MessageResponse;
import laurie.giry.channelmessaging.OnWsRequestListener;
import laurie.giry.channelmessaging.R;
import laurie.giry.channelmessaging.WsRequest;

/**
 * Created by Wibou on 08/03/2016.
 */
public class MessageFragment extends Fragment implements OnWsRequestListener, View.OnClickListener{

    private static final int REQUEST_GET_MESSAGES = 1;
    private static final int REQUEST_SEND_MESSAGES = 0;
    private ListView lstMessage;
    private ImageButton send;
    private EditText message;
    private Handler handler;
    private long channelId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel,container);
        lstMessage = (ListView)view.findViewById(R.id.lvMessage);
        message = (EditText)view.findViewById(R.id.etMessage);
        send = (ImageButton)view.findViewById(R.id.btnSend);
        send.setOnClickListener(this);

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handler = new Handler();
        channelId = getActivity().getIntent().getLongExtra("ChannelId", -1);
        final Runnable r = new Runnable() {
            public void run() {
                if(getActivity() != null){
                    SharedPreferences settings = getActivity().getSharedPreferences("PrefAccessToken", 0);
                    String accessToken = settings.getString("AccessToken", "");
                    List<NameValuePair> values = new ArrayList<NameValuePair>(1);
                    if(accessToken.length() != 0){
                        values.add(new BasicNameValuePair("accesstoken", accessToken));
                    }
                    values.add(new BasicNameValuePair("channelid", "" + channelId));

                    final WsRequest connectionRqt = new WsRequest(REQUEST_GET_MESSAGES, "http://www.raphaelbischof.fr/messaging/?function=getmessages", values);
                    connectionRqt.setOnWsRequestListener(MessageFragment.this);
                    connectionRqt.execute();

                    handler.postDelayed(this, 2000);
                }
            }
        };
        handler.post(r);
    }

    @Override
    public void OnSuccess(int mRequestCode, String result) {
        Gson gson = new Gson();
        if(mRequestCode == REQUEST_GET_MESSAGES){
            MessageResponse messageRsp = gson.fromJson(result, MessageResponse.class);
            List<Message> messages = messageRsp.getResponse();
            if(messages != null){
                // save index and top position
                int index = lstMessage.getFirstVisiblePosition();
                View v = lstMessage.getChildAt(0);
                int top = (v == null) ? 0 : (v.getTop() - lstMessage.getPaddingTop());
                // SET ADAPTER
                lstMessage.setAdapter(new MessageAdapter(messages, getActivity()));
                // restore index and position
                lstMessage.setSelectionFromTop(index, top);
            }
        }
    }

    public void OnChannelClick(long newChannelId)
    {
        channelId = newChannelId;
    }
    @Override
    public void OnError(int mRequestCode) {}

    @Override
    public void onClick(View v) {
        String msg = message.getText().toString();

        SharedPreferences settings = getActivity().getSharedPreferences("PrefAccessToken", 0);
        String accessToken = settings.getString("AccessToken", "");
        List<NameValuePair> values = new ArrayList<NameValuePair>(1);
        if(accessToken.length() != 0){
            values.add(new BasicNameValuePair("accesstoken", accessToken));
        }
        values.add(new BasicNameValuePair("channelid", "" + channelId));
        values.add(new BasicNameValuePair("message", msg));

        WsRequest connectionRqt = new WsRequest(REQUEST_SEND_MESSAGES, "http://www.raphaelbischof.fr/messaging/?function=sendmessage", values);
        connectionRqt.setOnWsRequestListener(this);
        connectionRqt.execute();

    }
}
