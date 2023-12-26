package com.example.projectcn.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcn.MainActivity;
import com.example.projectcn.R;
import com.example.projectcn.interfaces.UserAPI;
import com.example.projectcn.model.User;
import com.example.projectcn.model.connect.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    TextInputEditText username,password,email,fullname,phone,diachi;
    Button btnRegister;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Anhxa();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                RegisterUser();
            }
        });
    }
    private void RegisterUser(){
        EditText accountEditText = findViewById(R.id.account);
        EditText passwordEditText = findViewById(R.id.password);
        EditText emailEditText = findViewById(R.id.email);
        EditText fullnameEditText = findViewById(R.id.fullname);
        EditText phoneNumberEditText = findViewById(R.id.phonenumber);
        EditText addressEditText = findViewById(R.id.address);

        String account ;
        String password ;
        String email;
        String fullname;
        String phoneNumber;
        String address;

        // Kiểm tra giá trị null cho từng trường
        if (!checkNull(accountEditText, "Tài khoản") ||
                !checkNull(passwordEditText, "Mật khẩu") ||
                !checkNull(emailEditText, "Email") ||
                !checkNull(fullnameEditText, "Họ và tên") ||
                !checkNull(phoneNumberEditText, "Số điện thoại") ||
                !checkNull(addressEditText, "Địa chỉ")) {
            // Nếu có trường nào đó là null, không tiếp tục đăng ký
            progressBar.setVisibility(View.GONE);
            return;
        }else {
            account = ((EditText) findViewById(R.id.account)).getText().toString();
            password = ((EditText) findViewById(R.id.password)).getText().toString();
            email = ((EditText) findViewById(R.id.email)).getText().toString();
            fullname = ((EditText) findViewById(R.id.fullname)).getText().toString();
            phoneNumber = ((EditText) findViewById(R.id.phonenumber)).getText().toString();
            address = ((EditText) findViewById(R.id.address)).getText().toString();
        }
        if (phoneNumber.length() != 10) {
            phoneNumberEditText.setError("Số điện thoại phải có đúng 10 số");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (account.length() >= 6) {
            accountEditText.setError("Tài khoản phải trên 6 kí tự");
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (password.length() >= 6) {
            passwordEditText.setError("Mật khẩu phải trên 6 kí tự");
            progressBar.setVisibility(View.GONE);
            return;
        }
        UserAPI userServiceApi = RetrofitClient.getClient().create(UserAPI.class);
        User user = new User(account,password,email,fullname,phoneNumber,address);
        Call<ResponseBody> call = userServiceApi.registerUser(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String json = null;
                    try {
                        json = response.body().string();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Log.e("LoginActivity", "Register: " + json );
                    Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    // Đăng ký không thành công
                    if (response.code() == 400) {
                        // 400 Bad Request, người dùng đã tồn tại
                        Toast.makeText(getApplicationContext(), "Đã có tài khoản với tên tài khoản này!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    private boolean checkNull(EditText editText, String fieldName) {
        String value = editText.getText().toString().trim();

        if (value.isEmpty()) {
            editText.setError(fieldName + " không được để trống");
            return false;
        }
        return true;
    }
    void Anhxa(){
        username = findViewById(R.id.account);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fullname = findViewById(R.id.fullname);
        phone = findViewById(R.id.phonenumber);
        diachi = findViewById(R.id.address);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
    }
}