package com.example.johnwhisker.schoolproject.Activities;

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
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johnwhisker.schoolproject.Activities.ShowResult;
import com.example.johnwhisker.schoolproject.DatabaseController;
import com.example.johnwhisker.schoolproject.Question;
import com.example.johnwhisker.schoolproject.R;
import com.firebase.client.Config;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OfflineMode extends AppCompatActivity {
    private List<Question> quesList;
    private int score=0;
    @Bind(R.id.tvProgress)
    TextView tvProgress;
    @Bind(R.id.cardViewAnswer3)
    CardView cv3;
    @Bind(R.id.cardViewAnswer4)
    CardView cv4;
    @Bind(R.id.cardViewAnswer1)
    CardView cv1;
    @Bind(R.id.cardViewAnswer2)
    CardView cv2;
    @Bind(R.id.tvQuestion)
    TextView tvQuestion;
    @Bind(R.id.tvAnswer1)
    TextView tvAnswer1;
    @Bind(R.id.tvAnswer2)
    TextView tvAnswer2;
    @Bind(R.id.tvAnswer3)
    TextView tvAnswer3;
    @Bind(R.id.tvAnswer4)
    TextView tvAnswer4;
    Question currentQ = new Question();
    int x = currentQ.getID();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_test);
        ButterKnife.bind(this);
        DatabaseController db = new DatabaseController(this);
        db.onCreate();
        quesList=db.getAllProducts();
        currentQ = quesList.get(x);
        setQuestionView();
        tvAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(tvAnswer1.getText().toString());
            }
        });
        tvAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(tvAnswer2.getText().toString());
            }
        });
        tvAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(tvAnswer3.getText().toString());
            }
        });
        tvAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(tvAnswer4.getText().toString());
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.make_test_menu, menu);
        return true;
    }
    public void getAnswer(String answerString) {
        if (currentQ.getAnswer1().equals(answerString)){
            score++;
            Toast.makeText(OfflineMode.this, "YOUR SCORE IS NOW " + score, Toast.LENGTH_SHORT).show();
        }
        if (x < 88) {
            currentQ = quesList.get(x);
            setQuestionView();
        }
        else {
            Intent intent = new Intent(OfflineMode.this,
                    ShowResult.class);
            Bundle b = new Bundle();
            b.putInt("A", score);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
    }
    private void setQuestionView()
    {
        x++;
        tvProgress.setText((x)+"/88");
        tvQuestion.setText(currentQ.getQuestion());
        tvAnswer1.setText(currentQ.getA());
        tvAnswer2.setText(currentQ.getB());
        tvAnswer3.setText(currentQ.getC());
        tvAnswer4.setText(currentQ.getD());

        Log.d("currentID",String.valueOf(x));

    }
}

