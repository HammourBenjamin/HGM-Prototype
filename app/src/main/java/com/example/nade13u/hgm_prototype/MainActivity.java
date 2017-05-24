package com.example.nade13u.hgm_prototype;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bouton_msg;
    Button bouton_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        bouton_msg = (Button) findViewById(R.id.btn_msg);
        bouton_contacts = (Button) findViewById(R.id.btn_contact);

        bouton_msg.setOnClickListener(this);
        bouton_contacts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == bouton_msg.getId()) {
            Intent intentListeConversation = new Intent(this,ListeConversation.class);
            //intentSmsActivity.putExtra("cle", )
            startActivityForResult(intentListeConversation, 1);
            overridePendingTransition(0,0);
        }

        if(v.getId() == bouton_contacts.getId()) {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);

            sendIntent.putExtra("address","0102030405");
            sendIntent.putExtra("sms_body","xyz");
            sendIntent.setData(Uri.parse("sms:"));

            startActivity(sendIntent);
        }
    }
}
