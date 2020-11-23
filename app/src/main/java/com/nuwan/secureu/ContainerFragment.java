package com.nuwan.secureu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContainerFragment extends Fragment implements View.OnClickListener{

    private Button btnAdd,btnView,btnEdit,btnDelete;

    public ContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnView = view.findViewById(R.id.btnView);
        btnView.setOnClickListener(this);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd: HomeActivity.fragmentManager.beginTransaction().replace(R.id.container,new AddFragment()).addToBackStack(null).commit();
            break;

            case R.id.btnView: HomeActivity.fragmentManager.beginTransaction().replace(R.id.container,new ViewFragment()).addToBackStack(null).commit();
            break;

            case R.id.btnEdit: HomeActivity.fragmentManager.beginTransaction().replace(R.id.container,new EditFragment()).addToBackStack(null).commit();
            break;

            case R.id.btnDelete: HomeActivity.fragmentManager.beginTransaction().replace(R.id.container,new DeleteFragment()).addToBackStack(null).commit();
            break;
        }
    }


}
