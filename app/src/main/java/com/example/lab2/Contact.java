package com.example.lab2;

public class Contact {
    private int id;
    private String name;
    private String phonenumber;
    private String email;
    private boolean status;
    private String ImagePath;
    public Contact(int Id, String Name, String phoneNumber, String email, boolean Status,String ImagePath){
        this.id= Id;
        this.name = Name;
        this.phonenumber= phoneNumber;
        this.email = email;
        this.status = Status;
        this.ImagePath = ImagePath;
    }
    public void setContact(int id, String name, String phoneNumber, String email, boolean status, String imagePath) {
        this.id = id;
        this.name = name;
        this.phonenumber = phoneNumber;
        this.email = email;
        this.status = status;
        this.ImagePath = imagePath;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
