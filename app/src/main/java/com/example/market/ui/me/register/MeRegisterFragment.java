package com.example.market.ui.me.register;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.market.R;
import com.google.android.material.snackbar.Snackbar;

public class MeRegisterFragment extends Fragment {
    private MeRegisterViewModel meRegisterViewModel;

    private EditText etRegisterName;
    private EditText etRegisterPhoneNumber;
    private EditText etRegisterPassword;
    private EditText etRegisterConfirmPassword;

    private TextView tvRegisterName;
    private TextView tvRegisterPhoneNumber;
    private TextView tvRegisterPassword;
    private TextView tvRegisterConfirmPassword;

    private ColorStateList tvRegisterNameOldColor;
    private ColorStateList tvRegisterPhoneNumberOldColor;
    private ColorStateList tvRegisterPasswordOldColor;
    private ColorStateList tvRegisterConfirmPasswordOldColor;

    private float tvRegisterNameOldTextSize;
    private float tvRegisterPhoneNumberOldTextSize;
    private float tvRegisterPasswordOldTextSize;
    private float tvRegisterConfirmPasswordOldTextSize;

    private Button registerButton;
    private Button goBackButton;
    private boolean success = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        meRegisterViewModel = new ViewModelProvider(getActivity()).get(MeRegisterViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_me_register, container, false);

        etRegisterName = root.findViewById(R.id.edit_text_register_name);
        etRegisterPhoneNumber = root.findViewById(R.id.edit_text_register_phone_number);
        etRegisterPassword = root.findViewById(R.id.edit_text_register_password);
        etRegisterConfirmPassword = root.findViewById(R.id.edit_text_register_confirm_password);

        tvRegisterName = root.findViewById(R.id.text_view_register_name);
        tvRegisterPhoneNumber = root.findViewById(R.id.text_view_register_phone_number);
        tvRegisterPassword = root.findViewById(R.id.text_view_register_password);
        tvRegisterConfirmPassword = root.findViewById(R.id.text_view_register_confirm_password);

        tvRegisterNameOldColor =  tvRegisterName.getTextColors();
        tvRegisterPhoneNumberOldColor = tvRegisterPhoneNumber.getTextColors();
        tvRegisterPasswordOldColor =  tvRegisterPassword.getTextColors();
        tvRegisterConfirmPasswordOldColor = tvRegisterConfirmPassword.getTextColors();

        tvRegisterNameOldTextSize = tvRegisterName.getTextSize();
        tvRegisterPhoneNumberOldTextSize = tvRegisterPhoneNumber.getTextSize();
        tvRegisterPasswordOldTextSize = tvRegisterPassword.getTextSize();
        tvRegisterConfirmPasswordOldTextSize = tvRegisterConfirmPassword.getTextSize();

        registerButton = root.findViewById(R.id.button_register);

        //        set text color and text size when focused and check if null
        MyOnFocusChangeListener(etRegisterName, tvRegisterName, tvRegisterNameOldColor, tvRegisterNameOldTextSize);
        MyOnFocusChangeListener(etRegisterPhoneNumber, tvRegisterPhoneNumber, tvRegisterPhoneNumberOldColor, tvRegisterPhoneNumberOldTextSize);
        MyOnFocusChangeListener(etRegisterPassword, tvRegisterPassword, tvRegisterPasswordOldColor, tvRegisterPasswordOldTextSize);
        MyOnFocusChangeListener(etRegisterConfirmPassword, tvRegisterConfirmPassword, tvRegisterConfirmPasswordOldColor, tvRegisterConfirmPasswordOldTextSize);

//        register
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
//                goBackButton = root.findViewById(R.id.button_go_back);
//                if (success == true) {
//                    goBackButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            getActivity().onBackPressed();
//                        }
//                    });
//                    goBackButton.setVisibility(View.VISIBLE);
//                }
            }
        });


        return root;
    }


    private void CreateAccount() {
        String name = etRegisterName.getText().toString();
        String phone = etRegisterPhoneNumber.getText().toString();
        String password = etRegisterPassword.getText().toString();
        String confirmPassword = etRegisterConfirmPassword.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)
                && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
            if (!confirmPassword.equals(password)) {
                Toast.makeText(getContext(), "Passwords are not the same", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(), "Registering", Toast.LENGTH_SHORT).show();
                ValidatePhoneNumber(name, phone, password);
                Toast.makeText(getContext(), "Please login", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (TextUtils.isEmpty(name)) {
                etRegisterName.setHintTextColor(Color.RED);
                etRegisterName.setHint("Required");
            }
            if (TextUtils.isEmpty(phone)) {
                etRegisterPhoneNumber.setHintTextColor(Color.RED);
                etRegisterPhoneNumber.setHint("Required");
            }
            if (TextUtils.isEmpty(password)) {
                etRegisterPassword.setHintTextColor(Color.RED);
                etRegisterPassword.setHint("Required");
            }
            if (TextUtils.isEmpty(confirmPassword)) {
                etRegisterConfirmPassword.setHintTextColor(Color.RED);
                etRegisterConfirmPassword.setHint("Required");
            }
        }

    }

    private void ValidatePhoneNumber(String name, String phone, String password) {
        meRegisterViewModel.insertNewUser(name, phone, password);
        meRegisterViewModel.getRegisterStatus().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@NonNull Integer integer) {
                if (integer == -1) {
//                    Toast.makeText(getContext(), "This phone number has been registered", Toast.LENGTH_SHORT).show();
                    Snackbar.make(getView(), R.string.has_been_register, Snackbar.LENGTH_SHORT).show();
                } else if (integer == -2) {
//                    Toast.makeText(getContext(), "Network error, please try later", Toast.LENGTH_SHORT).show();
                    Snackbar.make(getView(), R.string.network_issue, Snackbar.LENGTH_SHORT).show();
                } else if (integer == 1) {
//                    Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                    Snackbar.make(getView(), R.string.success, Snackbar.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                    success = true;
                }
            }
        });
    }

    private void MyOnFocusChangeListener(@NonNull  final EditText et, final TextView tv, final ColorStateList oldColor, final float oldTextSize) {
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
