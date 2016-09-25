package com.example.triibe.triibeuserapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author michael.
 */

public class TriibeRepositoryImpl implements TriibeRepository {

    private final TriibeServiceApi mTriibeServiceApi;
    Map<String, Boolean> mCachedSurveyIds;
    Map<String, Boolean> mCachedQuestionIds;
    Map<String, Question> mCachedQuestions;
    Map<String, Boolean> mCachedOptionIds;
    Map<String, Option> mCachedOptions;
    Map<String, Answer> mCachedAnswers;

    public TriibeRepositoryImpl(@NonNull TriibeServiceApi triibeServiceApi) {
        mTriibeServiceApi = triibeServiceApi;
    }


    /*
    * Surveys
    * */
    @Override
    public void getSurveyIds(@NonNull String path, @NonNull final GetSurveyIdsCallback callback) {
        if (mCachedSurveyIds == null) {
            mTriibeServiceApi.getSurveyIds(path, new TriibeServiceApi.GetSurveyIdsCallback() {
                @Override
                public void onSurveyIdsLoaded(@Nullable Map<String, Boolean> surveyIds) {
                    if (surveyIds != null) {
                        mCachedSurveyIds = ImmutableMap.copyOf(surveyIds);
                    }
                    callback.onSurveyIdsLoaded(mCachedSurveyIds);
                }
            });
        } else {
            callback.onSurveyIdsLoaded(mCachedSurveyIds);
        }
    }

    @Override
    public void refreshSurveyIds() {
        mCachedSurveyIds = null;
    }

    @Override
    public void saveSurveyIds(@NonNull String path, @NonNull Map<String, Boolean> surveyIds) {
        mTriibeServiceApi.saveSurveyIds(path, surveyIds);
    }

    @Override
    public void getSurvey(@NonNull String surveyId, @NonNull final GetSurveyCallback callback) {
        mTriibeServiceApi.getSurvey(surveyId, new TriibeServiceApi.GetSurveyCallback() {
            @Override
            public void onSurveyLoaded(@Nullable SurveyDetails survey) {
                callback.onSurveyLoaded(survey);
            }
        });
    }

    @Override
    public void saveSurvey(@NonNull String surveyId, @NonNull SurveyDetails surveyDetails) {
        mTriibeServiceApi.saveSurvey(surveyId, surveyDetails);
    }

    @Override
    public void deleteSurvey(@NonNull String surveyId) {
        mTriibeServiceApi.deleteSurvey(surveyId);
    }


    /*
    * Questions
    * */
    @Override
    public void getQuestions(@NonNull String surveyId,
                             @NonNull final GetQuestionsCallback callback) {
        if (mCachedQuestions == null) {
            mTriibeServiceApi.getQuestions(surveyId, new TriibeServiceApi.GetQuestionsCallback() {
                @Override
                public void onQuestionsLoaded(@Nullable Map<String, Question> questions) {
                    if (questions != null) {
                        mCachedQuestions = ImmutableMap.copyOf(questions);
                    }
                    callback.onQuestionsLoaded(mCachedQuestions);
                }
            });
        } else {
            callback.onQuestionsLoaded(mCachedQuestions);
        }
    }

    @Override
    public void refreshQuestions() {
        mCachedQuestions = null;
    }

    @Override
    public void getQuestionIds(@NonNull String path, @NonNull final GetQuestionIdsCallback callback) {
        if (mCachedQuestionIds == null) {
            mTriibeServiceApi.getQuestionIds(path, new TriibeServiceApi.GetQuestionIdsCallback() {
                @Override
                public void onQuestionIdsLoaded(@Nullable Map<String, Boolean> questionIds) {
                    if (questionIds != null) {
                        mCachedQuestionIds = ImmutableMap.copyOf(questionIds);
                    }
                    callback.onQuestionIdsLoaded(mCachedQuestionIds);
                }
            });
        } else {
            callback.onQuestionIdsLoaded(mCachedQuestionIds);
        }
    }

    @Override
    public void refreshQuestionIds() {
        mCachedQuestionIds = null;
    }

    @Override
    public void saveQuestionIds(@NonNull String path, @NonNull Map<String, Boolean> questionIds) {
        mTriibeServiceApi.saveQuestionIds(path, questionIds);
    }

    @Override
    public void getQuestion(@NonNull String surveyId, @NonNull String questionId,
                            @NonNull final GetQuestionCallback callback) {
        mTriibeServiceApi.getQuestion(surveyId, questionId,
                new TriibeServiceApi.GetQuestionCallback() {
            @Override
            public void onQuestionLoaded(@Nullable QuestionDetails question) {
                callback.onQuestionLoaded(question);
            }
        });
    }

