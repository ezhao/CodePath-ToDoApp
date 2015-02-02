package com.herokuapp.ezhao.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    public static final String CURRENT_POSITION_ID_KEY = "current_position_id";
    private final int REQUEST_CODE = 20;
    ArrayList<String> items;
    ToDoCursorAdapter todoAdapter;
    ListView lvItems;
    EditText etNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get views
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNewItem = (EditText) findViewById(R.id.etNewItem);

        // Connect our ListView to a ToDoAdapter
        Cursor todoCursor = ToDoItem.fetchAllCursor();
        todoAdapter = new ToDoCursorAdapter(this, todoCursor);
        lvItems.setAdapter(todoAdapter);

        // Setup listeners
        setupListViewListener();

        // Misc
        unfocusEtNewItem();
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

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        // Persist the delete to our database and update our ListView
                        ToDoItem currentItem = ToDoItem.load(ToDoItem.class, id);
                        currentItem.delete();
                        todoAdapter.changeCursor(ToDoItem.fetchAllCursor());
                        return true;
                    }
                }
        );
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Send the id of the ToDoItem to update
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        i.putExtra(CURRENT_POSITION_ID_KEY, id);
                        startActivityForResult(i, REQUEST_CODE);
                    }
                }
        );
    }

    public void onAddItem(View v) {
        // Get the description for our new ToDoItem
        String itemText = etNewItem.getText().toString();
        etNewItem.setText("");

        // Persist the add to our database and update our ListView
        ToDoItem newItem = new ToDoItem(itemText);
        newItem.save();
        todoAdapter.changeCursor(ToDoItem.fetchAllCursor());

        unfocusEtNewItem(); // TODO(emily) this doesn't entirely work
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            todoAdapter.changeCursor(ToDoItem.fetchAllCursor());
        }
    }

    // Misc
    private void unfocusEtNewItem() {
        // Unfocus the etNewItem
        etNewItem.setSelected(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
