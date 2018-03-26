package com.dk.agriculture;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FarmScheduleActivity extends AppCompatActivity {
    RecyclerView cropsbatch;
    ArrayList<BatchModel> batchModels= new ArrayList<>();
    SQLiteHelper db = new SQLiteHelper(this);
    FloatingActionButton mfab;
    DatabaseReference myBatchbase= FirebaseDatabase.getInstance().getReference("farmer").child("9769084086").child("myBatches");
    RecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_schedule);
        cropsbatch= findViewById(R.id.cropsbatch);
        mfab = (FloatingActionButton) findViewById(R.id.fab);
        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBatchDialog();
            }
        });
        cropsbatch.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        cropsbatch.setLayoutManager(mLayoutManager);
        FirebaseDatabase.getInstance().getReference("farmer").child(db.getAllValues().get("phone")).child("myBatches").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){

                }
                else {
                    for (DataSnapshot fire : dataSnapshot.getChildren()) {
                        batchModels.add(fire.getValue(BatchModel.class));
                    }
                    Toast.makeText(FarmScheduleActivity.this, Integer.toString(batchModels.size()), Toast.LENGTH_SHORT).show();
                    recyclerAdapter= new RecyclerAdapter(batchModels);
                    cropsbatch.setAdapter(recyclerAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        ArrayList<BatchModel> batchModels = new ArrayList<>();

        RecyclerAdapter(ArrayList<BatchModel> batchModels){
            this.batchModels=batchModels;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView harvestat,waterat, fertilizeat, cropname;
            public Button harvest;
            public ViewHolder(View v) {
                super(v);
                harvestat =v.findViewById(R.id.harvestat);
                waterat= v.findViewById(R.id.waterat);
                fertilizeat= v.findViewById(R.id.fertilizeat);
                cropname= v.findViewById(R.id.cropname);
                harvest=v.findViewById(R.id.harvest);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.crop_batch_row, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((ViewHolder)holder).cropname.setText(batchModels.get(position).getCropname());
            ((ViewHolder)holder).fertilizeat.setText(batchModels.get(position).getFertilizeat());
            ((ViewHolder)holder).harvestat.setText(batchModels.get(position).getHarvestat());
            ((ViewHolder)holder).waterat.setText(batchModels.get(position).getWaterat());
            ((ViewHolder)holder).harvest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference("farmer").child(db.getAllValues().get("phone")).child("myBatches").child(batchModels.get(position).getId()).removeValue();
                    FirebaseDatabase.getInstance().getReference("farmer").child(db.getAllValues().get("phone")).child("myBatches").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.exists()){
                                batchModels.clear();
                                recyclerAdapter= new RecyclerAdapter(batchModels);
                                cropsbatch.setAdapter(recyclerAdapter);
                            }
                            else {
                                batchModels.clear();
                                for (DataSnapshot fire : dataSnapshot.getChildren()) {
                                    batchModels.add(fire.getValue(BatchModel.class));
                                }
                                Toast.makeText(FarmScheduleActivity.this, Integer.toString(batchModels.size()), Toast.LENGTH_SHORT).show();
                                recyclerAdapter= new RecyclerAdapter(batchModels);
                                cropsbatch.setAdapter(recyclerAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return batchModels.size();
        }
    }

    public void AddBatchDialog(){
        final AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View conformationview = layoutInflater.inflate(R.layout.batch_dialog, null);
        dialogbuilder.setView(conformationview);
        dialogbuilder.setTitle("Add Crop Batch");
        final AlertDialog conformationdialog = dialogbuilder.create();
        conformationdialog.show();
        final TextView cropname= conformationview.findViewById(R.id.cropname);
        final TextView harvestat= conformationview.findViewById(R.id.harvestat);
        final TextView waterat= conformationview.findViewById(R.id.waterat);
        final TextView fertilizeat= conformationview.findViewById(R.id.fertilizeat);
        Button add= conformationview.findViewById(R.id.add);
        Button cancel= conformationview.findViewById(R.id.cancel);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key =myBatchbase.push().getKey();
                final BatchModel batchModel= new BatchModel(cropname.getText().toString(),
                        harvestat.getText().toString(),
                        fertilizeat.getText().toString(),
                        waterat.getText().toString(), key);
                myBatchbase.child(key).setValue(batchModel);

                FirebaseDatabase.getInstance().getReference("farmer").child(db.getAllValues().get("phone")).child("myBatches").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){

                        }
                        else {
                            batchModels.clear();
                            for (DataSnapshot fire : dataSnapshot.getChildren()) {
                                batchModels.add(fire.getValue(BatchModel.class));
                            }
                            Toast.makeText(FarmScheduleActivity.this, Integer.toString(batchModels.size()), Toast.LENGTH_SHORT).show();
                            recyclerAdapter= new RecyclerAdapter(batchModels);
                            cropsbatch.setAdapter(recyclerAdapter);
                            conformationdialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Toast.makeText(FarmScheduleActivity.this, "Your Batch has been placed!", Toast.LENGTH_SHORT).show();
                conformationdialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conformationdialog.dismiss();
            }
        });
    }
}