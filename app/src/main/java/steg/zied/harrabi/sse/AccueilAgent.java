package steg.zied.harrabi.sse;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AccueilAgent extends Activity {
ListView list;
    ArrayList<String> listitems;
    ArrayAdapter<String> adapt;
    public String a,b;
    Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_agent);
        list=(ListView)findViewById(R.id.listView3);
        listitems=new ArrayList<>();
        adapt=new ArrayAdapter<String>(AccueilAgent.this,android.R.layout.simple_list_item_1,listitems);
        list.setAdapter(adapt);
but=(Button)findViewById(R.id.button);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = getIntent().getStringExtra("nom");
                String te = getIntent().getStringExtra("tel");
                Intent i = new Intent(AccueilAgent.this, ReclamationAg.class);

                i.putExtra("type", "agent");
                i.putExtra("tel", te);
                i.putExtra("nom", n);
                startActivity(i);
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AccueilAgent.this, "Vous avez consulté cette mission", Toast.LENGTH_SHORT).show();
                final  String cin=getIntent().getStringExtra("cin");
                String item1 = list.getItemAtPosition(position).toString();

                Intent i=new Intent(AccueilAgent.this,Trajectoire.class);
                i.putExtra("lat",a);
                i.putExtra("lon",b);
                i.putExtra("cin",cin);
                startActivity(i);
            }
        });


    }
    protected void onStart()
    {
      final  String cin=getIntent().getStringExtra("cin");
        super.onStart();
        adapt.clear();
        StringRequest sr=new StringRequest(Request.Method.POST, Params.ip+"mission.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
try
{
    JSONArray js=new JSONArray(response);
    for(int i=0;i<js.length();i++)
    {
        JSONObject K=js.getJSONObject(i);
        String c=K.getString("compteur");
         a=K.getString("lat");
         b=K.getString("lon");
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        addresses = geocoder.getFromLocation(Double.parseDouble(a), Double.parseDouble(b), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();

        listitems.add("Réference: "+c+"\n"+"Adresse:"+address+", "+city+", "+state+", "+country+", "+postalCode);
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
                para.put("cin",cin);
                return para;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(AccueilAgent.this);
        rq.add(sr);
    }
}
