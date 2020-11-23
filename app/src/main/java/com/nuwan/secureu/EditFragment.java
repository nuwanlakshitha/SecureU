package com.nuwan.secureu;


import android.content.res.ColorStateList;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private Button btnUpadate, btnEditCancel;
    private EditText editUsername,editPassword,editConfirmPassword;
    private Spinner editUrl;
    private List<UserCredential> userCredentials;
    private TextView iconEditPassword,iconEditConfirmPassword;
    private boolean passwordVisibility,confirmPasswordVisibility;

    public EditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        editUrl = view.findViewById(R.id.editUrl);
        editUsername = view.findViewById(R.id.editUsername);
        editPassword = view.findViewById(R.id.editPassword);
        editConfirmPassword = view.findViewById(R.id.editConfirmPassword);
        btnUpadate = view.findViewById(R.id.btnUpdate);
        btnEditCancel = view.findViewById(R.id.btnEditCancel);
        iconEditPassword = view.findViewById(R.id.iconEditPassword);
        iconEditConfirmPassword = view.findViewById(R.id.iconEditConfirmPassword);
        passwordVisibility = false;
        confirmPasswordVisibility = false;

        iconEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordVisibility){
                    passwordVisibility = false;
                    editPassword.setTransformationMethod(new PasswordTransformationMethod());
                    iconEditPassword.setBackgroundResource(R.mipmap.ic_visibility_off);
                }else {
                    passwordVisibility = true;
                    editPassword.setTransformationMethod(null);
                    iconEditPassword.setBackgroundResource(R.mipmap.ic_visibility);
                }
            }
        });

        iconEditConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirmPasswordVisibility){
                    confirmPasswordVisibility = false;
                    editConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                    iconEditConfirmPassword.setBackgroundResource(R.mipmap.ic_visibility_off);
                }else {
                    confirmPasswordVisibility = true;
                    editConfirmPassword.setTransformationMethod(null);
                    iconEditConfirmPassword.setBackgroundResource(R.mipmap.ic_visibility);
                }
            }
        });

        loadUrls();

        btnEditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.fragmentManager.beginTransaction().replace(R.id.container,new ContainerFragment()).commit();
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        if(position!=0){
            editUsername.setText(userCredentials.get(position-1).getUsername());
            editPassword.setText(userCredentials.get(position-1).getPassword());
            editConfirmPassword.setText(userCredentials.get(position-1).getPassword());
            btnUpadate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newPassword = editPassword.getText().toString();
                    String newConfirmPassword = editConfirmPassword.getText().toString();
                    if(newPassword.equals(newConfirmPassword)){
                        UserCredential userCredential = new UserCredential(userCredentials.get(position-1).getUrl(),editUsername.getText().toString(),newPassword);
                        MainActivity.database.dao().editUserCredential(userCredential);
                        loadUrls();
                        Toast.makeText(getActivity(),"Successfully updated!",Toast.LENGTH_SHORT).show();
                        editUsername.setText("");
                    }else{
                        Toast.makeText(getActivity(),"Passwords mismatched!",Toast.LENGTH_SHORT).show();
                    }
                    editPassword.setText("");
                    editConfirmPassword.setText("");
                }
            });
        }else{
            btnUpadate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"Select a URL...",Toast.LENGTH_SHORT).show();
                }
            });
            editUsername.setText("");
            editPassword.setText("");
            editConfirmPassword.setText("");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void loadUrls(){
        userCredentials = MainActivity.database.dao().viewUserCredential();
        ArrayList<String> urls = new ArrayList<String>();
        urls.add("Select a URL...");
        for (UserCredential userCredential:userCredentials){
            urls.add(userCredential.getUrl());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,urls);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editUrl.setAdapter(adapter);
        editUrl.setOnItemSelectedListener(this);
    }

}
