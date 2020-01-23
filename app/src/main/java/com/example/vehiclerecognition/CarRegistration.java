package com.example.vehiclerecognition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vehiclerecognition.model.CarRegister;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class CarRegistration extends AppCompatActivity implements View.OnClickListener {

    EditText Body,Brand,Year,Color,Model,Number;
    Button Submit,chooseImage;
    ImageView imageView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference carDitails;
    private StorageReference mStorageRef;

    StorageTask uploadTask;

    private Uri imageUri;
    private static final int imageRequest = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_registration);

        firebaseDatabase = FirebaseDatabase.getInstance();
        carDitails = firebaseDatabase.getReference().child("Vehicle Recognition").child("Vehicle");
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Vehicle Recognition").child("Vehicle");
        Body = findViewById(R.id.bodyText);
        Brand = findViewById(R.id.brandText);
        Year = findViewById(R.id.yearText);
        Color = findViewById(R.id.colorText);
        Model = findViewById(R.id.modelText);
        Number = findViewById(R.id.numberText);

        imageView = findViewById(R.id.imageView);

        chooseImage = findViewById(R.id.imageChooseButton);
        chooseImage.setOnClickListener(this);
        Submit = findViewById(R.id.submitButton);
        Submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.imageChooseButton:
                OpenFileChooser();
                break;
            case R.id.submitButton:

                SaveData();
               /* String body = Body.getText().toString().trim();
                String brand = Brand.getText().toString().trim();
                String year = Year.getText().toString().trim();
                String color = Color.getText().toString().trim();
                String model = Model.getText().toString().trim();
                String number = Number.getText().toString().trim();

                String ID = carDitails.push().getKey();

                CarRegister carRegister = new CarRegister(body,brand,year,color,model,number);

                carDitails.child(ID).setValue(carRegister);*/

                Body.setText("");
                Brand.setText("");
                Year.setText("");
                Color.setText("");
                Model.setText("");
                Number.setText("");


                break;
        }
    }

    private void OpenFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,imageRequest);
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
        super.onActivityResult(requestCode , resultCode , data);
        if (requestCode == imageRequest && resultCode == RESULT_OK && data!= null && data.getData()!=null)
        {
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(imageView);
        }
    }

    public String getFileExtention(Uri imageUri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }


    private void SaveData() {

        final String body = Body.getText().toString().trim();
        final String brand = Brand.getText().toString().trim();
        final String year = Year.getText().toString().trim();
        final String color = Color.getText().toString().trim();
        final String model = Model.getText().toString().trim();
        final String number = Number.getText().toString().trim();



        StorageReference ref = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtention(imageUri));
        ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                while (!uriTask.isSuccessful());

                Uri downloadUri = uriTask.getResult();

                CarRegister carRegister = new CarRegister(body,brand,year,color,model,number,downloadUri.toString());
                String ID = carDitails.push().getKey();
                carDitails.child(ID).setValue(carRegister);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(CarRegistration.this , "Image Is not save Successfully" , Toast.LENGTH_SHORT).show();
            }
        });
    }


}
