import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private String courseShortName;
    private ArrayList<String> prerequisite, corequisite;
    private int creditHours;

    /**
     * @param courseName
     * @param instructor
     * @param prerequisite
     * @param corequisite
     */
    public Course() {
    };

    public Course(String courseShortName, int creditHours, ArrayList<String> prerequisite,
            ArrayList<String> corequisite) {
        this.courseShortName = courseShortName;
        this.creditHours = creditHours;
        this.prerequisite = prerequisite;
        this.corequisite = corequisite;
        this.creditHours = creditHours;
    }

    public ArrayList<String> getPrerequisite() {
        return this.prerequisite;
    }

    public void setPrerequisite(ArrayList<String> prerequisite) {
        this.prerequisite = prerequisite;
    }

    public ArrayList<String> getCorequisite() {
        return this.corequisite;
    }

    public void setCorequisite(ArrayList<String> corequisite) {
        this.corequisite = corequisite;
    }

    public int getCreditHours() {
        return this.creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public String getCourseShortName() {
        return this.courseShortName;
    }

    public void setCourseShortName(String courseShortName) {
        this.courseShortName = courseShortName;
    }

    @Override
    public String toString() {
        return "{" +
                " courseName='" + getCourseShortName() + "'" +
                ", prerequisite='" + getPrerequisite() + "'" +
                ", corequisite='" + getCorequisite() + "'" +
                ", creditHours='" + getCreditHours() + "'" +
                "}";
    }

    /**
     * @param finishedCourses
     * @return
     */
    public boolean checkPrerequisite(ArrayList<String> finishedCourses) {
        if (finishedCourses.size() > this.prerequisite.size()) {
            return finishedCourses.containsAll(this.prerequisite);
        }
        return this.prerequisite.containsAll(finishedCourses);
    }
}