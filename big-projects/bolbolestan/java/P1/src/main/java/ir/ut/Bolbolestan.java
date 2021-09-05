package ir.ut;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import ir.ut.exception.*;
import ir.ut.offering.Offering;
import ir.ut.student.Student;
import ir.ut.student.WeeklySchedule;

public class Bolbolestan {
    private static Bolbolestan instance;

    private final HashMap<String, Student> students;
    private final HashMap<String, Offering> offerings;

    private static Bolbolestan getInstance() {
        if (instance == null) {
            instance = new Bolbolestan();
        }
        return instance;
    }

    public Bolbolestan() {
        this.students = new HashMap<>();
        this.offerings = new HashMap<>();
    }

    public static void run(InputStream inputStream, PrintStream printStream) {
        new CommandHandler(getInstance()).run(inputStream, printStream);
    }

    public void addStudent(Student student) throws DuplicateStudentException {
        if (this.students.containsKey(student.getId())) {
            throw new DuplicateStudentException();
        }
        this.students.put(student.getId(), student);
    }

    public void addOffering(Offering offering) throws DuplicateOfferingException {
        if (this.offerings.containsKey(offering.getCode())) {
            throw new DuplicateOfferingException();
        }
        this.offerings.put(offering.getCode(), offering);
    }

    public ArrayList<Offering> getOfferings() {
        ArrayList<Offering> offerings = new ArrayList<>(this.offerings.values());
        Collections.sort(offerings, new OfferingComparator());
        return offerings;
    }

    public Offering getOffering(String code) throws OfferingNotFoundException {
        if (this.offerings.containsKey(code)) {
            return this.offerings.get(code);
        }
        throw new OfferingNotFoundException();
    }

    public Student getStudent(String studentId) throws StudentNotFoundException {
        if (this.students.containsKey(studentId)) {
            return this.students.get(studentId);
        }
        throw new StudentNotFoundException();
    }

    public void addToWeeklySchedule(String studentId, String code) throws OfferingNotFoundException, StudentNotFoundException {
        Student student = getStudent(studentId);
        Offering offering = getOffering(code);
        student.addToWeeklySchedule(offering);
    }

    public void removeFromWeeklySchedule(String studentId, String code) throws OfferingNotFoundException, StudentNotFoundException, OfferingNotInScheduleException {
        Student student = getStudent(studentId);
        Offering offering = getOffering(code);
        student.removeFromWeeklySchedule(offering);
    }

    public void finalizeSchedule(String studentId) throws BolbolestanException {
        getStudent(studentId).finalizeSchedule();
    }

    public WeeklySchedule getWeeklySchedule(String studentId) throws StudentNotFoundException {
        Student student = getStudent(studentId);
        return student.getWeeklySchedule();
    }
}

class OfferingComparator implements Comparator<Offering> {
    public int compare(Offering a, Offering b) {
        return a.getName().compareTo(b.getName());
    }
}
