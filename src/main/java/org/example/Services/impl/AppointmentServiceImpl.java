package org.example.Services.impl;

import org.example.Dto.AppointmentDto;
import org.example.Dto.PatientDto;
import org.example.Exception.ConflictException;
import org.example.Exception.NotFoundException;
import org.example.Model.Appointment;
import org.example.Model.Doctor;
import org.example.Model.Patient;
import org.example.Repository.AppointmentRepository;
import org.example.Services.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements IAppointmentService {


    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<AppointmentDto> viewAppointmentsByDoctor(Long doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        if (appointments.isEmpty()) {
            return Collections.emptyList();
        }
        return appointments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public void deleteAppointment(Long appointmentId, Long doctorId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("قرار ملاقات یافت نشد."));
        if (!appointment.getStatus().equals("open")) {
            throw new ConflictException("قرار ملاقات توسط بیمار گرفته شده است.");
        }
        appointmentRepository.delete(appointment);
    }


    public AppointmentDto convertToDto(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        AppointmentDto dto = new AppointmentDto();
        dto.setId(appointment.getId());
        if (appointment.getDoctor() != null) {
            dto.setDoctorId(appointment.getDoctor().getId());
        }
        dto.setStartTime(appointment.getStartTime());
        dto.setEndTime(appointment.getEndTime());
        dto.setStatus(appointment.getStatus());
        if (appointment.getPatient() != null) {
            dto.setPatientName(appointment.getPatient().getName());
            dto.setPatientPhone(appointment.getPatient().getPhoneNumber());
        }

        return dto;
    }

    public Appointment convertToEntity(AppointmentDto appointmentDto) {
        if (appointmentDto == null) {
            return null;
        }

        Appointment appointment = new Appointment();
        appointment.setId(appointmentDto.getId());

        if (appointmentDto.getDoctorId() != null) {
            Doctor doctor = new Doctor();
            doctor.setId(appointmentDto.getDoctorId());
            appointment.setDoctor(doctor);
        }

        appointment.setStartTime(appointmentDto.getStartTime());
        appointment.setEndTime(appointmentDto.getEndTime());
        appointment.setStatus(appointmentDto.getStatus());

        if (appointmentDto.getPatientName() != null && appointmentDto.getPatientPhone() != null) {
            Patient patient = new Patient();
            patient.setName(appointmentDto.getPatientName());
            patient.setPhoneNumber(appointmentDto.getPatientPhone());
            appointment.setPatient(patient);
        }

        return appointment;
    }

}