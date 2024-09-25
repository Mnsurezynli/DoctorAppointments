package org.example.Services.impl;

import org.example.Dto.DoctorDto;
import org.example.Model.Appointment;
import org.example.Model.Doctor;
import org.example.Repository.AppointmentRepository;
import org.example.Repository.DoctorRepository;
import org.example.Services.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.xml.ws.Action;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements IDoctorService {

    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    @Transactional
    public DoctorDto saveDoctor(DoctorDto doctorDto) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorDto.getId());
        doctor.setName(doctorDto.getName());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setPhone(doctorDto.getPhone());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setOfficeAddress(doctorDto.getOfficeAddress());
        Doctor savedDoctor = doctorRepository.save(doctor);
        DoctorDto savedDoctorDto = new DoctorDto();
        savedDoctorDto.setId(savedDoctor.getId());
        savedDoctorDto.setName(savedDoctor.getName());
        savedDoctorDto.setSpecialization(savedDoctor.getSpecialization());
        savedDoctorDto.setPhone(savedDoctor.getPhone());
        savedDoctorDto.setEmail(savedDoctor.getEmail());
        savedDoctorDto.setOfficeAddress(savedDoctor.getOfficeAddress());

        return savedDoctorDto;
    }


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

        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (!doctorOptional.isPresent()) {
            throw new IllegalArgumentException("دکتری با این شناسه یافت نشد.");
        }
        Doctor doctor = doctorOptional.get();

        for (LocalDateTime slot : slots) {
            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setStartTime(slot);
            appointment.setEndTime(slot.plusMinutes(30));
            appointment.setStatus("open");
            appointmentRepository.saveAndFlush(appointment);
        }
    }


}


