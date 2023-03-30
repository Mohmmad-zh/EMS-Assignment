package assignment.EMS.repository;

import assignment.EMS.model.Employee;
import assignment.EMS.model.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Query(value = "SELECT * FROM attendance WHERE CONCAT(employee_id, ' ', check_in_time,' ', check_out_time, ' ', justification) LIKE %:keyword% ",
            nativeQuery = true)
    Page<Employee> findAll(String keyword, Pageable pageable);

    Attendance findByEmployeeAndCheckInTime(Employee employee, Timestamp valueOf);

    @Query(value = "SELECT * FROM attendance WHERE employee_id = :employeeId AND check_in_time BETWEEN :startDate AND :endDate", nativeQuery = true)
    Attendance getAttendanceByEmployeeAndDate(@Param("employeeId") Long employeeId, @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
}
