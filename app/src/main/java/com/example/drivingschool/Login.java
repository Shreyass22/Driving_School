package com.example.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Login extends AppCompatActivity {

    Button login_btn, btn_signup, forget_password;
    ImageView imageView;
    TextView logo_name, slogan_name;
    TextInputLayout login_e_mail, login_password;
    ProgressBar login_progess_bar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    DatabaseReference databaseReference;
    Switch active;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        imageView = findViewById(R.id.imageView);
        logo_name = findViewById(R.id.logo_name);
        slogan_name = findViewById(R.id.slogan_name);
        login_e_mail = findViewById(R.id.login_e_mail);
        login_password = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        btn_signup = findViewById(R.id.btn_signup);
        login_progess_bar = findViewById(R.id.login_progess_bar);
        forget_password = findViewById(R.id.forget_password);
//        active = findViewById(R.id.active);


        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        forget_password.setOnClickListener(v -> startActivity(new Intent(Login.this, ForgetPassword.class)));


    }

    private Boolean validateUsername () {
        String val = login_e_mail.getEditText().getText().toString();
        if(val.isEmpty()) {
            login_e_mail.setError("Field cannot be empty");
            return false;
        }
        else{
            login_e_mail.setError(null);
            login_e_mail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword () {
        String val = login_password.getEditText().getText().toString();
        if(val.isEmpty()) {
            login_password.setError("Field cannot be empty");
            return false;
        }
        else{
            login_password.setError(null);
            login_password.setErrorEnabled(false);
            return true;
        }
    }

    //animation
    public void btn_signup(View view) {
        //animation start
        Intent intent = new Intent(Login.this, SignUp.class);
        this.finish();
        Pair[] pairs = new Pair[6];
        pairs[0] = new Pair<View,String>(imageView,"logo_image");
        pairs[1] = new Pair<View,String>(logo_name,"logo_name");
        pairs[2] = new Pair<View,String>(slogan_name,"slogan_name");
        pairs[3] = new Pair<View,String>(login_e_mail,"login_e_mail");
        pairs[4] = new Pair<View,String>(login_password,"login_password");
        pairs[5] = new Pair<View,String>(login_btn,"login_btn");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
        startActivity(intent,options.toBundle());
        //animation end
    }


    //check validation
    public void login_btn(View view) {
        String userEnteredName = login_e_mail.getEditText().getText().toString().trim();
        String userEnteredPassword = login_password.getEditText().getText().toString().trim();
        //validate
        if(!validateUsername() | !validatePassword()) {
            return;
        }
        else{
            login(userEnteredName,userEnteredPassword);
        }
    }

    private void login(String userEnteredName, String userEnteredPassword) {
        login_progess_bar.setVisibility(View.VISIBLE);
        //RDB
//        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child(userEnteredName).exists()) {
//                    if (snapshot.child(userEnteredName).child("password").getValue(String.class).equals(userEnteredPassword)) {
//                        if (active.isChecked()) {
//                            if (snapshot.child(userEnteredName).child("asIn").getValue(String.class).equals("Admin")) {
//                                preferences.setDataLogin(Login.this, true);
//                                preferences.setDataAs(Login.this, "Admin");
//                                startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
//                            }
//                            else if (snapshot.child(userEnteredName).child("asIn").getValue(String.class).equals("Trainer")) {
//                                preferences.setDataLogin(Login.this, true);
//                                preferences.setDataAs(Login.this, "Trainer");
//                                startActivity(new Intent(getApplicationContext(),Trainer.class));
//                            }
//                            else if (snapshot.child(userEnteredName).child("asIn").getValue(String.class).equals("Client")){
//                                preferences.setDataLogin(Login.this, true);
//                                preferences.setDataAs(Login.this, "Client");
//                                startActivity(new Intent(getApplicationContext(),Client.class));
//                            }
//                        }
//                        else {
//                            if (snapshot.child(userEnteredName).child("asIn").getValue(String.class).equals("Admin")) {
//                                preferences.setDataLogin(Login.this, false);
//                                startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
//                            }
//                            else if (snapshot.child(userEnteredName).child("asIn").getValue(String.class).equals("Trainer")) {
//                                preferences.setDataLogin(Login.this, false);
//                                startActivity(new Intent(getApplicationContext(),Trainer.class));
//                            }
//                            else if (snapshot.child(userEnteredName).child("asIn").getValue(String.class).equals("Client")){
//                                preferences.setDataLogin(Login.this, false);
//                                startActivity(new Intent(getApplicationContext(),Client.class));
//                            }
//                        }
//                    }
//                    else {
//                        Toast.makeText(Login.this, "Password doesnot match", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//                else {
//                    Toast.makeText(Login.this, "Email doesnot exist", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(Login.this, "ERROR : 404", Toast.LENGTH_SHORT).show();
//            }
//        });

        //login with rdb
        firebaseAuth.signInWithEmailAndPassword(userEnteredName,userEnteredPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(Login.this, Dashboard.class);
                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Firestore
//        firebaseAuth.signInWithEmailAndPassword(userEnteredName,userEnteredPassword).addOnSuccessListener(authResult -> {
//            checkUserAccessLevel(authResult.getUser().getUid());
//            Intent intent = new Intent(Login.this, Dashboard.class);
//            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
//            startActivity(intent);
//            finish();
//        });
    }

//    private void checkUserAccessLevel(String uid) {
//        DocumentReference df = fstore.collection("users").document(uid);
//        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Log.d("TAG", "onSuccess" + documentSnapshot.getData());
//
//                if (documentSnapshot.getString("isAdmin") != null) {
//                    startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
//                    finish();
//                }
//                if (documentSnapshot.getString("isTrainer") != null) {
//                    startActivity(new Intent(getApplicationContext(), Trainer.class));
//                    finish();
//                }
//                if (documentSnapshot.getString("isClient") != null) {
//                    startActivity(new Intent(getApplicationContext(), Client.class));
//                    finish();
//                }
//            }
//        });
//    }






    //onstart : when app starts
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),Dashboard.class));
        }

        //as per firestore
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            DocumentReference df = FirebaseFirestore.getInstance().collection("users")
//                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
//            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    if (documentSnapshot.getString("isAdmin") != null) {
//                        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
//                        finish();
//                    }
//                    if (documentSnapshot.getString("isTrainer") != null) {
//                        startActivity(new Intent(getApplicationContext(), Trainer.class));
//                        finish();
//                    }
//                    if (documentSnapshot.getString("isClient") != null) {
//                        startActivity(new Intent(getApplicationContext(), Client.class));
//                        finish();
//                    }
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    FirebaseAuth.getInstance().signOut();
//                    startActivity(new Intent(getApplicationContext(), Login.class));
//                    finish();
//                }
//            });
//        }


        //as per RDB
//        if (preferences.getDataLogin(this)) {
//            if (preferences.getDataAs(this).equals("Admin")) {
//                startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
//                finish();
//            }
//            else if (preferences.getDataAs(this).equals("Trainer")) {
//                startActivity(new Intent(getApplicationContext(), Trainer.class));
//                finish();
//            }
//            else if (preferences.getDataAs(this).equals("Client")) {
//                startActivity(new Intent(getApplicationContext(), Client.class));
//                finish();
//            }
//        }
    }
}