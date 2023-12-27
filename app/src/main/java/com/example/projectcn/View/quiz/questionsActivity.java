package com.example.projectcn.View.quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcn.R;
import com.example.projectcn.View.Login;
import com.example.projectcn.datalocal.PreferenceManager;
import com.example.projectcn.interfaces.TotalQuestionsScoreAPI;
import com.example.projectcn.model.Answers;
import com.example.projectcn.model.QuestionsRespone;
import com.example.projectcn.model.QuestionsScore;
import com.example.projectcn.model.Quiz;
import com.example.projectcn.model.TotalQuestionsScore;
import com.example.projectcn.model.User;
import com.example.projectcn.model.connect.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class questionsActivity extends AppCompatActivity {
    List<QuestionsRespone> questionsRespones = new ArrayList<>();
    private questionsAdapter adapter;
    private RecyclerView recyclerView;
    private CountDownTimer countDownTimer;
    private TextView countdownTimerTextView;
    private Button cancelButton;
    private Button submitButton;
    Long quizId;
    private Double totalScore;
    private Double questionsScore;
    private int numberquestions;
    private int iscorret = 0;
    private int isnotcorret = 0;

    PreferenceManager preferenceManager= new PreferenceManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        // Nhận dữ liệu từ Intent
        String questionsResponesJson = getIntent().getStringExtra("questionsRespones");
        quizId = getIntent().getLongExtra("quizId", -1);
        // Chuyển đổi JSON thành danh sách quizResponses
        questionsRespones = new Gson().fromJson(questionsResponesJson, new TypeToken<List<QuestionsRespone>>() {}.getType());

        // Khởi tạo RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Khởi tạo Adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Khởi tạo LayoutManager
        adapter = new questionsAdapter(questionsRespones);
        recyclerView.setAdapter(adapter);

        Collections.shuffle(questionsRespones);

        numberquestions = questionsRespones.size();
        questionsScore = 10.0 / (double) numberquestions;

        // Khởi tạo bộ đếm thời gian (20 phút)
        initializeCountDownTimer(20 * 60 * 1000); // Đổi thành mili giây

//        // Khởi tạo bộ đếm thời gian (5 giây)
//        initializeCountDownTimer(5 * 1000); // Đổi thành mili giây


        // Khởi tạo các thành phần giao diện
        countdownTimerTextView = findViewById(R.id.countdownTimer);
        cancelButton = findViewById(R.id.button1);
        submitButton = findViewById(R.id.button2);

        // Thiết lập sự kiện cho nút Cancel
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hủy bỏ bộ đếm thời gian
                AlertDialog.Builder builder = new AlertDialog.Builder(questionsActivity.this);

                builder.setTitle("Xác nhận thoát")
                        .setMessage("Bạn có chắc chắn muốn thoát?\nBài thi của bạn sẽ ghi nhận những câu đã làm.")
                        .setCancelable(false)
                        .setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Đóng dialog và tiếp tục đếm thời gian
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Hủy bỏ bộ đếm thời gian và thoát activity
                                cancelCountDownTimer();
                                finish();
                            }
                        });

                // Lấy AlertDialog từ builder
                AlertDialog dialog = builder.create();

                // Lấy đối tượng Window để thiết lập background drawable
                Window window = dialog.getWindow();
                if (window != null) {
                    window.setBackgroundDrawableResource(R.drawable.dialog_cancel);
                }
                dialog.show();
            }
        });

        // Thiết lập sự kiện cho nút Submit
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelCountDownTimer();
                List<QuestionsScore> questionsScores = new ArrayList<>();
                for (QuestionsRespone question : questionsRespones) {

                    int selectedId = question.getSelectedAnswerId();
                    int correctAnswer = question.getAnswers().getIdAsInt();

                    boolean isCorrect = checkAnswer(selectedId, correctAnswer);// So sánh đáp án
                    if(isCorrect){
                        //Câu đúng
                        iscorret++;
                    }else {
                        //Câu sai
                        isnotcorret++;
                    }

                    QuestionsRespone questionsRespone = new QuestionsRespone();
                    questionsRespone.setId(question.getId());

                    Answers answers = new Answers();
                    answers.setId((long) question.getSelectedAnswerId());

                    QuestionsScore questionsScore = new QuestionsScore(questionsRespone,answers,isCorrect);
                    questionsScores.add(questionsScore);
                }
                //Điểm tổng
                totalScore = questionsScore * (double)iscorret;
                sendTotalScore();
                sendDetailScore(questionsScores);

            }
        });

        // Bắt đầu bộ đếm thời gian
        startCountDownTimer();
    }

    public void sendTotalScore() {
        User user = new User();
        user.setId(Long.parseLong(preferenceManager.getId()));

        Quiz quizResponse = new Quiz();
        quizResponse.setQuizId(quizId); // ID của Quiz

        TotalQuestionsScore totalQuestionsScore = new TotalQuestionsScore(user,quizResponse,totalScore);
        Log.e("QuestionActivity", "Summit: " +
                "UserId: " + totalQuestionsScore.getUser().getId() +
                ", QuizId: " + totalQuestionsScore.getQuiz().getQuizId() +
                ", TotalScore: " + totalQuestionsScore.getTotalScore());
        TotalQuestionsScoreAPI totalQuestionsScoreAPI = RetrofitClient.getClient().create(TotalQuestionsScoreAPI.class);
        Call<ResponseBody> call = totalQuestionsScoreAPI.saveTotalScore(totalQuestionsScore);
        Log.e("TotalQuestionsScoreAPI", "sd" + new Gson().toJson(totalQuestionsScore));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(questionsActivity.this, "Đã summit thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(questionsActivity.this, "Không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Xử lý lỗi mạng hoặc lỗi khác
            }
        });
    }
    public void sendDetailScore(List<QuestionsScore> questionsScores){
        
    }
    private boolean checkAnswer(int selectedId, int correctAnswer) {
        return selectedId == correctAnswer;
    }
    private void initializeCountDownTimer(long duration) {
        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdownText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                // Xử lý khi hết giờ
                new AlertDialog.Builder(questionsActivity.this)
                        .setTitle("Hết giờ !")
                        .setMessage("Bài quiz đã kết thúc. Câu trả lời của bạn đã được tự động nộp")
                        .setPositiveButton("OK", (dialog, which) -> {
                            // End quiz activity
                            finish();
                        })
                        .create()
                        .show();
            }
        };
    }

    private void startCountDownTimer() {
        countDownTimer.start();
    }

    private void cancelCountDownTimer() {
        countDownTimer.cancel();
        finish();
    }
    private void updateCountdownText(long millisUntilFinished) {
        long minutes = millisUntilFinished / 60000;
        long seconds = (millisUntilFinished % 60000) / 1000;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countdownTimerTextView.setText(timeFormatted);
    }

    @Override
    public void onBackPressed() {

        showExitConfirmationDialog();
        //super.onBackPressed();
    }

    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Xác nhận thoát")
                .setMessage("Bạn có chắc chắn muốn thoát? Bài quiz của bạn sẽ không được nộp.")
                .setCancelable(false)
                .setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Đóng dialog và tiếp tục đếm thời gian
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Hủy bỏ bộ đếm thời gian và thoát activity
                        cancelCountDownTimer();
                        finish();
                    }
                });

        // Lấy AlertDialog từ builder
        AlertDialog dialog = builder.create();

        // Lấy đối tượng Window để thiết lập background drawable
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.dialog_cancel);
        }

        // Hiển thị AlertDialog
        dialog.show();
    }

}