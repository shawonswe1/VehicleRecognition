package com.example.vehiclerecognition.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vehiclerecognition.R;
import com.example.vehiclerecognition.model.CarRegister;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarDetailAdapter extends RecyclerView.Adapter<CarDetailAdapter.MyViewHolder> {

    Context context;
    List<CarRegister> carRegisterList;

    public CarDetailAdapter(Context context , List<CarRegister> carRegisterList) {
        this.context = context;
        this.carRegisterList = carRegisterList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.car_detail_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder , int position) {

        CarRegister carRegister = carRegisterList.get(position);
        holder.Body.setText(carRegister.getBody());
        holder.Brand.setText(carRegister.getBrand());
        holder.Color.setText(carRegister.getColor());
        holder.Model.setText(carRegister.getModel());
        holder.Year.setText(carRegister.getYear());
        holder.Number.setText(carRegister.getNumber());

        Picasso.with(context).load(carRegister.getImage())
                .placeholder(R.drawable.picture)
                .fit().centerCrop().into(holder.imageView);

        Glide.with(context).load(carRegister.getImage())
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.picture)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return carRegisterList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView Body,Brand,Year,Color,Model,Number;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Body = itemView.findViewById(R.id.bodyText);
            Brand = itemView.findViewById(R.id.brandText);
            Year = itemView.findViewById(R.id.yearText);
            Color = itemView.findViewById(R.id.colorText);
            Model = itemView.findViewById(R.id.modelText);
            Number = itemView.findViewById(R.id.numberText);

            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
