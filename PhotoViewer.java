package ijp;

import javafx.stage.Stage;

/**
 * An object for displaying photos and accepting user interaction.
 *
 * @author  Paul Anderson &lt;dcspaul@ed.ac.uk&gt;
 * @version 16:40 25 Sep 2014
 */
public interface PhotoViewer {

    /**
     * Set the controller for the window. The <code>topicSelected(int subjectNumber)</code>
     * method in the controller will be called when a topic is selected in the interface.
     * The <code>exit()</code> method will be called when the user closes the window.
     * If no controller is specified, then selecting topics will have no effect.
     *
     * @param controller the <code>PhotoController</code> which will receive commands
     * from the interface.
     */
    public void setController(PhotoController controller);

    /**
     * Sets the caption for selecting an image of the specified topic.
     * If the caption is empty, then the selector (button/menu item, etc.)
     * is made invisible. If the number is out of range, it is ignored.
     *
     * @param topicNumber the topic number
     * @param caption the caption string
     */
    public void setCaption(int topicNumber, String caption);

    /**
     * Starts displaying the viewer on the specified stage.
     *
     * @param stage the JavaFx stage to display the viewer
     */
    public void start(Stage stage);
 
    /**
     * Shows the specified photo in the window.
     *
     * @param photo the photo to show
     */
    public void show(Photo photo);
}
