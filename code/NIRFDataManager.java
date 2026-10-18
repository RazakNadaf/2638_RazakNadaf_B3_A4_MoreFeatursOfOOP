/*
 * Author: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 * Assignment: A4
 */

import java.io.*;
import java.util.*;

// Custom exception for invalid student data (Requirement 9)
class InvalidStudentDataException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidStudentDataException(String message) {
        super(message);
    }
}

// Generic repository class for holding domain entities (Requirement 8)
class SimpleRepository<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        if (item != null) items.add(item);
    }

    public List<T> getAll() {
        return Collections.unmodifiableList(items);
    }

    public T get(int index) {
        if (index >= 0 && index < items.size()) return items.get(index);
        return null;
    }

    public int size() {
        return items.size();
    }
}

// Central manager coordinating data, collections, persistence, and reports
public class NIRFDataManager implements Serializable {
    private static final long serialVersionUID = 1L;

    // Repositories using Generic class (Requirement 8)
    private SimpleRepository<Student> studentRepo = new SimpleRepository<>();
    private SimpleRepository<TeachingStaff> teachingStaffRepo = new SimpleRepository<>();
    private SimpleRepository<NonTeachingStaff> nonTeachingStaffRepo = new SimpleRepository<>();
    private SimpleRepository<AcademicActivity> academicActivityRepo = new SimpleRepository<>();
    private SimpleRepository<NonAcademicActivity> nonAcademicActivityRepo = new SimpleRepository<>();
    private SimpleRepository<Program> programRepo = new SimpleRepository<>();
    private SimpleRepository<Course> courseRepo = new SimpleRepository<>();
    private SimpleRepository<Subject> subjectRepo = new SimpleRepository<>();

    // Requirement 8: Collection Frameworks
    // 1. Queue: FIFO document verification queue for NIRF
    private Queue<Student> verificationQueue = new LinkedList<>();

    // 2. Set: Enforces unique subject codes
    private Set<String> uniqueSubjectCodes = new HashSet<>();

    // 3. Map: Fast key-value lookup by student ID
    private Map<String, Student> studentMap = new HashMap<>();

    public NIRFDataManager() {
        seedSampleData();
    }

