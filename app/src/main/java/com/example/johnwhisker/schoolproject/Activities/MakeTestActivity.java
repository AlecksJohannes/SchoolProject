package com.example.johnwhisker.schoolproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johnwhisker.schoolproject.Question;
import com.example.johnwhisker.schoolproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MakeTestActivity extends AppCompatActivity {

    public static final int totalQuestion = 30;
    int current, score;
    Question question;
    CircularCountdown circularCountdown;
    LinearLayout parent;
    List<Question> questionList, askedQuestionList;
    Random random;
    Boolean reset;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_test);
        ButterKnife.bind(this);
        parent = (LinearLayout) findViewById(R.id.viewx);
        current = 0;
        reset = false;
        score = 0;
        questionList = new ArrayList<>();
        random = new Random();
        askedQuestionList = new ArrayList<>();
        questionList = getIntent().getParcelableArrayListExtra("A");
        setQuestion();
        InitListener();


    }

    public void setQuestion() {
        reset = true;
        circularCountdown = new CircularCountdown(this);
        parent.addView(circularCountdown);
        if (current == totalQuestion) {
            Intent i = new Intent(this, ShowResult.class);
            i.putExtra("A", score);
            startActivity(i);
        } else {
            cv3.setVisibility(TextView.VISIBLE);
            cv4.setVisibility(TextView.VISIBLE);
            if (questionList.isEmpty()) {
                questionList = askedQuestionList;
                askedQuestionList.clear();
            }

            int index = random.nextInt(questionList.size());
            Log.d("Size matter", "" + index + "/" + questionList.size());
            question = questionList.get(index);
            setRippleEffect(question.getAnswer());
            askedQuestionList.add(question);
            questionList.remove(question);
            //randomize the question
            // still not figure it out

            // set View
            Log.d("Debug", question.toString());
            tvProgress.setText((current + 1) + "/30" + "  QUESTIONS");
            tvQuestion.setText(question.getQuestion());
            tvAnswer1.setText(question.getA());
            tvAnswer2.setText(question.getB());
            if (question.getC() == null) {
                cv3.setVisibility(TextView.GONE);
                cv4.setVisibility(TextView.GONE);
            }
            tvAnswer3.setText(question.getC());
            tvAnswer4.setText(question.getD());

            current++;
            //Init listener
        }
    }

    public void setRippleEffect(int Correct) {
        switch (Correct) {
            case 0: {
                cv1.setForeground(getResources().getDrawable(R.drawable.ripple_effect_correct));
                cv2.setForeground(getResources().getDrawable(R.drawable.ripple_effect_incorrect));
                cv3.setForeground(getResources().getDrawable(R.drawable.ripple_effect_incorrect));
                cv4.setForeground(getResources().getDrawable(R.drawable.ripple_effect_incorrect));
            }
            case 1: {
                cv2.setForeground(getResources().getDrawable(R.drawable.ripple_effect_correct));
                cv1.setForeground(getResources().getDrawable(R.drawable.ripple_effect_incorrect));
                cv3.setForeground(getResources().getDrawable(R.drawable.ripple_effect_incorrect));
                cv4.setForeground(getResources().getDrawable(R.drawable.ripple_effect_incorrect));
            }
            case 2: {
                cv3.setForeground(getResources().getDrawable(R.drawable.ripple_effect_correct));
                cv2.setForeground(getResources().getDrawable(R.drawable.ripple_effect_incorrect));
                cv1.setForeground(getResources().getDrawable(R.drawable.ripple_effect_incorrect));
                cv4.setForeground(getResources().getDrawable(R.drawable.ripple_effect_incorrect));
            }
            case 3: {
                cv4.setForeground(getResources().getDrawable(R.drawable.ripple_effect_correct));
                cv2.setForeground(getResources().getDrawable(R.drawable.ripple_effect_incorrect));
                cv3.setForeground(getResources().getDrawable(R.drawable.ripple_effect_incorrect));
                cv1.setForeground(getResources().getDrawable(R.drawable.ripple_effect_incorrect));
            }
        }
    }

    public void InitListener() {

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCorrect(question, 0);


            }
        });
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCorrect(question, 1);

            }
        });
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCorrect(question, 2);


            }
        });
        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCorrect(question, 3);


            }
        });
    }

    public void setColorCard(int Posision, boolean correct) {
        if (Posision == -1) {
            cv1.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
            cv2.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
            cv3.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
            cv4.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));

        }
        if (correct == false) {
            if (Posision == 0) {
                cv1.setBackgroundColor(getResources().getColor(R.color.Correct));
                cv2.setBackgroundColor(getResources().getColor(R.color.Incorrect));
                cv3.setBackgroundColor(getResources().getColor(R.color.Incorrect));
                cv4.setBackgroundColor(getResources().getColor(R.color.Incorrect));

            }
            if (Posision == 1) {
                cv2.setBackgroundColor(getResources().getColor(R.color.Correct));
                cv1.setBackgroundColor(getResources().getColor(R.color.Incorrect));
                cv3.setBackgroundColor(getResources().getColor(R.color.Incorrect));
                cv4.setBackgroundColor(getResources().getColor(R.color.Incorrect));

            }
            if (Posision == 2) {
                cv3.setBackgroundColor(getResources().getColor(R.color.Correct));
                cv1.setBackgroundColor(getResources().getColor(R.color.Incorrect));
                cv2.setBackgroundColor(getResources().getColor(R.color.Incorrect));
                cv4.setBackgroundColor(getResources().getColor(R.color.Incorrect));

            }
            if (Posision == 3) {
                cv4.setBackgroundColor(getResources().getColor(R.color.Correct));
                cv1.setBackgroundColor(getResources().getColor(R.color.Incorrect));
                cv2.setBackgroundColor(getResources().getColor(R.color.Incorrect));
                cv3.setBackgroundColor(getResources().getColor(R.color.Incorrect));

            }
        } else {
            if (Posision == 0) {
                cv1.setBackgroundColor(getResources().getColor(R.color.Correct));
            }
            if (Posision == 1) {
                cv2.setBackgroundColor(getResources().getColor(R.color.Correct));
            }
            if (Posision == 2) {
                cv3.setBackgroundColor(getResources().getColor(R.color.Correct));
            }
            if (Posision == 3) {
                cv4.setBackgroundColor(getResources().getColor(R.color.Correct));
            }
        }
    }

    public void isCorrect(Question question, int Answer) {
        if (question.getAnswer() == Answer) {
            score++;
            setColorCard(question.getAnswer(), true);
            Log.d("Debug", "Correct");
        } else {
            setColorCard(question.getAnswer(), false);
            Log.d("Debug", "Not Correct");
        }
        Log.d("Debug", "" + score);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setColorCard(-1, false);
                setQuestion();
            }
        }, 1000);
    }

    private class CircularCountdown extends View {

        private final Paint backgroundPaint;
        private final Paint progressPaint;
        private final Paint textPaint;
        private final Handler viewHandler;
        private final Runnable updateView;
        private long startTime;
        private long currentTime;
        private long maxTime;
        private long progressMillisecond;
        private double progress;
        private RectF circleBounds;
        private float radius;
        private float handleRadius;
        private float textHeight;
        private float textOffset;

        public CircularCountdown(Context context) {
            super(context);

            // used to fit the circle into
            circleBounds = new RectF();

            // size of circle and handle
            radius = 130;
            handleRadius = 10;

            // limit the counter to go up to maxTime ms
            maxTime = 50000;

            // start and current time
            startTime = System.currentTimeMillis();
            currentTime = startTime;


            // the style of the background
            backgroundPaint = new Paint();
            backgroundPaint.setStyle(Paint.Style.STROKE);
            backgroundPaint.setAntiAlias(true);
            backgroundPaint.setStrokeWidth(10);
            backgroundPaint.setStrokeCap(Paint.Cap.SQUARE);
            backgroundPaint.setColor(Color.parseColor("#4D4D4D"));  // dark gray

            // the style of the 'progress'
            progressPaint = new Paint();
            progressPaint.setStyle(Paint.Style.STROKE);
            progressPaint.setAntiAlias(true);
            progressPaint.setStrokeWidth(10);
            progressPaint.setStrokeCap(Paint.Cap.SQUARE);
            progressPaint.setColor(Color.parseColor("#00A9FF"));    // light blue

            // the style for the text in the middle
            textPaint = new TextPaint();
            textPaint.setTextSize(radius / 2);
            textPaint.setColor(Color.BLACK);
            textPaint.setTextAlign(Paint.Align.CENTER);

            // text attributes
            textHeight = textPaint.descent() - textPaint.ascent();
            textOffset = (textHeight / 2) - textPaint.descent();


            // This will ensure the animation will run periodically
            viewHandler = new Handler();
            updateView = new Runnable() {
                @Override
                public void run() {
                    // update current time
                    if (reset == true) {
                        startTime = System.currentTimeMillis();
                        Toast.makeText(getApplication(), "Reset", Toast.LENGTH_SHORT).show();
                        reset = false;
                    }
                    currentTime = System.currentTimeMillis();
                    // get elapsed time in milliseconds and clamp between <0, maxTime>
                    progressMillisecond = maxTime - (currentTime - startTime) % maxTime;
                    // get current progress on a range <0, 1>
                    progress = (double) progressMillisecond / maxTime;
                    CircularCountdown.this.invalidate();
                    viewHandler.postDelayed(updateView, 1000 / 60);
                }
            };
            viewHandler.post(updateView);
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            // get the center of the view
            float centerWidth = canvas.getWidth() / 2;
            float centerHeight = (canvas.getHeight() / 2) - 500f;
            // set bound of our circle in the middle of the view
            circleBounds.set(centerWidth - radius,
                    centerHeight - radius,
                    centerWidth + radius,
                    centerHeight + radius);


            // draw background circle
            canvas.drawCircle(centerWidth, centerHeight, radius, backgroundPaint);

            // we want to start at -90°, 0° is pointing to the right
            canvas.drawArc(circleBounds, -90, (float) (progress * 360), false, progressPaint);

            // display text inside the circle
            canvas.drawText((double) (progressMillisecond / 100) / 10 + "s",
                    centerWidth,
                    centerHeight + textOffset,
                    textPaint);
            if ((double) (progressMillisecond / 100) / 10 == 0) {
                setQuestion();

            }
            // draw handle or the circle
            canvas.drawCircle((float) (centerWidth + (Math.sin(progress * 2 * Math.PI) * radius)),
                    (float) (centerHeight - (Math.cos(progress * 2 * Math.PI) * radius)),
                    handleRadius,
                    progressPaint);
        }

    }

}
