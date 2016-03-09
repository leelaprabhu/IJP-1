package ijp.controller;

import ijp.Photo;
import ijp.PhotoFinder;
import ijp.PhotoViewer;
import ijp.PhotoController;
import java.util.ArrayList;

/**
 * PhotoController3 for IJP Assignment- I.
 * Displays random photos of three different locations that can be selected from a drop down menu.
 * @author  Paul Anderson &lt;dcspaul@ed.ac.uk&gt, Leela Prabhu (S1471625);
 * @version 21:00 22 Oct 2014
 */
public class PhotoController3 implements PhotoController {

    private PhotoFinder finder;                                 // The finder object for locating photos from the photo service (eg. Flickr)
    private PhotoViewer viewer;                                 // the viewer object for displaying photos in a window
    /**
     * selectedTopic[]- Set of topics that will be given to Flikr, stored like this to make modification easy.
     */
    String selectedTopic[] = new String[3];                     //selectedTopic[]- Set of topics that will be given to Flikr, stored like this to make modification easy.
    /**
     * photoArray- A 2D ArrayList of photos, each topic has a row. This structure was chosen to make the code more efficient.
     */
    ArrayList<ArrayList<Photo>> photoArray= new ArrayList<ArrayList<Photo>>(3);     //photoArray[][]- A matrix of photos, each topic has a row. This structure was chosen to make the code more efficient.
    /**
     * topicCounter[]- An array, it's size equals the number of topics, this array keeps track of the number of photos in each topic.
     */
    int topicCounter[]= new int[3];                             //topicCounter[]- An array, it's size equals the number of topics, this array keeps track of the number of photos in each topic.           
    /**
     * loadCheck[][]- A matrix that keeps track of random numbers that have been generated while loading pictures.
     */
    int loadCheck[][]= new int[3][40];                          //loadCheck[][]- A matrix that keeps track of random numbers that have been generated while loading pictures.
    /**
     * displayCheck[][]- A matrix that keeps track of the random numbers that have been generated while displaying pictures
     */
    int displayCheck[][]= new int[3][30];                       //displayCheck[][]- A matrix that keeps track of the random numbers that have been generated while displaying pictures
                                                               
    public PhotoController3(PhotoFinder finder, PhotoViewer viewer) {
        this.finder = finder;
        this.viewer = viewer;
    }

    /**
     * The intializing function, it sets the topic Strings, captions and loads photos.
     */
    @Override
    public void initialize() {                                  //The intializing function, it sets the topic Strings, captions and loads photos.
        setTopics();                                            //This sets the topic Strings
        for (int i=0; i <3; i ++)                               //This loop sets the captions for the buttons
        {
            viewer.setCaption(i+1, selectedTopic[i]);
        }
        loadPhotos();                                           //This loads photos initially and not after each click so that the response time after each click is less
    }
    
    /**
     * This function is called during initialization, initialize(), it maps a String tag to a number so that it can be easily refered to.
     */
    public void setTopics()                                     //This function is called during initialization, initialize(), it maps a String tag to a number so that it can be easily refered to
    {
       selectedTopic[0]= "Edinburgh Informatics Building";      //Add a String caption corresponding to the 1st button
       selectedTopic[1]= "Edinburgh Main Library";              //Add a String caption corresponding to the 2nd button
       selectedTopic[2]= "Edinburgh Appleton Tower";            //Add a String caption corresponding to the 3rd button
    }
    
    /**
     * This function is can be called during initialization, but isn't necessary. It sets all picture counts to zero.
     */
    public void setCounters()                                   //This function is can be called during initialization, but isn't necessary. It sets all picture counts to zero.
    {
        topicCounter[0]=0;                                      
        topicCounter[1]=0;
        topicCounter[2]=0;
    }
    
