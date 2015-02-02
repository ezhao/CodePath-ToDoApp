package com.herokuapp.ezhao.todoapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {
    public static final String RESULT_POS_ID_KEY = "edited_item_pos_id";
    private EditText etCurrentItem;
    private EditText etPriority;
    private EditText etDueDate;
    private long positionId;
    private ToDoItem currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // Load views
        etCurrentItem = (EditText) findViewById(R.id.etCurrentItem);
        etPriority = (EditText) findViewById(R.id.etPriority);
        etDueDate = (EditText) findViewById(R.id.etDueDate);

        // Get currentItem
        positionId = getIntent().getLongExtra(MainActivity.CURRENT_POSITION_ID_KEY, 0);
        currentItem = ToDoItem.load(ToDoItem.class, positionId);

        // Put default values in views
        etCurrentItem.append(currentItem.description);
        etPriority.setText(String.valueOf(currentItem.priority));
        etDueDate.setText(ToDoItem.getDateString(currentItem.dueDate, false));

        // Show keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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

    public void onSaveButton(View v) {
        Intent i = new Intent();
        currentItem.description = etCurrentItem.getText().toString();
        currentItem.dueDate = ToDoItem.parseDateString(etDueDate.getText().toString());
        currentItem.priority = Integer.valueOf(etPriority.getText().toString()).intValue();
        currentItem.save();
        setResult(RESULT_OK, i);
        finish();
    }
}
