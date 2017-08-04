package com.example.aji.joblist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateField extends AppCompatActivity {
    EditText editText2;
    DatabaseHelper mDatabaseHelper;
    private String SelJob;
    private int SelID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_layout);
        editText2 = (EditText) findViewById(R.id.message);
        mDatabaseHelper = new DatabaseHelper(this);

        Intent recIntent = getIntent();// get intent passed from main activity
        SelID = recIntent.getIntExtra("ID", -1);//get extra from intent main activity
        SelJob = recIntent.getStringExtra("Job");
        editText2.setText(SelJob);


    }

    public void saveButton(View v) { //method when we click button save
        String item = editText2.getText().toString();
        if (!item.equals("")) {
            mDatabaseHelper.UpdateJob(item, SelID, SelJob);
            toasMessage("Update Successfully!");
            editText2.setText("");
        } else {
            toasMessage("Please input job!");
        }
    }
    public void finishC(View v) {
        Intent intent2 = new Intent();
        intent2.setClass(UpdateField.this, MainActivity.class);
        startActivity(intent2);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        finish();
        //intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }


    private void toasMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
