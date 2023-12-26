package com.example.projectcn.View.quiz;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcn.R;
import com.example.projectcn.model.QuestionsRespone;

import java.util.List;

public class questionsAdapter extends RecyclerView.Adapter<questionsAdapter.ViewHolder> {

    private List<QuestionsRespone> data;
    private OnItemClickListener listener;

    public questionsAdapter(List<QuestionsRespone> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionsRespone question = data.get(position);
        holder.bind(question, listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView questionTextView;
        private RadioGroup optionsRadioGroup;
        private RadioButton radioButton1;
        private RadioButton radioButton2;
        private RadioButton radioButton3;
        private RadioButton radioButton4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.question);
            optionsRadioGroup = itemView.findViewById(R.id.radioGroup);
            radioButton1 = itemView.findViewById(R.id.radioButton1);
            radioButton2 = itemView.findViewById(R.id.radioButton2);
            radioButton3 = itemView.findViewById(R.id.radioButton3);
            radioButton4 = itemView.findViewById(R.id.radioButton4);
        }

        public void bind(final QuestionsRespone question, final OnItemClickListener listener) {
            int questionNumber = getAdapterPosition() + 1; // Lấy vị trí câu hỏi trong danh sách và cộng 1 để có số thứ tự
            questionTextView.setText("Câu " + questionNumber + ": " + question.getQuestion());

            radioButton1.setText(question.getAnswerA());
            radioButton2.setText(question.getAnswerB());
            radioButton3.setText(question.getAnswerC());
            radioButton4.setText(question.getAnswerD());

            optionsRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId == R.id.radioButton1) {
                    question.setSelectedAnswerId(1);
                } else if (checkedId == R.id.radioButton2) {
                    question.setSelectedAnswerId(2);
                } else if (checkedId == R.id.radioButton3) {
                    question.setSelectedAnswerId(3);
                } else if (checkedId == R.id.radioButton4) {
                    question.setSelectedAnswerId(4);
                }

                if (listener != null) {
                    listener.onItemClick(question, checkedId);
                }
            });

            // Kiểm tra và phục hồi trạng thái lựa chọn trước đó
            switch (question.getSelectedAnswerId()) {
                case 1:
                    optionsRadioGroup.check(R.id.radioButton1);
                    break;
                case 2:
                    optionsRadioGroup.check(R.id.radioButton2);
                    break;
                case 3:
                    optionsRadioGroup.check(R.id.radioButton3);
                    break;
                case 4:
                    optionsRadioGroup.check(R.id.radioButton4);
                    break;
                default:
                    optionsRadioGroup.clearCheck();
                    break;
            }
        }

    }

    public interface OnItemClickListener {
        void onItemClick(QuestionsRespone question, int checkedId);
    }
}
