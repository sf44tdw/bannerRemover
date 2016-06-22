/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover.config;

import maventest.bannerremover.config.mapobjects.ImageSize;
import maventest.bannerremover.config.mapobjects.Config;
import com.moandjiezana.toml.Toml;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import maventest.bannerremover.sizechecker.ImageSize_IM;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 設定ファイルのロードとチェックを行う。
 *
 * @author normal
 */
public final class ConfigLoader {

    private final Log log = LogFactory.getLog(ConfigLoader.class);

    private final File sourceDir;
    private final File destDir;
    private final boolean recursive;
    private final Set<ImageSize_IM> sizes;

    /**
     * @param configFile 設定ファイルのパス
     */
    public ConfigLoader(File configFile) {
        File t_file = new File(configFile.getAbsolutePath());
        if (!t_file.isFile()) {
            throw new IllegalArgumentException("設定ファイルが見つからない。 " + t_file.getAbsolutePath());
        }
        log.info("設定ファイルロード開始。ファイル=" + t_file.getAbsolutePath());

        Config conf = new Toml().read(t_file).to(Config.class);

        log.debug(conf);

        this.sourceDir = new File(conf.directory.source);
        if (!this.sourceDir.isDirectory()) {
            IllegalArgumentException ex = new IllegalArgumentException("検索先がディレクトリではないか、存在しない。 " + this.sourceDir.getAbsolutePath());
            log.error(ex);
            throw ex;
        }

        this.destDir = new File(conf.directory.dest);
        if (!this.destDir.isDirectory()) {
            IllegalArgumentException ex = new IllegalArgumentException("移動先がディレクトリではないか、存在しない。 " + this.destDir.getAbsolutePath());
            log.error(ex);
            throw ex;
        }

        if (this.sourceDir.equals(this.destDir)) {
            IllegalArgumentException ex = new IllegalArgumentException("送り側と受け側のディレクトリが同じ。");
            log.error(ex);
            throw ex;
        }
        log.info("検索先=" + this.sourceDir.getAbsolutePath());
        log.info("移動先=" + this.destDir.getAbsolutePath());

        //省略されている場合はサブディレクトリ探索を行うものとする。
        if (conf.recursive == null) {
            this.recursive = true;
        } else {
            this.recursive = conf.recursive.recursive;
        }

        log.info("サブディレクトリ探索実施フラグ=" + this.recursive);

        if (conf.imagesize == null || conf.imagesize.length == 0) {
            IllegalArgumentException ex = new IllegalArgumentException("画像サイズの設定がされていない。");
            log.error(ex);
            throw ex;
        }

        Set<ImageSize_IM> sizes_t = new HashSet<>();

        for (ImageSize im : conf.imagesize) {
            ImageSize_IM imsim = new ImageSize_IM(im.Height, im.Width);
            log.info("選択対象画像ピクセル=" + imsim);
            sizes_t.add(imsim);
        }

        this.sizes = Collections.unmodifiableSet(sizes_t);
        log.info("設定ファイルロード完了。ファイル=" + t_file.getAbsolutePath());
    }

    public File getSourceDir() {
        return new File(sourceDir.getAbsolutePath());
    }

    public File getDestDir() {
        return new File(destDir.getAbsolutePath());
    }

    public boolean isRecursive() {
        return recursive;
    }

    public Set<ImageSize_IM> getSizes() {
        return sizes;
    }

    /**
     * テキストに変換可能なものを全てダンプする。
     *
     * @return このオブジェクトが保存している内容を文字列に変換したもの。
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
