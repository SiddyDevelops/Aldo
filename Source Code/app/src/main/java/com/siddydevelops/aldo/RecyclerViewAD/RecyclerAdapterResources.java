package com.siddydevelops.aldo.RecyclerViewAD;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.siddydevelops.aldo.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerAdapterResources extends RecyclerView.Adapter<RecyclerAdapterResources.ResourcesViewHolder> {

    private List<String> courseName;
    private List<String> instructor;
    private List<String> platform;
    private List<String> price;
    private List<String> f_or_paid;
    private List<String> link;
    private List<String> coverURL;

    Context context;

    public RecyclerAdapterResources(List<String> courseName, List<String> instructor, List<String> platform, List<String> price, List<String> f_or_paid, List<String> link, List<String> coverURL) {
        this.courseName = courseName;
        this.instructor = instructor;
        this.platform = platform;
        this.price = price;
        this.f_or_paid = f_or_paid;
        this.link = link;
        this.coverURL = coverURL;
    }

    @NonNull
    @NotNull
    @Override
    public ResourcesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.resources_item_layout,parent,false);
        return new ResourcesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerAdapterResources.ResourcesViewHolder holder, int position) {

        String course_Name = courseName.get(position);
        String teacher = instructor.get(position);
        String plat = platform.get(position);
        String paisa = price.get(position);
        String f_p = f_or_paid.get(position);
        String linky = link.get(position);
        String photo_url = coverURL.get(position);

        holder.courseName.setText(course_Name);
        holder.instructor.setText(teacher);
        holder.platform.setText(plat);
        holder.price.setText(paisa);
        holder.f_paid.setText(f_p);
        holder.link.setText(linky);

        Glide.with(context).load(photo_url).into(holder.cover);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Hold On!", Toast.LENGTH_SHORT).show();

                String url = linky;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try{
                    context.startActivity(intent);
                }catch (Exception e)
                {
                    intent.setPackage(null);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return courseName.size();
    }

    public class ResourcesViewHolder extends RecyclerView.ViewHolder
    {
        TextView courseName;
        TextView instructor;
        TextView platform;
        TextView price;
        TextView f_paid;
        TextView link;
        ImageView cover;

        public ResourcesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            courseName = itemView.findViewById(R.id.courseName);
            instructor = itemView.findViewById(R.id.instructor);
            platform = itemView.findViewById(R.id.platform);
            price = itemView.findViewById(R.id.price);
            f_paid = itemView.findViewById(R.id.f_or_paid);
            link = itemView.findViewById(R.id.link);
            cover = itemView.findViewById(R.id.resource_imageView);

            context = itemView.getContext();

        }
    }

}
