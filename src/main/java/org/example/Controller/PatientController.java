package org.example.Controller;

import org.example.Dto.AppointmentDto;
import org.example.Dto.PatientDto;
import org.example.Services.IAppointmentService;
import org.example.Services.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Patient")
public class PatientController {

    private IPatientService iPatientService;
    private IAppointmentService iAppointmentService;

    @Autowired
    public PatientController(IPatientService iPatientService) {
        this.iPatientService = iPatientService;

    }

    @PostMapping
    public ResponseEntity<PatientDto> addPatient(@RequestBody PatientDto patientDto) {
        PatientDto savedPatient = iPatientService.savePatient(patientDto);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @GetMapping("/open")
    public List<AppointmentDto> getOpenAppointments() {
        return iPatientService.getOpenAppointments();
    }

    @PostMapping("/selectAppointment")
    public void selectAppointment(@RequestParam Long appointmentId, @RequestParam String patientName, @RequestParam String patientPhone) {
        iPatientService.selectAppointment(appointmentId, patientName, patientPhone);
    }

    @GetMapping("/appointments")
    public List<AppointmentDto> getAllAppointments(@RequestParam String patientPhone) {
        return iPatientService.getAllAppointments(patientPhone);
    }
}
