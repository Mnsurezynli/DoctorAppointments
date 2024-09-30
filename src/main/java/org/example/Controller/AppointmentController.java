package org.example.Controller;

import org.example.Model.Appointment;
import org.example.Services.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/Appointment")
public class AppointmentController {

    private IAppointmentService iAppointmentService;

    @Autowired
    public AppointmentController(IAppointmentService iAppointmentService) {
        this.iAppointmentService = iAppointmentService;
    }

    @PostMapping("/freeTime")
    public void addFreeTimeSlot(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        iAppointmentService.addFreeTimeSlot(startTime, endTime);
    }


    @GetMapping("/viewByDoctor")
    public List<Appointment> viewOpenAppointmentsByDoctor() {
        return iAppointmentService.getAllAppointments();
    }

    @GetMapping("/viewAppointment")
    public List<Appointment> viewAppointmentsByPatientPhone(@RequestParam String patientPhone){
        return iAppointmentService.viewAppointmentsByPatientPhone(patientPhone);
    }

    @PostMapping("/selectAppointment")
    public void selectAppointment(@RequestParam Long appointmentId, @RequestParam String patientName, String patientPhone) {
        iAppointmentService.selectAppointment(appointmentId, patientName, patientPhone);
    }

    @DeleteMapping("/{appointmentId}")
    public void deleteOpenAppointmentnt(@PathVariable Long appointmentId) {
        iAppointmentService.deleteOpenAppointment(appointmentId);
    }

    @GetMapping("/all")
    public List<Appointment> getAllAppointments() {
        return iAppointmentService.getAllAppointments();
    }

}


