package org.example.Services;

import org.example.Dto.FreeTimeSlotDto;
import org.example.Model.Appointment;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IAppointmentService {

    void addFreeTimeSlot(FreeTimeSlotDto freeTimeSlotDto);

    List<Appointment> viewOpenAppointmentsByDoctor();

    void deleteOpenAppointment(Long appointmentId);

    List<Appointment> patientViewOpenAppointment();

    void selectAppointment(Long appointmentId, String patientName, String patientPhone);

    List<Appointment> viewAppointmentsByPatientPhone(String patientPhone);


}
