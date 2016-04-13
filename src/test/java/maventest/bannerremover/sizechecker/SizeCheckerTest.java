/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover.sizechecker;

import maventest.bannerremover.sizechecker.SizeChecker;
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
        this.targetInstance.setSize(new ImageSize(76, 110));
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

    /**
     * Test of getSize method, of class SizeChecker.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        SizeChecker instance = this.targetInstance;
        ImageSize expResult = new ImageSize(76, 110);
        ImageSize result = instance.getSize();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSize method, of class SizeChecker.
     */
    @Test
    public void testSetSize() {
        System.out.println("setSize");
        int Height = 110;
        int Width = 76;
        SizeChecker instance = new SizeChecker(this.fl.getImageList());
        instance.setSize(new ImageSize(Height, Width));
    }

}
