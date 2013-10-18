package com.tokoro;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Teomo
 * Date: 15/10/13
 * Time: 08:30
 * To change this template use File | Settings | File Templates.
 */
public class Booking {

    private Date dateBookedByEmployee;
    private Date meetingDate;
    private int meetingDuration;
    private String bookingStartTime;
    private String bookingEndTime;
    private String Employee;

    public Date getDateBookedByEmployee() {
        return dateBookedByEmployee;
    }

    public void setDateBookedByEmployee(Date dateBookedByEmployee) {
        this.dateBookedByEmployee = dateBookedByEmployee;
    }

    public int getMeetingDuration() {
        return meetingDuration;
    }

    public void setMeetingDuration(int meetingDuration) {
        this.meetingDuration = meetingDuration;
    }

    public String getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(String bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public String getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(String bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public String getEmployee() {
        return Employee;
    }

    public void setEmployee(String employee) {
        Employee = employee;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

}
