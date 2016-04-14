/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover.config;

import common.JpgFileList;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import maventest.bannerremover.sizechecker.ImageSize;
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
public class ConfigValueCheckerTest {

    private final Set<ImageSize> temp;

    private final JpgFileList fl;
    private final ConfigValueChecker conf;

    public ConfigValueCheckerTest() {
        temp = new HashSet<>();
        temp.add(new ImageSize(76, 110));
        fl = new JpgFileList();
        conf = new ConfigValueChecker(this.fl.getSourceDir(), new File("testdata"), true, temp);
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

    @Test(expected = IllegalArgumentException.class)
    public void construct1() {
        System.out.println("検索先がディレクトリではないか、存在しない");
        ConfigValueChecker instance = new ConfigValueChecker(new File("djwu2se"), new File("testdata"), true, temp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct2() {
        System.out.println("移動先がディレクトリではないか、存在しない");
        ConfigValueChecker instance = new ConfigValueChecker(this.fl.getSourceDir(), new File("djwu2se"), true, temp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct3() {
        System.out.println("送り側と受け側のディレクトリが同じ");
        ConfigValueChecker instance = new ConfigValueChecker(this.fl.getSourceDir(), this.fl.getSourceDir(), true, temp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct4() {
        System.out.println("画像サイズの設定がされていない");
        ConfigValueChecker instance = new ConfigValueChecker(this.fl.getSourceDir(), new File("testdata"), true, new HashSet<ImageSize>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct5() {
        System.out.println("画像サイズの設定がされていない");
        ConfigValueChecker instance = new ConfigValueChecker(this.fl.getSourceDir(), new File("testdata"), true, null);
    }

    /**
     * Test of getTargetDir method, of class ConfigValueChecker.
     */
    @Test
    public void testGetTargetDir() {
        System.out.println("getTargetDir");
        ConfigValueChecker instance = this.conf;
        File expResult = this.fl.getSourceDir();
        File result = instance.getTargetDir();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDestDir method, of class ConfigValueChecker.
     */
    @Test
    public void testGetDestDir() {
        System.out.println("getDestDir");
        ConfigValueChecker instance = this.conf;
        File expResult = new File(new File("testdata").getAbsolutePath());
        File result = instance.getDestDir();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSizes method, of class ConfigValueChecker.
     */
    @Test
    public void testGetSizes() {
        System.out.println("getSizes");
        ConfigValueChecker instance = this.conf;
        Set<ImageSize> expResult = new HashSet<>();
        expResult.add(new ImageSize(76, 110));
        Set<ImageSize> result = instance.getSizes();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class ConfigValueChecker.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        ConfigValueChecker instance = this.conf;
        System.out.println(instance.toString());
    }

    /**
     * Test of isRecursive method, of class ConfigValueChecker.
     */
    @Test
    public void testIsRecursive() {
        System.out.println("isRecursive");
        ConfigValueChecker instance = this.conf;
        boolean expResult = true;
        boolean result = instance.isRecursive();
        assertEquals(expResult, result);
    }

}
