package com.example.practica22octubre2021;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.practica22octubre2021.Library.Sqlite.MovieHelper;
import com.example.practica22octubre2021.Models.Movie;
import com.example.practica22octubre2021.fragments.DashboardFragment;
import com.example.practica22octubre2021.fragments.HomeFragment;
import com.example.practica22octubre2021.fragments.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new DashboardFragment();
    final Fragment fragment3 = new NotificationsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    private BottomNavigationView bottomNavigationView;
    MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieHelper = new MovieHelper(MainActivity.this);
        movieHelper.close();
        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container,fragment1, "1").commit();
        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;

                    case R.id.page_2:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;

                    case R.id.page_3:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;
                }
                return false;
            }
        });
    }
}