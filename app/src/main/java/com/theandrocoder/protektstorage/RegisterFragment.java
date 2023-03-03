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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.theandrocoder.protektstorage.listeners.ViewPagerClickListener;
import com.theandrocoder.protektstorage.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterFragment extends Fragment {

    private static final String TAG = RegisterFragment.class.getCanonicalName();

    private ViewPagerClickListener listener=null;
    private FirebaseAuth mAuth;

    @OnClick(R.id.login_btn) void clicked(){
        listener.switchPage(0);
    }

    @BindView(R.id.email_edittext)
    EditText emailEditText;
    @BindView(R.id.username_edittext) EditText usernameEditText;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.password_edittext) EditText passwordEditText;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public RegisterFragment(ViewPagerClickListener listener){
        this.listener=listener;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG,"Inflating layout for Register Fragment");
        View inflate = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this,inflate);


        mAuth=FirebaseAuth.getInstance();
        registerBtn.setOnClickListener(v->{

            if(Utils.validateEmailPassUser(emailEditText.getText().toString(),passwordEditText.getText().toString(),usernameEditText.getText().toString())) {
                mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                        .addOnCompleteListener(getActivity(), task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Created User");
                                // take username and put a UserProfileUpdateRequest
                                UserProfileChangeRequest req = new UserProfileChangeRequest.Builder().setDisplayName(usernameEditText.getText().toString()).build();
                                mAuth.getCurrentUser().updateProfile(req);
                                listener.authenticated();
                            } else {
                                Log.e(TAG, "Failed to register " + task.getException().getMessage());
                                Toast.makeText(getContext(), "Failed to register " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }else{
                Toast.makeText(getContext(), "Invalid Inputs", Toast.LENGTH_SHORT).show();
            }
        });



        return inflate;
    }
}