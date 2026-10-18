/*
 * Author: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 * Assignment: A4
 */

import java.io.Serializable;

// Enum representing activity categories for NIRF reporting (Requirement 2)
enum ActivityCategory {
    ACADEMIC("Academic (ISA, Midpoint, SEA, Seminar)"),
    NON_ACADEMIC("Non-Academic (InfoFest, Plateaunia, Play-on)"),
    CO_CURRICULAR("Co-Curricular / Study Tour"),
    PLACEMENT("Placement Drive");

    private final String description;

    ActivityCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name() + " - " + description;
    }
}

// Abstract base class for activities (Requirement 5 & 6)
public abstract class Activity implements Reportable, Serializable {
    private static final long serialVersionUID = 1L;

    private String activityId;
    private String name;
    private int year;
    private String description;
    private ActivityCategory category;

    public Activity(String activityId, String name, int year, String description, ActivityCategory category) {
        this.activityId = activityId;
        this.name = name;
        this.year = year;
        this.description = description;
        this.category = category;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityCategory getCategory() {
        return category;
    }

    public void setCategory(ActivityCategory category) {
        this.category = category;
    }

    // Abstract method implemented by concrete activity classes
    public abstract void displayActivityDetails();

    @Override
    public String getSummary() {
        return activityId + " | " + name + " | Year " + year + " | " + category.name();
    }
}
