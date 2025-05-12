package com.andriod.card.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.andriod.card.R;
import com.andriod.card.databinding.ActivityLoginRegisterBinding;
import com.andriod.card.databinding.ActivityMainBinding;

public class LoginRegisterActivity extends AppCompatActivity {

    private ActivityLoginRegisterBinding mbinding;
    private boolean isLogin=true; //login or register

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = ActivityLoginRegisterBinding.inflate(getLayoutInflater());
        setContentView(mbinding.getRoot());

        initEvent();

    }

    private void initEvent() {
        mbinding.login.setOnClickListener(v->{
            Intent intent = new Intent(LoginRegisterActivity.this, GameActivity.class);
//            startActivity(intent);
            setState(true);
        });
        mbinding.signUp.setOnClickListener(v->{
            setState(false);
        });
    }

    private void setState(boolean isLogin) {
        this.isLogin=isLogin;
        if (isLogin) {
            //登录
            mbinding.viodeContainer.setVisibility(View.INVISIBLE);
            mbinding.signUp.setVisibility(View.VISIBLE);
            mbinding.login.setText("sign in");
        }else {
            //注册
            mbinding.viodeContainer.setVisibility(View.VISIBLE);
            mbinding.signUp.setVisibility(View.INVISIBLE);
            mbinding.login.setText("sign up");

        }
    }


}