package com.herokuapp.ezhao.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by emily on 2/1/15.
 */
public class ToDoCursorAdapter extends CursorAdapter {
    public ToDoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);
        TextView tvDueDate = (TextView) view.findViewById(R.id.tvDueDate);
        RelativeLayout rlListItem = (RelativeLayout) view.findViewById(R.id.rlListItem);

        String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));
        int priority = cursor.getInt(cursor.getColumnIndexOrThrow("Priority"));
        long dueDateLong = cursor.getLong(cursor.getColumnIndexOrThrow("DueDate"));

        Integer color = ToDoItem.COLOR_MAP.get(priority);
        rlListItem.setBackgroundColor(context.getResources().getColor(color.intValue()));
        rlListItem.invalidate();

        tvDescription.setText(description);
        tvPriority.setText(String.valueOf(priority));
        tvDueDate.setText(ToDoItem.getDateString(dueDateLong, true));
    }

    @Override
    public long getItemId(int position) {
        Cursor cursor = getCursor();
        cursor.moveToPosition(position);

        return cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
    }
}
