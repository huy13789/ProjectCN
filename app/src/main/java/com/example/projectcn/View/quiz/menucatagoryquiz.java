package com.example.projectcn.View.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.projectcn.R;
import com.example.projectcn.interfaces.CategoryAPI;
import com.example.projectcn.model.Category;
import com.example.projectcn.model.Quiz;
import com.example.projectcn.model.connect.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menucatagoryquiz extends AppCompatActivity {

    private List<Category> categories = new ArrayList<>();
    private menucategoryAdapter adapter;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizmenucatagory);


        // Khởi tạo RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Khởi tạo Adapter
        adapter = new menucategoryAdapter(categories);
        recyclerView.setAdapter(adapter);

        // Thiết lập LayoutManager là GridLayoutManager với spanCount là 2
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        imageButton = findViewById(R.id.back_button);
        //Nút back
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Lấy dữ liệu từ API
        getDataCategoryApi(new DataCallback() {
            @Override
            public void onDataReceived(List<Category> data) {
                processData(data);
            }
        });

        // Thiết lập sự kiện click cho Adapter
        adapter.setOnItemClickListener(new menucategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                getQuizzesByCategoryId(category.getId());
            }
        });
    }

    private void getDataCategoryApi(final DataCallback callback) {
        CategoryAPI categoryAPI = RetrofitClient.getClient().create(CategoryAPI.class);
        Call<List<Category>> call = categoryAPI.getAllCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> data = response.body();
                callback.onDataReceived(data);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    private void processData(List<Category> data) {
        categories.clear();
        categories.addAll(data);
        adapter.notifyDataSetChanged();
    }
    public interface DataCallback {
        void onDataReceived(List<Category> data);
    }

    private void getQuizzesByCategoryId(Long categoryId) {
        CategoryAPI categoryAPI = RetrofitClient.getClient().create(CategoryAPI.class);

        Call<List<Quiz>> call = categoryAPI.getQuizzesByCategoryId(categoryId);
        call.enqueue(new Callback<List<Quiz>>() {
            @Override
            public void onResponse(Call<List<Quiz>> call, Response<List<Quiz>> response) {
                List<Quiz> quizResponses = response.body();
                // Xử lý dữ liệu nhận được, ví dụ: hiển thị danh sách quiz, chuyển sang màn hình khác, vv.
                // Tạo Intent
                Intent intent = new Intent(menucatagoryquiz.this, quizActivity.class);
                // Đưa danh sách quizResponses vào Intent
                intent.putExtra("quizResponses", new Gson().toJson(quizResponses));
                // Chuyển sang Activity mới
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<Quiz>> call, Throwable t) {
                // Xử lý lỗi nếu cần
                Toast.makeText(menucatagoryquiz.this, "Empty or unsuccessful response", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Gọi finish() khi người dùng nhấn nút back
        super.onBackPressed();
        finish();
    }

}