/*
 * Author: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 * Assignment: A4
 */

import java.io.Serializable;

// Concrete class representing teaching faculty (Requirement 5 & 3)
public class TeachingStaff extends Person implements Reportable, AcademicParticipant, Serializable {
    private static final long serialVersionUID = 1L;

    private String designation;
    private String department;
    private String specialization;
    private int experienceYears;
    private StaffType staffType;

    public TeachingStaff(String staffId, String name, String email, String designation, 
                         String department, String specialization, int experienceYears) {
        super(staffId, name, email);
        this.designation = designation;
        this.department = department;
        this.specialization = specialization;
        this.experienceYears = experienceYears;
        this.staffType = StaffType.TEACHING;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public StaffType getStaffType() {
        return staffType;
    }

    @Override
    public String getRole() {
        return "Teaching Faculty";
    }

    // Requirement 5 & 7: Overriding abstract method from Person
    @Override
    public void displayDetails() {
        System.out.println("--------------------------------------------------");
        System.out.println("[TEACHING FACULTY] ID: " + getId());
        System.out.println("Name           : " + this.name + " (Direct protected access)");
        System.out.println("Email          : " + this.email + " (Direct protected access)");
        System.out.println("Designation    : " + designation);
        System.out.println("Department     : " + department);
        System.out.println("Specialization : " + specialization);
        System.out.println("Experience     : " + experienceYears + " Years");
        System.out.println("Category       : " + staffType);
        System.out.println("--------------------------------------------------");
    }

    // Requirement 6: Dynamic message passing via Reportable interface
    @Override
    public void generateReport() {
        System.out.println("[NIRF REPORT - FACULTY]");
        System.out.println("ID: " + getId() + " | Name: " + name + " | Post: " + designation);
        System.out.println("Domain: " + specialization + " | Experience: " + experienceYears + " yrs");
    }

    @Override
    public String getSummary() {
        return getId() + " | " + name + " | " + designation + " | " + specialization;
    }

    @Override
    public void participateInAcademicActivity(String activityName) {
        System.out.println("Faculty " + name + " coordinated: " + activityName);
    }
}
