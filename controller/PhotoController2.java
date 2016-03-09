package ijp.controller;

import ijp.Photo;
import ijp.PhotoFinder;
import ijp.PhotoViewer;
import ijp.PhotoController;

/**
 * A simple controller for IJP Assignment 1 (for you to copy and modify).
 * This trivial controller includes a simple demonstration of the
 * photo viewer which will display a single photograph.
 *
 * @author  Paul Anderson &lt;dcspaul@ed.ac.uk&gt;
 * @version 16:40 25 Sep 2014
 */
public class PhotoController2 implements PhotoController {
    private PhotoFinder finder;
    private PhotoViewer viewer;
    String selectedTopic[] = new String[3];                 //selectedTopic[]- Set of topics that will be given to Flikr, stored like this to make modification easy.
    Photo photoArray[] = new Photo[3];                      //photoArray[]- An array of photos. The are loaded during intialization to reduce response time.
    
    /**
     * Create a new PhotoController.
     *
     * @param finder the photo finder object to locate images from the photo service (eg. Flickr)
     * @param viewer the photo viewer object to display images in a window
     */
    public PhotoController2(PhotoFinder finder, PhotoViewer viewer) {

        // save the finder and the viewer as instance variables
        // so that we have access to them in the other methods
        this.finder = finder;
        this.viewer = viewer;
    }

    /**
     * Initialize the controller.
     * In this simple case, we set the caption on a single selector (eg. button or menu item)
     * (So only the first selector will be enabled)
     */
    @Override
    public void initialize() {
        setTopics();                                            //This sets the topic Strings
        for (int i=0; i <3; i ++)                               //This sets the captions for the buttons
        {
            viewer.setCaption(i+1, selectedTopic[i]);
        }
        loadPhotos();                                           //This loads photos initially and not after each click so that the response time after each click is less
    }
    
    public void setTopics()                                     //This function is called during initialization, initialize(), it maps a String tag to a number so that it can be easily refered to
    {
       selectedTopic[0]= "Edinburgh Informatics Building";      //Add a String caption corresponding to the 1st button
       selectedTopic[1]= "Edinburgh Main Library";              //Add a String caption corresponding to the 2nd button
       selectedTopic[2]= "Edinburgh Appleton Tower";            //Add a String caption corresponding to the 3rd button
    }
    
    public void loadPhotos()
    {
        for (int i= 0; i < 3; i++)
        {
          photoArray[i]= finder.find(selectedTopic[i], 5);  
        }
    }
    
    /**
     * Handle a topic selection from the interface. This simple controller
     * retrieves an image of the Informatics Forum and displays it in the
     * viewer. Notice that only one selector has been enabled, so the topic
     * number can be ignored (it will always be 1).
     *
     * @param n the topic number
     */
    @Override
    public void topicSelected(int topicNumber) {
        String selectedTopic = new String();
        // fetch an image of the Forum from Flickr
        //Photo photo = finder.find("edinburgh informatics forum", 5);
        //Photo photo = finder.find(selectedTopic[0], 5);
        // if the image was successfully retrieved, display it
        // do nothing if there was an error fetching the image
        Photo photo = photoArray[topicNumber-1];
        if (photo != null) {
            viewer.show(photo);
        }
    }

    /**
     * Handle the "Close" button on the interface window.
     * Exits the application.
     */
    @Override
    public void exit() {
        System.exit(0);
    }
}
