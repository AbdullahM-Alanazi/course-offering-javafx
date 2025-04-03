
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class Project extends Application {
        static private Stage currentSrage;

        @Override
        public void start(Stage stage) throws IOException {
                // * Copy of the current stage.
                Project.currentSrage = stage;
                // ! Scene1, loaded when the program start.
                Parent root = FXMLLoader.load(getClass().getResource("components/scene1/scene1.fxml"));

                currentSrage.setScene(new Scene(root, 738, 732));
                currentSrage.setResizable(false);
                currentSrage.show();
        }

        public static void main(String[] args) {
                launch();
        }

        static public Stage getCurrenStage() {
                return Project.currentSrage;
        }
}
