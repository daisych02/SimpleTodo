package com.daisych.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class EditItemActivity extends ActionBarActivity {

    int pos;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        pos = getIntent().getIntExtra("index", 0);
        item = getIntent().getStringExtra("item");
        EditText etItemEdit = (EditText) findViewById(R.id.etItemEdit);
        etItemEdit.setText(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSubmit(View view) {
        EditText etItemEdit = (EditText) findViewById(R.id.etItemEdit);
        String item = etItemEdit.getText().toString();
        Intent data = new Intent();

        data.putExtra("item", item);
        data.putExtra("index", pos);
        setResult(RESULT_OK, data);
        finish();
    }
}
