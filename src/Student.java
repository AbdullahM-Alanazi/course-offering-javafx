import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
    private String name, major;
    private int id;
    private ArrayList<String> finishedCourses;
    // ? Should i add a DegreePlan as Student property or valid it in the Main class

    public Student(String name, String major, int id, ArrayList<String> finishedCourses) {
        this.name = name;
        this.major = major;
        this.id = id;
        this.finishedCourses = finishedCourses;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getFinishedCourses() {
        return this.finishedCourses;
    }

    public void setFinishedCourses(ArrayList<String> finishedCourses) {
        this.finishedCourses = finishedCourses;
    }

    public boolean isFinished(Course course) {
        return this.finishedCourses.contains(course.getCourseShortName());
    }

    @Override
    public String toString() {
        return "{" +
                " name='" + getName() + "'" +
                ", major='" + getMajor() + "'" +
                ", id='" + getId() + "'" +
                ", finishedCourses='" + getFinishedCourses() + "'" +
                "}";
    }

}