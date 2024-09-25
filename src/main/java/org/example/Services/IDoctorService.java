package org.example.Services;

import org.example.Dto.DoctorDto;

import java.time.LocalDateTime;

public interface IDoctorService {

    DoctorDto saveDoctor(DoctorDto doctorDto);

    void addFreeTimeSlot(Long doctorId, LocalDateTime startTime, LocalDateTime endTime);
}
