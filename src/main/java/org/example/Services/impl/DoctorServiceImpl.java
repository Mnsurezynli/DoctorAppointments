package org.example.Services.impl;

import org.example.Model.Appointment;
import org.example.Repository.AppointmentRepository;
import org.example.Repository.DoctorRepository;
import org.example.Services.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.xml.ws.Action;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorServiceImpl implements IDoctorService {

    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Validated
    public void addFreeTimeSlot(Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("تاریخ پایان باید بعد از تاریخ شروع باشد.");
        }

        if (endTime.minusMinutes(30).isBefore(startTime)) {
            throw new IllegalArgumentException("مدت زمان باید حداقل 30 دقیقه باشد.");
        }

        List<LocalDateTime> slots = new ArrayList<>();
        for (LocalDateTime time = startTime; time.plusMinutes(30).isBefore(endTime) || time.plusMinutes(30).isEqual(endTime);
             time = time.plusMinutes(30)) {
            slots.add(time);
        }
        for (LocalDateTime slot : slots) {
            Appointment appointment = new Appointment();
            appointment.setDoctorId(doctorId);
            appointment.setStartTime(slot);
            appointment.setEndTime(slot.plusMinutes(30));
            appointment.setStatus("open");
            appointmentRepository.saveAndFlush(appointment);
        }
    }



}


