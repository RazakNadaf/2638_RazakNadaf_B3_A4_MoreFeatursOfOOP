/*
 * Author: Razak Nadaf
 * Roll No: 2638
 * Batch: B3
 * Assignment: A4
 */

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

// Class containing interactive demonstrations for all 10 required OOP concepts
public class OOPDemonstrations {

    private final NIRFDataManager dataManager;

    public OOPDemonstrations(NIRFDataManager dataManager) {
        this.dataManager = dataManager;
    }

    // REQUIREMENT 1: Object Referencing & Constructors
    public void demonstrateObjectReferencing() {
        System.out.println("\n========== [1] OBJECT REFERENCING & CONSTRUCTORS ==========");
        System.out.println("Constructors initialize object attributes upon creation.");
        System.out.println("In Java, object variables hold memory references, not the actual object.");

        // Creating student1 with constructor
        DepartmentInfo dept = new DepartmentInfo("DCST", "Block B, Goa University", "Head of DCST");
        Student student1 = new Student("STU-001", "Rahul Naik", "rahul@unigoa.ac.in", ProgramType.MCA, 2, 8.50, "Full Stack", dept);
        System.out.println("\nstudent1 created: " + student1.getName() + " | Hash: " + System.identityHashCode(student1));

        // student2 references the same object as student1
        Student student2 = student1;
        System.out.println("student2 assigned from student1 (student2 = student1);");
        System.out.println("student2 Hash: " + System.identityHashCode(student2));
        System.out.println("Are references identical? (student1 == student2) : " + (student1 == student2));

        // Modifying student2 causes side-effect on student1
        System.out.println("\nModifying student2: student2.setName(\"Amit Sharma\");");
        student2.setName("Amit Sharma");

        System.out.println("Result after modification:");
        System.out.println("student1 name: " + student1.getName());
        System.out.println("student2 name: " + student2.getName());
        System.out.println("Conclusion: student1 and student2 refer to the same object. Modifying student2 altered student1.");
    }

    // REQUIREMENT 2: Static Attributes, Methods & Enums
    public void demonstrateStaticAndEnums() {
        System.out.println("\n========== [2] STATIC ATTRIBUTES, METHODS & ENUMS ==========");
        System.out.println("Static members belong to the class itself, shared by all instances.");

        System.out.println("Current Person.getMemberCount(): " + Person.getMemberCount());
        System.out.println("Creating a new member...");
        new Student("TEMP-S1", "Demo Member", "demo@unigoa.ac.in", ProgramType.MCA, 1, 7.8);
        Person.displayMemberCount(); // Static method call

        System.out.println("\nDemonstrating Enums:");
        System.out.println("ProgramType values:");
        for (ProgramType pt : ProgramType.values()) {
            System.out.println(" - " + pt.name() + ": " + pt.getDisplayName() + " (" + pt.getDurationYears() + " yrs)");
        }
        System.out.println("ActivityCategory values:");
        for (ActivityCategory ac : ActivityCategory.values()) {
            System.out.println(" - " + ac.name() + ": " + ac.getDescription());
        }
        System.out.println("StaffType values:");
        for (StaffType st : StaffType.values()) {
            System.out.println(" - " + st.name() + ": " + st.getDescription());
        }
    }

    // REQUIREMENT 3: Multiple Inheritance using Interfaces
    public void demonstrateMultipleInheritance() {
        System.out.println("\n========== [3] MULTIPLE INHERITANCE USING INTERFACES ==========");
        System.out.println("Java does NOT support multiple class inheritance to avoid the Diamond Problem.");
        System.out.println("Instead, Java supports multiple inheritance of type through interfaces.");
        System.out.println("Example: Student extends Person implements NIRFSubmissible, AcademicParticipant, Cloneable");
        System.out.println("Also: NIRFSubmissible extends Reportable, Verifiable (Interface extending multiple interfaces)");

        Student student = dataManager.getStudentRepo().get(0);
        System.out.println("\nInstanceof checks on Student object (" + student.getName() + "):");
        System.out.println(" - instanceof Person              : " + (student instanceof Person) + " (Class inheritance)");
        System.out.println(" - instanceof NIRFSubmissible     : " + (student instanceof NIRFSubmissible) + " (Multiple interface)");
        System.out.println(" - instanceof Reportable          : " + (student instanceof Reportable) + " (Inherited via NIRFSubmissible)");
        System.out.println(" - instanceof Verifiable          : " + (student instanceof Verifiable) + " (Inherited via NIRFSubmissible)");
        System.out.println(" - instanceof AcademicParticipant : " + (student instanceof AcademicParticipant) + " (Interface implementation)");
    }

