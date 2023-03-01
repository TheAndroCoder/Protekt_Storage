package com.theandrocoder.protektstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.theandrocoder.protektstorage.adapters.ViewPagerAdapter;
import com.theandrocoder.protektstorage.listeners.ViewPagerClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPagerClickListener {

    private static final String TAG = MainActivity.class.getCanonicalName();

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private ViewPagerAdapter adapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.setListener(this);
        adapter.add(new LoginFragment(this),"Login");
        adapter.add(new RegisterFragment(this),"Register");

        viewpager.setAdapter(adapter);

        //viewpager.setCurrentItem(1);

    }

    @Override
    public void switchPage(int page) {
        Log.d(TAG,"Switching to page "+page);
        viewpager.setCurrentItem(page);
    }

    @Override
    public void authenticated() {
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            Log.d(TAG,"User is already logged In");
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        }
    }
}
