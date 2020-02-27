package com.example.market.ui.me.register;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.market.FirebaseRepository;
import com.example.market.models.Order;
import com.example.market.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;

import java.util.Map;

public class MeRegisterViewModel extends ViewModel {
    private static final String LOG_TAG = "MeRegisterViewModel";
    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> registerStatus;
    private static String parentDB = "user";
    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(parentDB);
    private final FirebaseRepository liveData = new FirebaseRepository(databaseReference);

    public MeRegisterViewModel() {
        registerStatus = new MutableLiveData<>();
        registerStatus.setValue(0);
    }

    public LiveData<Integer> getRegisterStatus() {
//        default 0, exist -1, success 1, failure -2
        return registerStatus;
    }


    public void insertNewUser(final String name, final String phone, final String password) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(phone).exists()) {
                    Order newOrder = new Order();
                    User user = new User(name, phone, password);
                    Map<String, Object> data = user.toMap();
                    databaseReference.child(phone).updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                registerStatus.setValue(1);
                            } else {
                                registerStatus.setValue(-2);
                            }
                        }
                    });
                } else {
                    registerStatus.setValue(-1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(LOG_TAG, "error", databaseError.toException());
            }
        });
    }
}
