package steg.zied.harrabi.sse;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Inscription extends Activity {
EditText num,nom,tel,adr,pass;
    Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        ok=(Button)findViewById(R.id.ok);
        num=(EditText)findViewById(R.id.num);
        nom=(EditText)findViewById(R.id.nom);
        tel=(EditText)findViewById(R.id.tel);
        adr=(EditText)findViewById(R.id.adr);
        pass=(EditText)findViewById(R.id.pass);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String n,no,t,a,p;
                n=num.getText().toString();
                no=nom.getText().toString();
                t=tel.getText().toString();
                a=adr.getText().toString();
                p=pass.getText().toString();
                StringRequest sr=new StringRequest(Request.Method.POST,
                        Params.ip+"ajoutclient.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
try
{
    JSONObject js=new JSONObject(response);
    String f=js.getString("flag");
    if(f.equals("ok"))
    {
        Toast.makeText(Inscription.this, "Compte crée avec succès", Toast.LENGTH_LONG).show();
        Intent i=new Intent(Inscription.this,Login.class);
        startActivity(i);
    }
    else
    {
        Toast.makeText(Inscription.this, "Erreur de connexion", Toast.LENGTH_LONG).show();
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
                        para.put("compteur",n);
                        para.put("nom",no);
                        para.put("tel",t);
                        para.put("adresse",a);
                        para.put("password",p);
                        return para;
                    }
                };
                RequestQueue rq= Volley.newRequestQueue(Inscription.this);
                rq.add(sr);
            }
        });
    }
}
