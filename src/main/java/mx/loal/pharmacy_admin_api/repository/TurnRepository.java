package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.Turn;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TurnRepository extends JpaRepository<Turn, Long> {

    @Query("""
        SELECT MAX(CAST(SUBSTRING(t.turnCode, LENGTH(:identifier) + 1) AS int))
        FROM Turn t
        WHERE t.medicalConsultType.identifier = :identifier
          AND DATE(t.createdAt) = :date
    """)
    Integer getLastIdentifierNumber(LocalDate date, @Param("identifier") String identifier);

    @Query("""
        SELECT t
        FROM Turn t
        WHERE DATE(t.createdAt) = :date
          AND (t.status = 'PENDING' OR t.status = 'ATTENDING')
        ORDER BY t.medicalConsultType.priority ASC, t.createdAt ASC
    """)
    List<Turn> findTurnsByDate(LocalDate date);

    @Query("""
        SELECT t
        FROM Turn t
        WHERE DATE(t.createdAt) = :date
          AND t.status = 'ATTENDING'
        ORDER BY t.createdAt DESC
        LIMIT 1
    """)
    Optional<Turn> findAttendingTurn(LocalDate date);

    // Obtener el último turno atendido
    @Query("""
        SELECT t
        FROM Turn t
        WHERE t.status = 'COMPLETED'
        ORDER BY t.updatedAt DESC
    """)
    List<Turn> getLastAttendedTurn(Pageable pageable);

    // Obtener el siguiente turno pendiente de tipo alta prioridad
    @Query("""
        SELECT t
        FROM Turn t
        WHERE t.status = 'PENDING'
          AND t.medicalConsultType.priority = 1
        ORDER BY t.createdAt ASC
    """)
    List<Turn> getNextHighPriorityTurn(Pageable pageable);

    // Obtener el siguiente turno pendiente de Consulta Médica General
    @Query("""
        SELECT t
        FROM Turn t
        WHERE t.status = 'PENDING'
          AND t.medicalConsultType.priority = 2
        ORDER BY t.createdAt ASC
    """)
    List<Turn> getNextGeneralConsultTurn(Pageable pageable);

    @Query("""
        SELECT t
        FROM Turn t
        WHERE DATE(t.createdAt) = :date
          AND (:medicalConsultType IS NULL OR t.medicalConsultType.id = :medicalConsultType)
        ORDER BY t.createdAt DESC
    """)
    List<Turn> findTurnsForId(LocalDate date, @Param("medicalConsultType") Long medicalConsultType);

}
