package steg.zied.harrabi.sse;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReclamationAg extends AppCompatActivity {
EditText desc;
    Button ok;
    String c="";
    SimpleDateFormat sd;
    SimpleDateFormat sd1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamation_ag);
   sd=new SimpleDateFormat("yyyy-MM-dd");
        sd=new SimpleDateFormat("H:m:s");
        final String n=getIntent().getStringExtra("nom");
        final String t=getIntent().getStringExtra("type");
        final String te=getIntent().getStringExtra("tel");
        if(t.equals("client"))
        {
            c=getIntent().getStringExtra("compteur");
        }
        desc=(EditText)findViewById(R.id.editText);
        ok=(Button)findViewById(R.id.ok);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String des=desc.getText().toString();
                final String d=sd.format(new Date());
                final String tt=sd.format(new Date());
                StringRequest sr=new StringRequest(Request.Method.POST, Params.ip+"rec.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
try
{
    JSONObject js=new JSONObject(response);
    String f=js.getString("flag");
    if(f.equals("ok"))
    {
        Toast.makeText(ReclamationAg.this, "Réclamation envoyée avec succès", Toast.LENGTH_SHORT).show();
    }
    else
    {
        Toast.makeText(ReclamationAg.this, "Erreur survenue", Toast.LENGTH_SHORT).show();
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
                        para.put("tel",te);
                        para.put("type",t);
                        para.put("nom",n);
                        para.put("date",d);
                        para.put("heure",tt);
                        para.put("compteur",c);
                        para.put("desc",des);
                        return para;
                    }
                };
                RequestQueue rq= Volley.newRequestQueue(ReclamationAg.this);
                rq.add(sr);
            }
        });

    }
}
