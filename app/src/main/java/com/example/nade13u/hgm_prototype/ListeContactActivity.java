package com.example.nade13u.hgm_prototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListeContactActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listContact;
    ArrayList<Contact> arrayContact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    public void onStart()
    {
        super.onStart();
        listContact = (ListView)findViewById(R.id.list_contact);

        arrayContact = Contact.getAllContact(getContentResolver());

        CustomerAdapter customerAdapter = new CustomerAdapter();

        listContact.setAdapter(customerAdapter);
        listContact.setSelection(customerAdapter.getCount()-1);
        listContact.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // a definir
    }

    class CustomerAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return arrayContact.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.item_contact,null);
            TextView nom = (TextView)convertView.findViewById(R.id.lbl_nom);
            TextView num = (TextView)convertView.findViewById(R.id.lbl_numero);

            nom.setText(arrayContact.get(position).getNom());
            num.setText(arrayContact.get(position).getNum());

            return convertView;
        }
    }
}
