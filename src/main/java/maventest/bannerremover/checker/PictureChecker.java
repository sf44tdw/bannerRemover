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
     * リストアップを行う。
     *
     * @return 対象のサイズの画像ファイル一覧
     */
    Set<File> makeList();
    
}
