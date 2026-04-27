package com.example.demo.dto;
 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Past;
 
import java.time.LocalDate;

public class CitizenDTO {
 
    @NotBlank(message = "Name is required")
    private String name;
 
    @Past(message = "DOB cannot be in future")
    private LocalDate dob;
 
    @NotBlank(message = "Gender is required")
    private String gender;
 
    @NotBlank(message = "Address is required")
    private String address;
 
    @NotBlank(message = "Contact info is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact must be 10 digits")
    private String contactInfo;
 
    // Getters and Setters
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public LocalDate getDob() {
        return dob;
    }
 
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
 
    public String getGender() {
        return gender;
    }
 
    public void setGender(String gender) {
        this.gender = gender;
    }
 
    public String getAddress() {
        return address;
    }
 
    public void setAddress(String address) {
        this.address = address;
    }
 
    public String getContactInfo() {
        return contactInfo;
    }
 
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}
 