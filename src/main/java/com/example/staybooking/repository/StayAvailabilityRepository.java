package com.example.staybooking.repository;

import com.example.staybooking.model.StayAvailability;
import com.example.staybooking.model.StayAvailabilityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface StayAvailabilityRepository extends JpaRepository<StayAvailability, StayAvailabilityKey> {
    @Query(value = "SELECT sa.id.stay_id FROM StayAvailability sa WHERE sa.id.stay_id IN ?1 AND sa.state = 0 AND sa.id.date BETWEEN ?2 AND ?3 GROUP BY sa.id.stay_id HAVING COUNT(sa.id.date) = ?4")
    List<Long> findByDateBetweenAndStateIsAvailable(List<Long> stayIds, LocalDate startDate, LocalDate endDate, long duration);
}
