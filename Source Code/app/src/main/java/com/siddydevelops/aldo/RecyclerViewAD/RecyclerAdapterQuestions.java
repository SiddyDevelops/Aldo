package com.siddydevelops.aldo.RecyclerViewAD;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.siddydevelops.aldo.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerAdapterQuestions extends RecyclerView.Adapter<RecyclerAdapterQuestions.QuestionsViewHolder> {

    private List<String> question;
    private List<String> answerURL;

    Context context;

    public RecyclerAdapterQuestions(List<String> question, List<String> answerURL) {
        this.question = question;
        this.answerURL = answerURL;
    }

    @NonNull
    @NotNull
    @Override
    public QuestionsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.question_item_layout,parent,false);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull QuestionsViewHolder holder, int position) {

        String questionTitle = question.get(position);
        String ansURL = answerURL.get(position);

        holder.questionTextView.setText(questionTitle);
        Glide.with(context).load(ansURL).into(holder.answerImageView);

        holder.showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.showAnswer.setVisibility(View.INVISIBLE);
                holder.answerImageView.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return question.size();
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder
    {
        TextView questionTextView;
        ImageView answerImageView;
        Button showAnswer;

        public QuestionsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            questionTextView = itemView.findViewById(R.id.questionTextView);
            answerImageView = itemView.findViewById(R.id.answerImageView);
            showAnswer = itemView.findViewById(R.id.showAnswer);
            context = itemView.getContext();
        }
    }

}
