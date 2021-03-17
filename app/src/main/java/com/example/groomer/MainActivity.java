package com.example.groomer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Console;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText name, weight, instruccions, breed;
    ListView list;
    ArrayList<Pet> arrayList, Temp;
    ArrayAdapter arrayAdapter;

    ImageButton save, deleteAll, deleteIndividual, clearButton, updateButton;
    Boolean[] checked;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadData();
        name = (EditText) findViewById(R.id.textName);
        instruccions = (EditText) findViewById(R.id.textSpecial);
        weight = (EditText) findViewById(R.id.textWeight);
        breed = (EditText) findViewById(R.id.textBreed);
        list = findViewById(R.id.list);
        save = findViewById(R.id.addButton);

        deleteAll = findViewById(R.id.deletAll);
        deleteIndividual = findViewById(R.id.deleteIndividual);
        clearButton = findViewById(R.id.clearButton);
        updateButton = findViewById(R.id.updateButton);
        fillList();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fillFakeData();
            }
        });


        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Log.i(TAG, "onItemClick: " +position);
                CheckedTextView v = (CheckedTextView) view;
                // boolean currentCheck = v.isChecked();
                //UserAccount user = (UserAccount) listView.getItemAtPosition(position);
                //setActive(!currentCheck);

                System.out.println(list.getCheckedItemCount());
                System.out.println(list.getCheckedItemPositions().get(0));


            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean();
            }
        });

        deleteIndividual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Temp = new ArrayList<Pet>();


                if (arrayList.size() >= 1) {

                    for (int i = 0; i < arrayList.size(); i++) {
                        if (!list.isItemChecked(i)) {
                            Temp.add(arrayList.get(i));
                        }

                    }
                    arrayList = Temp;
                    updateData();

                } else {
                    deleteIndividual.setClickable(false);


                }


            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                deleteIndividual.setClickable(true);


            }
        });


        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearShare();
                LoadData();
                fillList();
            }
        });


    }

    private void clearShare() {
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

    }


    private void LoadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("pets", null);
        Type type = new TypeToken<ArrayList<Pet>>() {
        }.getType();
        arrayList = gson.fromJson(json, type);

        if (arrayList == null) {
            arrayList = new ArrayList<Pet>();

        }


    }


    private void saveData() {

        if (name.getText().toString().length() <= 1 || weight.getText().toString().length() <= 1 || instruccions.getText().toString().length() <= 1 || breed.getText().toString().length() <= 1) {
            Toast toast = Toast.makeText(getApplicationContext(), "Some Data Missing !!", Toast.LENGTH_SHORT);
            toast.show();

        } else {

            Pet pet = new Pet(name.getText().toString(), weight.getText().toString(), instruccions.getText().toString(), breed.getText().toString());
            arrayList.add(pet);
            SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(arrayList);
            editor.putString("pets", json);
            editor.apply();
            fillList();
            Toast toast = Toast.makeText(getApplicationContext(), "Saved !!", Toast.LENGTH_SHORT);
            toast.show();
            clean();
        }
    }


    public void fillList() {
        String element;
        ArrayList<String> arrayfoTheList = new ArrayList<String>();

        for (int i = 0; i < arrayList.size(); i++) {
            element = (i + 1) + "  :   " + arrayList.get(i).getName() + " | " + arrayList.get(i).getWeight() + " | " + arrayList.get(i).getBreed() + " | " + arrayList.get(i).getInstruccions();
            arrayfoTheList.add(element);
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, arrayfoTheList);
        list.setAdapter(arrayAdapter);


    }

    public void deleteIndividual() {


    }

    public void updateData() { //update the sharepreferences from de list

        clearShare();
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("pets", json);
        editor.apply();
        fillList();
    }

    public void clean() {
        name.setText("");
        instruccions.setText("");
        weight.setText("");
        breed.setText("");
    }

    private void fillFakeData (){
        // sample data to test the program
        int ran =0;
        ran= (int) (Math.random() * (5));
        String [] names= {"Charlie","Cooper","Rocky","Jack","Milo"};
        String [] weight1= {"100","150","500","50","25"};
        String [] instruccions1= {"Be Nice","Buy a toy","Go out","Be careful","Love food"};
        String [] breed1= {"Bulldog","Beagle","French Bulldog","Poodle","Chulo"};

        name.setText(names[ran]);
        instruccions.setText(instruccions1[ran]);
        weight.setText(weight1[ran]);
        breed.setText(breed1[ran]);

    }



}