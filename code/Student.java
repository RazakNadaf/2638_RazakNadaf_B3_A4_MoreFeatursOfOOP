/*
 * Author: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 * Assignment: A4
 */

import java.io.Serializable;

// Nested mutable class used to demonstrate shallow vs deep cloning (Requirement 10)
class DepartmentInfo implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private String departmentName;
    private String blockName;
    private String hodName;

    public DepartmentInfo(String departmentName, String blockName, String hodName) {
        this.departmentName = departmentName;
        this.blockName = blockName;
        this.hodName = hodName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getHodName() {
        return hodName;
    }

    public void setHodName(String hodName) {
        this.hodName = hodName;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return departmentName + " [" + blockName + ", HOD: " + hodName + "]";
    }
}

// Concrete class extending Person and implementing multiple interfaces (Requirement 3 & 5)
public class Student extends Person implements NIRFSubmissible, AcademicParticipant, Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    private ProgramType program;
    private int yearOfStudy;
    private double cgpa;
    private String specialization;

    // Nested mutable object for shallow and deep cloning
    private DepartmentInfo departmentInfo;

    // Requirement 1: Constructor initializing all attributes
    public Student(String studentId, String name, String email, ProgramType program, 
                   int yearOfStudy, double cgpa, String specialization, DepartmentInfo departmentInfo) {
        super(studentId, name, email);
        this.program = program;
        this.yearOfStudy = yearOfStudy;
        this.cgpa = cgpa;
        this.specialization = specialization;
        this.departmentInfo = departmentInfo;
    }

    // Overloaded constructor with default department
    public Student(String studentId, String name, String email, ProgramType program, int yearOfStudy, double cgpa) {
        this(studentId, name, email, program, yearOfStudy, cgpa, "General Computing",
             new DepartmentInfo("DCST", "Block B, Goa University", "Head of DCST"));
    }

    public ProgramType getProgram() {
        return program;
    }

    public void setProgram(ProgramType program) {
        this.program = program;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public DepartmentInfo getDepartmentInfo() {
        return departmentInfo;
    }

    public void setDepartmentInfo(DepartmentInfo departmentInfo) {
        this.departmentInfo = departmentInfo;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    // Requirement 5 & 7: Overriding abstract method from Person
    // Requirement 4: Direct access to protected fields 'name' and 'email'
    @Override
    public void displayDetails() {
        System.out.println("--------------------------------------------------");
        System.out.println("[STUDENT] ID: " + getId());
        System.out.println("Name           : " + this.name + " (Direct protected access)");
        System.out.println("Email          : " + this.email + " (Direct protected access)");
        System.out.println("Program        : " + program.getDisplayName());
        System.out.println("Year of Study  : Year " + yearOfStudy);
        System.out.println("CGPA           : " + String.format("%.2f", cgpa));
        System.out.println("Specialization : " + specialization);
        System.out.println("Department     : " + (departmentInfo != null ? departmentInfo.toString() : "N/A"));
        System.out.println("NIRF Status    : " + getNIRFSubmissionStatus());
        System.out.println("--------------------------------------------------");
    }

    // Requirement 6: Message passing via Reportable interface
    @Override
    public void generateReport() {
        System.out.println("[NIRF REPORT - STUDENT]");
        System.out.println("ID: " + getId() + " | Name: " + name + " | Program: " + program.name());
        System.out.println("CGPA: " + cgpa + " | Status: " + getNIRFSubmissionStatus());
    }

    @Override
    public String getSummary() {
        return getId() + " | " + name + " | " + program.name() + " | Year " + yearOfStudy + " | CGPA: " + cgpa;
    }

    @Override
    public boolean verifyData() {
        return getId() != null && !getId().trim().isEmpty() &&
               name != null && !name.trim().isEmpty() &&
               cgpa >= 0.0 && cgpa <= 10.0;
    }

    @Override
    public String getNIRFSubmissionStatus() {
        return verifyData() ? "VERIFIED (Ready for NIRF)" : "PENDING VERIFICATION";
    }

    @Override
    public void participateInAcademicActivity(String activityName) {
        System.out.println("Student " + name + " (" + getId() + ") attended: " + activityName);
    }

    // Requirement 10: Shallow cloning (copies reference of departmentInfo)
    public Student shallowClone() throws CloneNotSupportedException {
        return (Student) super.clone();
    }

    // Requirement 10: Deep cloning (creates independent copy of departmentInfo)
    public Student deepClone() throws CloneNotSupportedException {
        Student copy = (Student) super.clone();
        if (this.departmentInfo != null) {
            copy.departmentInfo = (DepartmentInfo) this.departmentInfo.clone();
        }
        return copy;
    }

    @Override
    public String toString() {
        return "Student{" + "id='" + getId() + "', name='" + name + "', program=" + program + 
               ", year=" + yearOfStudy + ", cgpa=" + cgpa + "}";
    }
}
