import java.io.Serializable;
import java.util.ArrayList;

public class Schedule implements Serializable {
    private ArrayList<Section> regSections = new ArrayList<>();

    public Schedule() {
    }

    public Schedule(ArrayList<Section> regSections) {
        this.regSections = regSections;
    }

    public ArrayList<Section> getRegSections() {
        return this.regSections;
    }

    public void setRegSections(ArrayList<Section> regSections) {
        this.regSections = regSections;
    }

    public void removeRegSection(Section section) {
        this.regSections.remove(section);
    }

    public void addRegSection(Section section) {
        this.regSections.add(section);
    }

    @Override
    public String toString() {
        return "{" +
                " regSections='" + getRegSections() + "'" +
                "}";
    }

}