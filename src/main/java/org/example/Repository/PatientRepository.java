package org.example.Repository;

import org.example.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByName(String name);


}
