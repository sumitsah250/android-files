package com.boss.class10allguidemanual2081.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.class10allguidemanual2081.R;
import com.boss.class10allguidemanual2081.modelclass.Model;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHoder> {
    private Context context;
    private ArrayList<Model> models;

    public Adapter(Context context, ArrayList<Model> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public Adapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row,parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHoder holder, int position) {
        Model model = models.get(position);
        holder.chapter.setText(model.getChapter());
        holder.subtitle.setText("Chapter : "+(position+1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context,R.style.CustomDialogTheme);
                dialog.setContentView(R.layout.item_selected_choice);
                AppCompatButton btnBook = dialog.findViewById(R.id.btnBook);
                AppCompatButton btnSolution = dialog.findViewById(R.id.btnSolution);
                btnBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Details.class);
                        intent.putExtra("chapter",model.getChapter());
                        intent.putExtra("Book_Pdf_link",model.getBookPdfLink());
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                });

                btnSolution.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Details.class);
                        intent.putExtra("chapter",model.getChapter());
                        intent.putExtra("Book_Pdf_link",model.getSolutionPdfLink());
                        context.startActivity(intent);
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });




    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView chapter,subtitle;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            chapter=itemView.findViewById(R.id.txtchapter);
            subtitle=itemView.findViewById(R.id.subtitle);
        }
    }
}
