package assignment.EMS.repository;

import assignment.EMS.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM employees WHERE CONCAT(first_name, ' ', last_name, ' ', email) LIKE %?1%",
            nativeQuery = true)
    Page<Employee> findAll(String keyword, Pageable pageable);
}
