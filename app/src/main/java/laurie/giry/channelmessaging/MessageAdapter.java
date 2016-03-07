package laurie.giry.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Wibou on 07/03/2016.
 */
public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<Message> messages;

    public MessageAdapter(List<Message> messages, Context context) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_channel, parent, false);

        TextView title = (TextView) rowView.findViewById(R.id.tvTitle);
        TextView subtitle = (TextView) rowView.findViewById(R.id.tvSubtitle);
        ImageView ivPhoto = (ImageView) rowView.findViewById(R.id.ivPhoto);

        Message msg = getItem(position);

        title.setText(msg.getUsername()+ " : " +msg.getMessage());
        subtitle.setText(msg.getDate());

        Picasso.with(context).load(msg.imageUrl).transform(new CircleTransform()).into(ivPhoto);

        return rowView;
    }
}
