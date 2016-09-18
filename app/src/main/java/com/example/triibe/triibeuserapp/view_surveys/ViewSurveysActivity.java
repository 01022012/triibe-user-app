package com.example.triibe.triibeuserapp.view_surveys;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.triibe.triibeuserapp.R;
import com.example.triibe.triibeuserapp.edit_survey.EditSurveyActivity;
import com.example.triibe.triibeuserapp.trackLocation.AddFencesIntentService;
import com.example.triibe.triibeuserapp.util.Constants;
import com.example.triibe.triibeuserapp.util.RunAppWhenAtMallService;
import com.example.triibe.triibeuserapp.view_survey_details.ViewSurveyDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class ViewSurveysActivity extends AppCompatActivity implements ViewSurveysContract.View,
        EasyPermissions.PermissionCallbacks {

    private static final String TAG = "ViewSurveysActivity";
    private static final int REQUEST_EDIT_SURVEY = 1;
    private static final int FINE_LOCAITON = 123;
    private String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
    private ViewSurveysContract.UserActionsListener mUserActionsListener;
    private SurveyAdapter mSurveyAdapter;

    @BindView(R.id.view_root)
    CoordinatorLayout mRootView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.surveys_text_view)
    TextView mSurveysTextView;

    @BindView(R.id.view_surveys_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.modify_survey_fab)
    FloatingActionButton mModifySurveyFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_surveys);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mModifySurveyFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateSurvey();
            }
        });

        mUserActionsListener = new ViewSurveysPresenter(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSurveyAdapter = new SurveyAdapter(mUserActionsListener, new ArrayList());
        mRecyclerView.setAdapter(mSurveyAdapter);

        // Add mall fences if not already added (will also be added automatically on boot)
        SharedPreferences preferences = getSharedPreferences(Constants.MALL_FENCES, 0);
        boolean mallfencesAdded = preferences.getBoolean(Constants.MALL_FENCES_ADDED, false);
        if (!mallfencesAdded) {
            if (EasyPermissions.hasPermissions(this, perms)) {
                // Have permission
                startAddfencesService();
            } else {
                // Do not have permissions, request them now
                EasyPermissions.requestPermissions(this, "Need location access to monitor location",
                        FINE_LOCAITON, perms);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserActionsListener.loadUser();
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSurveys(@NonNull List surveyDetails) {
        mSurveyAdapter.replaceData(surveyDetails);
    }

    @Override
    public void showNoSurveysMessage() {
        if (mSurveyAdapter.getItemCount() == 0) {
            mSurveysTextView.setVisibility(View.VISIBLE);
        } else {
            mSurveysTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSurveyDetails(String surveyId) {
        Intent intent = new Intent(this, ViewSurveyDetailsActivity.class);
        intent.putExtra(ViewSurveyDetailsActivity.EXTRA_SURVEY_ID, surveyId);
        startActivity(intent);
    }

    public void showCreateSurvey() {
        Intent intent = new Intent(this, EditSurveyActivity.class);
        startActivityForResult(intent, REQUEST_EDIT_SURVEY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EDIT_SURVEY && resultCode == Activity.RESULT_OK) {
            Snackbar.make(mModifySurveyFab, getString(R.string.successfully_saved_survey),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted: GRANTED");
        startAddfencesService();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied: DENIED");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void startAddfencesService() {
        Intent addMallFencesIntent = new Intent(this, AddFencesIntentService.class);
        addMallFencesIntent.putExtra(AddFencesIntentService.EXTRA_TRIIBE_FENCE_TYPE,
                AddFencesIntentService.TRIIBE_MALL);
        startService(addMallFencesIntent);


        // This was just for testing. Real landmark fences are added when the app service starts
        // after it listens for available fences to add, then ideally filters on them before adding

//        Intent addLandmarkFencesIntent = new Intent(this, AddFencesIntentService.class);
//        addLandmarkFencesIntent.putExtra("type", "landmark");
//        startService(addLandmarkFencesIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_surveys, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.start_data_tracking:
                Intent intent = new Intent(this, RunAppWhenAtMallService.class);
                startService(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
