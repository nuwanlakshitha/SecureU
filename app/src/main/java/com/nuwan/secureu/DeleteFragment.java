package com.nuwan.secureu;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private Button btnDelete,btnDeleteCancel;
    private Spinner deleteUrl;
    private List<UserCredential> userCredentials;

    public DeleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        deleteUrl = view.findViewById(R.id.deleteUrl);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnDeleteCancel = view.findViewById(R.id.btnDeleteCancel);

        loadUrls();

        btnDeleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.fragmentManager.beginTransaction().replace(R.id.container,new ContainerFragment()).commit();
            }
        });
        return view;
    }

    private void loadUrls(){
        userCredentials = MainActivity.database.dao().viewUserCredential();
        ArrayList<String> urls = new ArrayList<String>();
        urls.add("Select a URL...");
        for(UserCredential userCredential : userCredentials){
            urls.add(userCredential.getUrl());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,urls);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deleteUrl.setAdapter(adapter);
        deleteUrl.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        if(position!=0){
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Are you sure you want to delete this URL?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.database.dao().deleteUserCredential(userCredentials.get(position-1));
                            Toast.makeText(getActivity(),"Successfully Deleted!",Toast.LENGTH_SHORT).show();
                            loadUrls();
                        }
                    }).setNegativeButton("No",null);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }else{
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"Select a URL...",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
