package ijp.viewer;

import ijp.Photo;
import ijp.PhotoController;
import ijp.viewer.JavaFxViewer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

/**
 * A photo viewer which allows the user to select the topic from a menu.
 * The skeleton code for this class is generated automatically by the
 * JavaFx Scene Builder (using the "Show Sample Controller Skeleton"
 * under the View menu).
 * 
 * Note that the instance of this object is created automatically by
 * the JavaFx framework when we load the fxml file - we don't create
 * objects of this class explicitly. You must specify this class as
 * the "Controller Class" for the top-level AnchorPane in Scene Builder.
 *
 * @author  Paul Anderson &lt;dcspaul@ed.ac.uk&gt;
 * @version 16:40 25 Sep 2014
 */
public class MenuViewer extends JavaFxViewer {

    private PhotoController controller;
    @FXML
    private ImageView imageView;   
    @FXML
    private Menu topicMenu;
    @FXML
    private MenuItem topic1MenuItem;
    @FXML
    private MenuItem topic2MenuItem;
    @FXML
    private MenuItem topic3MenuItem;

    @Override
    // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'MenuViewer.fxml'.";
        assert topic1MenuItem != null : "fx:id=\"topic1MenuItem\" was not injected: check your FXML file 'MenuViewer.fxml'.";
        assert topic2MenuItem != null : "fx:id=\"topic2MenuItem\" was not injected: check your FXML file 'MenuViewer.fxml'.";
        assert topic3MenuItem != null : "fx:id=\"topic3MenuItem\" was not injected: check your FXML file 'MenuViewer.fxml'.";
        assert topicMenu != null : "fx:id=\"topicMenu\" was not injected: check your FXML file 'MenuViewer.fxml'.";    	
        setCaption(1, "");
        setCaption(2, "");
        setCaption(3, "");
    }

    public final void setCaption(int n, String caption) {
        
        switch (n) {
            case 1:
                topic1MenuItem.setText(caption);
                break;
            case 2:
                topic2MenuItem.setText(caption);
                break;
            case 3:
                topic3MenuItem.setText(caption);
                break;
            default:
                break;
        }
        
        // rebuild the menu to include only non-blank items
        // we don't really need to rebuild it all every time
        // but this is easier ...
        topicMenu.getItems().clear();
        if (!topic1MenuItem.getText().equals("")) {
            topicMenu.getItems().add(topic1MenuItem);
        }
        if (!topic2MenuItem.getText().equals("")) {
            topicMenu.getItems().add(topic2MenuItem);
        }
        if (!topic3MenuItem.getText().equals("")) {
            topicMenu.getItems().add(topic3MenuItem);
        }
    }

    public void topic1Selected(ActionEvent event) {
        if (controller != null) {
            controller.topicSelected(1);
        }
    }

    public void topic2Selected(ActionEvent event) {
        if (controller != null) {
            controller.topicSelected(2);
        }
    }

    public void topic3Selected(ActionEvent event) {
        if (controller != null) {
            controller.topicSelected(3);
        }
    }

    public void setController(PhotoController controller) {
        this.controller = controller;
    }

    public void show(Photo photo) {
        // see: http://java-buddy.blogspot.co.uk/2013/01/convert-javaawtimagebufferedimage-to.html
        imageView.setImage(SwingFXUtils.toFXImage(photo.getImage(), null));
    }
 }
