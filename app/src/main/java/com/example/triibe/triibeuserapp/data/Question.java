package com.example.triibe.triibeuserapp.data;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Question entity.
 *
 * @author michael
 */
@IgnoreExtraProperties
public class Question {

    private String mId;
    private String mImageUrl;
    private String mTitle;
    private String mIntro;
    private Query mQuery;
    private String mIntroLinkKey;
    private String mIntroLinkUrl;
    private String mAction;

    // Empty constructor required for firebase
    public Question() {}

    public Question(String id, String imageUrl, String title, String intro, Query query) {
        mId = id;
        mImageUrl = imageUrl;
        mTitle = title;
        mIntro = intro;
        mQuery = query;
    }

    /*
    * All setters are getters required by firebase even if not used in the program.
    *
    * Note: getters must be of the form "get<parameter name>".
    * Boolean values cannot use "hasExtraValue" for example.
    * */
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getIntro() {
        return mIntro;
    }

    public void setIntro(String intro) {
        mIntro = intro;
    }

    public Query getQuery() {
        return mQuery;
    }

    public void setQuery(Query query) {
        mQuery = query;
    }

    public String getIntroLinkKey() {
        return mIntroLinkKey;
    }

    public void setIntroLinkKey(String introLinkKey) {
        mIntroLinkKey = introLinkKey;
    }

    public String getIntroLinkUrl() {
        return mIntroLinkUrl;
    }

    public void setIntroLinkUrl(String introLinkUrl) {
        mIntroLinkUrl = introLinkUrl;
    }
}
