package com.example.app1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 13-07-2017.
 */
public class ActivityFragment extends Fragment {

    private ListView listv;
    public static ArrayList<String> ArrayofName = new ArrayList<String>();
    public static String datenow;
    public static DatabaseHandler db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment, container, false);
        getActivity().setTitle("Activity");

        db = new DatabaseHandler(getActivity());
        ArrayofName.clear();
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        //Log.d("Insert: ", "Inserting ..");
        //datenow = Database.dateFormat.format(Database.date);
        //db.addContact(new Database("Ravi", datenow));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Database> contacts = db.getAllContacts();

        for (Database cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Date: " + datenow;
            // Writing Contacts to log
            Log.d("Name: ", log);

        }


        listv = (ListView) v.findViewById(R.id.listView1);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.activity_list,R.id.Time, ArrayofName);
        listv.setAdapter(adapter);

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //db.addContact(new Database("ISHA" , datenow));
                //Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
