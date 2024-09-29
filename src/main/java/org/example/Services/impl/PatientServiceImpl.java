package org.example.Services.impl;

import org.example.Dto.AppointmentDto;
import org.example.Dto.PatientDto;
import org.example.Exception.ConflictException;
import org.example.Exception.NotFoundException;
import org.example.Model.Appointment;
import org.example.Model.Patient;
import org.example.Repository.AppointmentRepository;
import org.example.Repository.PatientRepository;
import org.example.Services.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements IPatientService {

    private PatientRepository patientRepository;

    private AppointmentRepository appointmentRepository;

    private AppointmentServiceImpl appointmentService;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, AppointmentRepository appointmentRepository, AppointmentServiceImpl appointmentService) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
    }


    @Override
    public PatientDto savePatient(PatientDto patientDto) {
        Patient patient = convertToEntity(patientDto);
        Patient savedPatient = patientRepository.save(patient);
        return convertToDto(savedPatient);
    }


    public List<AppointmentDto> getOpenAppointments() {
        List<Appointment> appointments = appointmentRepository.findOpenAppointmentsByStatus("OPEN");
        return appointments.stream()
                .map(appointmentService::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void selectAppointment(Long appointmentId, String patientName, String patientPhone) {
        if (patientName == null || patientPhone == null) {
            throw new IllegalArgumentException("نام و شماره تلفن باید ارائه شوند.");
        }
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("قرار ملاقات یافت نشد."));
        if (!appointment.getStatus().equals("open")) {
            throw new ConflictException("این قرار ملاقات قبلاً گرفته شده یا حذف شده است.");
        }
        appointment.setStatus("BOOKED");
        appointment.setPatientName(patientName);
        appointment.setPatientPhone(patientPhone);
        appointmentRepository.save(appointment);
    }


    public List<AppointmentDto> getAllAppointments(String patientPhone) {
        List<Appointment> appointments = appointmentRepository.findByPatientPhone(patientPhone);
        return appointments.stream()
                .map(appointmentService::convertToDto)
                .collect(Collectors.toList());
    }


    public PatientDto convertToDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        patientDto.setName(patient.getName());
        patientDto.setPhoneNumber(patient.getPhoneNumber());
        patientDto.setEmail(patient.getEmail());
        return patientDto;
    }


    private Patient convertToEntity(PatientDto patientDto) {
        Patient patient = new Patient();
        patient.setName(patientDto.getName());
        patient.setEmail(patientDto.getEmail());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        return patient;

    }
}