    // Pre-loads realistic sample data for 3-year DCST NIRF evaluation
    public void seedSampleData() {
        // Programs
        programRepo.add(new Program("MCA", "Master of Computer Applications", ProgramType.MCA, 2, 80));
        programRepo.add(new Program("MSCAI", "M.Sc. Artificial Intelligence", ProgramType.MSC_AI, 2, 80));
        programRepo.add(new Program("MSCDS", "M.Sc. Data Science", ProgramType.MSC_DS, 2, 80));
        programRepo.add(new Program("IMSC", "Integrated M.Sc. in Computer Science", ProgramType.IMSC, 5, 200));

        // Subjects (enforcing Set uniqueness)
        addSubjectWithCheck(new Subject("CST-501", "Advanced Java Programming & OOP", 1, 4, true));
        addSubjectWithCheck(new Subject("CST-502", "Data Structures & Algorithms", 1, 4, true));
        addSubjectWithCheck(new Subject("CST-503", "Relational Database Management Systems", 1, 4, true));
        addSubjectWithCheck(new Subject("CST-504", "Machine Learning & Neural Networks", 2, 4, true));
        addSubjectWithCheck(new Subject("CST-505", "Cloud Computing & DevOps", 2, 3, true));
        addSubjectWithCheck(new Subject("CST-506", "Software Engineering & Agile", 2, 3, false));

        courseRepo.add(new Course("MCA-C1", "Core Computing Module", 1, "MCA", 12));
        courseRepo.add(new Course("MCA-C2", "Advanced Analytics Module", 2, "MCA", 11));

        // Students
        DepartmentInfo dept = new DepartmentInfo("DCST", "Block B, Goa University", "Head of DCST");
        Student s1 = new Student("STU-2023-01", "Rahul Naik", "rahul@unigoa.ac.in", ProgramType.MCA, 2, 8.75, "Full Stack", dept);
        Student s2 = new Student("STU-2023-02", "Sneha Fernandes", "sneha@unigoa.ac.in", ProgramType.MCA, 2, 9.10, "Cloud & DevOps", dept);
        Student s3 = new Student("STU-2024-03", "Aman Sawant", "aman@unigoa.ac.in", ProgramType.MSC_AI, 1, 8.40, "AI & ML", dept);
        Student s4 = new Student("STU-2024-04", "Divya Borkar", "divya@unigoa.ac.in", ProgramType.MSC_DS, 1, 8.95, "Data Analytics", dept);

        addStudent(s1);
        addStudent(s2);
        addStudent(s3);
        addStudent(s4);

        verificationQueue.offer(s1);
        verificationQueue.offer(s2);
        verificationQueue.offer(s3);

        // Teaching Faculty
        teachingStaffRepo.add(new TeachingStaff("FAC-101", "Dr. V. V. Kamat", "kamat@unigoa.ac.in", "Professor", "DCST", "Graphics & Systems", 28));
        teachingStaffRepo.add(new TeachingStaff("FAC-102", "Dr. Jyoti Pawar", "jdp@unigoa.ac.in", "Professor & Dean", "DCST", "Data Mining & NLP", 24));
        teachingStaffRepo.add(new TeachingStaff("FAC-103", "Dr. Ramdas Karmali", "rnk@unigoa.ac.in", "Associate Professor", "DCST", "Networks & Security", 16));

        // Non-Teaching Staff
        nonTeachingStaffRepo.add(new NonTeachingStaff("STF-201", "Sanjay Parab", "sanjay@unigoa.ac.in", "System Administrator", "DCST Lab", "Network and Lab Systems"));
        nonTeachingStaffRepo.add(new NonTeachingStaff("STF-202", "Pooja Gaonkar", "pooja@unigoa.ac.in", "Laboratory Assistant", "DCST Lab", "Equipment Maintenance"));

        // Academic Activities (ISA, Midpoint, SEA, Study-Tour, Placements)
        academicActivityRepo.add(new AcademicActivity("ACT-ACAD-01", "Continuous ISA-1 & ISA-2 Tests", 2023, "Intra-Semester Evaluations", ActivityCategory.ACADEMIC, "ISA", "Exam Committee", 120));
        academicActivityRepo.add(new AcademicActivity("ACT-ACAD-02", "Midpoint Project Review", 2024, "Mid-term defense of MCA industrial internships", ActivityCategory.ACADEMIC, "Midpoint", "Project Coordinator", 55));
        academicActivityRepo.add(new AcademicActivity("ACT-ACAD-03", "Semester End Assessment (SEA)", 2024, "Final Theory and Practical Examinations", ActivityCategory.ACADEMIC, "SEA", "Controller of Exams", 140));
        academicActivityRepo.add(new AcademicActivity("ACT-ACAD-04", "Industrial IT Study Tour", 2024, "Visit to Bengaluru IT Hub & Tech Parks", ActivityCategory.CO_CURRICULAR, "Study-Tour", "Dr. Ramdas Karmali", 48));
        academicActivityRepo.add(new AcademicActivity("ACT-ACAD-05", "Campus Placement Drive", 2025, "On-campus placement recruitment", ActivityCategory.PLACEMENT, "Placements", "Placement Officer", 52));

        // Non-Academic Activities (InfoFest, Plateaunia, Play-on)
        nonAcademicActivityRepo.add(new NonAcademicActivity("ACT-NONACAD-01", "InfoFest State Tech Fiesta", 2023, "State Level Inter-Collegiate Technical Festival", ActivityCategory.NON_ACADEMIC, "University Hall", "Student Council", 350));
        nonAcademicActivityRepo.add(new NonAcademicActivity("ACT-NONACAD-02", "Plateaunia Cultural Championship", 2024, "Goa University Inter-Department Cultural Fest", ActivityCategory.NON_ACADEMIC, "Faculty Quadrangle", "Cultural Secretary", 200));
        nonAcademicActivityRepo.add(new NonAcademicActivity("ACT-NONACAD-03", "Play-on Annual Sports Meet", 2025, "Departmental Sports Championship", ActivityCategory.NON_ACADEMIC, "University Sports Ground", "Sports Coordinator", 180));
    }

