/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import maventest.bannerremover.FileSeeker.PictureFileSeeker;
import maventest.bannerremover.config.Config;
import maventest.bannerremover.sizechecker.ImageSize;
import maventest.bannerremover.sizechecker.SizeChecker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 画像ファイルの中から特定のサイズのものを探して除去する。
 */
public class BannerRemover {

    /**
     * @param args the command line arguments 検索対象の最上位ディレクトリ
     * サブディレクトリの探査を行うかを0か1で指定 画像の高さ 画像の幅の順で指定
     */
    public static void main(String[] args) {
        Log log = LogFactory.getLog(BannerRemover.class);

        File dir = new File("");
        File src = new File(dir, "");
        File dest = new File(dir, "");

        Set<ImageSize> sizes = new HashSet<>();
        sizes.add(new ImageSize(40, 200));
        sizes.add(new ImageSize(80, 80));
        sizes.add(new ImageSize(120, 120));
        sizes.add(new ImageSize(40, 198));
        sizes.add(new ImageSize(32, 122));
        sizes.add(new ImageSize(172, 241));
        sizes.add(new ImageSize(40, 199));

        Config conf = new Config(src, dest, false, sizes);

        PictureFileSeeker seeker = new PictureFileSeeker(conf.getTargetDir());

        List<File> images = seeker.seek();

        SizeChecker checker = new SizeChecker(images);

        checker.setSizes(sizes);

        Set<File> banners = checker.makeList();

        for (File f : banners) {
            try {
                log.info("移動します。" + f.toString());
                FileUtils.moveFileToDirectory(f, conf.getDestDir(), true);
                log.info("移動しました。" + f.toString());
            } catch (IOException ex) {
                log.error(ex);
            }
        }
    }
}
