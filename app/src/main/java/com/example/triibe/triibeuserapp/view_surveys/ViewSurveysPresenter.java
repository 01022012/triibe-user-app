package com.example.triibe.triibeuserapp.view_surveys;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.triibe.triibeuserapp.data.SurveyDetails;
import com.example.triibe.triibeuserapp.data.TriibeRepository;
import com.example.triibe.triibeuserapp.util.EspressoIdlingResource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author michael.
 */
public class ViewSurveysPresenter implements ViewSurveysContract.UserActionsListener {

    private static final String TAG = "ViewSurveysPresenter";
    private TriibeRepository mTriibeRepository;
    private ViewSurveysContract.View mView;

    public ViewSurveysPresenter(TriibeRepository triibeRepository, ViewSurveysContract.View view) {
        mTriibeRepository = triibeRepository;
        mView = view;
    }

    @Override
    public void loadSurveys(@NonNull final String userId, @NonNull final Boolean forceUpdate) {
        mView.setProgressIndicator(true);

        final Map<String, SurveyDetails> surveys = new HashMap<>();
        final String path = "users/" + userId + "/activeSurveyIds/";
        if (forceUpdate) {
            mTriibeRepository.refreshSurveyIds();
        }
        EspressoIdlingResource.increment();
        mTriibeRepository.getSurveyIds(path, new TriibeRepository.GetSurveyIdsCallback() {
                    @Override
                    public void onSurveyIdsLoaded(@Nullable final Map<String, Boolean> userSurveyIds) {
                        EspressoIdlingResource.decrement();
                        if (userSurveyIds != null) {
                            Object[] surveyIds = userSurveyIds.keySet().toArray();
                            surveys.clear();
                            for (int i = 0; i < surveyIds.length; i++) {
                                final int position = i;
                                EspressoIdlingResource.increment();
                                mTriibeRepository.getSurvey(surveyIds[i].toString(),
                                        new TriibeRepository.GetSurveyCallback() {
                                            @Override
                                            public void onSurveyLoaded(SurveyDetails survey) {
                                                EspressoIdlingResource.decrement();
                                                surveys.put("" + position, survey);
                                                if (surveys.size() == 0) {
                                                    mView.showNoSurveysMessage();
                                                } else {
                                                    // Only show survey and hide the progress bar
                                                    // when all values are received.
                                                    if (position == userSurveyIds.size() - 1) {
                                                        mView.showSurveys(surveys);
                                                        mView.setProgressIndicator(false);
                                                    }
                                                }
                                            }
                                        });
                            }
                        } else {
                            mView.showNoSurveysMessage();
                            mView.setProgressIndicator(false);
                        }
                    }
                });
    }

    @Override
    public void openSurveyQuestions(@NonNull String surveyId, @NonNull Integer numProtectedQuestions) {
        mView.showQuestionUi(surveyId, "q1", numProtectedQuestions);
    }
}
