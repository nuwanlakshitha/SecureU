package com.nuwan.secureu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {

    private List<String> urls;
    private EditText addUrl,addUsername,addPassword,addConfirmPassword;
    private Button btnSave,btnAddCancel;
    private TextView iconAddPassword,iconAddConfirmPassword;
    private boolean passwordVisibility,confirmPasswordVisibility;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        addUrl = view.findViewById(R.id.addUrl);
        addUsername = view.findViewById(R.id.addUsername);
        addPassword = view.findViewById(R.id.addPassword);
        addConfirmPassword = view.findViewById(R.id.addConfirmPassword);
        iconAddPassword = view.findViewById(R.id.iconAddPassword);
        iconAddConfirmPassword = view.findViewById(R.id.iconAddConfirmPassword);
        btnSave = view.findViewById(R.id.btnSave);
        btnAddCancel = view.findViewById(R.id.btnAddCancel);
        passwordVisibility = false;
        confirmPasswordVisibility = false;

        iconAddPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordVisibility){
                    passwordVisibility = false;
                    addPassword.setTransformationMethod(new PasswordTransformationMethod());
                    iconAddPassword.setBackgroundResource(R.mipmap.ic_visibility_off);
                }else {
                    passwordVisibility = true;
                    addPassword.setTransformationMethod(null);
                    iconAddPassword.setBackgroundResource(R.mipmap.ic_visibility);
                }
            }
        });

        iconAddConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirmPasswordVisibility){
                    confirmPasswordVisibility = false;
                    addConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                    iconAddConfirmPassword.setBackgroundResource(R.mipmap.ic_visibility_off);
                }else {
                    confirmPasswordVisibility = true;
                    addConfirmPassword.setTransformationMethod(null);
                    iconAddConfirmPassword.setBackgroundResource(R.mipmap.ic_visibility);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urls = MainActivity.database.dao().getUrls();
                if (addUrl.getText().toString().equals("")||addUsername.getText().toString().equals("")||addPassword.getText().toString().equals("")||addConfirmPassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity(),"Incomplete details!",Toast.LENGTH_SHORT).show();
                }else{
                    if (!urls.contains(addUrl.getText().toString())) {
                        String password = addPassword.getText().toString();
                        String confirmPasword = addConfirmPassword.getText().toString();
                        if (password.equals(confirmPasword)) {
                            UserCredential userCredential = new UserCredential(addUrl.getText().toString(), addUsername.getText().toString(), addPassword.getText().toString());
                            MainActivity.database.dao().addUserCredential(userCredential);
                            Toast.makeText(getActivity(), "Successfully added!", Toast.LENGTH_LONG).show();
                            addUrl.setText("");
                            addUsername.setText("");
                        } else {
                            Toast.makeText(getActivity(), "Passwords mismatched!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "URL is already existed!", Toast.LENGTH_SHORT).show();
                        addUrl.setText("");
                        addUsername.setText("");
                    }
                    addPassword.setText("");
                    addConfirmPassword.setText("");
                }
            }
        });
        btnAddCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.fragmentManager.beginTransaction().replace(R.id.container,new ContainerFragment()).commit();
            }
        });
        return view;
    }


}


