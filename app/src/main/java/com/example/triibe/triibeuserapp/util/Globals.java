package com.example.triibe.triibeuserapp.util;

import com.example.triibe.triibeuserapp.data.User;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Singleton for storing global variables.
 *
 * @author michael
 */
public class Globals {

    public static final int NUM_QUALIFYING_QUESTIONS = 2;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private volatile static Globals uniqueInstance;
    private boolean firebasePersistenceSet;
    private volatile User mUser;
//    public static GoogleMap mMap;

    private Globals() {}

    public static Globals getInstance() {
        if (uniqueInstance == null) {
            synchronized (Globals.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Globals();
                }
            }
        }
        return uniqueInstance;
    }

    public boolean isFirebasePersistenceSet() {
        return firebasePersistenceSet;
    }

    public void setFirebasePersistenceEnabled() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        this.firebasePersistenceSet = true;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
