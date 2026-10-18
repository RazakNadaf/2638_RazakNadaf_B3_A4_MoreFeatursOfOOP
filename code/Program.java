/*
 * Author: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 * Assignment: A4
 */

import java.io.Serializable;

// Enum representing academic programs offered at DCST (Requirement 2)
enum ProgramType {
    MCA("Master of Computer Applications", 2),
    MSC_AI("M.Sc. in Artificial Intelligence", 2),
    MSC_DS("M.Sc. in Data Science", 2),
    IMSC("Integrated M.Sc. in Computer Science", 5);

    private final String displayName;
    private final int durationYears;

    ProgramType(String displayName, int durationYears) {
        this.displayName = displayName;
        this.durationYears = durationYears;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDurationYears() {
        return durationYears;
    }

    @Override
    public String toString() {
        return name() + " (" + displayName + ", " + durationYears + " Years)";
    }
}

// Entity representing degree programs (Requirement 6)
public class Program implements Reportable, Serializable {
    private static final long serialVersionUID = 1L;

    private String programCode;
    private String programName;
    private ProgramType programType;
    private int durationYears;
    private int totalCredits;

    public Program(String programCode, String programName, ProgramType programType, int durationYears, int totalCredits) {
        this.programCode = programCode;
        this.programName = programName;
        this.programType = programType;
        this.durationYears = durationYears;
        this.totalCredits = totalCredits;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public ProgramType getProgramType() {
        return programType;
    }

    public void setProgramType(ProgramType programType) {
        this.programType = programType;
    }

    public int getDurationYears() {
        return durationYears;
    }

    public void setDurationYears(int durationYears) {
        this.durationYears = durationYears;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public void displayProgramDetails() {
        System.out.println("--------------------------------------------------");
        System.out.println("[PROGRAM] Code: " + programCode + " | " + programName);
        System.out.println("Type          : " + programType.getDisplayName());
        System.out.println("Duration      : " + durationYears + " Years | Credits: " + totalCredits);
        System.out.println("--------------------------------------------------");
    }

    @Override
    public void generateReport() {
        System.out.println("[NIRF REPORT - PROGRAM]");
        System.out.println("Code: " + programCode + " | Name: " + programName);
        System.out.println("Duration: " + durationYears + " Years | Credits: " + totalCredits);
    }

    @Override
    public String getSummary() {
        return programCode + " | " + programName + " (" + durationYears + " Yrs, " + totalCredits + " Credits)";
    }

    @Override
    public String toString() {
        return programCode + " - " + programName;
    }
}
