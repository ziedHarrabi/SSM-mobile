package steg.zied.harrabi.sse;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class PrendreMesure extends Activity {
EditText num,newm;
    Button ok,send;
    TextView lastm,nom,tel,montant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prendre_mesure);
        ok=(Button)findViewById(R.id.aff);
        send=(Button)findViewById(R.id.send);
        newm=(EditText)findViewById(R.id.newm);
        num=(EditText)findViewById(R.id.num);
        lastm=(TextView)findViewById(R.id.lastm);
        nom=(TextView)findViewById(R.id.nom);
        tel=(TextView)findViewById(R.id.tel);
        montant=(TextView)findViewById(R.id.montant);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String n=num.getText().toString();
                StringRequest sr=new StringRequest(Request.Method.POST, Params.ip+"getinfo.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
try
{
    Toast.makeText(PrendreMesure.this, response, Toast.LENGTH_SHORT).show();
    JSONObject js=new JSONObject(response);
    String no=js.getString("nom");
    String te=js.getString("tel");
    String l=js.getString("dermesure");
    lastm.setText(l);
    nom.setText(no);
    tel.setText(te);
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
                        HashMap<String,String> para=new HashMap<String, String>();
                        para.put("num",n);
                        return para;
                    }
                };
                RequestQueue rq= Volley.newRequestQueue(PrendreMesure.this);
                rq.add(sr);
            }
        });



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String n=num.getText().toString();
                final String m=newm.getText().toString();

                final String di=String.valueOf(Integer.parseInt(m) - Integer.parseInt(lastm.getText().toString()));
                final String mo=String.valueOf(Integer.parseInt(di) * 300);
                montant.setText(mo);
                final String latt=getIntent().getStringExtra("lat");
                final String lonn=getIntent().getStringExtra("lon");
                final String cin=getIntent().getStringExtra("cin");
                StringRequest sr=new StringRequest(Request.Method.POST, Params.ip+"calcul.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject js=new JSONObject(response);
                            String no=js.getString("flag");
                           if(no.equals("ok"))
                           {
                               Toast.makeText(PrendreMesure.this, "Mesure pris en considération", Toast.LENGTH_SHORT).show();
                               Intent i=new Intent(PrendreMesure.this,AccueilAgent.class);

                               i.putExtra("cin", cin);
                               startActivity(i);
                           }
                            else
                           {
                               Toast.makeText(PrendreMesure.this, "Mesure n'est pris en considération", Toast.LENGTH_SHORT).show();
                           }
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
                        HashMap<String,String> para=new HashMap<String, String>();
                        para.put("num",n);
                        para.put("newmesure",m);
                        para.put("diff",di);
                        para.put("montant",mo);
                        return para;
                    }
                };
                RequestQueue rq= Volley.newRequestQueue(PrendreMesure.this);
                rq.add(sr);
            }
        });
    }
}
