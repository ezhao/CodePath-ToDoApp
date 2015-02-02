package com.herokuapp.ezhao.todoapp;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by emily on 2/1/15.
 */
@Table(name = "ToDoItems")
public class ToDoItem extends Model {
    @Column(name = "Description")
    public String description;

    @Column(name = "Priority")
    public int priority;

    @Column(name = "DueDate")
    public Long dueDate;

    public static final HashMap<Integer, Integer> COLOR_MAP;
    static {
        COLOR_MAP = new HashMap<Integer, Integer>();
        COLOR_MAP.put(0, android.R.color.holo_green_light);
        COLOR_MAP.put(1, android.R.color.holo_orange_light);
        COLOR_MAP.put(2, android.R.color.holo_red_light);
    }

    // Helper to get date as string
    public static String getDateString(long dueDate, boolean wantShort) {
        String format = "MMMM d, yyyy";
        if (wantShort) {
            format = "MMMM d";
        }
        Date dueDateDate = new Date(dueDate);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dueDateDate);
    }

    // Helper to get string back to long
    public static long parseDateString(String dueDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
        try {
            return sdf.parse(dueDate).getTime();
        } catch (ParseException e) {
            return (new Date()).getTime() + 24 * 60 * 60 * 1000; // TODO(emily) better err handling
        }
    }

    public ToDoItem() {
        super();
    }

    public ToDoItem(String description) {
        super();
        this.description = description;

        // Currently creates with these defaults
        this.priority = 0;
        this.dueDate = (new Date()).getTime() + 24 * 60 * 60 * 1000; // increment by one day
    }

    // Query all items without conditions
    public static Cursor fetchAllCursor() {
        String tableName = Cache.getTableName(ToDoItem.class);

        String resultRecords = new Select(tableName + ".*, " + tableName + ".Id as _id").
                from(ToDoItem.class).toSql();
        Cursor resultCursor = Cache.openDatabase().rawQuery(resultRecords, null);

        return resultCursor;
    }

}
