package org.example.Services.impl;

import org.example.Dto.PatientDto;
import org.example.Exception.ResourceAlreadyExistsException;
import org.example.Model.Patient;
import org.example.Repository.PatientRepository;
import org.example.Services.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PatientServiceImpl implements IPatientService {

    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }




    public PatientDto convertToDto(Patient patient) {
        PatientDto patientDto=new PatientDto();
       patientDto.setId(patient.getId());
       patientDto.setName(patient.getName());
       patientDto.setPhoneNumber(patient.getPhoneNumber());
       patientDto.setEmail(patient.getEmail());
        return patientDto;
    }


        private Patient convertToEntity(PatientDto patientDto) {
        Patient patient = new Patient();
        patient.setId(patientDto.getId());
        patient.setName(patientDto.getName());
        patient.setEmail(patientDto.getEmail());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        return patient;

    }
}
