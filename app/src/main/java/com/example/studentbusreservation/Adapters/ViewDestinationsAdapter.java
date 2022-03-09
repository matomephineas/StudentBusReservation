package com.example.studentbusreservation.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentbusreservation.MODELS.DestinationDetails;
import com.example.studentbusreservation.R;
import com.example.studentbusreservation.fragments.ViewDestinations;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class ViewDestinationsAdapter extends RecyclerView.Adapter<ViewDestinationsAdapter.viewholder> {
    private List<DestinationDetails> Items;
    private Context context;

    public ViewDestinationsAdapter(List<DestinationDetails> items, Context context) {
        Items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_destination, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.name.setText(Items.get(position).getName());
        holder.city.setText(Items.get(position).getCity());
        holder.type.setText(Items.get(position).getType());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Destination").child(Items.get(position).getDestID());
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context, "Destination successfully deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Destination").child(Items.get(position).getDestID());
                HashMap<String,Object> map = new HashMap<>();

                map.put("name",holder.name.getText().toString().trim());
                map.put("city",holder.city.getText().toString().trim());
                map.put("type",holder.type.getText().toString().trim());

                reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context, "Destination successfully updated", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(context, "failed to update", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        private EditText name, city, type;
        private ImageView edit,delete;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.dname);
            type = itemView.findViewById(R.id.dtype);
            city = itemView.findViewById(R.id.dcity);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
