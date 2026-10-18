/*
 * Author: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 * Assignment: A4
 */

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Main driver class providing a clean 12-item console menu
public class Main {

    private static NIRFDataManager dataManager = new NIRFDataManager();
    private static OOPDemonstrations oopDemos = new OOPDemonstrations(dataManager);
    private static final Scanner scanner = new Scanner(System.in);

    private static final String DATA_FILE = "dcst_data.dat";
    private static final String REPORT_FILE = "dcst_report.txt";

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = readInt("Enter your choice (1-12): ", 1, 12);
            System.out.println();

            switch (choice) {
                case 1:
                    handleStudentManagement();
                    break;
                case 2:
                    handleStaffManagement();
                    break;
                case 3:
                    handleAcademicActivities();
                    break;
                case 4:
                    handleNonAcademicActivities();
                    break;
                case 5:
                    handleProgramsAndCourses();
                    break;
                case 6:
                    handleSubjects();
                    break;
                case 7:
                    handleStudentSearch();
                    break;
                case 8:
                    handleOOPDemonstrations();
                    break;
                case 9:
                    handleSaveData();
                    break;
                case 10:
                    handleLoadData();
                    break;
                case 11:
                    handleGenerateReport();
                    break;
                case 12:
                    System.out.println("=============================================");
                    System.out.println(" Exiting DCST NIRF System. Thank you!");
                    System.out.println("=============================================");
                    running = false;
                    break;
            }

            if (running) {
                System.out.println("\nPress ENTER to continue back to menu...");
                scanner.nextLine();
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=============================================");
        System.out.println("   DCST NIRF INFORMATION MANAGEMENT SYSTEM   ");
        System.out.println("     Goa Business School, Goa University     ");
        System.out.println("=============================================");
        System.out.println(" 1. Student Management");
        System.out.println(" 2. Staff Management");
        System.out.println(" 3. Academic Activities");
        System.out.println(" 4. Non-Academic Activities");
        System.out.println(" 5. Programs and Courses");
        System.out.println(" 6. Subjects");
        System.out.println(" 7. Search Student (Method Overloading)");
        System.out.println(" 8. OOP Concepts Demonstration");
        System.out.println(" 9. Save Data (Serialization)");
        System.out.println(" 10. Load Data (Deserialization)");
        System.out.println(" 11. Generate NIRF Report (File I/O)");
        System.out.println(" 12. Exit");
        System.out.println("=============================================");
    }

    // 1. Student Management
    private static void handleStudentManagement() {
        System.out.println("--- [1] STUDENT MANAGEMENT ---");
        System.out.println("1. View All Students");
        System.out.println("2. Add New Student (With Validation)");
        System.out.println("3. Process Verification Queue (FIFO Queue Demo)");
        int sub = readInt("Select (1-3): ", 1, 3);

        if (sub == 1) {
            List<Student> list = dataManager.getStudentRepo().getAll();
            System.out.println("\nTotal Registered Students: " + list.size());
            for (Student s : list) s.displayDetails();
        } else if (sub == 2) {
            String id = readString("Enter Student ID (e.g. STU-2025-05): ");
            String name = readString("Enter Name: ");
            String email = readString("Enter Email: ");
            System.out.println("Select Program: 1. MCA | 2. M.Sc. AI | 3. M.Sc. DS | 4. IMSC");
            int pIdx = readInt("Choice (1-4): ", 1, 4);
            ProgramType pt = ProgramType.values()[pIdx - 1];
            int year = readInt("Enter Year of Study (1-" + pt.getDurationYears() + "): ", 1, pt.getDurationYears());
            double cgpa = readDouble("Enter CGPA (0.00 - 10.00): ", 0.0, 10.0);
            String spec = readString("Enter Specialization: ");

            try {
                dataManager.addStudentWithValidation(id, name, email, pt, year, cgpa, spec);
                System.out.println("[SUCCESS] Student added successfully.");
            } catch (InvalidStudentDataException e) {
                System.out.println("[VALIDATION ERROR]: " + e.getMessage());
            }
        } else {
            System.out.println("\nPending queue count: " + dataManager.getPendingVerificationCount());
            Student s = dataManager.processNextVerification();
            if (s != null) {
                System.out.println("Verified & Dequeued: " + s.getName() + " (" + s.getId() + ")");
                System.out.println("Remaining queue count: " + dataManager.getPendingVerificationCount());
            } else {
                System.out.println("Verification queue is empty.");
            }
        }
    }