    @Override
    public void saveQuestion(@NonNull String surveyId, @NonNull String questionId,
                             @NonNull QuestionDetails questionDetails) {
        mTriibeServiceApi.saveQuestion(surveyId, questionId, questionDetails);
    }

    @Override
    public void deleteQuestion(@NonNull String surveyId, @NonNull String questionId) {
        mTriibeServiceApi.deleteQuestion(surveyId, questionId);
    }


    /*
    * Options
    * */
    @Override
    public void getOptionIds(@NonNull String path, @NonNull final GetOptionIdsCallback callback) {
        if (mCachedOptionIds == null) {
            mTriibeServiceApi.getOptionIds(path, new TriibeServiceApi.GetOptionIdsCallback() {
                @Override
                public void onOptionIdsLoaded(@Nullable Map<String, Boolean> optionIds) {
                    if (optionIds != null) {
                        mCachedOptionIds = ImmutableMap.copyOf(optionIds);
                    }
                    callback.onOptionIdsLoaded(mCachedOptionIds);
                }
            });
        } else {
            callback.onOptionIdsLoaded(mCachedOptionIds);
        }
    }

    @Override
    public void refreshOptionIds() {
        mCachedOptionIds = null;
    }

    @Override
    public void getOptions(@NonNull String surveyId, @NonNull String questionId, @NonNull final GetOptionsCallback callback) {
        if (mCachedOptions == null) {
            mTriibeServiceApi.getOptions(surveyId, questionId, new TriibeServiceApi.GetOptionsCallback() {
                @Override
                public void onOptionsLoaded(@Nullable Map<String, Option> options) {
                    if (options != null) {
                        mCachedOptions = ImmutableMap.copyOf(options);
                    }
                    callback.onOptionsLoaded(mCachedOptions);
                }
            });
        } else {
            callback.onOptionsLoaded(mCachedOptions);
        }
    }

    @Override
    public void refreshOptions() {
        mCachedOptions = null;
    }

    @Override
    public void saveOptionIds(@NonNull String path, @NonNull Map<String, Boolean> optionIds) {
        mTriibeServiceApi.saveOptionIds(path, optionIds);
    }

    @Override
    public void getOption(@NonNull String surveyId, @NonNull String questionId, @NonNull String optionId, @NonNull final GetOptionCallback callback) {
        mTriibeServiceApi.getOption(surveyId, questionId, optionId, new TriibeServiceApi.GetOptionCallback() {
            @Override
            public void onOptionLoaded(@Nullable Option option) {
                callback.onOptionLoaded(option);
            }
        });
    }

    @Override
    public void saveOption(@NonNull String surveyId, @NonNull String questionId, @NonNull String optionId, @NonNull Option option) {
        mTriibeServiceApi.saveOption(surveyId, questionId, optionId, option);
    }

    @Override
    public void deleteOption(@NonNull String surveyId, @NonNull String questionId, @NonNull String optionId) {
        mTriibeServiceApi.deleteOption(surveyId, questionId, optionId);
    }


    /*
    * Triggers
    * */


    /*
    * Answers
    * */
    @Override
    public void getAnswers(@NonNull String surveyId, @NonNull String userId,
                           @NonNull final GetAnswersCallback callback) {
        if (mCachedAnswers == null) {
            mTriibeServiceApi.getAnswers(surveyId, userId,
                    new TriibeServiceApi.GetAnswersCallback() {
                @Override
                public void onAnswersLoaded(@Nullable Map<String, Answer> answers) {
                    if (answers != null) {
                        mCachedAnswers = ImmutableMap.copyOf(answers);
                    }
                    callback.onAnswersLoaded(mCachedAnswers);
                }
            });
        } else {
            callback.onAnswersLoaded(mCachedAnswers);
        }
    }

    @Override
    public void refreshAnswers() {
        mCachedAnswers = null;
    }

    @Override
    public void getAnswer(@NonNull String surveyId, @NonNull String questionId,
                          @NonNull final GetAnswerCallback callback) {
        mTriibeServiceApi.getAnswer(surveyId, questionId, new TriibeServiceApi.GetAnswerCallback() {
            @Override
            public void onAnswerLoaded(@Nullable AnswerDetails answer) {
                callback.onAnswerLoaded(answer);
            }
        });
    }

    @Override
    public void saveAnswer(@NonNull String surveyId, @NonNull String userId,
                           @NonNull String questionId, @NonNull Answer answer) {
        mTriibeServiceApi.saveAnswer(surveyId, userId, questionId, answer);
    }
}