    // REQUIREMENT 4: Access Modifiers (private, protected, public)
    public void demonstrateAccessModifiers() {
        System.out.println("\n========== [4] ACCESS MODIFIERS (PRIVATE, PROTECTED, PUBLIC) ==========");
        System.out.println("1. private   : Accessible only within the declaring class.");
        System.out.println("2. protected : Accessible within package and by derived subclasses.");
        System.out.println("3. public    : Accessible from anywhere.");

        Student s = dataManager.getStudentRepo().get(0);
        System.out.println("\nPrivate access: Field 'id' in Person is private.");
        System.out.println("Direct 's.id' is blocked by compiler. Accessed via public getter: s.getId() -> " + s.getId());

        System.out.println("\nProtected access: Fields 'name' and 'email' in Person are protected.");
        System.out.println("Subclass Student accesses them directly inside displayDetails():");
        s.displayDetails();
    }

    // REQUIREMENT 5: Abstract Classes & Interfaces
    public void demonstrateAbstractClassesAndInterfaces() {
        System.out.println("\n========== [5] ABSTRACT CLASSES & INTERFACES ==========");
        System.out.println("Abstract class Person cannot be instantiated directly and has abstract displayDetails().");
        System.out.println("Derived classes provide concrete implementations:\n");

        Person p1 = dataManager.getStudentRepo().get(0);
        Person p2 = dataManager.getTeachingStaffRepo().get(0);
        Person p3 = dataManager.getNonTeachingStaffRepo().get(0);

        System.out.println("Polymorphic call on Student reference:");
        p1.displayDetails();
        System.out.println("Polymorphic call on TeachingStaff reference:");
        p2.displayDetails();
        System.out.println("Polymorphic call on NonTeachingStaff reference:");
        p3.displayDetails();
    }

    // REQUIREMENT 6: Class as a Type & Dynamic Message Passing
    public void demonstrateMessagePassing() {
        System.out.println("\n========== [6] CLASS AS A TYPE & MESSAGE PASSING ==========");
        System.out.println("Method processReport(Reportable obj) accepts any object implementing Reportable.");
        System.out.println("At runtime, dynamic dispatch executes the specific class's generateReport().\n");

        List<Reportable> list = new ArrayList<>();
        list.add(dataManager.getStudentRepo().get(0));
        list.add(dataManager.getTeachingStaffRepo().get(0));
        list.add(dataManager.getAcademicActivityRepo().get(0));
        list.add(dataManager.getProgramRepo().get(0));

        for (Reportable item : list) {
            processReport(item);
            System.out.println("---");
        }
    }

    // Requirement 6: Dynamic message passing method
    public static void processReport(Reportable reportable) {
        System.out.println("Dispatching to: " + reportable.getClass().getSimpleName());
        reportable.generateReport();
    }

    // REQUIREMENT 7: Polymorphism (Overriding & Overloading)
    public void demonstratePolymorphism() {
        System.out.println("\n========== [7] POLYMORPHISM (OVERRIDING & OVERLOADING) ==========");
        
        System.out.println("Part 1: Dynamic Polymorphism (Method Overriding)");
        Person ref = dataManager.getStudentRepo().get(0);
        System.out.println("Superclass Person ref calling displayDetails():");
        ref.displayDetails(); // Calls Student's overridden method

        System.out.println("\nPart 2: Static Polymorphism (Method Overloading)");
        System.out.println("Demonstrating 3 overloaded searchStudent methods in NIRFDataManager:\n");

        System.out.println("Call 1: searchStudent(String id)");
        List<Student> r1 = dataManager.searchStudent("STU-2023-01");
        for (Student s : r1) System.out.println(" -> Found: " + s.getSummary());

        System.out.println("\nCall 2: searchStudent(String namePart, ProgramType program)");
        List<Student> r2 = dataManager.searchStudent("Sneha", ProgramType.MCA);
        for (Student s : r2) System.out.println(" -> Found: " + s.getSummary());

        System.out.println("\nCall 3: searchStudent(int yearOfStudy)");
        List<Student> r3 = dataManager.searchStudent(1);
        for (Student s : r3) System.out.println(" -> Found: " + s.getSummary());
    }

