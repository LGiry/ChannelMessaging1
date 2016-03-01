package laurie.giry.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Wibou on 08/02/2016.
 */
public class ChannelAdaptater extends BaseAdapter{

    private Context context;
    private List<Channel> channels;

    public ChannelAdaptater(List<Channel> channels, Context context){
        this.context = context;
        this.channels = channels;
    }

    @Override
    public int getCount() {
        return channels.size();
    }

    @Override
    public Channel getItem(int position) {
        return channels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getChannelID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_channel, parent, false);

        TextView title = (TextView) rowView.findViewById(R.id.tvTitle);
        TextView subtitle = (TextView) rowView.findViewById(R.id.tvSubtitle);

        Channel channel = getItem(position);

        title.setText(channel.getName());
        subtitle.setText("Nombre d'utilisateurs connectés :"+channel.getConnectedusers());

        return rowView;
    }
}
