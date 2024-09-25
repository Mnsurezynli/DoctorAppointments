package org.example.Repository;

import org.example.Dto.AppointmentDto;
import org.example.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Appointment saveAndFlush(Appointment appointment);

    List<Appointment> findByDoctorId(Long doctorId);

    Optional<Appointment> findById(Long appointmentId);

    List<Appointment> findOpenAppointmentsByStatus(String status);


    List<Appointment> findByPatientPhone(String patientPhone);
}
