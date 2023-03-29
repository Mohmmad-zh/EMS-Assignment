package assignment.EMS.repository;

import assignment.EMS.model.Employee;
import assignment.EMS.model.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Query(value = "SELECT * FROM attendance WHERE CONCAT(first_name, ' ', last_name, ' ', check_in_time,' ', check_out_time, ' ', justification, ' ', employee_id) LIKE %?1%",
            nativeQuery = true)
    Page<Employee> findAll(String keyword, Pageable pageable);

}
