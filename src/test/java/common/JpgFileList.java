/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author normal
 */
public class JpgFileList {

    private final File sourceDir = new File("testdata/forList");

    private final File f1 = new File(sourceDir,"00000001.jpg");
    private final File f2 = new File(sourceDir,"00000002.jpg");
    private final File f3 = new File(sourceDir,"00000003.jpg");
    private final File f4 = new File(sourceDir,"00000004.jpg");
    private final File f5 = new File(sourceDir,"00000005.jpg");
    private final File f6 = new File(sourceDir,"00000006.jpg");

    public JpgFileList() {

    }

    private File copy(File f) {
        return new File(f.getAbsolutePath());
    }

    public File getSourceDir() {
        return copy(sourceDir);
    }

    public File getF1() {
        return copy(f1);
    }

    public File getF2() {
        return copy(f2);
    }

    public File getF3() {
        return copy(f3);
    }

    public File getF4() {
        return copy(f4);
    }

    public File getF5() {
        return copy(f5);
    }

    public File getF6() {
        return copy(f6);
    }

    public List<File> getImageList() {
        List<File> imageList = new ArrayList<>();
        imageList.add(this.getF1());
        imageList.add(this.getF2());
        imageList.add(this.getF3());
        imageList.add(this.getF4());
        imageList.add(this.getF5());
        imageList.add(this.getF6());
        return Collections.unmodifiableList(imageList);
    }
}
