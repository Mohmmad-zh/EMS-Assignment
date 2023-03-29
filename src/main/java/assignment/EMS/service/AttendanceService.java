package assignment.EMS.service;

import java.util.List;

import org.springframework.data.domain.Page;

import assignment.EMS.model.Attendance;

public interface AttendanceService {
    List<Attendance> getAllAttendances();

    void saveAttendance(Attendance attendance);
    Attendance getAttendanceById(long id);
    void deleteAttendanceById(long id);
    Page<Attendance> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}