package com.example.projectcn.View.quiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcn.R;
import com.example.projectcn.model.Quiz;

import java.util.List;

public class quizActivityAdapter extends RecyclerView.Adapter<quizActivityAdapter.ViewHolder>{
    private List<Quiz> quizData;
    private OnItemClickListener listener;

    public quizActivityAdapter(List<Quiz> quizData) {
        this.quizData = quizData;
    }

    public void setQuizData(List<Quiz> quizData) {
        this.quizData = quizData;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quiz quiz = quizData.get(position);
        holder.bind(quiz, listener);
    }

    @Override
    public int getItemCount() {
        return quizData.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView quizNameTextView;
        private CardView quizCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quizNameTextView = itemView.findViewById(R.id.textView);
            quizCardView = itemView.findViewById(R.id.cardView);
        }

        public void bind(final Quiz quiz, final OnItemClickListener listener) {
            quizNameTextView.setText(quiz.getNameQuiz());
            quizCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onQuizItemClick(quiz);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onQuizItemClick(Quiz quiz);
    }
}
