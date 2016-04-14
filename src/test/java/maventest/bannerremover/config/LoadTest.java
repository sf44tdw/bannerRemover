/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover.config;

import java.io.File;
import java.util.List;

import org.junit.Test;

/**
 *
 * @author normal
 */
public class LoadTest {

    private final File sourceDir = new File("testdata/SampleConfig");
    private final File configFile = new File(sourceDir, "config.XML");

    @Test
    public void load() throws ConfigurationException {

        XMLConfiguration config = new XMLConfiguration(configFile);
        List<String> elements = config.getList("elements.element");
        for (String element : elements) {
            System.out.println(element);
        }
    }
}
