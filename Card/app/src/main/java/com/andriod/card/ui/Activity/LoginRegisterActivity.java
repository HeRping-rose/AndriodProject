package com.andriod.card.ui.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.andriod.card.R;
import com.andriod.card.SystemUtils;
import com.andriod.card.databinding.ActivityLoginRegisterBinding;
import com.andriod.card.databinding.ActivityMainBinding;
import com.andriod.card.databinding.ChoosePhotoBottomSheetDialogBinding;
import com.andriod.card.utils.AnimatorUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LoginRegisterActivity extends AppCompatActivity {

    private ActivityLoginRegisterBinding mbinding;
    private boolean isLogin=true; //login or register
    private int mTopMaxMargin;


    //创建权限申请的起启动器
    private ActivityResultLauncher requestPermissionLauncher =registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),isGranted->{
                if (isGranted) {
                    showImagePickerDialog();
                }else {
                    Toast.makeText(this, "需要相机权限才能拍照", Toast.LENGTH_SHORT).show();
                }
            }

    );




    private void showImagePickerDialog() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = ActivityLoginRegisterBinding.inflate(getLayoutInflater());
        setContentView(mbinding.getRoot());

        mTopMaxMargin = SystemUtils.dp2px(60, this);

        initEvent();//初始化
    }

    private void initEvent() {
        //登录点击事件
        mbinding.login.setOnClickListener(v->{
            Intent intent = new Intent(LoginRegisterActivity.this, GameActivity.class);
//            startActivity(intent);
            setState(true);
        });
        //文本注册点击事件
        mbinding.signUp.setOnClickListener(v->{
            setState(false);
        });
        mbinding.viode.setOnClickListener(v->{
            //检测是否有相机访问权限
//            ContextCompat.checkSelfPermission();
//            checkSelfPermission("android.permission.CAMERA");//有继承关系
            int result =checkSelfPermission(Manifest.permission.CAMERA);
            if (result == PackageManager.PERMISSION_GRANTED) {
                //有权限 执行自己的任务
                showBottomDialog();
            } else {
                //没有权限?  -->  申请权限 .launch()
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);

            }
//            showBottomDialog();
        });
    }

    private void setState(boolean isLogin) {
        this.isLogin=isLogin;
        if (isLogin) {
            //登录
            mbinding.viodeContainer.setVisibility(View.GONE);
            mbinding.signUp.setVisibility(View.VISIBLE);//注册
            mbinding.login.setText("sign in");//登录
            scaleHeightAnimation(false);
        }else {
            //注册
            mbinding.viodeContainer.setVisibility(View.VISIBLE);
            mbinding.signUp.setVisibility(View.INVISIBLE);//隐藏注册文本
            mbinding.login.setText("sign up");//显示注册按钮
            scaleHeightAnimation(true);

        }
    }

    private void scaleHeightAnimation(boolean isToBig) {
        if (isToBig) {
            AnimatorUtils.fadeIn(mbinding.viodeContainer);
            AnimatorUtils.fadeOut(mbinding.signUp);
            AnimatorUtils.scaleTop(mbinding.bgContainer,mTopMaxMargin,0);
        }else {
            AnimatorUtils.fadeOut(mbinding.viodeContainer);
            AnimatorUtils.fadeIn(mbinding.signUp);
            AnimatorUtils.scaleTop(mbinding.bgContainer,0,mTopMaxMargin);
        }
    }

    //从设备底部弹出一个菜单
    private void showBottomDialog() {
        //创建底部菜单弹窗视图   ,设置弹窗显示的自定义视图

        //1...  nb只适用于纯粹显示一个控件给用户 不需要交互 才需要选择这种方式
//        AlertDialog;//中间弹出
//        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
//        //配置属性方法
//        alertBuilder.setView(R.layout.choose_photo_bottom_sheet_dialog);
//        //使用建造者对象构建一个完整对象
//        AlertDialog alertDialog=alertBuilder.create();
//        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
//        alertDialog.show();//显示dialog

//        Dialog;

//        BottomSheetDialog dialog=new BottomSheetDialog(this);
//        //1.layout中创建一个layout
//        dialog.setContentView(R.layout.choose_photo_bottom_sheet_dialog);
//        dialog.show();//显示  //消失dismiss()

        // 2...  自己把layout对象的xml文件解析成view 使用findById查找子控件
//        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
//        View view = getLayoutInflater().inflate(R.layout.choose_photo_bottom_sheet_dialog, null, false);
//        alertBuilder.setView(view);
//        AlertDialog alertDialog=alertBuilder.create();
//        //使用findById查找子控件
//        Button cancelBtn = view.findViewById(R.id.cancelBtn);
//        cancelBtn.setOnClickListener(v->{
//            alertDialog.dismiss();
//        });
//        alertDialog.show();

        //3...  viewBinding
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
        ChoosePhotoBottomSheetDialogBinding binding = ChoosePhotoBottomSheetDialogBinding.inflate(getLayoutInflater());
        alertBuilder.setView(binding.getRoot());
        AlertDialog alertDialog=alertBuilder.create();
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        //显示
        alertDialog.show();
        //事件处理
        binding.cancelBtn.setOnClickListener(v->{
            alertDialog.dismiss();
        });
        binding.libirayBtn.setOnClickListener(v->{
            ActivityResultLauncher<Intent> galleryLauncher=registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result->{
                        if(result.getResultCode()==RESULT_OK&&result.getData()!=null){
                            Uri selectedImage=result.getData().getData();
                            mbinding.viode.setImageURI(selectedImage);
                        }
                    }
            );
            Intent intent = new Intent(Intent.ACTION_PICK_ACTIVITY, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intent);

//            ActivityResultLauncher<String> launcher=registerForActivityResult(
//                    new ActivityResultContracts.GetContent(),
//                    uri->{
//                        if(uri!=null){
//
//                        }
//                    }
//            );
//            launcher.launch("image/*");

        });
        binding.cameraBtn.setOnClickListener(v->{

        });

    }

}