    // 2. Staff Management
    private static void handleStaffManagement() {
        System.out.println("--- [2] STAFF MANAGEMENT ---");
        System.out.println("1. View Teaching Faculty");
        System.out.println("2. Add Teaching Faculty");
        System.out.println("3. View Non-Teaching Staff");
        System.out.println("4. Add Non-Teaching Staff");
        int sub = readInt("Select (1-4): ", 1, 4);

        if (sub == 1) {
            for (TeachingStaff t : dataManager.getTeachingStaffRepo().getAll()) t.displayDetails();
        } else if (sub == 2) {
            String id = readString("Enter Staff ID: ");
            String name = readString("Enter Name: ");
            String email = readString("Enter Email: ");
            String desig = readString("Enter Designation: ");
            String dept = readString("Enter Department: ");
            String spec = readString("Enter Specialization: ");
            int exp = readInt("Enter Experience in Years: ", 0, 50);

            dataManager.getTeachingStaffRepo().add(new TeachingStaff(id, name, email, desig, dept, spec, exp));
            System.out.println("[SUCCESS] Faculty member added.");
        } else if (sub == 3) {
            for (NonTeachingStaff nt : dataManager.getNonTeachingStaffRepo().getAll()) nt.displayDetails();
        } else {
            String id = readString("Enter Staff ID: ");
            String name = readString("Enter Name: ");
            String email = readString("Enter Email: ");
            String desig = readString("Enter Designation: ");
            String dept = readString("Enter Department: ");
            String role = readString("Enter Role / Responsibilities: ");

            dataManager.getNonTeachingStaffRepo().add(new NonTeachingStaff(id, name, email, desig, dept, role));
            System.out.println("[SUCCESS] Support staff added.");
        }
    }

    // 3. Academic Activities
    private static void handleAcademicActivities() {
        System.out.println("--- [3] ACADEMIC ACTIVITIES (ISA, Midpoint, SEA, Study-Tour, Placements) ---");
        System.out.println("1. View All Academic Activities");
        System.out.println("2. Add Academic Activity");
        int sub = readInt("Select (1-2): ", 1, 2);

        if (sub == 1) {
            for (AcademicActivity a : dataManager.getAcademicActivityRepo().getAll()) a.displayActivityDetails();
        } else {
            String id = readString("Enter Activity ID: ");
            String name = readString("Enter Title: ");
            int year = readInt("Enter Year: ", 2020, 2030);
            String desc = readString("Enter Description: ");
            System.out.println("Select Category: 1. ACADEMIC | 2. CO_CURRICULAR | 3. PLACEMENT");
            int catIdx = readInt("Choice (1-3): ", 1, 3);
            ActivityCategory cat = (catIdx == 1) ? ActivityCategory.ACADEMIC : 
                                   (catIdx == 2) ? ActivityCategory.CO_CURRICULAR : ActivityCategory.PLACEMENT;
            String type = readString("Enter Type (ISA / Midpoint / SEA / Study-Tour / Placement): ");
            String coord = readString("Enter Coordinator: ");
            int count = readInt("Enter Participant Count: ", 1, 1000);

            dataManager.getAcademicActivityRepo().add(new AcademicActivity(id, name, year, desc, cat, type, coord, count));
            System.out.println("[SUCCESS] Academic activity added.");
        }
    }