    // REQUIREMENT 8: Generics and Collection Frameworks
    public void demonstrateGenericsAndCollections() {
        System.out.println("\n========== [8] GENERICS & COLLECTION FRAMEWORKS ==========");
        
        System.out.println("1. Generic Class: SimpleRepository<T>");
        System.out.println("Student repository count: " + dataManager.getStudentRepo().size());
        System.out.println("Program repository count: " + dataManager.getProgramRepo().size());

        System.out.println("\n2. Generic Method: displayCollection()");
        NIRFDataManager.displayCollection("Active Programs", dataManager.getProgramRepo().getAll());

        System.out.println("\n3. Collections Framework:");
        System.out.println("- List<Student>: Ordered storage. First student: " + dataManager.getStudentRepo().get(0).getName());

        System.out.println("\n- Queue<Student>: FIFO verification queue.");
        System.out.println("Queue size before poll: " + dataManager.getPendingVerificationCount());
        Student polled = dataManager.processNextVerification();
        System.out.println("Dequeued (FIFO head): " + (polled != null ? polled.getName() : "None"));
        System.out.println("Remaining queue size: " + dataManager.getPendingVerificationCount());

        System.out.println("\n- Set<String>: Enforces unique subject codes.");
        System.out.println("Existing codes: " + dataManager.getUniqueSubjectCodes());
        System.out.println("Attempting to add duplicate 'CST-501'...");
        boolean added = dataManager.addSubjectWithCheck(new Subject("CST-501", "Duplicate Sub", 1, 4, true));
        System.out.println("Was duplicate accepted? " + added + " (Rejected by Set!)");

        System.out.println("\n- Map<String, Student>: Fast key-value lookup.");
        Student mapped = dataManager.getStudentMap().get("STU-2023-02");
        System.out.println("Lookup key 'STU-2023-02': " + (mapped != null ? mapped.getSummary() : "Not Found"));
    }

    // REQUIREMENT 9: Exception Handling
    public void demonstrateExceptionHandling() {
        System.out.println("\n========== [9] EXCEPTION HANDLING ==========");
        System.out.println("Demonstrating try, catch, finally, throw, and custom InvalidStudentDataException.\n");

        System.out.println("Scenario 1: Invalid CGPA (> 10.00)");
        try {
            System.out.println("Attempting: addStudentWithValidation with CGPA 11.50...");
            dataManager.addStudentWithValidation("STU-BAD", "Test Student", "test@unigoa.ac.in", ProgramType.MCA, 1, 11.50, "AI");
        } catch (InvalidStudentDataException e) {
            System.out.println("[CAUGHT EXCEPTION]: " + e.getMessage());
        } finally {
            System.out.println("[FINALLY BLOCK]: Always executes for cleanup.");
        }

        System.out.println("\nScenario 2: Duplicate Student ID");
        try {
            System.out.println("Attempting duplicate ID 'STU-2023-01'...");
            dataManager.addStudentWithValidation("STU-2023-01", "Duplicate Student", "dup@unigoa.ac.in", ProgramType.MCA, 1, 8.0, "AI");
        } catch (InvalidStudentDataException e) {
            System.out.println("[CAUGHT EXCEPTION]: " + e.getMessage());
        } finally {
            System.out.println("[FINALLY BLOCK]: Completed safely.");
        }
    }

