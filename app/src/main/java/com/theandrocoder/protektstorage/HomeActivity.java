package com.theandrocoder.protektstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.theandrocoder.protektstorage.adapters.RecentsRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getCanonicalName();

    private FirebaseAuth mAuth;

    @BindView(R.id.recents_recycler)
    RecyclerView recentsRecycler;

    @BindView(R.id.menu_icon)
    ImageView menuIcon;

    private RecentsRecyclerAdapter recentsRecyclerAdapter;
    @BindView(R.id.sliding_menu)
    FrameLayout slidingMenuLayout;

    @BindView(R.id.closeMenuBtn) ImageView closeMenuBtn;

    private int marginLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        // TODO : handle user rejection of permission
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1001);
        List<Uri> recentImageList=fetchRecentImages();
        recentImageList=recentImageList.stream().limit(10).collect(Collectors.toList());
        recentsRecyclerAdapter=new RecentsRecyclerAdapter(this,recentImageList);
        recentsRecycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recentsRecycler.setAdapter(recentsRecyclerAdapter);

        // set Params of Sliding menu
        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams) slidingMenuLayout.getLayoutParams();
        DisplayMetrics metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        params.width=(int)(0.7*metrics.widthPixels);
        params.leftMargin=-params.width;
        marginLeft=-params.width;
        slidingMenuLayout.setLayoutParams(params);
        Log.d(TAG,"Left Margin is "+params.leftMargin);

        menuIcon.setOnClickListener(v->{
            // slide the frame layout outside

            ValueAnimator animator=ValueAnimator.ofInt(params.leftMargin,0);
            animator.setDuration(300);
            animator.addUpdateListener(anim->{
                params.setMargins((int)anim.getAnimatedValue(),0,0,0);
                slidingMenuLayout.setLayoutParams(params);
            });
            animator.start();

        });

        closeMenuBtn.setOnClickListener(v->{
            ValueAnimator animator=ValueAnimator.ofInt(0,marginLeft);
            animator.setDuration(300);
            animator.addUpdateListener(anim->{
                params.setMargins((int)anim.getAnimatedValue(),0,0,0);
                slidingMenuLayout.setLayoutParams(params);
            });
            animator.start();
        });

    }

    private List<Uri> fetchRecentImages(){
        List<Uri> list=new ArrayList<>();
        String projections[]={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,projections,null,null,MediaStore.Images.ImageColumns.DATE_TAKEN+" DESC");
        if(cursor!=null)cursor.moveToPosition(0);
        while(true){
            //int imageIdIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID);
            int imageUriIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);
            //String id = cursor.getString(imageIdIndex);
            Uri imageUri = Uri.parse(cursor.getString(imageUriIndex));
            list.add(imageUri);
            //Log.d(TAG,"Image URI added to list "+imageUri);
            if(!cursor.isLast())cursor.moveToNext();
            else break;

        }
        Log.d(TAG,"Returned list of size "+list.size());
        return list;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()==null){
            finish();
        }
    }
}