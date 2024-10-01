package org.example.Services.impl;

import jdk.jfr.TransitionFrom;
import org.example.Exception.NotFoundException;
import org.example.Model.Appointment;
import org.example.Model.Status;
import org.example.Repository.AppointmentRepository;
import org.example.Services.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements IAppointmentService {


    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    @Transactional
    public void addFreeTimeSlot(LocalDateTime startTime,  LocalDateTime  endTime) {
        if (endTime.isBefore(startTime)  || Duration.between(startTime, endTime).toMinutes() < 30) {
            throw new IllegalArgumentException("تایم غیرمعتبر");
        }

        List<Appointment> appointments = new ArrayList<>();
        while (startTime.plusMinutes(30).isBefore(endTime) || startTime.plusMinutes(30).isEqual(endTime)) {
            Appointment appointment = new Appointment();
            appointment.setId(appointment.getId());
            appointment.setStartTime(LocalDateTime.parse(startTime.toString()));
            appointment.setEndTime(LocalDateTime.parse(startTime.plusMinutes(30).toString()));
            appointment.setStatus(Status.open);
            appointment.setPatientName("null");
            appointment.setPatientPhone("null");
            appointments.add(appointment);
            startTime = startTime.plusMinutes(30);
        }
        appointmentRepository.saveAll(appointments);


    }
    public List<Appointment> viewOpenAppointmentsByDoctor() {
        List<Appointment> appointments = appointmentRepository.findAll();
        if (appointments.isEmpty()) {
            return Collections.emptyList();
        }

        return appointments.stream()
                .filter(appointment -> appointment.getStatus().equals("open") || appointment.getStatus().equals("taken"))
                .map(appointment -> {
                    Appointment appointment1 = new Appointment();
                    appointment1.setId(appointment.getId());
                    appointment1.setStatus(appointment.getStatus());
                    if (appointment.getStatus().equals("taken")) {
                        appointment1.setPatientName(appointment.getPatientName());
                        appointment.setPatientPhone(appointment.getPatientPhone());
                    }
                    return appointment1;
                })
                .collect(Collectors.toList());
    }

@Transactional
    public void deleteOpenAppointment(Long appointmentId) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (!optionalAppointment.isPresent()) {
            throw new NotFoundException("این قرار ملاقات وجود ندارد.");
        }
        Appointment appointment = optionalAppointment.get();
        if (!appointment.getStatus().equals("open")) {
            throw new IllegalArgumentException("این قرار ملاقات نمی‌تواند حذف شود، قبلاً گرفته شده است.");
        }
        appointmentRepository.delete(appointment);

    }


    public List<Appointment> patientViewOpenAppointment() {
        List<Appointment> openAppointments = appointmentRepository.findAll().stream()
                .filter(appointment -> appointment.getStatus().equals("open"))
                .collect(Collectors.toList());
        return openAppointments;
    }



    @Transactional
    public void selectAppointment(Long appointmentId, String patientName, String patientPhone) {
        if (patientName == null || patientPhone == null) {
            throw new IllegalArgumentException("لطفاً نام و شماره تلفن خود را وارد کنید.");
        }
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (!optionalAppointment.isPresent()) {
            throw new NotFoundException("این قرار ملاقات وجود ندارد.");
        }
        Appointment appointment = optionalAppointment.get();

        if (!appointment.getStatus().equals("open")) {
            throw new IllegalArgumentException("این قرار ملاقات قبلاً گرفته شده است یا حذف شده است.");
        }
        appointment.setStatus(Status.valueOf("taken"));
        appointment.setPatientName(patientName);
        appointment.setPatientPhone(patientPhone);
        appointmentRepository.saveAndFlush(appointment);
    }

    public List<Appointment> viewAppointmentsByPatientPhone(String patientPhone) {
        List<Appointment> appointments = appointmentRepository.findByPatientPhoneNumber(patientPhone);
        if (appointments.isEmpty()) {
            return Collections.emptyList();
        }
        return appointments;
    }


}