package com.daisych.simpletodo;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements EditItemDialog.EditItemDialogListener {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdaptor;
    ArrayList<Item> items_list;
    ItemsAdapter items_listAdaptor;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
//        items = new ArrayList<String>();
//        itemsAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        items_list = new ArrayList<Item>();
        items_listAdaptor = new ItemsAdapter(this, items_list);
        lvItems.setAdapter(items_listAdaptor);

//        lvItems.setAdapter(itemsAdaptor);
//        items.add("Milk");
//        items.add("Bread");
        items_list.add(new Item("Buy Milk", "1/1"));
        items_list.add(new Item("Buy Bread", "1/2"));
        setupListViewListenser();
    }

    private void setupListViewListenser() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adaptor, View item, int pos, long id) {
//                        items.remove(pos);
//                        itemsAdaptor.notifyDataSetChanged();
                        items_list.remove(pos);
                        items_listAdaptor.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
               new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> adaptor, View item, int pos, long id) {
                       Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                       i.putExtra("index", pos);
//                       i.putExtra("item", items.get(pos));
                       i.putExtra("item", items_list.get(pos).getName());
                       i.putExtra("date", items_list.get(pos).getDate());

                       // The dialog (fragment) way
                       showEditDialog(pos);

                       // The new activity way
//                       startActivityForResult(i, 200); // brings up the second activity
//                       writeItems();
                   }
               }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onAddItem (View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        EditText etDueDate = (EditText) findViewById(R.id.etDueDate);
        String itemText = etNewItem.getText().toString();
        String dueDate = etDueDate.getText().toString();

//        itemsAdaptor.add(itemText);
        Item added = new Item(itemText, dueDate);
        boolean exist = false;
        for(Item item : items_list) {
            if(item.Equals(added)) {
                exist = true;
            }
        }
        if(!exist) {
            items_listAdaptor.add(added);
            etNewItem.setText("");
            etDueDate.setText("");
            writeItems();
        } else {
            Toast.makeText(this, "Item already exist!", Toast.LENGTH_LONG);
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            items = new ArrayList<String>(FileUtils.readLines(todoFile));
//        } catch (IOException e) {
//            items = new ArrayList<String>();
//        }
        try {
            ArrayList<String> list = new ArrayList<String>(FileUtils.readLines(todoFile));
            items_list = new ArrayList<Item>();
            for(String str : list) {
                items_list.add(new Item(str));
            }
        } catch (IOException e) {
            items_list = new ArrayList<Item>();
            e.printStackTrace();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            ArrayList<String> list = new ArrayList<String>();
            for(Item item : items_list) {
                list.add(item.getName() + " " + item.getDate());
            }
            FileUtils.writeLines(todoFile, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 200) {
            String item = data.getStringExtra("item");
            String date = data.getStringExtra("date");
            int pos = data.getIntExtra("index", 0);

//            items.set(pos, item);
//            itemsAdaptor.notifyDataSetChanged();
            items_list.set(pos, new Item(item, date));
            items_listAdaptor.notifyDataSetChanged();
            writeItems();
        }
    }

    private void showEditDialog(int pos) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog editItemDialog = EditItemDialog.newInstance("Edit Item");
        Bundle bundle = new Bundle();
        bundle.putInt("index", pos);
//        bundle.putString("item", items.get(pos));
        bundle.putString("item", items_list.get(pos).getName());
        bundle.putString("date", items_list.get(pos).getDate());
        editItemDialog.setArguments(bundle);

        editItemDialog.show(fm, "fragment_edit_item");
    }

    @Override
    public void onFinishEditDialog(Intent i) {
        String item = i.getStringExtra("item");
        String date = i.getStringExtra("date");
        int pos = i.getIntExtra("index", 0);

//        items.set(pos, item);
//        itemsAdaptor.notifyDataSetChanged();
        items_list.set(pos, new Item(item, date));
        items_listAdaptor.notifyDataSetChanged();
        writeItems();
    }
}

