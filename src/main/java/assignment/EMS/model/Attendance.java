package assignment.EMS.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "check_in_time")
    private Timestamp checkInTime;

    @Column(name = "check_out_time")
    private Timestamp checkOutTime;

    @Column(name = "justification")
    private String justification;

    public Attendance(Employee employee, Timestamp checkInTime) {
        this.employee = employee;
        this.checkInTime = checkInTime;
    }

    public Attendance() {

    }

    public void checkOut(Timestamp checkOutTime, String justification) {
        this.checkOutTime = checkOutTime;
        this.justification = justification;
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
    }

    public Timestamp getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Timestamp checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

}