    // REQUIREMENT 10: Reflection, Persistence, I/O & Cloning
    public void demonstrateReflectionPersistenceIOCloning() {
        System.out.println("\n========== [10] REFLECTION, PERSISTENCE, I/O & CLONING ==========");

        // Part A: Reflection (RTTI)
        System.out.println("--- Part A: Java Reflection (RTTI) ---");
        Class<?> clazz = Student.class;
        System.out.println("Class Name  : " + clazz.getName());
        System.out.println("Superclass  : " + clazz.getSuperclass().getName());
        System.out.println("Modifiers   : " + Modifier.toString(clazz.getModifiers()));

        System.out.println("Implemented Interfaces:");
        for (Class<?> iface : clazz.getInterfaces()) {
            System.out.println(" - " + iface.getName());
        }

        System.out.println("Declared Fields (Sample):");
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < Math.min(5, fields.length); i++) {
            System.out.println(" - " + fields[i].getType().getSimpleName() + " " + fields[i].getName());
        }

        try {
            Method m = clazz.getMethod("getRole");
            Object res = m.invoke(dataManager.getStudentRepo().get(0));
            System.out.println("Dynamic invocation: student.getRole() -> " + res);
        } catch (Exception e) {
            System.out.println("Reflection error: " + e.getMessage());
        }

        // Part B: Shallow vs Deep Cloning
        System.out.println("\n--- Part B: Shallow vs Deep Cloning ---");
        try {
            DepartmentInfo dept = new DepartmentInfo("DCST", "Block B", "Prof. Original");
            Student orig = new Student("CLONE-01", "Kavita", "kavita@unigoa.ac.in", ProgramType.MCA, 2, 8.9, "AI", dept);
            System.out.println("Original Student: " + orig.getName() + " | HOD: " + orig.getDepartmentInfo().getHodName());

            // 1. Shallow copy
            Student shallow = orig.shallowClone();
            System.out.println("\nShallow Copy performed (DepartmentInfo reference shared).");
            shallow.getDepartmentInfo().setHodName("Prof. Mutated by Shallow");
            System.out.println("After mutating shallow copy:");
            System.out.println("Original HOD : " + orig.getDepartmentInfo().getHodName() + " (Modified!)");
            System.out.println("Shallow HOD  : " + shallow.getDepartmentInfo().getHodName());

            // 2. Deep copy
            orig.getDepartmentInfo().setHodName("Prof. Original Restored");
            Student deep = orig.deepClone();
            System.out.println("\nDeep Copy performed (DepartmentInfo cloned independently).");
            deep.getDepartmentInfo().setHodName("Prof. Mutated by Deep");
            System.out.println("After mutating deep copy:");
            System.out.println("Original HOD : " + orig.getDepartmentInfo().getHodName() + " (Unchanged!)");
            System.out.println("Deep HOD     : " + deep.getDepartmentInfo().getHodName());

        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning error: " + e.getMessage());
        }

        // Part C: Persistence (Serialization)
        System.out.println("\n--- Part C: Persistence (Serialization) ---");
        String testFile = "dcst_demo_data.dat";
        try {
            dataManager.saveData(testFile);
            NIRFDataManager loaded = NIRFDataManager.loadData(testFile);
            System.out.println("Restored Student Count: " + loaded.getStudentRepo().size());
        } catch (Exception e) {
            System.out.println("Persistence error: " + e.getMessage());
        }

        // Part D: File I/O
        System.out.println("\n--- Part D: File I/O (Report) ---");
        String reportFile = "dcst_demo_report.txt";
        try {
            dataManager.exportNIRFTextReport(reportFile);
            NIRFDataManager.readAndDisplayReport(reportFile);
        } catch (Exception e) {
            System.out.println("File I/O error: " + e.getMessage());
        }
    }

    public void runAllDemonstrations() {
        demonstrateObjectReferencing();
        demonstrateStaticAndEnums();
        demonstrateMultipleInheritance();
        demonstrateAccessModifiers();
        demonstrateAbstractClassesAndInterfaces();
        demonstrateMessagePassing();
        demonstratePolymorphism();
        demonstrateGenericsAndCollections();
        demonstrateExceptionHandling();
        demonstrateReflectionPersistenceIOCloning();
    }
}
