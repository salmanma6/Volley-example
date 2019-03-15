package volleys.volleyexample;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private Button btnRequest;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://api.darksky.net/forecast/119dc98c995a619baca4f351823ded10/37.8267,-122.4233";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRequest = (Button) findViewById(R.id.buttonRequest);

        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v){

                                              sendAndRequestResponse();

                                          }
                                      }

        );

    }

    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    JSONObject dataObject = json.getJSONObject("currently");

    Toast.makeText(getApplicationContext()," "+dataObject,Toast.LENGTH_SHORT).show();

                TextView temp=(TextView)findViewById(R.id.temp);
                temp.setText("Temperature:"+dataObject.get("temperature"));
                TextView temp1=(TextView)findViewById(R.id.hum);
                temp1.setText("Humidity:"+dataObject.get("humidity"));
                TextView temp2=(TextView)findViewById(R.id.press);
                temp2.setText("Pressure:"+dataObject.get("pressure"));
                } catch (JSONException e) {
                    // manage exceptions
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }
}
