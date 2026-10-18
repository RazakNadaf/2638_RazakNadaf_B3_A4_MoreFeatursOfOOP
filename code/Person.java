/*
 * Author: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 * Assignment: A4
 */

import java.io.Serializable;

// Interface for generating NIRF reports (Requirement 5 & 6)
interface Reportable {
    void generateReport();
    String getSummary();
}

// Interface for verifying data validity (Requirement 5)
interface Verifiable {
    boolean verifyData();
}

// Interface for academic participation (Requirement 3 & 5)
interface AcademicParticipant {
    void participateInAcademicActivity(String activityName);
}

// Interface demonstrating multiple interface inheritance (Requirement 3)
interface NIRFSubmissible extends Reportable, Verifiable {
    String getNIRFSubmissionStatus();
}

// Enum representing staff categories (Requirement 2)
enum StaffType {
    TEACHING("Faculty / Teaching Staff"),
    NON_TEACHING("Administrative & Technical Support Staff");

    private final String description;

    StaffType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name() + " (" + description + ")";
    }
}

// Abstract base class representing a person in the system (Requirement 5)
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    // Requirement 2: Static member counter
    private static int memberCount = 0;

    // Requirement 4: Access modifiers (private field with getter/setter)
    private String id;

    // Requirement 4: Protected fields accessible directly by subclasses
    protected String name;
    protected String email;

    // Requirement 1: Constructor initializing all attributes
    public Person(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        memberCount++;
    }

    protected Person() {
        memberCount++;
    }

    // Requirement 2: Static methods
    public static int getMemberCount() {
        return memberCount;
    }

    public static void displayMemberCount() {
        System.out.println("Total University Members Registered (Static Counter): " + memberCount);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Requirement 5: Abstract method
    public abstract void displayDetails();

    public abstract String getRole();

    @Override
    public String toString() {
        return "[" + getRole() + "] ID: " + id + ", Name: " + name + ", Email: " + email;
    }
}
