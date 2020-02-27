package com.example.market;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.google.firebase.database.*;

public class FirebaseRepository extends LiveData<DataSnapshot> {
    private static final String LOG_TAG = "Firebase Repository";
    private final MyValueEventListener listener = new MyValueEventListener();
    private final DatabaseReference databaseReference;
    boolean[] success = {false, false};
    int done = 0;

//    public FirebaseRepository(Query query) {
//        this.query = query;
//    }

    public FirebaseRepository(DatabaseReference databaseReference) {
        this.databaseReference  = databaseReference;
    }

    @Override
    protected void onActive() {
        databaseReference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        databaseReference.removeEventListener(listener);
    }


    private class MyValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(dataSnapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(LOG_TAG, "can't listen" + databaseReference, databaseError.toException());
        }
    }
}