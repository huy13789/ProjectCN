package com.example.projectcn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcn.View.Login;
import com.example.projectcn.fragment.MyViewpager2Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;
public class MainActivity extends AppCompatActivity {

    private ViewPager2 MviewPager2;
    private BottomNavigationView MbottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MviewPager2 = findViewById(R.id.viewPager2);
        MbottomNavigationView = findViewById(R.id.bottomNavigation);

        MyViewpager2Adapter adapter = new MyViewpager2Adapter(this);
        MviewPager2.setAdapter(adapter);

        MviewPager2.setPageTransformer(new DepthPageTransformer());

        MviewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        MbottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;

                    case 1:
                        MbottomNavigationView.getMenu().findItem(R.id.menu_lesson).setChecked(true);
                        break;

                    case 2:
                        MbottomNavigationView.getMenu().findItem(R.id.menu_advise).setChecked(true);
                        break;

                    case 3:
                        MbottomNavigationView.getMenu().findItem(R.id.menu_account).setChecked(true);
                        break;
                }
            }
        });
        MbottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_home) {
                    MviewPager2.setCurrentItem(0);
                } else if (item.getItemId() == R.id.menu_lesson) {
                    MviewPager2.setCurrentItem(1);
                } else if (item.getItemId() == R.id.menu_advise) {
                    MviewPager2.setCurrentItem(2);
                } else if (item.getItemId() == R.id.menu_account) {
                    MviewPager2.setCurrentItem(3);
                }
                return true;
            }
            });
    }
}