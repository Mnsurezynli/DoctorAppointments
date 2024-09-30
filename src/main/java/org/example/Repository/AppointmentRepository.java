package org.example.Repository;

import org.example.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findById(Long appointmentId);
    List<Appointment> findAll();

    @Query("SELECT a FROM Appointment a WHERE a.patientPhone = :patientPhone")
    List<Appointment> findByPatientPhoneNumber(@Param("patientPhone") String patientPhone);
}
