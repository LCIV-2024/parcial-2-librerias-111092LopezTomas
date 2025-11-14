package com.example.libreria.repository;


import com.example.libreria.model.Reservation;
import com.example.libreria.model.Reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Todas las reservas de un usuario
    List<Reservation> findByUserId(Long userId);

    // Reservas por estado (ej: ACTIVE, RETURNED)
    List<Reservation> findByStatus(ReservationStatus status);

    // Reservas vencidas (fecha de devolución esperada < hoy y aún activas)
    @Query("SELECT r FROM Reservation r " +
            "WHERE r.expectedReturnDate < CURRENT_DATE " +
            "AND r.status = com.example.libreria.model.Reservation.ReservationStatus.ACTIVE")
    List<Reservation> findOverdueReservations();
}