    /**
     * Loads individual photos while ensuring there are no repeats, the topic number, t, is passed as a paramenter. It is called by loadPhotos().
     */
    public void photoLoader(int t)                              //Loads individual photos while ensuring there are no repeats, the topic number, t, is passed as a paramenter. It is called by loadPhotos().
    {      /**
           * Use the Java class for random numbers.
           */
           java.util.Random r = new java.util.Random();         //Use the Java class for random numbers.
           int randomNumber2;                                   //Initialize a random number for selecting which picture in that topic to load.
           randomNumber2= r.nextInt(40);                        //Randomly pick a number from 0-39
           photoArray.get(t).add(topicCounter[t], finder.find(selectedTopic[t], (randomNumber2+1)));              //Load the picture mapped to by randomNumber2
           int trialCounter=1;
           while (((photoArray.get(t).get(topicCounter[t]) == null)||  (search(t, randomNumber2))) && (trialCounter <= 15))    //Ensure that the picture is not null and is not repeated, if it is then keep loading pictures till 15 trials are over
           {
               randomNumber2= r.nextInt(40);                    //Generate another random number
               photoArray.get(t).add(topicCounter[t], finder.find(selectedTopic[t], (randomNumber2+1)));        //Load this picture
               trialCounter++;                                  //increment the trial counter
           }
           loadCheck[t][randomNumber2]=1;                       //Add this random number to the list of random numbers picked
           //FOR DEBUG:- System.out.println("Random Number "+ (randomNumber2+1)+" of topic "+i);
           topicCounter[t]++;                                   //Increment this marker to the next position in the list of pictures for this topic
    }
    
    /**
     * This function loads 30 photos (5 of each topic, 15 of random topics and all photo numbers are random), it is called initially to reduce delays between clicks.
     */
    public void loadPhotos()                                    //This function loads 30 photos, it is called initially to reduce delays between clicks.
    {
        java.util.Random r = new java.util.Random();            //Use the Java class for random numbers.
        int randomNumber;                                       //Initialize one random number for selecting which topic to load a picture of.
                                                                //The following part ensures that atleast 5 pictures for all topics are loaded
        for (int i= 0 ; i< 3 ; i++)                             //The outer loop controls the topics, there are 3 iterations
        {   
            for (int j=0; j<5; j++)                             //The inner loop controls how many pictures per topic, here 5. 
            {
                photoArray.add(new ArrayList<Photo>());
                photoLoader(i);
            }
        }
                                                                //After ensuring that each topic has 2 pictures, the remaining pictures we load at random
        for (int i= 0 ; i< 15 ; i++)
        {
           randomNumber= r.nextInt(3);                          //Randomly pick a topic number
           photoLoader(randomNumber);
        }
        /*FOR DEBUG:- for (int j=0; j <3; j++)
        {
            //System.out.println(topicCounter[j]);
        }*/
    }       
    
    /**
     * Checks whether a given photo has been downloaded or not. The topic number of the photo to be checked and the photo number must be passed.
     *
     * @param index The topic number 
     * @param check The photo number
     */
    public boolean search(int index, int check)                 //Checks whether a given photo has been downloaded or not. 
    {
        if (loadCheck[index][check] == 1)
            return true;
        else 
            return false;
    } 
    
    /**
     * Checks whether a given photo has been viewed or not. The topic number of the photo to be checked and the photo number must be passed.
     *
     * @param index The topic number 
     * @param check The photo number
     */
    public boolean search1(int index, int check)                //Checks whether a given photo has been viewed or not. 
    {
        if (displayCheck[index][check]==1)
            return true;
        else
            return false;
    }
    
    /**
     * After photos have been loaded and GUI started, this displays random photos from the downloaded set. The topic number must be passed.
     *
     * @param topicIndex the index of the selected topic.
     */
    public Photo findPhoto(int topicIndex)                      //After photos have been loaded and GUI started, this displays random photos from the downloaded set.  
    {
       java.util.Random r = new java.util.Random();             
       int randomNumber= r.nextInt(topicCounter[topicIndex]);                   //Intialize a random number to select which photo to pick from the downloaded pictures, given a topic number.
       Photo photoReturn = photoArray.get(topicIndex).get(randomNumber);        //Get this photo.
       int trialCounter=0;
       while ((search1(topicIndex, randomNumber))&&(trialCounter<=15))          //Check if this photo has been viewed before, maximum number of trials is 15.
       {
           randomNumber= r.nextInt(topicCounter[topicIndex]);                   //Again generate a random number between 0 and the length of that topic's list. 
           photoReturn = photoArray.get(topicIndex).get(randomNumber); 
           trialCounter++;
       }
       displayCheck[topicIndex][randomNumber]=1;                                //Keep track of this viewed photo
       return photoReturn;                                                      //Return the photo
    }
    
    /**
     * This handles selection of a topic given the topic index number, if the photo is not null, it displays it.
     *
     * @param topicNumber the topic number
     */
    @Override
    public void topicSelected(int topicNumber) {
        Photo photo= findPhoto((topicNumber-1)%3);      //Find a photo of the given topic
        if (photo != null) {                            //if it exists, display it
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