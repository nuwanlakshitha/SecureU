package com.nuwan.secureu;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private Spinner viewUrl;
    private TextView viewUsername,viewPassword,iconViewPassword;
    private List<UserCredential> userCredentials;
    private Button btnOk;
    private boolean passwordVisibility;

    public ViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        viewUrl = view.findViewById(R.id.viewUrl);
        viewUsername = view.findViewById(R.id.viewUsername);
        viewPassword = view.findViewById(R.id.viewPassword);
        btnOk = view.findViewById(R.id.btnOk);
        iconViewPassword = view.findViewById(R.id.iconViewPassword);
        passwordVisibility = false;

        iconViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordVisibility){
                    passwordVisibility = false;
                    viewPassword.setTransformationMethod(new PasswordTransformationMethod());
                    iconViewPassword.setBackgroundResource(R.mipmap.ic_visibility_off);
                }else {
                    passwordVisibility = true;
                    viewPassword.setTransformationMethod(null);
                    iconViewPassword.setBackgroundResource(R.mipmap.ic_visibility);
                }
            }
        });

        userCredentials = MainActivity.database.dao().viewUserCredential();
        ArrayList<String> urls = new ArrayList<String>();
        urls.add("Select a url...");
        for (UserCredential userCredential : userCredentials) {
            urls.add(userCredential.getUrl());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, urls);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewUrl.setAdapter(adapter);
        viewUrl.setOnItemSelectedListener(this);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.fragmentManager.beginTransaction().replace(R.id.container,new ContainerFragment()).commit();
            }
        });
        return  view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position!=0){
            viewUsername.setText(userCredentials.get(position-1).getUsername());
            viewPassword.setText(userCredentials.get(position-1).getPassword());
            viewUsername.setTextColor(getResources().getColor(R.color.colorBlack));
            viewPassword.setTextColor(getResources().getColor(R.color.colorBlack));
        }else{
            viewUsername.setText("");
            viewPassword.setText("");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
