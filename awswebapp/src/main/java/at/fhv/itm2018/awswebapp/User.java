package at.fhv.itm2018.awswebapp;


import lombok.Getter;
import lombok.Setter;

public class User {

    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;

    public User (String fname, String lname) {
        firstName = fname;
        lastName = lname;
    }



}

