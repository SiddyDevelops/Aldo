package com.siddydevelops.aldo.RecyclerViewAD;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.siddydevelops.aldo.BookActivity;
import com.siddydevelops.aldo.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterRefBooks extends RecyclerView.Adapter<RecyclerAdapterRefBooks.RefBooksViewHolder> {

    private List<String> bookName;
    private List<String> authorName;
    private List<String> bookCover;
    private List<String> bookDiscription;
    private List<String> bookUrl;

    Context context;

    public RecyclerAdapterRefBooks(List<String> bookName, List<String> authorName, List<String> bookCover, List<String> bookDiscription, List<String> bookUrl) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookCover = bookCover;
        this.bookDiscription = bookDiscription;
        this.bookUrl = bookUrl;
    }

    @NonNull
    @NotNull
    @Override
    public RefBooksViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.reference_books_layout,parent,false);
        return new RefBooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerAdapterRefBooks.RefBooksViewHolder holder, int position) {

        String bookTitle = bookName.get(position);
        String author = authorName.get(position);
        String discription = bookDiscription.get(position);
        String bookCoverImage = bookCover.get(position);
        String bookUrlToDownload = bookUrl.get(position);

        holder.bookName.setText(bookTitle);
        holder.authorName.setText( "~" + author);

        Glide.with(context).load(bookCoverImage).into(holder.bookImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;
                Intent intent = new Intent(context, BookActivity.class);
                //Pass data to next Activity
                intent.putExtra("BookName",bookTitle);
                intent.putExtra("AuthorName",author);
                intent.putExtra("BookDiscription",discription);
                intent.putExtra("ImageUrl", bookCoverImage);
                intent.putExtra("BookNo",position + 1);    //BookNo starting from 1.
                intent.putExtra("BookURL",bookUrlToDownload);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookName.size();
    }

    public class RefBooksViewHolder extends RecyclerView.ViewHolder
    {

        TextView bookName;
        TextView authorName;
        ImageView bookImage;

        public RefBooksViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.booksName);
            authorName = itemView.findViewById(R.id.authorName);
            bookImage = itemView.findViewById(R.id.refBookImageView);

            context = itemView.getContext();

        }
    }

}
