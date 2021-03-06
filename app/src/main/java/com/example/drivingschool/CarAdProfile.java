package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class CarAdProfile extends Fragment {

    private long backPressedTime;

    DrawerLayout drawerLayout;
    TextInputLayout cr_name, cr_model, cr_company, cr_color;
    ProgressBar add_progess_bar;
    Button addDataCar;

    FirebaseDatabase rootNode;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_car_ad_profile, container, false);
//        viewPager = (ViewPager) rootView .findViewById(R.id.viewPager);

        //setContentView(R.layout.activity_car_ad_profile);
        drawerLayout = rootView.findViewById(R.id.drawer_layout);




        cr_name = rootView.findViewById(R.id.cr_name);
        cr_model = rootView.findViewById(R.id.cr_model);
        cr_company = rootView.findViewById(R.id.cr_company);
        cr_color = rootView.findViewById(R.id.cr_color);
        add_progess_bar = rootView.findViewById(R.id.add_progess_bar);

        //db Firebase instance
        rootNode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        addDataCar = rootView.findViewById(R.id.add_btn);
        addDataCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = cr_name.getEditText().getText().toString();
                String model = cr_model.getEditText().getText().toString();
                String company = cr_company.getEditText().getText().toString();
                String color = cr_color.getEditText().getText().toString();

                //validate
                if(!validateName() | !validateModel() | !validateCompany() | !validateColor()) {
                    return;
                }
                else{
                    processinsertCar(name, model, company, color);
                }
            }
        });

        return rootView;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    private Boolean validateName () {
        String val = cr_name.getEditText().getText().toString();
        if(val.isEmpty()) {
            cr_name.setError("Field cannot be empty");
            return false;
        }
        else{
            cr_name.setError(null);
            cr_name.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateModel () {
        String val = cr_model.getEditText().getText().toString();
        if(val.isEmpty()) {
            cr_model.setError("Field cannot be empty");
            return false;
        }
        else{
            cr_model.setError(null);
            cr_model.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateCompany () {
        String val = cr_company.getEditText().getText().toString();
        if(val.isEmpty()) {
            cr_company.setError("Field cannot be empty");
            return false;
        }
        else{
            cr_company.setError(null);
            cr_company.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateColor () {
        String val = cr_color.getEditText().getText().toString();
        if(val.isEmpty()) {
            cr_color.setError("Field cannot be empty");
            return false;
        }
        else{
            cr_color.setError(null);
            cr_color.setErrorEnabled(false);
            return true;
        }
    }

    //save data in firebase on button click
//    public void addDataCar(View view) {
//        String name = cr_name.getEditText().getText().toString();
//        String model = cr_model.getEditText().getText().toString();
//        String company = cr_company.getEditText().getText().toString();
//        String color = cr_color.getEditText().getText().toString();
//        //validate
//        if(!validateName() | !validateModel() | !validateCompany() | !validateColor()) {
//            return;
//        }
//        else{
//            processinsertCar(name, model, company, color);
//        }
//    }

    private void processinsertCar(String name, String model, String company, String color) {
        add_progess_bar.setVisibility(View.VISIBLE);
        reference = rootNode.getReference("car").child(name); //realtimedb
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("model", model);
        hashMap.put("company", company);
        hashMap.put("color", color);
        // Car add
        hashMap.put("isCar", "1");

        reference.setValue(hashMap).addOnCompleteListener(task1 -> {
            add_progess_bar.setVisibility(View.GONE);
            if (task1.isSuccessful()) {
//                Fragment fm = new Fragment();
//                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//                ft.add(R.id.drawer_layout, new CarAdminCard()).commit();
                Toast.makeText(getContext(), "Car Registration Successful", Toast.LENGTH_SHORT).show();


            }
            else {
                Toast.makeText(getContext(), Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}