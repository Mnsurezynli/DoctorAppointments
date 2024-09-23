package org.example.Services;

import org.example.Dto.AppointmentDto;

import java.util.List;

public interface IAppointmentService {

    List<AppointmentDto> viewAppointmentsByDoctor(Long doctorId);
}
