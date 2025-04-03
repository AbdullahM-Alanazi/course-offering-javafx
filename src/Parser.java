import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

    static Student parseStudentFile(File file) throws FileNotFoundException {
        /*
         * Create a Scanner object.
         * Read lines, Create a Student object,
         * return a student object.
         */
        Scanner reader1 = new Scanner(file);
        Student student = new Student("Abdullah", "Swe", 202037600, new ArrayList<String>());
        // reader1.useDelimiter(",");
        while (reader1.hasNext()) {
            String finishedCourse = reader1.nextLine().split(",")[0];
            student.getFinishedCourses().add(finishedCourse);
        }
        reader1.close();
        return student;
    }

    static ArrayList<Course> parseDegreePlanFile(File file) throws FileNotFoundException {
        /*
         * Create a Scanner object.
         * Init an array-list<Course> called DgPlanOfStudent, append each course in the
         * array.
         * return Course array.
         */
        ArrayList<Course> DegreePlan = new ArrayList<>();

        Scanner reader2 = new Scanner(file);
        reader2.nextLine();
        while (reader2.hasNext()) {
            String[] line = reader2.nextLine().split(",");
            String courseShortName = line[0];
            Integer creditHours = Integer.parseInt(line[1].trim());
            String[] preReqCourses = line[2].split(";");
            ArrayList<String> preReq = new ArrayList<>();
            for (String course : preReqCourses) {
                preReq.add(course);
            }
            String[] preCooCourses = line[3].split(";");
            ArrayList<String> preCoo = new ArrayList<>();
            for (String course : preCooCourses) {
                preCoo.add(course);
            }
            // * Create the object;
            DegreePlan.add(new Course(courseShortName, creditHours, preReq, preCoo));
        }
        reader2.close();

        return DegreePlan;
    }

    static ArrayList<Section> parseCourseOfferingFile(File file, File finishedCourseFile, File DegreePlanFile) {
        /*
         * Create a Scanner object.
         * Init an array-list<Section>.
         * Diemnsions :
         * Course-Sec,Activity,CRN,Course
         * Name,Instructor,Day,Time,Location,Status,Waitlist.
         * 1 ) Check if the Course in the DgP of the student.
         * // * 2 ) Check if isStatus = true || isWaitlist = true.
         * 3 ) Check the preReq && coReq.
         */
        Student student;
        ArrayList<Course> DegreePlan;
        Scanner reader;
        ArrayList<Section> sections = new ArrayList<>();
        try {
            student = Parser.parseStudentFile(finishedCourseFile);
            DegreePlan = Parser.parseDegreePlanFile(DegreePlanFile);
            reader = new Scanner(new File("src/SampleFiles/CourseOffering.csv"));

            reader.nextLine();
            while (reader.hasNext()) {
                // ICS 104-01,LEC,13049,Introduction to Programming in Python and C,MOHAMMAD
                // AMRO,UT,0900,0950,24-121,Open,Closed
                String[] line = reader.nextLine().split(",");
                String course = line[0].split("-")[0];
                String section = line[0].split("-")[1];
                String Activity = line[1];
                Integer CRN = Integer.parseInt(line[2]);
                String course_name = line[3];
                String Instructor = line[4];
                String Day = line[5];
                //
                Integer startTime = Integer.parseInt(line[6]);
                Integer ednTime = Integer.parseInt(line[7]);
                //
                String Location = line[9];
                String Status = line[9];
                String Waitlist = line[10];
                // * Check if the course in the degree plan.
                for (Course course_ : DegreePlan) {
                    if (course_.getCourseShortName().equals(course)
                            && course_.checkPrerequisite(student.getFinishedCourses())
                            && !student.isFinished(course_)) {
                        sections.add(new Section(course_, CRN, startTime, ednTime, Instructor, Day, Activity, Location,
                                course_name, section, Status, Waitlist));
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("An error has occured while parsing the CO file..");
            System.out.println(e.getMessage());
        }

        return sections;
    }
}
