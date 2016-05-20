package com.example.johnwhisker.schoolproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
public class DatabaseController {
    private Context myContext;
    private static String DB_PATH = "/data/data/com.example.johnwhisker.schoolproject/databases/";
    private static String DB_NAME = "school.db";
    SQLiteDatabase myDataBase;
    private String fileLink;

    public DatabaseController(Context context) {
        myContext = context;
    }

    public void onCreate() {
        try {
            fileLink = Environment.getExternalStorageDirectory().getAbsolutePath();
            Log.d("1234", "d1: " + fileLink);
            File myDirs = new File(fileLink + "/databases");
            if (!myDirs.exists())
                myDirs.mkdirs();
            fileLink = fileLink + "/databases/" + DB_NAME;
            Log.d("1234", "d2: " + fileLink);
            File dbFile = new File(fileLink);
            if (!dbFile.exists()) {
                copydatabase();
            }
            Log.d("1234", "Question" + dbFile.exists());
            myDataBase = SQLiteDatabase.openDatabase(fileLink, null, SQLiteDatabase.OPEN_READONLY);
            getAllProducts();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void copydatabase() throws IOException {
        InputStream myinput = myContext.getAssets().open(DB_NAME);
        String outfilename = fileLink;
        Log.d("1234", "e " + fileLink);
        File file = new File(outfilename);
        file.createNewFile();
        OutputStream myoutput = new FileOutputStream(outfilename);
        Log.d("1234", "rer55");
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }
    public List<Question> getAllProducts() {
        List<Question> quesList = new ArrayList<Question>();
        String selectQuery = "SELECT * FROM Question ORDER BY ID ASC";
        Cursor cursor = myDataBase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setID(cursor.getInt(0));
                quest.setQuestion(cursor.getString(1));
                quest.setA(cursor.getString(2));
                quest.setB(cursor.getString(3));
                quest.setC(cursor.getString(4));
                quest.setD(cursor.getString(5));
                quest.setAnswer1(cursor.getString(6));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        return quesList;
    }
}