    // Requirement 9: Custom exception handling and throw
    public void addStudentWithValidation(String id, String name, String email, ProgramType program, 
                                        int year, double cgpa, String spec) throws InvalidStudentDataException {
        if (id == null || id.trim().isEmpty() || !id.matches("^[A-Za-z0-9-]+$")) {
            throw new InvalidStudentDataException("Invalid Student ID: '" + id + "'. Must be alphanumeric.");
        }
        if (studentMap.containsKey(id)) {
            throw new InvalidStudentDataException("Duplicate Student ID: '" + id + "' already exists in database.");
        }
        if (name == null || name.trim().length() < 2) {
            throw new InvalidStudentDataException("Invalid Name: Student name must be at least 2 characters.");
        }
        if (email == null || !email.contains("@")) {
            throw new InvalidStudentDataException("Invalid Email: Missing '@' in email address.");
        }
        if (cgpa < 0.0 || cgpa > 10.0) {
            throw new InvalidStudentDataException("Invalid CGPA: " + cgpa + ". Must be between 0.00 and 10.00.");
        }

        DepartmentInfo dept = new DepartmentInfo("DCST", "Block B, Goa University", "Head of DCST");
        Student student = new Student(id, name, email, program, year, cgpa, spec, dept);
        addStudent(student);
        verificationQueue.offer(student);
    }

    public void addStudent(Student student) {
        studentRepo.add(student);
        studentMap.put(student.getId(), student);
    }

    // Requirement 8: Set duplicate rejection
    public boolean addSubjectWithCheck(Subject subject) {
        if (subject != null && uniqueSubjectCodes.add(subject.getSubjectCode())) {
            subjectRepo.add(subject);
            return true;
        }
        return false;
    }

    // Requirement 7: Method Overloading (Search Student)
    // Overloaded search 1: by ID
    public List<Student> searchStudent(String studentId) {
        System.out.println("// Overloaded Method: searchStudent(String studentId)");
        List<Student> results = new ArrayList<>();
        Student s = studentMap.get(studentId);
        if (s != null) {
            results.add(s);
        } else {
            for (Student item : studentRepo.getAll()) {
                if (item.getId().equalsIgnoreCase(studentId)) results.add(item);
            }
        }
        return results;
    }

    // Overloaded search 2: by Name and ProgramType
    public List<Student> searchStudent(String namePart, ProgramType program) {
        System.out.println("// Overloaded Method: searchStudent(String namePart, ProgramType program)");
        List<Student> results = new ArrayList<>();
        for (Student s : studentRepo.getAll()) {
            if (s.getName().toLowerCase().contains(namePart.toLowerCase()) && s.getProgram() == program) {
                results.add(s);
            }
        }
        return results;
    }

    // Overloaded search 3: by Year of Study
    public List<Student> searchStudent(int yearOfStudy) {
        System.out.println("// Overloaded Method: searchStudent(int yearOfStudy)");
        List<Student> results = new ArrayList<>();
        for (Student s : studentRepo.getAll()) {
            if (s.getYearOfStudy() == yearOfStudy) {
                results.add(s);
            }
        }
        return results;
    }

    public List<AcademicActivity> searchAcademicActivity(String keyword) {
        List<AcademicActivity> results = new ArrayList<>();
        for (AcademicActivity a : academicActivityRepo.getAll()) {
            if (a.getName().toLowerCase().contains(keyword.toLowerCase()) || 
                a.getActivityType().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(a);
            }
        }
        return results;
    }

    // Requirement 8: Generic method displaying any collection
    public static <E> void displayCollection(String title, Collection<E> collection) {
        System.out.println("--- Generic Display: " + title + " (Count: " + collection.size() + ") ---");
        int index = 1;
        for (E item : collection) {
            System.out.println(" [" + (index++) + "] " + item);
        }
    }

