package ijp;

import javafx.application.Application;
import javafx.stage.Stage;

import ijp.viewer.JavaFxViewer;
import ijp.finder.FlickrPhotoFinder;

/**
 * The main program for IJP Assignment1.
 *
 * @author  Paul Anderson &lt;dcspaul@ed.ac.uk&gt;
 * @version 09:45 08 Oct 2014
 */
public class MainProgram extends Application {

    /**
     * Start the application.
     *
     * @param primaryStage the JavaFx primary stage
     */
    @Override
    public void start(Stage primaryStage) {
    	    
    	/* create the viewer
    	 * we can easily use different viewer implementations without changing
    	 * any of the other code. eg. by uncommenting the corresponding
    	 * line here, we can have a menu-based, or a button-based interface
    	 */
    	PhotoViewer viewer = JavaFxViewer.create( this.getClass(), primaryStage, "MenuViewer" );
    	// PhotoViewer viewer = Viewer.create( this.getClass(), primaryStage, "MenuViewer" );
    	
        /* create a finder for locating photos on Flickr 
       	 * notice that we could use a completely different service 
    	 * by specifying a different finder class here.
         * you must obtain and fill in this key before the program will work
         * obtain your own key from here: http://bit.ly/8B4ql0
    	 */
        // PhotoFinder finder = new FlickrPhotoFinder("INSERT_YOUR_FLICKR_API_KEY_HERE");
        PhotoFinder finder = new FlickrPhotoFinder();

         /* create a controller
         * we can easily use different controller implementations without changing
         * any of the other code. simply change the controller class here. Eg. one
         * of the commented out lines can be use to try out different controllers 
         * for the solutions to the various parts of the exercise.
         */
        //PhotoController controller = new ijp.controller.SimplePhotoController(finder, viewer);
        // PhotoController controller = new ijp.controller.PhotoController1(finder, viewer);
        // PhotoController controller = new ijp.controller.PhotoController2(finder, viewer);
        // PhotoController controller = new ijp.controller.PhotoController3(finder, viewer);
        // PhotoController controller = new ijp.controller.PhotoController4(finder, viewer);
         PhotoController controller = new ijp.controller.PhotoController3(finder, viewer);
 
        /* tell the viewer about the controller
         * so that it knows who to tell about button presses
         */
        viewer.setController(controller);

        /* initialize the controller
         * eg. put the labels on the buttons
         */
        controller.initialize();
        
        /* tell the viewer to display the interface on the primary stage
         * and start processing events (eg. buttonpresses)
         */
        viewer.start(primaryStage); 
    }

    /**
     * Launch the application 
     *
     * @param args the command line arguments (not used)
     */
    public static void main(String args[]) {
    	
    	/* JavaFx programs should call the "launch" method when they are started
    	 * this will create the main window and then call our "start" method
    	 */
    	launch(args);
    }
}