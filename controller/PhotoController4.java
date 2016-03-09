package ijp.controller;

import ijp.Photo;
import ijp.PhotoFinder;
import ijp.PhotoViewer;
import ijp.PhotoController;
import java.util.ArrayList;

/**
 * A simple controller for IJP Assignment 1 (for you to copy and modify).
 * This trivial controller includes a simple demonstration of the
 * photo viewer which will display a single photograph.
 *
 * @author  Paul Anderson &lt;dcspaul@ed.ac.uk&gt;
 * @version 16:40 25 Sep 2014
 */
public class PhotoController4 implements PhotoController {

    // The finder object for locating photos from the photo service (eg. Flickr)
    private PhotoFinder finder;
    // the viewer object for displaying photos in a window
    private PhotoViewer viewer;
    String selectedTopic[] = new String[3];                     //selectedTopic[]- Set of topics that will be given to Flikr, stored like this to make modification easy.
    ArrayList<ArrayList<Photo>> photoArray= new ArrayList<ArrayList<Photo>>(3);     //photoArray[][]- A matrix of photos, each topic has a row. This structure was chosen to make the code more efficient.
    int topicCounter[]= new int[3];                             //topicCounter[]- An array, it's size equals the number of topics, this array keeps track of the number of photos in each topic.           
    int loadCheck[][]= new int[3][20];                          //loadCheck[][]- A matrix that keeps track of random numbers that have been generated while loading pictures.
    int displayCheck[][]= new int[3][20];                       //displayCheck[][]- A matrix that keeps track of the random numbers that have been generated while displaying pictures
                                                                
    /**
     * Create a new PhotoController.
     *
     * @param finder the photo finder object to locate images from the photo service (eg. Flickr)
     * @param viewer the photo viewer object to display images in a window
     */
    public PhotoController4(PhotoFinder finder, PhotoViewer viewer) {

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
        // set up the caption for one button or menu item
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
    
    public void setCounters()                                   //This function is can be called during initialization, but isn't necessary. It sets all picture counts to zero.
    {
        topicCounter[0]=0;                                      
        topicCounter[1]=0;
        topicCounter[2]=0;
    }
    
    public void loadPhotos()                                    //This function loads photos, it is called initially to reduce delays
    {
        java.util.Random r = new java.util.Random();            //Use the Java class for random numbers.
        int randomNumber;                                       //Initialize one random number for selecting which topic to load a picture of.
        int randomNumber2;                                      //Initialize another random number for selecting which picture in that topic to load.
                                                                //The following part ensures that atleast 2 pictures for all topics are loaded
        photoArray.add(new ArrayList<Photo>());
        photoArray.add(new ArrayList<Photo>());
        photoArray.add(new ArrayList<Photo>());
        
        for (int i= 0 ; i< 3 ; i++)                             //The outer loop controls the topics, there are 3 iterations
        {
            for (int j =0; j <2; j++)                           //2 pictures for each topic so 2 iterations
            {
               randomNumber2= r.nextInt(20);                    //Randomly pick a number from 0-19
               photoArray.get(i).add(topicCounter[i], finder.find(selectedTopic[i], (randomNumber2+1)));              //Load the picture mapped to by randomNumber2
               int trialCounter=1;
               while (((photoArray.get(i).get(topicCounter[i]) == null)||  (search(i, randomNumber2))) && (trialCounter <= 15))    //Ensure that the picture is not null and is not repeated, if it is then keep loading pictures till 15 trials are over
               {
                   randomNumber2= r.nextInt(20);                //Generate another random number
                   photoArray.get(i).add(topicCounter[i], finder.find(selectedTopic[i], (randomNumber2+1)));        //Load this picture
                   trialCounter++;                                //increment the trial counter
               }
               loadCheck[i][randomNumber2]=1;                   //Add this random number to the list of random numbers picked
               //System.out.println("Random Number "+ (randomNumber2+1)+" of topic "+i);
               topicCounter[i]++;                               //Increment this marker to the next position in the list of pictures for this topic
            }
           
        }
                                                                //After ensuring that each topic has 2 pictures, the remaining pictures we load at random
        for (int i= 0 ; i< 14 ; i++)
        {
           randomNumber= r.nextInt(3);                          //Randomly pick a topic number
           randomNumber2= r.nextInt(20);                        //Randomly pick a picture in that topic
           photoArray.get(randomNumber).add(topicCounter[randomNumber], finder.find(selectedTopic[randomNumber], (randomNumber2+1))); 
           int trialCounter=1;                                  //Counts how many attempts were made at loading the picture.
           while (((photoArray.get(randomNumber).get(topicCounter[randomNumber]) == null)||  (search(randomNumber, randomNumber2))) && (trialCounter <= 15)) //Has this number been used? Does it give an empty picture? If yes, load another one. Keep doing this till 15 trials get over. Then give up.
           {
               randomNumber2= r.nextInt(20);                    //
               photoArray.get(randomNumber).add(topicCounter[randomNumber], finder.find(selectedTopic[randomNumber], (randomNumber2+1))); 
               trialCounter++;                 
           }
           loadCheck[randomNumber][randomNumber2]=1;
           //System.out.println("Random Number"+ (randomNumber2+1)+" of topic "+randomNumber);
           topicCounter[randomNumber]++;
        }
        for (int j=0; j <3; j++)
        {
            //System.out.println(topicCounter[j]);
        }
    }       
    
    public boolean search(int index, int check)
    {
        if (loadCheck[index][check] == 1)
            return true;
        else 
            return false;
    } 
    
    public boolean search1(int index, int check)
    {
        if (displayCheck[index][check]==1)
            return true;
        else
            return false;
    }
    
    public Photo findPhoto(int topicIndex)
    {
       java.util.Random r = new java.util.Random();
       int randomNumber= r.nextInt(topicCounter[topicIndex]);
       Photo photoReturn = photoArray.get(topicIndex).get(randomNumber);
       int trialCounter=0;
       while ((search1(topicIndex, randomNumber))&&(trialCounter<=15))
       {
           randomNumber= r.nextInt(topicCounter[topicIndex]);
           photoReturn = photoArray.get(topicIndex).get(randomNumber); 
           trialCounter++;
       }
       displayCheck[topicIndex][randomNumber]=1;
       return photoReturn;
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
        // fetch an image of the Forum from Flickr
        Photo photo= findPhoto((topicNumber-1)%3);
        // if the image was successfully retrieved, display it
        // do nothing if there was an error fetching the image
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
