package com.example.projectcn.View.quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.projectcn.R;
import com.example.projectcn.interfaces.QuestionsAPI;
import com.example.projectcn.model.QuestionsRespone;
import com.example.projectcn.model.Quiz;
import com.example.projectcn.model.connect.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class quizActivity extends AppCompatActivity {
    List<Quiz> quizResponses = new ArrayList<>();
    private quizActivityAdapter adapter;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // Nhận dữ liệu từ Intent
        String quizResponsesJson = getIntent().getStringExtra("quizResponses");

        // Chuyển đổi JSON thành danh sách quizResponses
        quizResponses = new Gson().fromJson(quizResponsesJson, new TypeToken<List<Quiz>>() {}.getType());

        // Hiển thị dữ liệu lên RecyclerView hoặc thực hiện các bước xử lý khác

        // Khởi tạo RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Khởi tạo Adapter
        adapter = new quizActivityAdapter(quizResponses);
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

        adapter.setOnItemClickListener(new quizActivityAdapter.OnItemClickListener(){
            @Override
            public void onQuizItemClick(Quiz quizResponse) {
                showConfirmationDialog(quizResponse.getQuizId());
            }
        });
    }
    private void showConfirmationDialog(final Long quizId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Xác nhận")
                .setMessage("Thời gian làm đề này là 20 phút \nBạn có muốn bắt đầu ngay bây giờ ?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xử lý khi người dùng bấm "Có"
                        getQuestionsByQuizId(quizId);
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xử lý khi người dùng bấm "Không" (nếu cần)
                        dialog.cancel();
                    }
                });
        // Lấy AlertDialog từ builder
        AlertDialog dialog = builder.create();

        // Lấy đối tượng Window để thiết lập background drawable
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.dialog_background);
        }
        dialog.show();
    }
    private void getQuestionsByQuizId(Long quizId){
        QuestionsAPI questionsAPI = RetrofitClient.getClient().create(QuestionsAPI.class);
        Call<List<QuestionsRespone>> call = questionsAPI.getQuestions(quizId);
        call.enqueue(new Callback<List<QuestionsRespone>>() {
            @Override
            public void onResponse(Call<List<QuestionsRespone>> call, Response<List<QuestionsRespone>> response) {
                List<QuestionsRespone> questionsRespones = response.body();
                Toast.makeText(quizActivity.this, "Bắt đầu làm", Toast.LENGTH_SHORT).show();
                //getApplicationContext
                Intent intent = new Intent(getApplicationContext(), questionsActivity.class);
                intent.putExtra("questionsRespones", new Gson().toJson(questionsRespones));
                intent.putExtra("quizId", quizId);
                // Bắt đầu màn hình mới
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<List<QuestionsRespone>> call, Throwable t) {

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