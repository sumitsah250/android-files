package com.example.recyclerview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder>
{
    Context context;
    ArrayList<ContactModel> arrContacts;
    private int lastPosition = -1;
    RecyclerContactAdapter(Context context, ArrayList<ContactModel> arrContacts){
        this.arrContacts=arrContacts;
        this.context=context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view =  LayoutInflater.from(context).inflate(R.layout.contact_row,parent,false);
       ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgContact.setImageResource(arrContacts.get(position).img);
        holder.txtName.setText(arrContacts.get(position).name);
        holder.txtNumber.setText(arrContacts.get(position).number);
        setAnimation(holder.itemView,position);
        holder.llRow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context );
                dialog.setContentView(R.layout.add);
                EditText edtname = dialog.findViewById(R.id.edtname);
                EditText edtnumber = dialog.findViewById(R.id.edtnumber);
                Button btnAction = dialog.findViewById(R.id.btnAction);
                TextView txttitle = dialog.findViewById(R.id.txtTitle);
                txttitle.setText("Update");
                btnAction.setText("update");
                edtname.setText(arrContacts.get(position).name);
                edtnumber.setText(arrContacts.get(position).number);
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name="";
                        String number="";
                        if (!edtname.getText().toString().equals("")) {
                            name = edtname.getText().toString();
                        } else
                            Toast.makeText(context, "please enter contact name", Toast.LENGTH_SHORT).show();
                        if (!edtnumber.getText().toString().equals("")) {
                            number = edtnumber.getText().toString();
                        } else {
                            Toast.makeText(context, "please enter contact number", Toast.LENGTH_SHORT).show();
                        }
                        arrContacts.set(position,new ContactModel(arrContacts.get(position).img,name,number));
                        notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        holder.btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.llRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Delete Contact")
                        .setMessage("are you sure you want to delete this contact")
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrContacts.remove(position);
                                notifyItemRemoved( position);
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
    }
    @Override
    public int getItemCount() {

        return arrContacts.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtName,txtNumber;
        ImageView imgContact;
        LinearLayout llRow;
        FloatingActionButton btndialog;
        public ViewHolder( View itemView){
            super(itemView);
            txtName=itemView.findViewById(R.id.txtname);
            txtNumber=itemView.findViewById(R.id.txtcontact);
            imgContact = itemView.findViewById(R.id.imgcontact);
            llRow=itemView.findViewById(R.id.llRow);
            btndialog=itemView.findViewById(R.id.btnOpenDialog);
        }

    }
    private void setAnimation(View viewToAnimation, int position){
        if(position>lastPosition){

            Animation slideIn = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            viewToAnimation.startAnimation(slideIn);
            lastPosition=position;

        }
    }
}
