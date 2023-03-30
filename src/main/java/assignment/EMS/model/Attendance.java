package assignment.EMS.model;

import assignment.EMS.model.Employee;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "check_in_time")
    private Timestamp checkInTime;

    @Column(name = "check_out_time")
    private Timestamp checkOutTime;

    @Column(name = "justification")
    private String justification;

    @Column(name = "late_check_in")
    private boolean lateCheckIn;

    @Column(name = "early_check_out")
    private boolean earlyCheckOut;

    public Attendance() {

    }

    public Attendance(long id, Employee employee, Timestamp checkInTime, Timestamp checkOutTime, String justification,
                      boolean lateCheckIn, boolean earlyCheckOut) {
        this.id = id;
        this.employee = employee;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.justification = justification;
        this.lateCheckIn = lateCheckIn;
        this.earlyCheckOut = earlyCheckOut;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Timestamp getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Timestamp checkInTime) {
        this.checkInTime = checkInTime;
        if (checkInTime.after(getExpectedCheckInTime())) {
            lateCheckIn = true;
        }
    }

    public Timestamp getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Timestamp checkOutTime) {
        this.checkOutTime = checkOutTime;
        if (checkOutTime.before(getExpectedCheckOutTime())) {
            earlyCheckOut = true;
        }
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public boolean isLateCheckIn() {
        return lateCheckIn;
    }

    public boolean isEarlyCheckOut() {
        return earlyCheckOut;
    }

    private Timestamp getExpectedCheckInTime() {
        LocalDate currentDate = LocalDate.now();
        LocalTime expectedCheckInTime = LocalTime.of(8, 0);

        return Timestamp.valueOf(LocalDateTime.of(currentDate, expectedCheckInTime));
    }

    private Timestamp getExpectedCheckOutTime() {
        LocalDate currentDate = LocalDate.now();
        LocalTime expectedCheckOutTime = LocalTime.of(15, 0);

        return Timestamp.valueOf(LocalDateTime.of(currentDate, expectedCheckOutTime));
    }
    public boolean isCheckInAllowed() {
        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();
        boolean isWeekend = currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.FRIDAY;
        boolean isBeforeAllowedTime = currentTime.isBefore(LocalTime.of(8, 0));
        boolean isBeforeLatenessWindow = currentTime.isBefore(LocalTime.of(8, 30));
        boolean isAfterLatenessWindow = currentTime.isAfter(LocalTime.of(8, 30));
        boolean isBeforeEndOfDay = currentTime.isBefore(LocalTime.of(16, 0));
        boolean isBeforeEndOfWorkWeek = currentTime.isBefore(LocalTime.of(15, 0));
        return !isWeekend && isBeforeAllowedTime && isBeforeLatenessWindow ||
                !isWeekend && isBeforeAllowedTime && isAfterLatenessWindow && isBeforeEndOfDay ||
                !isWeekend && isBeforeEndOfWorkWeek && isBeforeEndOfDay;
    }

    public boolean isCheckOutAllowed() {
        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();
        boolean isWeekend = currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.FRIDAY;
        boolean isBeforeEndOfWorkDay = currentTime.isBefore(LocalTime.of(15, 0));
        boolean isAfterAllowedTime = currentTime.isAfter(LocalTime.of(15, 0));
        boolean isAfterLatenessWindow = currentTime.isAfter(LocalTime.of(16, 0));

        return !isWeekend && isBeforeEndOfWorkDay ||
                !isWeekend && isAfterAllowedTime && isBeforeEndOfWorkDay ||
                !isWeekend && isAfterAllowedTime && isAfterLatenessWindow;
    }

    public void checkIn() throws Exception {
        if (checkInTime != null) {
            throw new Exception("Already checked in.");
        }
        if (!isCheckInAllowed()) {
            throw new Exception("Check-in not allowed.");
        }
        checkInTime = new Timestamp(System.currentTimeMillis());
    }

    public void checkOut() throws Exception {
        if (checkOutTime != null) {
            throw new Exception("Already checked out.");
        }
        if (!isCheckOutAllowed()) {
            throw new Exception("Check-out not allowed.");
        }
        checkOutTime = new Timestamp(System.currentTimeMillis());
    }

    public void sendNotification() {
        // simulate sending notification via email
        System.out.println("Notification sent to " + employee.getEmail());
    }
}
