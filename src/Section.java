import java.io.Serializable;

public class Section implements Serializable {
    private Course course;
    private int crn, startTime, endTime;
    private String instructor, day, activity, location, courseName, SectionNumber, status, waitlist;
    // private Boolean status, waitlist;

    public Section() {
    }

    public Section(Course course, int crn, int startTime, int endTime, String instructor, String day,
            String activity,
            String location, String courseName, String SectionNumber, String status, String waitlist) {
        this.course = course;
        this.crn = crn;
        this.startTime = startTime;
        this.endTime = endTime;
        this.instructor = instructor;
        this.day = day;
        this.activity = activity;
        this.location = location;
        this.courseName = courseName;
        this.SectionNumber = SectionNumber;
        this.status = status;
        this.waitlist = waitlist;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getCrn() {
        return this.crn;
    }

    public void setCrn(int crn) {
        this.crn = crn;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public void setEndTime(int ednTime) {
        this.endTime = ednTime;
    }

    public String getInstructor() {
        return this.instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSectionNumber() {
        return this.SectionNumber;
    }

    public void setSectionNumber(String SectionNumber) {
        this.SectionNumber = SectionNumber;
    }

    public String isStatus() {
        return this.status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String isWaitlist() {
        return this.waitlist;
    }

    public String getWaitlist() {
        return this.waitlist;
    }

    public void setWaitlist(String waitlist) {
        this.waitlist = waitlist;
    }

    public Section course(Course course) {
        setCourse(course);
        return this;
    }

    public Section crn(int crn) {
        setCrn(crn);
        return this;
    }

    public Section startTime(int startTime) {
        setStartTime(startTime);
        return this;
    }

    public Section endTime(int ednTime) {
        setEndTime(ednTime);
        return this;
    }

    public Section instructor(String instructor) {
        setInstructor(instructor);
        return this;
    }

    public Section day(String day) {
        setDay(day);
        return this;
    }

    public Section activity(String activity) {
        setActivity(activity);
        return this;
    }

    public Section location(String location) {
        setLocation(location);
        return this;
    }

    public Section courseName(String courseName) {
        setCourseName(courseName);
        return this;
    }

    public Section SectionNumber(String SectionNumber) {
        setSectionNumber(SectionNumber);
        return this;
    }

    @Override
    public String toString() {
        return this.course.getCourseShortName() + " " +
                getSectionNumber() + " " +
                getCrn() + " "
                + getInstructor() + " " +
                getDay() + " " +
                getStartTime() + " " +
                getEndTime() + " " +
                getActivity() + " " +
                getLocation() + " " +
                getCourseName() + " " +
                isStatus() + " " +
                isWaitlist();
    }

}
