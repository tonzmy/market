package com.example.market.ui.me.login;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.market.R;
import com.example.market.prevalent.Prevalent;
import com.google.android.material.snackbar.Snackbar;

public class MeLoginFragment extends Fragment {
    private static final String LOG_TAG = "MeLoginFragment";
    private int previousStatus;
    private int beforePreviousStatus = 0;
    private MeLoginViewModel meLoginViewModel;
    private Button loginButton;
    private Button registerButton;
    private EditText etLoginAccount;
    private EditText etLoginPassword;
    private TextView tvLoginAccount;
    private TextView tvLoginPassword;
    private ColorStateList tvLoginAccountOldColor;
    private ColorStateList tvLoginPasswordOldColor;
    private float tvLoginAccountOldTextSize;
    private float tvLoginPasswordOldTextSize;
    public Prevalent prevalent = new Prevalent();
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        meLoginViewModel = new ViewModelProvider(getActivity()).get(MeLoginViewModel.class);
//        meLoginViewModel = ViewModelProviders.of(getActivity()).get(MeLoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_me_login, container, false);
        loginButton = root.findViewById(R.id.button_login);

        etLoginAccount = root.findViewById(R.id.edit_text_login_account);
        etLoginPassword = root.findViewById(R.id.edit_text_login_password);

        tvLoginAccount = root.findViewById(R.id.text_view_login_account);
        tvLoginPassword = root.findViewById(R.id.text_view_login_password);

        tvLoginAccountOldColor =  tvLoginAccount.getTextColors();
        tvLoginPasswordOldColor =  tvLoginPassword.getTextColors();

        tvLoginAccountOldTextSize = tvLoginAccount.getTextSize();
        tvLoginPasswordOldTextSize = tvLoginPassword.getTextSize();

//        set text color and text size when focused and check if null
        MyOnFocusChangeListener(etLoginAccount, tvLoginAccount, tvLoginAccountOldColor, tvLoginAccountOldTextSize);
        MyOnFocusChangeListener(etLoginPassword, tvLoginPassword, tvLoginPasswordOldColor, tvLoginPasswordOldTextSize);
        registerButton = root.findViewById(R.id.button_register);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        registerButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.navigation_me_register));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etLoginAccount.setImeOptions(EditorInfo.IME_ACTION_DONE);
                etLoginPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
                LoginUser();
            }
        });

        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
//                meLoginViewModel.refuseValidate();
                navController.popBackStack(R.id.navigation_home, false);
            }
        });
    }


    private void LoginUser() {
        String phone = etLoginAccount.getText().toString();
        String password = etLoginPassword.getText().toString();
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(phone)) {
                AllowAccess(phone, password);
        } else {
            if (TextUtils.isEmpty(phone)) {
                etLoginAccount.setHintTextColor(Color.RED);
                etLoginAccount.setHint("Required");
            }
            if (TextUtils.isEmpty(password)) {
                etLoginPassword.setHintTextColor(Color.RED);
                etLoginPassword.setHint("Required");
            }
        }
    }

    private void AllowAccess(final String phone, final String password) {
        int count = 0;
        final View root = getView();
        meLoginViewModel.validate(phone, password);

        meLoginViewModel.getLoginStatus().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@NonNull Integer integer) {
                if (integer == 1 && previousStatus != 1) {
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    prevalent.login = true;
                    previousStatus = 1;
//                    navController.popBackStack(R.id.navigation_me, false);
                    navController.popBackStack();
//                    getActivity().onBackPressed();
//                    Navigation.findNavController(getView()).navigate(R.id.navigation_me_order);
                } else if (integer == -1 && previousStatus != -1) {
                    previousStatus = -1;
                    Snackbar.make(root, R.string.not_register_yet, Snackbar.LENGTH_SHORT).show();
                } else if (integer == -2 && previousStatus != -2) {
                    previousStatus = -2;
                    Snackbar.make(root, R.string.invalid_credentials, Snackbar.LENGTH_SHORT).show();
                } else {
                    tvLoginAccount.setTextColor(getResources().getColor(R.color.colorWarning));
                    tvLoginPassword.setTextColor(getResources().getColor(R.color.colorWarning));
                }
            }
        });


    }

    private void MyOnFocusChangeListener(@NonNull final EditText et, final TextView tv, final ColorStateList oldColor, final float oldTextSize) {
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    set text color and text size when focused
                    tv.setTextColor(getResources().getColor(R.color.colorTextDark));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
                    et.setHint("");
                } else {
                    tv.setTextColor(oldColor);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, oldTextSize);
                    if (et.getText().toString().equals("")) {
                        et.setHintTextColor(Color.RED);
                        et.setHint("Required");
                    }
                }
            }
        });
    }

}
