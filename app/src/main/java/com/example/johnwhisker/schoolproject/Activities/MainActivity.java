package com.example.johnwhisker.schoolproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.johnwhisker.schoolproject.Activities.MakeTestActivity;
import com.example.johnwhisker.schoolproject.Activities.OfflineMode;
import com.example.johnwhisker.schoolproject.Activities.ShowResult;
import com.example.johnwhisker.schoolproject.Config;
import com.example.johnwhisker.schoolproject.Question;
import com.example.johnwhisker.schoolproject.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    Firebase dtb;
    List<Question> questionList;
    @BindView(R.id.cardViewChoice1)
    CardView cv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        dtb = new Firebase(Config.FIREBASE_URL);
        questionList = new ArrayList<>();
        //cv1.setClickable(false);
        //isOnline();
        SendNetWorkRequest();
    }
    public boolean isOnline(){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            if(exitValue != 0)
            {


                Toast.makeText(this,"Connected to Firebase successful",Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(MainActivity.this,"Could not connect to the Internet ",Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this,"Check your Internet Connection",Toast.LENGTH_SHORT).show();

            }
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void SendNetWorkRequest() {
        AsyncHttpClient testOnline = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String url = "https://www.firebase.com/";
        testOnline.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System
                /*
                Toast.makeText(MainActivity.this,"Could not Connect to the FireBase",Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this,"Switching to OfflineMode",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, OfflineMode.class);
                startActivity(intent);
                */
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Toast.makeText(MainActivity.this,"FireBase Connected",Toast.LENGTH_SHORT).show();
                Log.e("Connected", responseString);
                getData();

            }
        });
    }

    public void getData(){

        dtb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                questionList.add(dataSnapshot.getValue(Question.class));
                if(questionList.size()==88){
                    Toast.makeText(getApplication(),"Load complete",Toast.LENGTH_SHORT).show();
                    cv1.setClickable(true);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void cvChoice1OnSelect(View v) {
        Intent i = new Intent(this, MakeTestActivity.class);
        i.putParcelableArrayListExtra("A", (ArrayList<? extends Parcelable>) questionList);
        startActivity(i);
    }

    public void cvChoice2OnSelect(View v) {
    Intent i = new Intent(this,ShowResult.class);
        Random ran = new Random();
        i.putExtra("A",ran.nextInt(30));
        startActivity(i);

    }
}