    // 4. Non-Academic Activities
    private static void handleNonAcademicActivities() {
        System.out.println("--- [4] NON-ACADEMIC ACTIVITIES (InfoFest, Plateaunia, Play-on) ---");
        System.out.println("1. View All Non-Academic Activities");
        System.out.println("2. Add Non-Academic Activity");
        int sub = readInt("Select (1-2): ", 1, 2);

        if (sub == 1) {
            for (NonAcademicActivity na : dataManager.getNonAcademicActivityRepo().getAll()) na.displayActivityDetails();
        } else {
            String id = readString("Enter Activity ID: ");
            String name = readString("Enter Event Name: ");
            int year = readInt("Enter Year: ", 2020, 2030);
            String desc = readString("Enter Description: ");
            String venue = readString("Enter Venue: ");
            String conv = readString("Enter Student Convener: ");
            int count = readInt("Enter Attendee Count: ", 1, 5000);

            dataManager.getNonAcademicActivityRepo().add(new NonAcademicActivity(id, name, year, desc, ActivityCategory.NON_ACADEMIC, venue, conv, count));
            System.out.println("[SUCCESS] Non-academic activity added.");
        }
    }

    // 5. Programs and Courses
    private static void handleProgramsAndCourses() {
        System.out.println("--- [5] PROGRAMS AND COURSES ---");
        System.out.println("Degree Programs:");
        for (Program p : dataManager.getProgramRepo().getAll()) p.displayProgramDetails();

        System.out.println("\nCourse Modules:");
        for (Course c : dataManager.getCourseRepo().getAll()) c.displayCourseDetails();
    }

    // 6. Subjects
    private static void handleSubjects() {
        System.out.println("--- [6] CURRICULUM SUBJECTS ---");
        System.out.println("1. View All Subjects");
        System.out.println("2. Add Subject (With Set Uniqueness Check)");
        int sub = readInt("Select (1-2): ", 1, 2);

        if (sub == 1) {
            for (Subject s : dataManager.getSubjectRepo().getAll()) s.displaySubjectDetails();
        } else {
            String code = readString("Enter Subject Code (e.g. CST-507): ");
            String name = readString("Enter Subject Name: ");
            int sem = readInt("Enter Semester: ", 1, 10);
            int credits = readInt("Enter Credits: ", 1, 6);
            System.out.println("Includes Lab? 1. Yes | 2. No");
            boolean lab = (readInt("Choice (1-2): ", 1, 2) == 1);

            Subject subject = new Subject(code, name, sem, credits, lab);
            boolean added = dataManager.addSubjectWithCheck(subject);
            if (added) {
                System.out.println("[SUCCESS] Subject added to repository and unique Set.");
            } else {
                System.out.println("[REJECTED BY SET]: Subject code '" + code + "' already exists!");
            }
        }
    }

    // 7. Search Student (Overloaded Methods Demonstration)
    private static void handleStudentSearch() {
        System.out.println("--- [7] SEARCH STUDENT (METHOD OVERLOADING DEMO) ---");
        System.out.println("1. By Student ID [searchStudent(String id)]");
        System.out.println("2. By Name and Program [searchStudent(String name, ProgramType program)]");
        System.out.println("3. By Year of Study [searchStudent(int year)]");
        int sub = readInt("Select search type (1-3): ", 1, 3);

        List<Student> results = null;
        if (sub == 1) {
            String id = readString("Enter Student ID: ");
            results = dataManager.searchStudent(id);
        } else if (sub == 2) {
            String name = readString("Enter Name (or partial): ");
            System.out.println("Select Program: 1. MCA | 2. M.Sc. AI | 3. M.Sc. DS | 4. IMSC");
            int pIdx = readInt("Choice (1-4): ", 1, 4);
            results = dataManager.searchStudent(name, ProgramType.values()[pIdx - 1]);
        } else {
            int year = readInt("Enter Year of Study: ", 1, 5);
            results = dataManager.searchStudent(year);
        }

        System.out.println("\nFound " + results.size() + " matching student(s):");
        for (Student s : results) System.out.println(" -> " + s.getSummary());
    }

