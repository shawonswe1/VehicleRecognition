package com.example.vehiclerecognition.model;

public class CarRegister {

    String body,brand,year,color,model,number,image;

    public CarRegister() {
    }

    public CarRegister(String body , String brand , String year , String color , String model , String number , String image) {
        this.body = body;
        this.brand = brand;
        this.year = year;
        this.color = color;
        this.model = model;
        this.number = number;
        this.image = image;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
