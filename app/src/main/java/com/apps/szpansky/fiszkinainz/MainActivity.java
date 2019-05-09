package com.apps.szpansky.fiszkinainz;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.szpansky.fiszkinainz.data.Question;
import com.apps.szpansky.fiszkinainz.provider.LoadingProvider;
import com.apps.szpansky.fiszkinainz.provider.NetworkLoadingProvider;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    LoadingProvider loadingProvider;

    @BindView(R.id.loading)
    ProgressBar progressBar;
    @BindView(R.id.title)
    TextView textViewTitle;
    @BindView(R.id.question)
    TextView textViewQuestion;
    @BindView(R.id.answer)
    TextView textViewAnswer;
    @BindView(R.id.question_id)
    EditText editTextQuestionID;
    @BindView(R.id.question_count)
    TextView textViewQuestionCount;
    @BindView(R.id.question_image)
    ImageView imageViewQuestionView;
    @BindView(R.id.data_layout)
    NestedScrollView nestedScrollViewDataLayout;
    @BindView(R.id.adView)
    AdView adView;

    @OnClick(R.id.next_question)
    void onNextClick() {
        int questionId =MySharedPreferences.getLastQuestionId(this) + 1;
        loadData(questionId);
    }

    @OnClick(R.id.previous_question)
    void onPrevClick() {
        int questionId = MySharedPreferences.getLastQuestionId(this) - 1;
        loadData(questionId);
    }

    void loadData(final int questionId) {
        progressBar.setVisibility(View.VISIBLE);
        nestedScrollViewDataLayout.setVisibility(View.GONE);

        loadingProvider.loadData(new LoadingProvider.CallBack() {
            @Override
            public void onSuccess(Question question) {
                if (!question.getQuestion_image().isEmpty()) Glide.with(imageViewQuestionView).load(question.getQuestion_image()).into(imageViewQuestionView);

                textViewTitle.setText(question.getTitle());
                textViewQuestion.setText(question.getQuestion());
                textViewAnswer.setText(question.getAnswer());
                editTextQuestionID.setText(Integer.toString(question.getId()));
                textViewQuestionCount.setText(Integer.toString(question.getQuestions_count()));
                progressBar.setVisibility(View.GONE);
                nestedScrollViewDataLayout.setVisibility(View.VISIBLE);
                MySharedPreferences.setLastQuestionId(getBaseContext(), question.getId());
            }

            @Override
            public void onFailed(Throwable t) {
                Toast.makeText(getBaseContext(), "Coś nie poszło", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                nestedScrollViewDataLayout.setVisibility(View.GONE);
            }
        }, questionId);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadingProvider = new NetworkLoadingProvider();
        loadData(MySharedPreferences.getLastQuestionId(this));
        editTextQuestionID.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    loadData(Integer.parseInt(editTextQuestionID.getText().toString()));
                    return true;
                }
                return false;
            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
