package org.example.Services;

import org.example.Model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {

     List<Appointment> viewOpenAppointmentsByDoctor();

    void deleteOpenAppointment(Long appointmentId);

     void addFreeTimeSlot(LocalDateTime startTime, LocalDateTime endTime);

    List<Appointment> viewAppointmentsByPatientPhone(String patientPhone);

    void selectAppointment(Long appointmentId, String patientName, String patientPhone);

    List<Appointment> getAllAppointments();
}
