package com.example.market.ui.me.login;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.market.FirebaseRepository;
import com.example.market.models.User;
import com.example.market.prevalent.Prevalent;
import com.google.firebase.database.*;

public class MeLoginViewModel extends ViewModel {
    private final static String LOG_TAG = "me login view model";

    String username;
    Prevalent prevalent = new Prevalent();

    private MutableLiveData<Integer> loginStatus;
    private static String parentDB = "user";
    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(parentDB);
    private final FirebaseRepository liveData = new FirebaseRepository(databaseReference);


    public MeLoginViewModel() {
        loginStatus = new MutableLiveData<>();
        loginStatus.setValue(0);
        username = "";
    }


    public LiveData<Integer> getLoginStatus() {
//        default 0, wrong password -2, haven't register -1, success 1, refuse -3
        return loginStatus;
    }

    public void validate(final String phone, final String password) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists()) {
                    Log.i(LOG_TAG, phone);
                    User userdata = dataSnapshot.child(phone).getValue(User.class);
                    if (userdata.getPhoneNumber().equals(phone) && userdata.getPassword().equals(password)) {
                        loginStatus.setValue(1);
                        username = userdata.getName();
                        prevalent.setLogin(true);
                        prevalent.setPhone(phone);
                    } else {
                        loginStatus.setValue(-2);
                    }
                } else {
                    loginStatus.setValue(-1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void refuseValidate() {
        loginStatus.setValue(-3);
    }

}
