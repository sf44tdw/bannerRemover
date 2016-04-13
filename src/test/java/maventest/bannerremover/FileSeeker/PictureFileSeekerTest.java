/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover.FileSeeker;

import common.JpgFileList;
import java.io.File;
import java.util.List;
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
public class PictureFileSeekerTest {

    private final JpgFileList fl = new JpgFileList();

    public PictureFileSeekerTest() {
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
     * Test of isRecursive method, of class PictureFileSeeker.
     */
    @Test
    public void testIsRecursive() {
        System.out.println("isRecursive");
        PictureFileSeeker instance = new PictureFileSeeker(this.fl.getSourceDir());
        boolean expResult = true;
        boolean result = instance.isRecursive();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRecursive method, of class PictureFileSeeker.
     */
    @Test
    public void testSetRecursive() {
        System.out.println("setRecursive");
        boolean recursive = false;
        PictureFileSeeker instance = new PictureFileSeeker(this.fl.getSourceDir());
        instance.setRecursive(recursive);
        boolean result = instance.isRecursive();
        assertEquals(recursive, result);
    }

    /**
     * Test of seek method, of class PictureFileSeeker.
     */
    @Test
    public void testSeek() {
        System.out.println("seek");
        PictureFileSeeker instance = new PictureFileSeeker(this.fl.getSourceDir());
        List<File> expResult = this.fl.getImageList();
        List<File> result = instance.seek();
        assertEquals(expResult, result);
    }

}
