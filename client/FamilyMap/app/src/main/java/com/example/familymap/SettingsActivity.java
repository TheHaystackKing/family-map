package com.example.familymap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.familymap.databinding.ActivitySettingsBinding;

import java.io.InputStream;

public class SettingsActivity extends AppCompatActivity {

    private Switch displayStoryLines;
    private Switch displayFamilyTree;
    private Switch displaySpouseLines;
    private Switch displayFatherSide;
    private Switch displayMotherSide;
    private Switch displayMaleEvents;
    private Switch displayFemaleEvents;
    private LinearLayout logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        DataCache dataCache = DataCache.getInstance();
        this.displayStoryLines = findViewById(R.id.lifeStorySwitch);
        displayStoryLines.setChecked(dataCache.isDisplayStoryLines());
        displayStoryLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataCache.setDisplayStoryLines(!dataCache.isDisplayStoryLines());
            }
        });

        this.displayFamilyTree = findViewById(R.id.familyTreeSwitch);
        displayFamilyTree.setChecked(dataCache.isDisplayFamilyLines());
        displayFamilyTree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataCache.setDisplayFamilyLines(!dataCache.isDisplayFamilyLines());
            }
        });

        this.displaySpouseLines = findViewById(R.id.spouseSwitch);
        displaySpouseLines.setChecked(dataCache.isDisplaySpouseLines());
        displaySpouseLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataCache.setDisplaySpouseLines(!dataCache.isDisplaySpouseLines());
            }
        });

        this.displayFatherSide = findViewById(R.id.fatherSideSwitch);
        displayFatherSide.setChecked(dataCache.isDisplayFatherSide());
        displayFatherSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataCache.setDisplayFatherSide(!dataCache.isDisplayFatherSide());
            }
        });

        this.displayMotherSide = findViewById(R.id.motherSideSwitch);
        displayMotherSide.setChecked(dataCache.isDisplayMotherSide());
        displayMotherSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataCache.setDisplayMotherSide(!dataCache.isDisplayMotherSide());
            }
        });

        this.displayMaleEvents = findViewById(R.id.maleEventSwitch);
        displayMaleEvents.setChecked(dataCache.isDisplayMaleEvents());
        displayMaleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataCache.setDisplayMaleEvents(!dataCache.isDisplayMaleEvents());
            }
        });

        this.displayFemaleEvents = findViewById(R.id.femaleEventSwitch);
        displayFemaleEvents.setChecked(dataCache.isDisplayFemaleEvents());
        displayFemaleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataCache.setDisplayFemaleEvents(!dataCache.isDisplayFemaleEvents());
            }
        });

        this.logoutButton = findViewById(R.id.logout);
    }

    public void onClick(View view) {
        DataCache dataCache = DataCache.getInstance();
        dataCache.clearCache();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, SettingsActivity.class);
        return intent;
    }

}