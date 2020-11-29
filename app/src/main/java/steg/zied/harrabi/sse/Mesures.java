package steg.zied.harrabi.sse;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mesures extends Activity {
ListView list;
    ArrayList<String> listitems;
    ArrayAdapter<String>adapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesures);
        list=(ListView)findViewById(R.id.listView);
        listitems=new ArrayList<>();
        adapt=new ArrayAdapter<String>(Mesures.this,android.R.layout.simple_list_item_1,listitems);
        list.setAdapter(adapt);
    }
    protected void onStart()
    {
        super.onStart();
        final String c=getIntent().getStringExtra("compteur");
        StringRequest sr=new StringRequest(Request.Method.POST,
                Params.ip+"getdatamesures.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
try
{
    JSONArray js=new JSONArray(response);
    for(int k=0;k<js.length();k++)
    {
        JSONObject H=js.getJSONObject(k);
        String ag=H.getString("montant");
        String d=H.getString("date");
        String h=H.getString("heure");
        String v=H.getString("val");
        String result="Montant: "+ag+"\n"+"Valeur mesurée: "+v+"\n"+"Date: "+d+"\n"+"Heure: "+h;
        listitems.add(result);
    }
    adapt.notifyDataSetChanged();
}
catch(Exception e)
{

}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> para=new HashMap<>();
                para.put("compteur",c);
                return para;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(Mesures.this);
        rq.add(sr);
    }
}
