/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template targetFile1, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover.FileSeeker;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author normal
 */
public class ImageReaderFileFilterTest {

    private final File targetFile1 = new File("testdata/target.txt");
    private final File targetFile2 = new File("testdata/target.jpg");

    public ImageReaderFileFilterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of accept method, of class ImageReaderFileFilter.
     */
    @Test
    public void testAccept_File_false() {
        System.out.println("accept_false");
        File file = this.targetFile1;
        ImageReaderFileFilter instance = new ImageReaderFileFilter();
        boolean expResult = false;
        boolean result = instance.accept(file);
        assertEquals(expResult, result);
    }

    /**
     * Test of accept method, of class ImageReaderFileFilter.
     */
    @Test
    public void testAccept_File_true() {
        System.out.println("accept_true");
        File file = this.targetFile2;
        ImageReaderFileFilter instance = new ImageReaderFileFilter();
        boolean expResult = true;
        boolean result = instance.accept(file);
        assertEquals(expResult, result);
    }

    /**
     * Test of getExtension method, of class ImageReaderFileFilter.
     */
    @Test
    public void testGetExtension_String() {
        System.out.println("getExtension");
        String fileName = "xxx.txt";
        ImageReaderFileFilter instance = new ImageReaderFileFilter();
        String expResult = "txt";
        String result = instance.getExtension(fileName);
        assertEquals(expResult, result);
    }

    /**
     * Test of getExtension method, of class ImageReaderFileFilter.
     */
    @Test
    public void testGetExtension_File() {
        System.out.println("getExtension");
        File filePath = targetFile1;
        ImageReaderFileFilter instance = new ImageReaderFileFilter();
        String expResult = "txt";
        String result = instance.getExtension(filePath);
        assertEquals(expResult, result);
    }

    /**
     * Test of accept method, of class ImageReaderFileFilter.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testAccept_File_String() {
        System.out.println("accept");
        File file = this.targetFile1;
        String string = "";
        ImageReaderFileFilter instance = new ImageReaderFileFilter();
        boolean expResult = false;
        boolean result = instance.accept(file, string);
        assertEquals(expResult, result);
    }

}
