package assignment.EMS.controller;

import assignment.EMS.service.AttendanceService;
import assignment.EMS.service.EmployeeService;
import assignment.EMS.model.Attendance;
import assignment.EMS.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AttendanceController {

    private final EmployeeService employeeService;

    public AttendanceController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    private AttendanceService attendanceService;

    // display list of attendance
    @GetMapping("/attendance")
    public String viewHomePage(Model model) {
        return findPaginated(1, 5, "employee", "asc", model);
    }

    @GetMapping("/attendance/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        Page<Attendance> page = attendanceService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Attendance> listAttendance = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listAttendances", listAttendance);

        return "attendances_list";
    }

    @GetMapping("/showNewAttendanceForm")
    public String showNewAttendanceForm(@RequestParam("id") String idParam, Model model) {
        if (idParam == null || idParam.isEmpty()) {
            // handle error case where id is missing or empty
            return "error_page";
        }

        long employeeId;
        try {
            employeeId = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            // handle error case where id is not a valid long value
            return "error_page";
        }

        Employee employee = employeeService.getEmployeeById(employeeId);
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        attendance.setCheckInTime(timestamp);
        model.addAttribute("attendance", attendance);
        model.addAttribute("employeeId", employeeId);
        return "new_attendance";
    }





    @PostMapping("/saveAttendance")
    public String saveAttendance(@RequestParam("employeeId") long employeeId, @ModelAttribute Attendance attendance)
    {
        Employee employee = employeeService.getEmployeeById(employeeId);
        attendance.setEmployee(employee);
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        attendance.setCheckInTime(timestamp);

        // check if an attendance record already exists for the employee on the current date
        Attendance existingAttendance = attendanceService.getAttendanceByEmployeeAndDate(employee, timestamp.toLocalDateTime().toLocalDate());
        if (existingAttendance != null) {
            // if an attendance record already exists, update the existing record with the new check-in time
            existingAttendance.setCheckInTime(timestamp);
            attendanceService.saveAttendance(existingAttendance);
        } else {
            attendanceService.saveAttendance(attendance);
        }

        return "redirect:/attendance";
    }


    // Update attendance record for an employee when they check out
    @PostMapping("/checkout")
    public String checkout(@RequestParam("attendanceId") long attendanceId) {
        Attendance attendance = attendanceService.getAttendanceById(attendanceId);
        if (attendance != null) {
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            attendance.setCheckOutTime(timestamp);
            attendanceService.saveAttendance(attendance);
        }
        return "redirect:/attendance";
    }




    @GetMapping("/showAttendanceFormForUpdate/{id}")
    public String showAttendanceFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        // get attendance from the service
        Attendance attendance = attendanceService.getAttendanceById(id);

        // set attendance as a model attribute to pre-populate the form
        model.addAttribute("attendance", attendance);
        return "update_attendance";
    }

    @GetMapping("/deleteAttendance/{id}")
    public String deleteAttendance(@PathVariable(value = "id") long id) {

        // call delete attendance method
        this.attendanceService.deleteAttendanceById(id);
        return "redirect:/";
    }

}
