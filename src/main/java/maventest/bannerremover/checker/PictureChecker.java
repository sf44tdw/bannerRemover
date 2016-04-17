/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover.checker;

import java.io.File;
import java.util.Set;

/**
 *
 * @author normal
 */
public interface PictureChecker {

    /**
     * 条件に該当する画像ファイルだけリストに加える。
     *
     * @return 条件に該当する画像ファイルの一覧。
     */
    Set<File> makeList();
    
}
