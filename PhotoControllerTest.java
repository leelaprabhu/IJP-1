package ijp;
import javafx.stage.Stage;

import org.junit.Test;

import ijp.PhotoViewer;
import ijp.finder.FlickrPhotoFinder;
import ijp.controller.PhotoController5;

public class PhotoControllerTest extends junit.framework.TestCase implements PhotoViewer {

    private String displayedPhotoSubject;
    java.awt.image.BufferedImage displayedPhoto;
    java.awt.image.BufferedImage testRepeat[]= new java.awt.image.BufferedImage[2];
    String selectedTopic[]= new String[3];
    @Override
    public void setController(PhotoController controller) {
        // ignored
    }

    @Override
    public void setCaption(int n, String caption) {
        // ignored
    }

    @Override
    public void show(Photo photo) {
        // this method will be called by the controller under test
        // when it is wants to display a photo on the screen.
        // we pretend to be the viewer and just save the subject
        // of the photo, so that we can check it later.
        displayedPhotoSubject = photo.getTopic();
        displayedPhoto=photo.getImage();
    }
    
    @Override
    public void start(Stage stage) {
        // ignored
    }

    @Test
    public void test1() {
        PhotoViewer viewer = this;
        
        PhotoFinder finder = new FlickrPhotoFinder();
        PhotoController controller = new PhotoController5(finder, viewer);  
        viewer.setController(controller);
        controller.initialize();
        controller.topicSelected(1);
        assertEquals("Edinburgh Informatics Building",displayedPhotoSubject);  
    }
    
    public void topicInitialize()
    {
       selectedTopic[0]= "Edinburgh Informatics Building";
       selectedTopic[1]= "Edinburgh Main Library";
       selectedTopic[2]= "Edinburgh Appleton Tower"; 
    }
    
    @Test
    public void test2()
    {
        PhotoViewer viewer = this;
        PhotoFinder finder = new FlickrPhotoFinder();
        PhotoController controller = new PhotoController5(finder, viewer);  
        viewer.setController(controller);
        controller.initialize();
        topicInitialize();
        for (int i= 0; i< 3; i++)
        {
            controller.topicSelected((i+1));
            assertEquals(selectedTopic[i],displayedPhotoSubject); 
        } 
    }
    
    @Test
    public void test3() //this test should fail. disconnnect net
    {
        PhotoViewer viewer = this;
        PhotoFinder finder = new FlickrPhotoFinder();
        PhotoController controller = new PhotoController5(finder, viewer);  
        viewer.setController(controller);
        controller.initialize();
        topicInitialize();
        for (int i= 0; i< 3; i++)
        {
            controller.topicSelected((i+1));
            assertNotNull(displayedPhotoSubject); 
        } 
    }
    
    @Test
    public void test9(){
        String text="Junction";
        assertTrue(text.equals("Junction"));
    }
    
    @Test
    public void test4() //This test checks if two consecutively loaded images are different, thereby checking that it doesn't repeat. 
    {
        PhotoViewer viewer = this;
        PhotoFinder finder = new FlickrPhotoFinder();
        PhotoController controller = new PhotoController5(finder, viewer);  
        viewer.setController(controller);
        controller.initialize();
        topicInitialize();
        for (int j=0; j<1; j++)
        {
            for (int i= 0; i< 3; i++)
            {
                controller.topicSelected((i+1));
                testRepeat[0]= displayedPhoto;
                controller.topicSelected((i+1));
                testRepeat[1]= displayedPhoto;
                assertNotSame(testRepeat[0], testRepeat[1]);
            } 
        }
    }
    
    @Test
    public void test5() //This test checks if two consecutively loaded images are different, thereby checking that it doesn't repeat. 
    {
        PhotoViewer viewer = this;
        PhotoFinder finder = new FlickrPhotoFinder();
        PhotoController controller = new PhotoController5(finder, viewer);  
        viewer.setController(controller);
        controller.initialize();
        topicInitialize();
        for (int j=0; j<10; j++)
        {
            for (int i= 0; i< 3; i++)
            {
                controller.topicSelected((i+1));
                testRepeat[0]= displayedPhoto;
                controller.topicSelected((i+1));
                testRepeat[1]= displayedPhoto;
                assertNotSame(testRepeat[0], testRepeat[1]);
            } 
        }
    }
    
    @Test
    public void test6() //This test checks if two consecutively loaded images are different, thereby checking that it doesn't repeat. 
    {
        PhotoViewer viewer = this;
        PhotoFinder finder = new FlickrPhotoFinder();
        PhotoController controller = new PhotoController5(finder, viewer);  
        viewer.setController(controller);
        controller.initialize();
        topicInitialize();
        controller.topicSelected(5);
        assertNotNull(displayedPhotoSubject);
        controller.topicSelected(-2);
        assertNotNull(displayedPhotoSubject); 
    }
}
