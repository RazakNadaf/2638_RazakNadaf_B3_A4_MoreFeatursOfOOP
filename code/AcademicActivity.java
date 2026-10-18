/*
 * Author: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 * Assignment: A4
 */

import java.io.Serializable;

// Concrete class for academic activities like ISA, Midpoint, SEA, Study-Tour, Placements (Requirement 5 & 6)
public class AcademicActivity extends Activity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String activityType; // ISA, Midpoint, SEA, Study-Tour, Placement, Workshop
    private String coordinator;
    private int numberOfParticipants;

    public AcademicActivity(String activityId, String name, int year, String description,
                            ActivityCategory category, String activityType, String coordinator, 
                            int numberOfParticipants) {
        super(activityId, name, year, description, category);
        this.activityType = activityType;
        this.coordinator = coordinator;
        this.numberOfParticipants = numberOfParticipants;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    @Override
    public void displayActivityDetails() {
        System.out.println("--------------------------------------------------");
        System.out.println("[ACADEMIC ACTIVITY] ID: " + getActivityId());
        System.out.println("Title         : " + getName());
        System.out.println("Year          : " + getYear());
        System.out.println("Type          : " + activityType);
        System.out.println("Coordinator   : " + coordinator);
        System.out.println("Participants  : " + numberOfParticipants);
        System.out.println("Description   : " + getDescription());
        System.out.println("Category      : " + getCategory());
        System.out.println("--------------------------------------------------");
    }

    @Override
    public void generateReport() {
        System.out.println("[NIRF REPORT - ACADEMIC ACTIVITY]");
        System.out.println("Code: " + getActivityId() + " (" + activityType + ") | Event: " + getName() + " (" + getYear() + ")");
        System.out.println("Coordinator: " + coordinator + " | Participants: " + numberOfParticipants);
    }
}
