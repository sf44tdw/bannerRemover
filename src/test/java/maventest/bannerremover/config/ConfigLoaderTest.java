/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover.config;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import maventest.bannerremover.sizechecker.ImageSize_IM;
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
public class ConfigLoaderTest {

    private final File sourceDir = new File("testdata/SampleConfig");
    private final File normalFile = new File(this.sourceDir, "normal.toml");

    public ConfigLoaderTest() {
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
        ConfigLoader instance = new ConfigLoader(new File(this.sourceDir, "nosrc.toml"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct2() {
        System.out.println("移動先がディレクトリではないか、存在しない");
        ConfigLoader instance = new ConfigLoader(new File(this.sourceDir, "nodest.toml"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct3() {
        System.out.println("送り側と受け側のディレクトリが同じ");
        ConfigLoader instance = new ConfigLoader(new File(this.sourceDir, "samedir.toml"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct4() {
        System.out.println("画像サイズの設定がされていない");
        ConfigLoader instance = new ConfigLoader(new File(this.sourceDir, "nosizes.toml"));
    }

    /**
     * Test of getSourceDir method, of class ConfigLoader.
     */
    @Test
    public void testGetSourceDir() {
        System.out.println("getSourceDir");
        ConfigLoader instance = new ConfigLoader(this.normalFile);
        File expResult = new File("testdata/dummysrc");
        File result = instance.getSourceDir();
        assertEquals(expResult.getAbsolutePath(), result.getAbsolutePath());
    }

    /**
     * Test of getDestDir method, of class ConfigLoader.
     */
    @Test
    public void testGetDestDir() {
        System.out.println("getDestDir");
        ConfigLoader instance = new ConfigLoader(this.normalFile);
        File expResult = new File("testdata/dummydest");
        File result = instance.getDestDir();
        assertEquals(expResult.getAbsolutePath(), result.getAbsolutePath());
    }

    /**
     * Test of isRecursive method, of class ConfigLoader.
     */
    @Test
    public void testIsRecursive_normal() {
        System.out.println("isRecursive_normal");
        ConfigLoader instance = new ConfigLoader(this.normalFile);
        boolean expResult = true;
        boolean result = instance.isRecursive();
        assertEquals(expResult, result);
    }

    /**
     * Test of isRecursive method, of class ConfigLoader.
     */
    @Test
    public void testIsRecursive_noparam() {
        System.out.println("isRecursive_noparam");
        ConfigLoader instance = new ConfigLoader(new File(this.sourceDir, "norecursive.toml"));
        boolean expResult = true;
        boolean result = instance.isRecursive();
        assertEquals(expResult, result);
    }

    /**
     * Test of isRecursive method, of class ConfigLoader.
     */
    @Test
    public void testIsRecursive_false() {
        System.out.println("isRecursive_false");
        ConfigLoader instance = new ConfigLoader(new File(this.sourceDir, "falserecursive.toml"));
        boolean expResult = false;
        boolean result = instance.isRecursive();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSizes method, of class ConfigLoader.
     */
    @Test
    public void testGetSizes() {
        System.out.println("getSizes");
        ConfigLoader instance = new ConfigLoader(this.normalFile);

        Set<ImageSize_IM> expResult = new HashSet<>();
        expResult.add(new ImageSize_IM(20, 30));
        expResult.add(new ImageSize_IM(40, 50));
        expResult.add(new ImageSize_IM(60, 70));

        Set<ImageSize_IM> result = instance.getSizes();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class ConfigLoader.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        ConfigLoader instance = new ConfigLoader(this.normalFile);
        System.out.println(instance.toString());
    }

}
