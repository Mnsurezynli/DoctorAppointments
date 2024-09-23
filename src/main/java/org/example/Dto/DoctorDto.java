package org.example.Dto;

import org.example.Model.Appointment;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;

public class DoctorDto {
    private Long id;
    private String name;
    private String specialization;
    private String phone;
    private String email;
    private String officeAddress;
    private List<Appointment> appointments;

    public DoctorDto() {
    }

    public DoctorDto(Long id, String name, String specialization, String phone, String email, String officeAddress, List<Appointment> appointments) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
        this.officeAddress = officeAddress;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
