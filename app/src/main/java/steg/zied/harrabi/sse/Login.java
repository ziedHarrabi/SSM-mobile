package steg.zied.harrabi.sse;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class Login extends Activity {
EditText login,password;
    Button connexion,inscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(EditText)findViewById(R.id.login);
        password=(EditText)findViewById(R.id.password);
        connexion=(Button)findViewById(R.id.connexion);
        inscription=(Button)findViewById(R.id.inscription);

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this,Inscription.class);
                startActivity(i);
            }
        });
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String l,p;
                l=login.getText().toString();
                p=password.getText().toString();
                if(l.equals(""))
                {
                    login.setError("Champ obligatoire");
                }
                else
                {
                 if(p.equals(""))
                 {
                     password.setError("Champ obligatoire");
                 }
                    else
                 {
                     //verification dans la base de données
                     StringRequest sr=new StringRequest(Request.Method.POST, Params.ip+"login.php",
                             new Response.Listener<String>() {
                                 @Override
                                 public void onResponse(String response) {

                                try
                                {

                                    JSONObject js=new JSONObject(response);
                                    String n=js.getString("name");
                                            String t=js.getString("type");
                                    String te=js.getString("tel");
                                    String c=js.getString("num");
                                    if(n.equals("error"))
                                    {
                                        Toast.makeText(Login.this, "Compte Introuvable", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(Login.this, "Bienvenue "+n, Toast.LENGTH_SHORT).show();
if(t.equals("Agent"))
{
    Intent i=new Intent(Login.this,AccueilAgent.class);
    i.putExtra("cin",c);
    i.putExtra("nom",n);
    i.putExtra("tel",te);
    startActivity(i);
}
                                        else
{
    Intent i=new Intent(Login.this,AccueilClient.class);
    i.putExtra("compteur",c);
    i.putExtra("nom",n);
    i.putExtra("tel",te);
    startActivity(i);
}
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
                             HashMap<String,String> po=new HashMap<String, String>();
                             po.put("log",l);
                             po.put("pass",p);
                             return po;
                         }
                     };
                     RequestQueue rq= Volley.newRequestQueue(Login.this);
                     rq.add(sr);
                 }
                }
            }
        });
    }
}
