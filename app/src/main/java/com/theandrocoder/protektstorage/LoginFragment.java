package com.theandrocoder.protektstorage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.theandrocoder.protektstorage.listeners.ViewPagerClickListener;
import com.theandrocoder.protektstorage.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getCanonicalName();

    private ViewPagerClickListener listener=null;
    private FirebaseAuth mAuth;

    @BindView(R.id.register_btn)
    Button register_btn;

    @OnClick(R.id.register_btn) void clicked(){
        listener.switchPage(1);
    }

    @BindView(R.id.login_btn) Button loginBtn;
    @BindView(R.id.email_edittext)
    EditText emailEditText;
    @BindView(R.id.password_edittext) EditText passwordEditText;

    public LoginFragment() {
        // Required empty public constructor
    }
    public LoginFragment(ViewPagerClickListener listener){
        Log.d(TAG,"Setting listener on LoginFragment");
        this.listener=listener;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG,"Inflating Layout for Login Fragment");
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,inflate);
        mAuth=FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(v->{
            // TODO : validate email and password before sending request
            if(Utils.validateEmail(emailEditText.getText().toString()))
            mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(),passwordEditText.getText().toString())
                    .addOnCompleteListener(getActivity(),task->{
                        if(task.isSuccessful()){
                            Log.d(TAG,"SignIn Success");
                            listener.authenticated();
                        }else{
                            Log.d(TAG,"Sign In Failed "+task.getException().getMessage());
                            Toast.makeText(getContext(), "Sign In Failed "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        return inflate;
    }
}