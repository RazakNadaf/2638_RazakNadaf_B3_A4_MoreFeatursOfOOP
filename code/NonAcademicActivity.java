/*
 * Author: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 * Assignment: A4
 */

import java.io.Serializable;

// Concrete class for non-academic activities like InfoFest, Plateaunia, Play-on (Requirement 5 & 6)
public class NonAcademicActivity extends Activity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String venue;
    private String studentConvener;
    private int attendeesCount;

    public NonAcademicActivity(String activityId, String name, int year, String description,
                               ActivityCategory category, String venue, String studentConvener, 
                               int attendeesCount) {
        super(activityId, name, year, description, category);
        this.venue = venue;
        this.studentConvener = studentConvener;
        this.attendeesCount = attendeesCount;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStudentConvener() {
        return studentConvener;
    }

    public void setStudentConvener(String studentConvener) {
        this.studentConvener = studentConvener;
    }

    public int getAttendeesCount() {
        return attendeesCount;
    }

    public void setAttendeesCount(int attendeesCount) {
        this.attendeesCount = attendeesCount;
    }

    @Override
    public void displayActivityDetails() {
        System.out.println("--------------------------------------------------");
        System.out.println("[NON-ACADEMIC ACTIVITY] ID: " + getActivityId());
        System.out.println("Event Name    : " + getName());
        System.out.println("Year          : " + getYear());
        System.out.println("Venue         : " + venue);
        System.out.println("Convener      : " + studentConvener);
        System.out.println("Attendees     : " + attendeesCount);
        System.out.println("Description   : " + getDescription());
        System.out.println("Category      : " + getCategory());
        System.out.println("--------------------------------------------------");
    }

    @Override
    public void generateReport() {
        System.out.println("[NIRF REPORT - NON-ACADEMIC ACTIVITY]");
        System.out.println("Code: " + getActivityId() + " | Event: " + getName() + " (" + getYear() + ")");
        System.out.println("Venue: " + venue + " | Convener: " + studentConvener + " | Footfall: " + attendeesCount);
    }
}
