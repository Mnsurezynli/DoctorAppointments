package org.example.Dto;

import org.example.Model.Appointment;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

public class PatientDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private List<Appointment> appointments;

    public PatientDto() {
    }

    public PatientDto(Long id, String name, String phoneNumber, String email, List<Appointment> appointments) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.appointments = appointments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