    // Requirement 8: Queue FIFO operation
    public Student processNextVerification() {
        return verificationQueue.poll();
    }

    public int getPendingVerificationCount() {
        return verificationQueue.size();
    }

    // Getters for collections
    public SimpleRepository<Student> getStudentRepo() { return studentRepo; }
    public SimpleRepository<TeachingStaff> getTeachingStaffRepo() { return teachingStaffRepo; }
    public SimpleRepository<NonTeachingStaff> getNonTeachingStaffRepo() { return nonTeachingStaffRepo; }
    public SimpleRepository<AcademicActivity> getAcademicActivityRepo() { return academicActivityRepo; }
    public SimpleRepository<NonAcademicActivity> getNonAcademicActivityRepo() { return nonAcademicActivityRepo; }
    public SimpleRepository<Program> getProgramRepo() { return programRepo; }
    public SimpleRepository<Course> getCourseRepo() { return courseRepo; }
    public SimpleRepository<Subject> getSubjectRepo() { return subjectRepo; }
    public Queue<Student> getVerificationQueue() { return verificationQueue; }
    public Set<String> getUniqueSubjectCodes() { return uniqueSubjectCodes; }
    public Map<String, Student> getStudentMap() { return studentMap; }

    // Requirement 10: Persistence (Serialization / Deserialization)
    public void saveData(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
            System.out.println("[SUCCESS] System data serialized successfully to: " + filename);
        }
    }

    public static NIRFDataManager loadData(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            NIRFDataManager loaded = (NIRFDataManager) ois.readObject();
            System.out.println("[SUCCESS] System data restored successfully from: " + filename);
            return loaded;
        }
    }

    // Requirement 10: File I/O (Export & Read text report)
    public void exportNIRFTextReport(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("==========================================================");
            writer.println("         DCST NIRF DATA REPORT (GOA UNIVERSITY)           ");
            writer.println("==========================================================");
            writer.println("Generated: " + new Date());
            writer.println("Total Members (Static Count): " + Person.getMemberCount());
            writer.println();

            writer.println("1. PROGRAMS OFFERED:");
            for (Program p : programRepo.getAll()) writer.println("   * " + p.getSummary());

            writer.println("\n2. STUDENTS (" + studentRepo.size() + " Total):");
            for (Student s : studentRepo.getAll()) writer.println("   * " + s.getSummary() + " | " + s.getNIRFSubmissionStatus());

            writer.println("\n3. TEACHING FACULTY (" + teachingStaffRepo.size() + " Total):");
            for (TeachingStaff t : teachingStaffRepo.getAll()) writer.println("   * " + t.getSummary());

            writer.println("\n4. NON-TEACHING STAFF (" + nonTeachingStaffRepo.size() + " Total):");
            for (NonTeachingStaff nt : nonTeachingStaffRepo.getAll()) writer.println("   * " + nt.getSummary());

            writer.println("\n5. ACADEMIC ACTIVITIES (" + academicActivityRepo.size() + " Total):");
            for (AcademicActivity a : academicActivityRepo.getAll()) writer.println("   * " + a.getSummary());

            writer.println("\n6. NON-ACADEMIC ACTIVITIES (" + nonAcademicActivityRepo.size() + " Total):");
            for (NonAcademicActivity na : nonAcademicActivityRepo.getAll()) writer.println("   * " + na.getSummary());

            writer.println("\n7. SUBJECTS (" + subjectRepo.size() + " Total, Set Verified):");
            for (Subject sub : subjectRepo.getAll()) writer.println("   * " + sub);
            writer.println("==========================================================");
        }
        System.out.println("[SUCCESS] Report exported successfully to: " + filename);
    }

    public static void readAndDisplayReport(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("[ERROR] Report file '" + filename + "' not found.");
            return;
        }
        System.out.println("--- Reading: " + filename + " ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
