package com.example.aji.joblist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditFieldClass extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_layout);
        mDatabaseHelper = new DatabaseHelper(this);
        editText = (EditText) findViewById(R.id.message);
    }
    public void saveButton(View v){
        String jobmessage= editText.getText().toString();
        if (jobmessage.length() !=0){
            AddData(jobmessage);
            editText.setText("");}
        else {
            toasMessage("Put Jobs at first!");
        }




    }
    public void finishC(View v){
        Intent intent2 = new Intent();
        intent2.setClass(EditFieldClass.this,MainActivity.class);
        startActivity(intent2);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        finish();
        //intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

    }
    private void toasMessage (String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    public void AddData(String entry){
        boolean inserData = mDatabaseHelper.addData(entry);
        if(inserData){
            toasMessage("Data had been saved successfully");
        }
        else {
            toasMessage("Something went wrong!");
        }
    }
}
