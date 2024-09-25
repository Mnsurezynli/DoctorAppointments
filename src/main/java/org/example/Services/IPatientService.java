package org.example.Services;

import org.example.Dto.AppointmentDto;
import org.example.Dto.PatientDto;

import java.time.LocalDateTime;
import java.util.List;

public interface IPatientService {

    PatientDto savePatient(PatientDto patientDto);

    List<AppointmentDto> getOpenAppointments();

    void selectAppointment(Long appointmentId, String patientName, String patientPhone);

    List<AppointmentDto> getAllAppointments(String patientPhone);
}
