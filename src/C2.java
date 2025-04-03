import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class C2 implements Initializable {
    private Schedule schedule_ = new Schedule();
    static private ArrayList<Section> potentialSections = C1.getPotentialSections();
    @FXML
    private VBox basketContainer;
    @FXML
    private GridPane scheduleContainerGridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // * Basket container initializer.
        try {
            for (int i = 0; i < potentialSections.size(); i++) {
                basketContainer.getChildren().add(getBasketUIVBox(potentialSections.get(i), i));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param potenialSection
     * @param index
     * @return
     * @throws IOException
     */
    private VBox getBasketUIVBox(Section potenialSection, int index) throws IOException {
        URL url = new File("src/components/scene2/VBox.fxml").toURI().toURL();
        URL url2 = new File("src/components/scene2/Label.fxml").toURI().toURL();
        VBox sectionContainer = FXMLLoader.load(url);
        Label sectionLabel = FXMLLoader.load(url2);
        String content = potenialSection.getCourse().getCourseShortName() + " " + potenialSection.getSectionNumber()
                + " " + potenialSection.getActivity()
                + " \n" + potenialSection.getStartTime() + " - " + potenialSection.getEndTime();
        sectionLabel.setText(content);
        // * Event-listner.
        sectionLabel.setOnMouseClicked(e -> {
            if (checkConflictTime(potenialSection) &&
                    checkDublicateSection(potenialSection)) {
                schedule_.addRegSection(potenialSection);
                for (Integer dayIndex : getDayIndex(potenialSection)) {
                    placeSection(potenialSection, dayIndex);
                }
                return;
            }
            // * Alert of dublicate or conflict time.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Dublicate course or time conflict.");
            alert.showAndWait();
        });
        // *
        sectionContainer.getChildren().addAll(sectionLabel);
        return sectionContainer;
    }

    /**
     * @param section
     * @return
     */
    static private ArrayList<Integer> getDayIndex(Section section) {
        LinkedHashMap<String, Integer> mapToDay = new LinkedHashMap<>();
        mapToDay.put("U", 1);
        mapToDay.put("M", 2);
        mapToDay.put("T", 3);
        mapToDay.put("W", 4);
        mapToDay.put("R", 5);
        String[] sectionDay = section.getDay().split("");
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < sectionDay.length; i++) {
            indices.add(mapToDay.get(sectionDay[i]));
        }
        return indices;
    }

    /**
     * @param section
     * @return
     */
    static private int getTimeIndex(Section section) {
        // * The time index is based upon the start time.
        int startTime = section.getStartTime();
        if (startTime < 800) { // 7am
            return 0;
        } else if (startTime < 900) { // 8am
            return 1;
        } else if (startTime < 1000) { // 9am
            return 2;
        } else if (startTime < 1100) { // 10am
            return 3;
        } else if (startTime < 1200) { // 11am
            return 4;

        } else if (startTime < 1300) { // 12pm
            return 5;

        } else if (startTime < 1400) { // 1pm
            return 6;

        } else if (startTime < 1500) { // 2pm
            return 7;

        } else if (startTime < 1600) { // 3pm
            return 8;

        } else if (startTime < 1700) { // 4pm
            return 9;

        } else if (startTime < 1800) { // 5pm
            return 10;
        }
        return -1;
    }

    /**
     * @param section
     * @param dayIndex
     */
    private void placeSection(Section section, int dayIndex) {
        int timeIndex = getTimeIndex(section);
        try {
            URL url_ = new File("src/components/scene2/VBoxScheduale.fxml").toURI().toURL();
            URL url2_ = new File("src/components/scene2/LabelSchedule.fxml").toURI().toURL();
            URL url3_ = new File("src/components/scene2/removeButton.fxml").toURI().toURL();
            VBox VBoxScheduale = FXMLLoader.load(url_);
            Label LabelSchedule = FXMLLoader.load(url2_);
            Button removeButton = FXMLLoader.load(url3_);
            LabelSchedule.setText(section.getCourse().getCourseShortName());
            removeButton.setText("remove");
            removeButton.setOnMouseClicked(e -> {
                // * Remove from the regSections.
                schedule_.removeRegSection(section);
                // * Remove from the schedule.
                scheduleContainerGridPane.getChildren().remove(VBoxScheduale);
            });
            VBoxScheduale.getChildren().addAll(LabelSchedule, removeButton);
            scheduleContainerGridPane.add(VBoxScheduale, dayIndex, timeIndex); // Node, COL, ROW.
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param section
     * @return
     */
    private boolean checkDublicateSection(Section section) {
        /*
         * Course is dublicate if the course name of the two sections are matched.
         * Consider the case of LAB, LUC.
         */
        for (Section regSection : schedule_.getRegSections()) {
            if (regSection.getCourse().getCourseShortName().equals(section.getCourse().getCourseShortName())
                    & regSection.getActivity().equals(section.getActivity())) {
                // * isDublicate
                System.out.println("is dublicate");
                return false;
            }
        }
        // * Not dublicate.
        return true;
    }

    /**
     * @param section
     * @return
     */
    private boolean checkConflictTime(Section section) {
        /*
         * Since the time format 8:00 -> 800, 8:30 -> 830.
         * Conflict occures when they are at the same hour.
         * To find the exact hour, divide by 100. then subtract == 0 ? same hour :
         * different hour.
         * Two cases :
         * section.getStartTime() < placedSection.getEndTime(). ) 1
         * section.getEndTime() > placedSection.getStartTime(). ) 2
         */
        int sectionStartTime = section.getStartTime() / 100;
        int sectionEndsTime = section.getEndTime() / 100;
        for (Section placedSection : schedule_.getRegSections()) {
            // * Consider the different time case.
            ArrayList<Integer> dayToCheck = getDayIndex(placedSection);
            dayToCheck.retainAll(getDayIndex(section));
            if (dayToCheck.size() == 0) {
                // Means they are in different days, no conflict would occure.
                return true;
            }
            int placedStartTime = placedSection.getStartTime() / 100;
            int placedEndTime = placedSection.getEndTime() / 100;
            // * Case1.
            boolean case1 = (sectionStartTime - placedEndTime) == 0 ? true : false;
            boolean case2 = (sectionEndsTime - placedStartTime) == 0 ? true : false;
            if (case1 & (section.getStartTime() < placedSection.getEndTime())) {
                // * Conflict occuer
                return false;
            } else if (case2 && (section.getEndTime() > placedSection.getStartTime())) {
                // * Conflict occuer
                return false;
            }
        }
        // * No conflict.
        return true;
    }

    /**
     * @param event
     */
    @FXML
    void saveSchedule(MouseEvent event) {
        try {
            // * Writing binary files
            FileOutputStream fileOutputStream = new FileOutputStream("savedSchedule.dat");
            ObjectOutputStream objOutStream = new ObjectOutputStream(fileOutputStream);
            objOutStream.writeObject(schedule_);
            objOutStream.close();
            // * */
            // * Alert for success save.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("");
            alert.setHeaderText("Saved!");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
