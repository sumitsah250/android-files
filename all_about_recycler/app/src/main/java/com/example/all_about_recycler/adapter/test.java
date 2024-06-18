package com.example.all_about_recycler.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.all_about_recycler.R;
import com.example.all_about_recycler.databinding.AddRowBinding;
import com.example.all_about_recycler.databinding.RowBinding;
import com.example.all_about_recycler.fruitdetails;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class test extends RecyclerView.Adapter<test.ViewHolder> {
    Context context;
    ArrayList<fruitdetails> arrayList = new ArrayList<>();

    public test(Context context, ArrayList<fruitdetails> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public test.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull test.ViewHolder holder, int position) {
        holder.binding.rowImage.setImageResource(arrayList.get(position).getImage());
        holder.binding.rowTitle.setText(arrayList.get(position).getTitle());
        holder.binding.rowDetails.setText(arrayList.get(position).getDetails());
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position);


            }
        });

        holder.binding.mainRowRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setMessage("do you want to delete this item?")
                        .setCancelable(false)
                        .setIcon(R.drawable.baseline_assignment_late_24)
                        .setTitle("DELETE ITEM")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemChanged(position);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        holder.binding.mainRowRecycler.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_row);
                EditText edttitle = dialog.findViewById(R.id.edttitle);
                EditText edtdiscription = dialog.findViewById(R.id.edtdescription);
                Button btnadd = dialog.findViewById(R.id.btnadd);
                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayList.add(new fruitdetails(R.drawable.ic_launcher_background,edttitle.getText().toString(),edtdiscription.getText().toString()));
                        notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });

                dialog.show();








                return false;
            }
        });


    }




    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =  RowBinding.bind(itemView);

        }
    }
}
