package org.example.Services.impl;

import org.example.Exception.NotFoundException;
import org.example.Model.Appointment;
import org.example.Repository.AppointmentRepository;
import org.example.Services.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements IAppointmentService {


    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void addFreeTimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("تاریخ پایان باید بعد از تاریخ شروع باشد.");
        }
        if (Duration.between(startTime, endTime).toMinutes() < 30) {
            throw new IllegalArgumentException("مدت زمان باید حداقل 30 دقیقه باشد.");
        }
        List<LocalDateTime> slots = new ArrayList<>();
        for (LocalDateTime time = startTime; time.plusMinutes(30).isBefore(endTime); time = time.plusMinutes(30)) {
            {
                slots.add(time);
            }
            for (LocalDateTime slot : slots) {
                Appointment appointment = new Appointment();
                appointment.setStartTime(slot);
                appointment.setEndTime(slot.plusMinutes(30));
                appointment.setStatus("open");
                appointmentRepository.saveAndFlush(appointment);
            }
        }
    }

    public List<Appointment> viewOpenAppointmentsByDoctor() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<Appointment> openAppointments = appointments.stream()
                .filter(appointment -> appointment.getStatus().equals("open"))
                .collect(Collectors.toList());

        return openAppointments;
    }



    public void deleteOpenAppointment(Long appointmentId) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (!optionalAppointment.isPresent()) {
            throw new NotFoundException("این قرار ملاقات وجود ندارد.");
        }
        Appointment appointment = optionalAppointment.get();
        if (!appointment.getStatus().equals("open")) {
            throw new IllegalArgumentException("این قرار ملاقات نمی‌تواند حذف شود، زیرا قبلاً گرفته شده است.");
        }
        appointmentRepository.delete(appointment);
    }


    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream()
                .collect(Collectors.toList());
    }

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
        appointment.setStatus("taken");
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