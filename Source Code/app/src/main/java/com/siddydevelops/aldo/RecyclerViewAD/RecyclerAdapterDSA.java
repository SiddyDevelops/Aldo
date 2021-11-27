package com.siddydevelops.aldo.RecyclerViewAD;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.siddydevelops.aldo.BookActivity;
import com.siddydevelops.aldo.R;
import com.siddydevelops.aldo.SpecificContentActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerAdapterDSA extends RecyclerView.Adapter<RecyclerAdapterDSA.DSAViewHolder> {

    private List<String> dsaName;
    private List<String> dsaCoverURL;
    private List<String> dsaDescription;
    private List<String> dsaNotesURL;
    private List<String> dsaWebURL;
    private List<String> dsaYTURL;
    private List<String> dsaCode;
    private List<String> dsaVisulaization;

    Context context;

    public RecyclerAdapterDSA(List<String> dsaName, List<String> dsaCoverURL, List<String> dsaDescription, List<String> dsaNotesURL, List<String> dsaWebURL, List<String> dsaYTURL, List<String> dsaCode, List<String> dsaVisulaization) {
        this.dsaName = dsaName;
        this.dsaCoverURL = dsaCoverURL;
        this.dsaDescription = dsaDescription;
        this.dsaNotesURL = dsaNotesURL;
        this.dsaWebURL = dsaWebURL;
        this.dsaYTURL = dsaYTURL;
        this.dsaCode = dsaCode;
        this.dsaVisulaization = dsaVisulaization;
    }

    @NonNull
    @NotNull
    @Override
    public DSAViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ds_item_layout,parent,false);
        return new DSAViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DSAViewHolder holder, int position) {

        String title = dsaName.get(position);
        String imageURL = dsaCoverURL.get(position);
        String description = dsaDescription.get(position);
        String notesURL = dsaNotesURL.get(position);
        String webURL = dsaWebURL.get(position);
        String YtURL = dsaYTURL.get(position);
        String code = dsaCode.get(position);
        String visLink = dsaVisulaization.get(position);

        holder.dsaTitleTextView.setText(title);

        //Glide.with(context).load(imageURL).into(holder.dsaCover);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;
                Intent intent = new Intent(context, SpecificContentActivity.class);
                //Pass data to next Activity
                intent.putExtra("Name",title);
                intent.putExtra("Description",description);
                intent.putExtra("NotesURL",notesURL);
                intent.putExtra("WebURL",webURL);
                intent.putExtra("YTURL",YtURL);
                intent.putExtra("Code",code);
                intent.putExtra("VisLink",visLink);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dsaName.size();
    }

    public class DSAViewHolder extends RecyclerView.ViewHolder
    {
        TextView dsaTitleTextView;
        //ImageView dsaCover;

        public DSAViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            dsaTitleTextView = itemView.findViewById(R.id.dsaTitleTextView);
            //dsaCover = itemView.findViewById(R.id.dsaImageView);
            context = itemView.getContext();

        }
    }

}
