package org.example.Services.impl;

import jdk.jfr.TransitionFrom;
import org.example.Dto.FreeTimeSlotDto;
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
    public void addFreeTimeSlot(FreeTimeSlotDto freeTimeSlotDto) {
        LocalDateTime startTime = freeTimeSlotDto.getStartTime();
        LocalDateTime endTime = freeTimeSlotDto.getEndTime();

        if (endTime.isBefore(startTime) || Duration.between(startTime, endTime).toMinutes() < 30) {
            throw new IllegalArgumentException("تایم غیرمعتبر");
        }

        List<Appointment> appointments = new ArrayList<>();
        while (startTime.plusMinutes(30).isBefore(endTime) || startTime.plusMinutes(30).isEqual(endTime)) {
            Appointment appointment = new Appointment();
            appointment.setStartTime(startTime);
            appointment.setEndTime(startTime.plusMinutes(30));
            appointment.setStatus(Status.open);
            appointment.setPatientName("");
            appointment.setPatientPhone("");
            appointments.add(appointment);
            startTime = startTime.plusMinutes(30);
        }
        appointmentRepository.saveAll(appointments);
    }

    public List<Appointment> viewOpenAppointmentsByDoctor() {
        List<Appointment> allAppointments = appointmentRepository.findAll();
        System.out.println("Total Appointments: " + allAppointments.size());
        allAppointments.forEach(a -> System.out.println("ID: " + a.getId() + ", Status: " + a.getStatus()));

        List<Appointment> openAppointments = allAppointments.stream()
                .filter(appointment -> appointment.getStatus().equals(Status.open))
                .collect(Collectors.toList());
        return openAppointments;
    }
    @Transactional
    public void deleteOpenAppointment(Long appointmentId) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (!optionalAppointment.isPresent()) {
            throw new NotFoundException("این قرار ملاقات وجود ندارد.");
        }
        Appointment appointment = optionalAppointment.get();
        System.out.println("Appointment Status: " + appointment.getStatus());

        if (!appointment.getStatus().equals(Status.open)) {
            throw new IllegalArgumentException("این قرار ملاقات نمی‌تواند حذف شود، قبلاً گرفته شده است.");
        }
        appointmentRepository.delete(appointment);
    }


    public List<Appointment> patientViewOpenAppointment() {
        List<Appointment> allAppointments = appointmentRepository.findAll();
        System.out.println("Total Appointments: " + allAppointments.size());
        allAppointments.forEach(a -> System.out.println("ID: " + a.getId() + ", Status: " + a.getStatus()));

        List<Appointment> openAppointments = allAppointments.stream()
                .filter(appointment -> appointment.getStatus().equals(Status.open))
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

        System.out.println("Current status: " + appointment.getStatus());

        if (!appointment.getStatus().equals(Status.open)) {
            throw new IllegalArgumentException("این قرار ملاقات قبلاً گرفته شده است یا حذف شده است.");
        }

        appointment.setStatus(Status.taken);
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