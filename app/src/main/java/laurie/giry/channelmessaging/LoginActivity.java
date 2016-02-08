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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnWsRequestListener {

    private Button btnValider;
    private EditText txtId;
    private EditText txtMDP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnValider = (Button) findViewById(R.id.btnValider);
        txtMDP = (EditText) findViewById(R.id.editTextMDP);
        txtId = (EditText) findViewById(R.id.editTextId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnValider.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String id = txtId.getText().toString();
        String mdp = txtMDP.getText().toString();

        List<NameValuePair> values = new ArrayList<NameValuePair>(2);
        values.add(new BasicNameValuePair("username", id));
        values.add(new BasicNameValuePair("password", mdp));

        WsRequest connectionRqt = new WsRequest("http://raphaelbischof.fr/messaging/?function=connect", values);
        connectionRqt.setOnWsRequestListener(this);
        connectionRqt.execute();
    }

    @Override
    public void OnSuccess(String result) {
        Gson gson = new Gson();
        Response response = gson.fromJson(result, Response.class);

        if(response.getCode() == 200){
            SharedPreferences settings = getSharedPreferences("PrefAccessToken", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("AccessToken", response.getAccesstoken());
            editor.commit();

            Intent myIntent = new Intent(getApplicationContext(),ChannelListActivity.class);
            startActivity(myIntent);
        }
    }

    @Override
    public void OnError() {
        Context context = getApplicationContext();
        CharSequence text = "No pain, No glory ! ";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
