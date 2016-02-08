package laurie.giry.channelmessaging;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wibou on 02/02/2016.
 */
public class WsRequest extends AsyncTask<String, Integer, String>{
    private String url;
    private List<NameValuePair> parametres;
    private ArrayList<OnWsRequestListener> listeners = new ArrayList<>();
    private Exception except = null;

    public WsRequest(String url, List<NameValuePair> parametres) {
        this.url = url;
        this.parametres = parametres;
    }

    public void setOnWsRequestListener(OnWsRequestListener listener){
        listeners.add(listener);
    }

    @Override
    protected String doInBackground(String... params) {
        String content = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        // Add your data
        try {
            httppost.setEntity(new UrlEncodedFormEntity(parametres));
        } catch (UnsupportedEncodingException e) {
            //TODO Handler
            except = e;
        }
        // Execute HTTP Post Request
        HttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
            content = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            //TODO Handler
            except = e;
        }
        return content;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        for (OnWsRequestListener listener : listeners)
        {
            if(except == null) {
                listener.OnSuccess(s);
            }
            else {
                listener.OnError();
            }
        }
    }
}
