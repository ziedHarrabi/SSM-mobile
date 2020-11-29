package steg.zied.harrabi.sse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.Map;

public class Factures extends Activity {
ListView list;
    ArrayList<String> listitems;
    ArrayAdapter<String>adapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factures);
        list=(ListView)findViewById(R.id.listView2);
        listitems=new ArrayList<>();
        adapt=new ArrayAdapter<String>(Factures.this,android.R.layout.simple_list_item_1,listitems);
        list.setAdapter(adapt);
        final String m=getIntent().getStringExtra("compteur");
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             final String l=list.getItemAtPosition(position).toString();
                Toast.makeText(Factures.this, l, Toast.LENGTH_SHORT).show();
                StringRequest sr=new StringRequest(Request.Method.POST, Params.ip+"getdatafacture.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject j=new JSONObject(response);
                            String date_debut=j.getString("dd");
                            String date_fin=j.getString("df");
                            String montant=j.getString("montant");
                            String nb_kilo=j.getString("nb");
                            String avant_de=j.getString("delai");
                            String res="Période de: "+date_debut+" à "+date_fin+"\n"+"Nombre de Kilo Watt: "+nb_kilo+"\n"+"Montant: "+montant+"\n"+"Payer avant le: "+avant_de;
                            //afficher alert dialog
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Factures.this);

                            // set title
                            alertDialogBuilder.setTitle("Details Factures");

                            // set dialog message
                            alertDialogBuilder
                                    .setMessage(res+"\n"+"Cliquer Oui pour sortir de la liste des facture!")
                                    .setCancelable(false)
                                    .setPositiveButton("Oui",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // if this button is clicked, close
                                            // current activity
                                            Factures.this.finish();
                                        }
                                    })
                                    .setNegativeButton("Annuler",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
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
                        para.put("compteur", m);
                        para.put("facture",l);
                        return para;
                    }
                };
                        RequestQueue rq=Volley.newRequestQueue(Factures.this);
                rq.add(sr);
            }
        });
    }
    protected  void onStart()
    {
        final String m=getIntent().getStringExtra("compteur");
        super.onStart();
        adapt.clear();
        StringRequest sr=new StringRequest(Request.Method.POST, Params.ip+"getfactures.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
try
{
    JSONArray js=new JSONArray(response);
    for(int k=0;k<js.length();k++)
    {
        JSONObject j=js.getJSONObject(k);
        String n=j.getString("num_facture");
        listitems.add(n);
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
                para.put("compteur",m);
                return para;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(Factures.this);
        rq.add(sr);

    }
}
