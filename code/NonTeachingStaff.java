/*
 * Author: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 * Assignment: A4
 */

import java.io.Serializable;

// Concrete class representing non-teaching support staff (Requirement 5 & 6)
public class NonTeachingStaff extends Person implements Reportable, Serializable {
    private static final long serialVersionUID = 1L;

    private String designation;
    private String department;
    private String roleDescription;
    private StaffType staffType;

    public NonTeachingStaff(String staffId, String name, String email, String designation, 
                            String department, String roleDescription) {
        super(staffId, name, email);
        this.designation = designation;
        this.department = department;
        this.roleDescription = roleDescription;
        this.staffType = StaffType.NON_TEACHING;
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

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public StaffType getStaffType() {
        return staffType;
    }

    @Override
    public String getRole() {
        return "Non-Teaching / Support Staff";
    }

    // Requirement 5 & 7: Overriding abstract method from Person
    @Override
    public void displayDetails() {
        System.out.println("--------------------------------------------------");
        System.out.println("[NON-TEACHING STAFF] ID: " + getId());
        System.out.println("Name           : " + this.name + " (Direct protected access)");
        System.out.println("Email          : " + this.email + " (Direct protected access)");
        System.out.println("Designation    : " + designation);
        System.out.println("Department     : " + department);
        System.out.println("Role           : " + roleDescription);
        System.out.println("Category       : " + staffType);
        System.out.println("--------------------------------------------------");
    }

    // Requirement 6: Dynamic message passing via Reportable interface
    @Override
    public void generateReport() {
        System.out.println("[NIRF REPORT - NON-TEACHING STAFF]");
        System.out.println("ID: " + getId() + " | Name: " + name + " | Designation: " + designation);
        System.out.println("Duties: " + roleDescription);
    }

    @Override
    public String getSummary() {
        return getId() + " | " + name + " | " + designation + " | " + roleDescription;
    }
}
