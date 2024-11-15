package com.example.hands_on_realm.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hands_on_realm.MainActivity;
import com.example.hands_on_realm.R;
import com.example.hands_on_realm.databinding.RowBinding;

import io.realm.RealmResults;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    Context context;
    RealmResults<Student>  studentsdata;

    public StudentAdapter(Context context, RealmResults<Student> studentsdata) {
        this.context = context;
        this.studentsdata = studentsdata;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        Student studentdata = studentsdata.get(position);
        holder.binding.name.setText(studentdata.getName().toString());
        holder.binding.age.setText(String.valueOf(studentdata.getAge()));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                deleteDialog.setTitle("Delete Transaction");
                deleteDialog.setMessage("Are you sure to delete this transaction?");
                deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialogInterface, i) -> {
                    ((MainActivity)context).mainViewModel.deletedata(studentdata);
                });
                deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", (dialogInterface, i) -> {
                    deleteDialog.dismiss();
                });
                deleteDialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentsdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=RowBinding.bind(itemView);
        }
    }
}
