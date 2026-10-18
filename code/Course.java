/*
 * Author: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 * Assignment: A4
 */

import java.io.Serializable;

// Entity representing individual subjects taught in CST discipline
class Subject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String subjectCode;
    private String subjectName;
    private int semester;
    private int credits;
    private boolean isPractical;

    public Subject(String subjectCode, String subjectName, int semester, int credits, boolean isPractical) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.semester = semester;
        this.credits = credits;
        this.isPractical = isPractical;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public boolean isPractical() {
        return isPractical;
    }

    public void setPractical(boolean practical) {
        isPractical = practical;
    }

    public void displaySubjectDetails() {
        System.out.println("--------------------------------------------------");
        System.out.println("[SUBJECT] " + subjectCode + ": " + subjectName);
        System.out.println("Semester: " + semester + " | Credits: " + credits + " | Type: " + (isPractical ? "Theory + Lab" : "Theory Only"));
        System.out.println("--------------------------------------------------");
    }

    @Override
    public String toString() {
        return subjectCode + " - " + subjectName + " (Sem " + semester + ", " + credits + " Cr, " + (isPractical ? "Lab" : "Theory") + ")";
    }
}

// Entity representing course modules
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseCode;
    private String courseName;
    private int semester;
    private String programCode;
    private int credits;

    public Course(String courseCode, String courseName, int semester, String programCode, int credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.semester = semester;
        this.programCode = programCode;
        this.credits = credits;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void displayCourseDetails() {
        System.out.println("--------------------------------------------------");
        System.out.println("[COURSE] " + courseCode + ": " + courseName);
        System.out.println("Semester: " + semester + " | Program: " + programCode + " | Credits: " + credits);
        System.out.println("--------------------------------------------------");
    }

    @Override
    public String toString() {
        return courseCode + ": " + courseName + " (Sem " + semester + ", " + credits + " Credits)";
    }
}
