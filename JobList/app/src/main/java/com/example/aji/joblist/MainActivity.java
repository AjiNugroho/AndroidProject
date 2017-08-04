package com.example.aji.joblist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    DatabaseHelper mDatabaseHelper;
    private boolean exit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //all activity start here
        listView = (ListView) findViewById(R.id.list); //read listview in the front and turn it into object called listView
        arrayList = new ArrayList<>();// this is array that on it i will place my data on
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);//adapter for arraylist to match with ListView
        listView.setAdapter(arrayAdapter);
        mDatabaseHelper = new DatabaseHelper(this); //turn DatabaseHelper Class into Object
        populateList();// run the method populateList below this code
        registerForContextMenu(listView);// registering Context menu to ListView



    }

    @Override
    public void onBackPressed() { //this code was build for make application will exit if we press back on MainActivity. So it wont back to last activity
       /* super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/

       if (exit){

           super.onBackPressed();
           Intent intent = new Intent(Intent.ACTION_MAIN);
           intent.addCategory(Intent.CATEGORY_HOME);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
           //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);
           finish();
            return;
        }
        this.exit=true;
        toasMessage("Press back again to exit");
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                exit=false;
            }
        },2000);

    }

    public void onClick(View v) {//method when we click float Button
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, EditFieldClass.class);

        startActivity(intent);

    }

    private void populateList(){  //method to show all data in Database SQLite into arrayList
        //log.d(TAG,"Populate List View : Displaying data in list");
        Cursor data = mDatabaseHelper.getData();
        while (data.moveToNext()){
            arrayList.add(data.getString(1));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //make item on listView Clickable to update content inside
                String Job = parent.getItemAtPosition(position).toString();
                Cursor data = mDatabaseHelper.getItemID(Job);
                int itemID = -1;
                while (data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if (itemID>-1){
                    Intent intent = new Intent(MainActivity.this,UpdateField.class);
                    intent.putExtra("ID",itemID);
                    intent.putExtra("Job",Job);
                    startActivity(intent);
                }
                else {
                    toasMessage("No File Detected");
                }
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) { //method to show menu contextmenu
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {//method when we select item in context menu that showed

        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.delete:
                String text = listView.getItemAtPosition(info.position).toString();
                //toasMessage(text);
                Cursor data = mDatabaseHelper.getItemID(text);
                int ID =-1;
                while(data.moveToNext()){
                   ID= data.getInt(0);
                }
                if(ID>-1){
                   mDatabaseHelper.DeleteJob(ID,text);
                    //populateList();
                    arrayList.remove(info.position);

                }
                else {
                    toasMessage("Error pada sesuatu");
                }







                arrayAdapter.notifyDataSetChanged();
            default:
                return super.onContextItemSelected(item);

        }

    }


    private void toasMessage (String message){ // method to toast message
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }




}
