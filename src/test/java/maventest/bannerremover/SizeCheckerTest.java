/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover;

import common.JpgFileList;
import java.io.File;
import java.util.ArrayList;
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
public class SizeCheckerTest {

    private final JpgFileList fl = new JpgFileList();

    private final SizeChecker targetInstance;

    public SizeCheckerTest() {


        this.targetInstance = new SizeChecker(this.fl.getImageList());

        //f5
        this.targetInstance.setPixelSize(76, 110);
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
     * Test of setPixelSize method, of class SizeChecker.
     */
    @Test
    public void testSetPixelSize() {
        System.out.println("setPixelSize");
        int Height = 110;
        int Width = 76;
        SizeChecker instance = new SizeChecker(this.fl.getImageList());
        instance.setPixelSize(Height, Width);
    }

    /**
     * Test of getHeight method, of class SizeChecker.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        SizeChecker instance = new SizeChecker(this.fl.getImageList());
        instance.setPixelSize(111, 222);
        int expResult = 111;
        int result = instance.getHeight();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWidth method, of class SizeChecker.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        SizeChecker instance = new SizeChecker(this.fl.getImageList());
        instance.setPixelSize(111, 222);
        int expResult = 222;
        int result = instance.getWidth();
        assertEquals(expResult, result);
    }

    /**
     * Test of makeList method, of class SizeChecker.
     */
    @Test
    public void testMakeList() {
        System.out.println("makeList");
        SizeChecker instance = this.targetInstance;
        List<File> expResult = new ArrayList<>();
        expResult.add(this.fl.getF5());
        List<File> result = instance.makeList();
        assertEquals(expResult, result);
    }

}
