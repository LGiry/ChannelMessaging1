package laurie.giry.channelmessaging.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import laurie.giry.channelmessaging.ChannelListActivity;
import laurie.giry.channelmessaging.ChannelResponse;
import laurie.giry.channelmessaging.OnWsRequestListener;
import laurie.giry.channelmessaging.R;
import laurie.giry.channelmessaging.WsRequest;

/**
 * Created by Wibou on 07/03/2016.
 */
public class ChannelListFragment extends Fragment implements OnWsRequestListener{
    private static final int REQUEST_GET_CHANNELS = 0;
    private ListView lvMyListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_channel_list,container);
        lvMyListView = (ListView)view.findViewById(R.id.channelList);

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvMyListView.setOnItemClickListener((ChannelListActivity) getActivity());

        SharedPreferences settings = getActivity().getSharedPreferences("PrefAccessToken", 0);
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
    public void OnSuccess(int requestCode, String result) {
        Gson gson = new Gson();

        ChannelResponse channelRep = gson.fromJson(result, ChannelResponse.class);
        List<Channel> channels = channelRep.getResponse();

        lvMyListView.setAdapter(new ChannelAdaptater(channels, getActivity()));
    }

    @Override
    public void OnError(int requestCode) { }

}
