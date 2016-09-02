package com.example.triibe.triibeuserapp.create_survey;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.triibe.triibeuserapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateSurveyActivity extends AppCompatActivity implements CreateSurveyContract.View {

    CreateSurveyContract.UserActionsListener mUserActionsListener;

    @BindView(R.id.submit)
    FloatingActionButton mSubmit;

    @BindView(R.id.survey_name)
    TextInputEditText mName;

    @BindView(R.id.survey_description)
    TextInputEditText mDescription;

    @BindView(R.id.survey_version)
    TextInputEditText mVersion;

    @BindView(R.id.survey_points)
    TextInputEditText mPoints;

    @BindView(R.id.survey_time_till_expiry)
    TextInputEditText mTimeTillExpiry;

    public CreateSurveyActivity() {
        mUserActionsListener = new CreateSurveyPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserActionsListener.createSurvey(mName.getText().toString(),
                        mDescription.getText().toString(), mVersion.getText().toString(),
                        mPoints.getText().toString(), mTimeTillExpiry.getText().toString());
            }
        });
    }

    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void showSurveys() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
