package org.example.Controller;

import org.example.Dto.DoctorDto;
import org.example.Services.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/Doctor")
public class DoctorController {

     private IDoctorService iDoctorService;

    @Autowired
    public DoctorController(IDoctorService iDoctorService) {
        this.iDoctorService=iDoctorService;
    }

    @PostMapping("/add")
    public ResponseEntity<DoctorDto> addDoctor(@RequestBody DoctorDto doctorDto) {
        DoctorDto savedDoctor = iDoctorService.saveDoctor(doctorDto);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @PostMapping("/{doctorId}/freeTime")
    public void addFreeTimeSlot(@PathVariable Long doctorId, @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
       iDoctorService.addFreeTimeSlot(doctorId, startTime, endTime);
    }


}
