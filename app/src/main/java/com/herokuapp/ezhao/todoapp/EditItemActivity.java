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
    public static final String RESULT_TEXT_KEY = "edited_item_text";
    public static final String RESULT_POS_KEY = "edited_item_pos";
    private EditText etCurrentItem;
    private String originalText;
    private int originalPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etCurrentItem = (EditText) findViewById(R.id.etCurrentItem);
        originalText = getIntent().getStringExtra(MainActivity.CURRENT_ITEM_KEY);
        originalPosition = getIntent().getIntExtra(MainActivity.CURRENT_POSITION_KEY, 0);
        etCurrentItem.append(originalText);

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
        i.putExtra(RESULT_TEXT_KEY, etCurrentItem.getText().toString());
        i.putExtra(RESULT_POS_KEY, originalPosition);
        setResult(RESULT_OK, i);
        finish();
    }
}
