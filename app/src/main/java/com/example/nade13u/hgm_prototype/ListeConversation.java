package com.example.nade13u.hgm_prototype;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListeConversation extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listConversation;
    ArrayAdapter<String> listeNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_conversation);
        listConversation = (ListView)findViewById(R.id.list_conversation);

        listeNumero = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        listeNumero.add("650167644");
        listeNumero.add("606550909");
        listeNumero.add("668090111");

        listConversation.setAdapter(listeNumero);
        listConversation.setOnItemClickListener(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        String numero = listeNumero.getItem(position).toString();

        Intent intentSmsActivity = new Intent(this,SmsActivity.class);
        intentSmsActivity.putExtra("numero",numero);
        startActivity(intentSmsActivity);
    }
}
