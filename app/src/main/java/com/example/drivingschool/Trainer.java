package com.example.drivingschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Trainer extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    //navigation drawer starts
    public void ClickMenu(View view){
        Dashboard.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        Dashboard.closeDrawer(drawerLayout);
    }

    public void ClickDashboard(View view){
        Dashboard.redirectActivity(this,Dashboard.class);
        this.finish();
    }

    public void ClickInstructions(View view){
        Dashboard.redirectActivity(this,InstructionsCard.class);
        this.finish();
    }

    public void ClickAdmin(View view){
        Dashboard.redirectActivity(this,AdminDashboard.class);
        this.finish();
    }

    public void ClickTrainer(View view){
        recreate();
    }

    public void ClickClient(View view){
        Dashboard.redirectActivity(this,Client.class);
        this.finish();
    }

    public void ClickLogin(View view){
        Dashboard.redirectActivity(this,Login.class);
        this.finish();
    }

    public void ClickUpdate(View view){
        Dashboard.redirectActivity(this,UserProfile.class);
        this.finish();
    }

    public void ClickAboutus(View view){
        Dashboard.redirectActivity(this,ContactusCard.class);
        this.finish();
    }

    public void ClickRate(View view){
        Dashboard.redirectActivity(this,Rate.class);
        this.finish();
    }

    public void ClickLogout(View view){
        Dashboard.logout(this);
    }

    protected void onPause(){
        super.onPause();
        Dashboard.closeDrawer(drawerLayout);
    }
    //navigation drawer ends
}