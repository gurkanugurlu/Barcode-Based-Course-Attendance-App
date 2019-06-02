package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.Rate;
import com.example.myapplication.model.Student;
import com.example.myapplication.model.api.JsonPlaceHolderApi;
import com.example.myapplication.model.api.RetrofitBringer;
import com.github.florent37.tutoshowcase.TutoShowcase;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;
    private Student student;
    private TextView txtAdSoyad;
    private SharedPreferences preferences;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Integer rate = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catchIntent();
        initComponents();
        registerEventHandler(savedInstanceState);
        preferences = getSharedPreferences("pref", MODE_PRIVATE);
        Toast.makeText(this, "Barcode okuyucuyu calıstırmak için resme tıklayınız", Toast.LENGTH_LONG).show();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.feedback:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.rate_app);
                final String[] items = new String[]{"1", "2", "3", "4", "5"};


                builder.setPositiveButton(R.string.rate_choice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Rate r = new Rate(rate);


                        Call<Rate> call = jsonPlaceHolderApi.rate(r);
                        call.enqueue(new Callback<Rate>() {
                            @Override
                            public void onResponse(Call<Rate> call, Response<Rate> response) {

                            }

                            @Override
                            public void onFailure(Call<Rate> call, Throwable t) {

                            }
                        });

                        Toast.makeText(MainActivity.this, R.string.thanks_message, Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("RATED", true);

                        editor.apply();
                    }
                });

                builder.setSingleChoiceItems(items, 2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rate = Integer.parseInt(items[which]);


                    }
                });
                AlertDialog alertDialog = builder.create();

                boolean isRated = preferences.getBoolean("RATED", false);
                if (isRated) {

                    Toast.makeText(this, "Daha önce oyladınız", Toast.LENGTH_SHORT).show();
                } else
                    alertDialog.show();


        }
        return super.onOptionsItemSelected(item);
    }

    private void catchIntent() {
        this.student = (Student) getIntent().getSerializableExtra("student");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_here:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HereFragment()).commit();
                break;
            case R.id.nav_class:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ClassFragment()).commit();
                break;
            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoryFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_exit:

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("loginState", false);
                editor.commit();
                finish();
                System.exit(0);//TODO BAK
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void registerEventHandler(Bundle savedInstanceState) {


        drawerEventHandler(savedInstanceState);
        navigationViewEventHandler();
    }


    private void navigationViewEventHandler() {

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void drawerEventHandler(Bundle savedInstanceState) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_draver_closed);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HereFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_here);
        }
    }

    private void initComponents() {
            jsonPlaceHolderApi = RetrofitBringer.bringRetrofit().create(JsonPlaceHolderApi.class);
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            drawer = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            if (headerView != null) {
                txtAdSoyad = headerView.findViewById(R.id.txtAdSoyad);
                if (txtAdSoyad != null) {
                    String adSoyad = student.getStudent_name() + " " + student.getStudent_surname();
                    txtAdSoyad.setText(adSoyad);
                }

            }


        }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Çıkmak için bir kez daha dokunun", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
            // super.onBackPressed();
        }
    }

}
