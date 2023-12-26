package com.example.projectcn.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcn.JWT.JwtService;
import com.example.projectcn.MainActivity;
import com.example.projectcn.R;
import com.example.projectcn.datalocal.PreferenceManager;
import com.example.projectcn.interfaces.UserAPI;
import com.example.projectcn.model.User;
import com.example.projectcn.model.connect.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import io.jsonwebtoken.Claims;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    Button btnregister;
    ProgressBar progressBar;
    Button bthLogin;
    CheckBox rememberCheckBox;
    PreferenceManager preferenceManager;
    TextInputEditText usernameEditText;
    TextInputEditText passwordEditText;
    private JwtService jwtService;
    private static final long DOUBLE_CLICK_TIME_DELTA = 2000; // Thời gian giữa hai lần nhấp đúp (milliseconds)
    private long lastClickTime = 0; // Thời điểm lần nhấp gần nhất

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progressBar);
        bthLogin = findViewById(R.id.bthLogin);
        btnregister = findViewById(R.id.btnRegister);
        rememberCheckBox = findViewById(R.id.luuser);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        //Hiện mật khẩu
        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP) {

                    // Lấy vị trí của icon mắt
                    int passwordWidth = passwordEditText.getWidth();
                    Drawable eyeIcon = getResources().getDrawable(R.drawable.icons8_eye_30px);
                    int eyeIconWidth = eyeIcon.getIntrinsicWidth();
                    int padding = 12; // khoảng cách từ mép phải
                    int eyeIconPosition = passwordWidth - eyeIconWidth - padding;
                    // Kiểm tra xem có phải click vào vị trí icon mắt không
                    if(event.getX() >= eyeIconPosition) {
                        // Bấm vào icon mắt
                        if(passwordEditText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                            // Hiển thị mật khẩu
                            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        } else {
                            // Ẩn mật khẩu
                            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        //Check lưu user
        preferenceManager = new PreferenceManager(this);
        String usershared = preferenceManager.getUsername();
        if (!usershared.equals("")) {
            usernameEditText.setText(usershared);
            passwordEditText.setText(preferenceManager.getUserPW());
            rememberCheckBox.setChecked(true); // Chọn checkbox nếu đã lưu tên đăng nhập
        } else {
            rememberCheckBox.setChecked(false); // Bỏ chọn checkbox nếu không có tên đăng nhập được lưu
        }

        bthLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                LoginUser();
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Lấy thời điểm hiện tại khi nhấp nút quay lại
        long clickTime = System.currentTimeMillis();

        // So sánh với thời điểm lần nhấp gần nhất
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            // Nếu thời gian giữa hai lần nhấp nhỏ hơn ngưỡng, coi như đã nhấp đúp
            super.onBackPressed(); // Thoát ứng dụng
        } else {
            // Nếu không, thông báo cho người dùng và cập nhật thời điểm lần nhấp gần nhất
            Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
            lastClickTime = clickTime;
        }
    }

    private void LoginUser(){
            usernameEditText = findViewById(R.id.username);
            passwordEditText = findViewById(R.id.password);
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            jwtService = new JwtService();
            UserAPI userServiceApi = RetrofitClient.getClient().create(UserAPI.class);
            User user = new User(username,password);
            Call<ResponseBody> call = userServiceApi.loginUser(user);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String token = null;
                        try {
                            token = response.body().string();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        if(rememberCheckBox.isChecked()){
                            SaveUser(token,password);
                            rememberCheckBox.setChecked(true);
                        }else {
                            preferenceManager.saveUsername(null);
                            preferenceManager.saveUserPW(null);
                            rememberCheckBox.setChecked(false);
                        }

                        Log.e("LoginActivity", "Login: " + token );
                        Claims claims = jwtService.validateToken(token);
                        String userIdClaim = String.valueOf(claims.get("userId"));
                        Long userId = Long.parseLong(userIdClaim);
                        preferenceManager.saveId(userId);
                        Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        // Xử lý đăng nhập thất bại
                        Log.e("LoginActivity", "Đăng nhập thất bại: " + response.message());
                        Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    // Xử lý lỗi đăng nhập
                    Log.e("LoginActivity", "Lỗi server  " + t.getMessage());
                    Toast.makeText(Login.this, "Lỗi server", Toast.LENGTH_SHORT).show();
                }
            });
    }
    void SaveUser(String token, String pw) {
        Claims claims = jwtService.validateToken(token);
        String userId = String.valueOf(claims.get("sub"));

        if (rememberCheckBox.isChecked()) {
            preferenceManager.saveUsername(userId);
            preferenceManager.saveUserPW(pw);
        } else {
        }
    }

}