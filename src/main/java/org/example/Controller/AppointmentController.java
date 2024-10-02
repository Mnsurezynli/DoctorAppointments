package org.example.Controller;

import org.example.Dto.FreeTimeSlotDto;
import org.example.Model.Appointment;
import org.example.Services.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
    public void addFreeTimeSlot(@RequestBody FreeTimeSlotDto freeTimeSlotDto) {
        iAppointmentService.addFreeTimeSlot(freeTimeSlotDto);
    }


    @GetMapping("/viewByDoctor")
    public List<Appointment> viewOpenAppointmentsByDoctor() {
        return iAppointmentService.viewOpenAppointmentsByDoctor();
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
    public List<Appointment> patientViewOpenAppointment() {
        return iAppointmentService.patientViewOpenAppointment();
    }

}