package org.example.Repository;

import org.example.Dto.AppointmentDto;
import org.example.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    Appointment saveAndFlush(AppointmentDto appointmentDto);

    List<Appointment> findByDoctorId(Long doctorId);
}