    // 8. OOP Concepts Demonstration Submenu
    private static void handleOOPDemonstrations() {
        boolean inSub = true;
        while (inSub) {
            System.out.println("\n========== OOP CONCEPT DEMONSTRATIONS ==========");
            System.out.println(" 1.  Object Referencing & Constructors");
            System.out.println(" 2.  Static Members & Enums");
            System.out.println(" 3.  Multiple Interface Inheritance");
            System.out.println(" 4.  Access Modifiers");
            System.out.println(" 5.  Abstract Classes & Interfaces");
            System.out.println(" 6.  Message Passing");
            System.out.println(" 7.  Polymorphism");
            System.out.println(" 8.  Generics & Collections");
            System.out.println(" 9.  Exception Handling");
            System.out.println(" 10. Reflection, Persistence, I/O & Cloning");
            System.out.println(" 11. Run All Demonstrations");
            System.out.println(" 0.  Return to Main Menu");
            System.out.println("================================================");

            int dChoice = readInt("Select Demonstration (0-11): ", 0, 11);
            switch (dChoice) {
                case 1: oopDemos.demonstrateObjectReferencing(); break;
                case 2: oopDemos.demonstrateStaticAndEnums(); break;
                case 3: oopDemos.demonstrateMultipleInheritance(); break;
                case 4: oopDemos.demonstrateAccessModifiers(); break;
                case 5: oopDemos.demonstrateAbstractClassesAndInterfaces(); break;
                case 6: oopDemos.demonstrateMessagePassing(); break;
                case 7: oopDemos.demonstratePolymorphism(); break;
                case 8: oopDemos.demonstrateGenericsAndCollections(); break;
                case 9: oopDemos.demonstrateExceptionHandling(); break;
                case 10: oopDemos.demonstrateReflectionPersistenceIOCloning(); break;
                case 11: oopDemos.runAllDemonstrations(); break;
                case 0: inSub = false; break;
            }

            if (inSub) {
                System.out.println("\nPress ENTER to continue...");
                scanner.nextLine();
            }
        }
    }

    // 9. Save Data (Serialization)
    private static void handleSaveData() {
        System.out.println("--- [9] SAVE DATA (OBJECT SERIALIZATION) ---");
        try {
            dataManager.saveData(DATA_FILE);
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to save: " + e.getMessage());
        }
    }

    // 10. Load Data (Deserialization)
    private static void handleLoadData() {
        System.out.println("--- [10] LOAD DATA (OBJECT DESERIALIZATION) ---");
        try {
            NIRFDataManager loaded = NIRFDataManager.loadData(DATA_FILE);
            dataManager = loaded;
            oopDemos = new OOPDemonstrations(dataManager);
            System.out.println("Loaded " + dataManager.getStudentRepo().size() + " students, " +
                               dataManager.getTeachingStaffRepo().size() + " faculty.");
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to load: " + e.getMessage());
        }
    }

    // 11. Generate & Read NIRF Report (File I/O)
    private static void handleGenerateReport() {
        System.out.println("--- [11] GENERATE NIRF REPORT (FILE I/O) ---");
        System.out.println("1. Export Summary Report to File (" + REPORT_FILE + ")");
        System.out.println("2. Read and Display Report from File (" + REPORT_FILE + ")");
        int sub = readInt("Select (1-2): ", 1, 2);

        try {
            if (sub == 1) {
                dataManager.exportNIRFTextReport(REPORT_FILE);
            } else {
                NIRFDataManager.readAndDisplayReport(REPORT_FILE);
            }
        } catch (IOException e) {
            System.out.println("[FILE ERROR]: " + e.getMessage());
        }
    }

    // Safe input helper functions (prevents crashes from invalid input)
    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int val = Integer.parseInt(input);
                if (val >= min && val <= max) return val;
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid format. Please enter an integer.");
            }
        }
    }

    private static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double val = Double.parseDouble(input);
                if (val >= min && val <= max) return val;
                System.out.println("Please enter a decimal number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid format. Please enter a valid decimal number.");
            }
        }
    }

    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Input cannot be blank.");
        }
    }
}
