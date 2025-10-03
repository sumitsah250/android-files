package com.example.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class RecyclerAdapter1 extends RecyclerView.Adapter<RecyclerAdapter1.ViewHolder> {
    Context context;
    ArrayList<taskModel>  task;
    RecyclerAdapter1(Context context,ArrayList<taskModel> task){
        this.context=context;
        this.task=task;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.checkbox.setText(task.get(position).rcheckbox);
        holder.date.setText(task.get(position).rdate);
        holder.time.setText(task.get(position).rtime);




        mydbhelper3 dbhelper3;
        dbhelper3 = new mydbhelper3(context);




        holder.lladd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete task")
                        .setMessage("do you really want to delete the task ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                task.remove(position);
                                notifyItemRemoved(position);

                                Contactmodel contactmodel = new Contactmodel();
                                ArrayList<Contactmodel> arrcontacts = dbhelper3.getcontect();
                                contactmodel.id=arrcontacts.get(position).id;
                                dbhelper3.DeleteContact(contactmodel);

                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                return true;
            }
        });

        holder.lladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.add_task);
                EditText edttask = dialog1.findViewById(R.id.edttask);
                EditText edtdate = dialog1.findViewById(R.id.edtdate);
                EditText edttime= dialog1.findViewById(R.id.edttime);
                TextView txtadd = dialog1.findViewById(R.id.txtadd);
                Button btnAction = dialog1.findViewById(R.id.btnAction);
                btnAction.setText("UPDATE");
                txtadd.setText("Update task");

                edttask.setText(task.get(position).rcheckbox);
                edtdate.setText(task.get(position).rdate);
                edttime.setText(task.get(position).rtime);
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String task1 ="";
                        String date1= "";
                        String time1="";
                        if(!edttask.getText().toString().equals("")){
                            task1=edttask.getText().toString();
                        }
                        else{
                            Toast.makeText(context, "task can't be empty", Toast.LENGTH_SHORT).show();
                        }
                        if(!edtdate.getText().toString().equals("")){
                            date1=edtdate.getText().toString();
                        }
                        else {
                            Toast.makeText(context, "date missing", Toast.LENGTH_SHORT).show();
                        }
                        if(!edttime.getText().toString().equals("")){
                            time1=edttime.getText().toString();
                        }
                        else {
                            Toast.makeText(context, "time missing", Toast.LENGTH_SHORT).show();
                        }
                        if(!edttask.getText().toString().equals("") && !edttime.getText().toString().equals("") && !edtdate.getText().toString().equals("")){
                            task.set(position,new taskModel(task1,date1,time1));
                            notifyItemChanged(position);

                            Contactmodel contactmodel = new Contactmodel();
                            contactmodel.id=position;
//                            Toast.makeText(context, ""+contactmodel.id, Toast.LENGTH_SHORT).show();
                            contactmodel.name=edttask.getText().toString();
                            contactmodel.date=edtdate.getText().toString();
                            contactmodel.time=edttime.getText().toString();

                            ArrayList<Contactmodel> arrcontacts = dbhelper3.getcontect();
                            contactmodel.id=arrcontacts.get(position).id;

                            dbhelper3.UpdateContact(contactmodel);
                            dialog1.dismiss();

                        }
                    }
                });
                dialog1.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return task.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;
        TextView date,time;
        LinearLayout lladd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
            date = itemView.findViewById(R.id.date);
            time=  itemView.findViewById(R.id.time);
            lladd= itemView.findViewById(R.id.lladd);
        }
    }
}
