package ijp.viewer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import ijp.PhotoViewer;

/**
 * A superclass containing some methods used by all JavaFx viewers
 *
 * @author  Paul Anderson &lt;dcspaul@ed.ac.uk&gt;
 * @version 16:40 25 Sep 2014
 */
public abstract class JavaFxViewer implements PhotoViewer, Initializable {
		
    /**
     * Create a viewer
     *
     * @param mainClass the class name to use when looking for resources
     * @param stage the JavaFx stage
     * @param viewerClass the class of the viewer to load
     */
	public static PhotoViewer create(Class<?> mainClass, Stage stage, String viewerClass) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			String viewerFxml = "viewer/" + viewerClass + ".fxml";
			AnchorPane page = (AnchorPane) fxmlLoader.load(mainClass.getResource(viewerFxml).openStream());
			PhotoViewer viewer = (PhotoViewer) fxmlLoader.getController();         
			Scene scene = new Scene(page);
			stage.setScene(scene);
			stage.setResizable(false);
			return viewer;
        
		} catch (IOException ex) {
			Logger.getLogger(mainClass.getName()).log(Level.SEVERE, null, ex);
			return null;
		}	
	}
	
    /**
     * Start the viewer on the given stage
     *
     * @param stage the JavaFx stage
     */
	public void start(Stage stage) {
		stage.show();
	}
}
