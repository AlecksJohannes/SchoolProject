package com.example.johnwhisker.schoolproject.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.johnwhisker.schoolproject.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowResult extends AppCompatActivity {
    @Bind(R.id.ivInsideCircle)
    ImageView ivInsideCircle;
    //    @Bind(R.id.ivOutsideCircle)
//    ImageView ivOutsideCircle;
    @Bind(R.id.tvPercent)
    TextView tvPercent;
    @Bind(R.id.tvCorrect)
    TextView tvCorrect;
    @Bind(R.id.tvLevel)
    TextView tvLevel;
    int valueDraw, valueIntent;
    int correct;
    int number;
    PhotoViewAttacher mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        ButterKnife.bind(this);
        correct = getIntent().getIntExtra("A", 0);

        Glide.with(this).load(R.drawable.inside).into(ivInsideCircle);
//        Glide.with(this).load(R.drawable.outside).into(ivOutsideCircle);
        double percent = ((double) correct) / 30;
        percent = percent * 100;
        Log.d("Percent", "" + (int) Math.round(percent) + "/" + correct);
        setResultCircle((int) Math.round(percent));
        animateTextView(0, correct, tvCorrect, "/30");
    }

    public void setResultCircle(int percent) {
        //       ivInsideCircle.setX(150);
//        ivInsideCircle.setY(150);
        float drawValue = (float) ((100 - percent) * 0.01);
        ivInsideCircle.animate().scaleXBy(-drawValue).setDuration(5000);
        ivInsideCircle.animate().scaleYBy(-drawValue).setDuration(5000);
        if(correct >= 10 && correct <20 )
        {
            tvLevel.setText("ADVANCE");
        }if(correct >= 20 && correct <=29)

        {
            tvLevel.setText("WELL PLAY");
        }
        if(correct == 30)
        {
            tvLevel.setText("YOU WIN ALL");
        }
        animateTextView(0, percent, tvPercent, "%");
    }

    public void animateTextView(int initialValue, int finalValue, final TextView textview, final String backend) {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.8f);
        int start = Math.min(initialValue, finalValue);
        int end = Math.max(initialValue, finalValue);
        int difference = Math.abs(finalValue - initialValue);
        Handler handler = new Handler();
        for (int count = start; count <= end; count++) {
            int time = Math.round(decelerateInterpolator.getInterpolation((((float) count) / difference)) * 100) * count;
            final int finalCount = ((initialValue > finalValue) ? initialValue - count : count);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textview.setText(finalCount + backend);
                }
            }, time);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ivInsideCircle.setImageResource(0);
//        ivOutsideCircle.setImageResource(0);
    }
}

