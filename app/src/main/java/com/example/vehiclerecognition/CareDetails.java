package com.example.vehiclerecognition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.vehiclerecognition.Adapter.CarDetailAdapter;
import com.example.vehiclerecognition.model.CarRegister;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CareDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    private CarDetailAdapter carDetailAdapter;
    private List<CarRegister> carRegisters;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference carDitails;
    private StorageReference mStorageRef;
    private FirebaseStorage mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_details);

        Intent intent = getIntent();
        String text = intent.getStringExtra(MainActivity.EXTRA_TEXT);

        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        carDitails = firebaseDatabase.getReference().child("Vehicle Recognition").child("Vehicle");
        //carDitails.addListenerForSingleValueEvent(valueEventListener);

        Query query = firebaseDatabase.getReference().child("Vehicle Recognition").child("Vehicle")
                .orderByChild("number").equalTo(text);

        query.addListenerForSingleValueEvent(valueEventListener);

        carRegisters = new ArrayList<>();

        carDetailAdapter = new CarDetailAdapter(this,carRegisters);

        Toast.makeText(this , ""+text , Toast.LENGTH_SHORT).show();
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists())
            {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    CarRegister carRegister = dataSnapshot1.getValue(CarRegister.class);
                    carRegisters.add(carRegister);
                }

                recyclerView.setAdapter(carDetailAdapter);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
