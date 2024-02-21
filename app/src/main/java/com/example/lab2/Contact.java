package com.example.lab2;

public class Contact {
    private int id;
    private String name;
    private String phonenumber;
    private boolean status;
    private String ImagePath;
    public Contact(int Id, String Name, String phoneNumber, boolean Status,String ImagePath){
        this.id= Id;
        this.name = Name;
        this.phonenumber= phoneNumber;
        this.status = Status;
        this.ImagePath = ImagePath;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}
