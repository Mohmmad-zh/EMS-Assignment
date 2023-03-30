package assignment.EMS.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import assignment.EMS.model.Attendance;
import assignment.EMS.model.Employee;
import assignment.EMS.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    @Override
    public void saveAttendance(Attendance attendance) {
        this.attendanceRepository.save(attendance);
    }

    @Override
    public Attendance getAttendanceById(long id) {
        Optional<Attendance> optional = attendanceRepository.findById(id);
        Attendance attendance = null;
        if (optional.isPresent()) {
            attendance = optional.get();
        } else {
            throw new RuntimeException(" Attendance not found for id :: " + id);
        }
        return attendance;
    }

    @Override
    public void deleteAttendanceById(long id) {
        this.attendanceRepository.deleteById(id);
    }




    @Override
    public Page<Attendance> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.attendanceRepository.findAll(pageable);
    }

    @Override
    public Attendance getAttendanceByEmployeeAndDate(Employee employee, LocalDate date) {
        return attendanceRepository.findByEmployeeAndCheckInTime(employee, Timestamp.valueOf(date.atStartOfDay()));
    }

}
