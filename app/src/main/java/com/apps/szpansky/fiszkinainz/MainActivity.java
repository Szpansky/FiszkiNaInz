package com.apps.szpansky.fiszkinainz;

import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements LoadingProvider.CallBack {

    public AdRequest adRequest;

    LoadingProvider loadingProvider = new NetworkLoadingProvider();

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
        int questionId = MySharedPreferences.getLastQuestionId(this) + 1;
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
        loadingProvider.loadData(this, questionId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData(MySharedPreferences.getLastQuestionId(this));

        editTextQuestionID.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                loadData(Integer.parseInt(editTextQuestionID.getText().toString()));
                return true;
            }
            return false;
        });
    }

    @Override
    public void onSuccess(Question question) {
        if (!question.getQuestion_image().isEmpty())
            Glide.with(imageViewQuestionView).load(question.getQuestion_image()).into(imageViewQuestionView);

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
}
