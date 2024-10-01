package org.example.Services;

import org.example.Model.Appointment;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IAppointmentService {

    void addFreeTimeSlot(LocalDateTime startTime, LocalDateTime endTime);

    List<Appointment> viewOpenAppointmentsByDoctor();

    void deleteOpenAppointment(Long appointmentId);

    List<Appointment> patientViewOpenAppointment();

    void selectAppointment(Long appointmentId, String patientName, String patientPhone);

    List<Appointment> viewAppointmentsByPatientPhone(String patientPhone);

}
