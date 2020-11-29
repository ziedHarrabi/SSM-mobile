package steg.zied.harrabi.sse;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccueilClient extends Activity {
Button mesure,facture,rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_client);
        mesure=(Button)findViewById(R.id.mesure);
        facture=(Button)findViewById(R.id.facture);
        rec=(Button)findViewById(R.id.rec);

        mesure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c=getIntent().getStringExtra("compteur");
                String n=getIntent().getStringExtra("nom");
              Intent i=new Intent(AccueilClient.this,Mesures.class);
                i.putExtra("compteur",c);
                startActivity(i);
            }
        });
        facture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c=getIntent().getStringExtra("compteur");
                String n=getIntent().getStringExtra("nom");
                Intent i=new Intent(AccueilClient.this,Factures.class);
                i.putExtra("compteur",c);
                startActivity(i);
            }
        });
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c=getIntent().getStringExtra("compteur");
                String n=getIntent().getStringExtra("nom");
                String te=getIntent().getStringExtra("tel");
                Intent i=new Intent(AccueilClient.this,ReclamationAg.class);
                i.putExtra("compteur",c);
                i.putExtra("type","client");
                i.putExtra("tel",te);
                i.putExtra("nom",n);
                startActivity(i);
            }
        });

    }
}
