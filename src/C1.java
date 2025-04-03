
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

public class C1 implements Initializable {
    private File CourseOfferingFile = new File("src/SampleFiles/CourseOffering.csv");
    private File FinishedCourseFile = new File("src/SampleFiles/FinishedCourses.csv");
    private File degreePlanFile = new File("src/SampleFiles/DegreePlan.csv");
    private ArrayList<Section> avaliableSections = Parser.parseCourseOfferingFile(CourseOfferingFile,
            FinishedCourseFile,
            degreePlanFile);
    static private ArrayList<Section> potentialSections = new ArrayList<>();
    private Stage currentStage = Project.getCurrenStage();
    @FXML
    private VBox sectionsContainerScroll;

    /*
     * (non-Javadoc)
     * 
     * @see javafx.fxml.Initializable#initialize(java.net.URL,
     * java.util.ResourceBundle)
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // * Add the available section to the sectionsContainer.
            for (int i = 0; i < avaliableSections.size(); i++) {
                // * Add individual sections to the sectionContainerScroll.
                sectionsContainerScroll.getChildren().add(getSectionUI(avaliableSections.get(i),
                        i));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param event
     * @throws IOException
     */
    /**
     * @param availablSection
     * @param index
     * @return
     * @throws IOException
     */
    private HBox getSectionUI(Section availablSection, int index) throws IOException {
        HBox sectionContainer = FXMLLoader.load(getClass().getResource("components/scene1/sectionContainer.fxml")); // src/sectionContainer.fxml
        Label sectionLabel = FXMLLoader.load(getClass().getResource("components/scene1/sectionLabel.fxml")); // src/sectionLabel.fxml
        Button addButton = FXMLLoader.load(getClass().getResource("components/scene1/addButton.fxml"));// src/addButton.fxml
        Button removeButton = FXMLLoader.load(getClass().getResource("components/scene1/removeButton.fxml"));// src/removeButton.fxml
        sectionLabel.setText(availablSection.toString());
        addButton.setOnMouseClicked(event -> {
            if (potentialSections.contains(avaliableSections.get(index)))
                return;
            potentialSections.add(avaliableSections.get(index));
            addButton.setStyle("-fx-background-color: MediumSeaGreen");
        });
        removeButton.setOnMouseClicked(e -> {
            potentialSections.remove(avaliableSections.get(index));
            addButton.setStyle("-fx-background-color:");

        });
        sectionContainer.getChildren().addAll(sectionLabel, addButton, removeButton);
        return sectionContainer;
    }

    static public ArrayList<Section> getPotentialSections() {
        return potentialSections;
    }

    static public void setPotentialSections(ArrayList<Section> sections) {
        potentialSections = sections;
    }

    @FXML
    void nextScene(MouseEvent event) throws IOException {
        // ! Scene2, loaded when the next button clicked.
        Parent root = FXMLLoader.load(getClass().getResource("components/scene2/scene2.fxml"));
        currentStage.setScene(new Scene(root, 738, 732));
        currentStage.setResizable(false);
        currentStage.show();
    }

    /**
     * @param event
     */
    @FXML
    void loadPreSchedule(MouseEvent event) {
        // *
        // * Saved file initializer if any.
        try {
            // * Reading binary files
            FileInputStream fileInputStream = new FileInputStream("savedSchedule.dat");
            ObjectInputStream objInStream = new ObjectInputStream(fileInputStream);
            Schedule schedule = (Schedule) objInStream.readObject();
            objInStream.close();
            setPotentialSections(schedule.getRegSections());
            if (schedule != null) {
                // * set sections.
                setPotentialSections(schedule.getRegSections());
                // * Move to scene2.
                nextScene(event);
                return;
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // * Alert of there is no schedule to read.
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("No pre schedule is saved!.");
        alert.showAndWait();
    }
}
