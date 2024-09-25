package org.example.Controller;

import org.example.Dto.AppointmentDto;
import org.example.Services.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Appointment")
public class AppointmentController {

    private IAppointmentService iAppointmentService;

    @Autowired
    public AppointmentController(IAppointmentService iAppointmentService) {
        this.iAppointmentService = iAppointmentService;
    }


    @GetMapping("/doctor/{doctorId}")
    public List<AppointmentDto> viewAppointmentsByDoctor(@PathVariable Long doctorId) {
        return iAppointmentService.viewAppointmentsByDoctor(doctorId);
    }

    @DeleteMapping("/{appointmentId}/doctor/{doctorId}")
    public void deleteAppointment(@PathVariable Long appointmentId, @PathVariable Long doctorId) {
        iAppointmentService.deleteAppointment(appointmentId, doctorId);
    }
}
