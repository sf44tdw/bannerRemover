/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover.sizechecker;

import common.JpgFileList;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
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
        Set<ImageSize> temp = new HashSet<>();
        temp.add(new ImageSize(76, 110));
        this.targetInstance.setSizes(temp);
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
        Set<File> expResult = new HashSet<>();
        expResult.add(this.fl.getF5());
        Set<File> result = instance.makeList();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSizes method, of class SizeChecker.
     */
    @Test
    public void testGetSizes() {
        System.out.println("getSizes");
        SizeChecker instance = this.targetInstance;
        Set<ImageSize> expResult = new HashSet<>();
        expResult.add(new ImageSize(76, 110));
        Set<ImageSize> result = instance.getSizes();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSizes method, of class SizeChecker.
     */
    @Test
    public void testSetSizes() {
        System.out.println("setSizes");
        Set<ImageSize> sizes = new HashSet<>();
        sizes.add(new ImageSize(76, 110));
        SizeChecker instance = new SizeChecker(this.fl.getImageList());
        instance.setSizes(sizes);

    }

}
