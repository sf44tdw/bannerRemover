/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import maventest.bannerremover.FileSeeker.PictureFileSeeker;
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

        try {

            log.info("探査ディレクトリ          " + args[0]);
            File Source = new File(args[0]);

            if (Source.isDirectory()) {
                PictureFileSeeker seeker = new PictureFileSeeker(Source);
                log.info("検索対象を発見しました。");

                log.info("サブディレクトリ探査  " + args[1]);
                boolean recursive = false;
                switch (args[1]) {
                    case "0":
                        recursive = false;
                        break;

                    case "1":
                        recursive = true;
                        break;
                }

                if (recursive == false) {
                    log.info("サブディレクトリ探査は行いません。");
                }

                if (recursive == true) {
                    log.info("サブディレクトリ探査を行います。");
                }
                seeker.setRecursive(recursive);
                log.info("サブディレクトリ探査設定を完了。");

                log.info("移動対象ファイルの移動先 " + args[2]);
                File Dest = new File(args[2]);
                if (Dest.isDirectory() && Dest.exists()) {
                    log.info("移動先の設定を完了。 " + Dest.toString());
                } else {
                    log.fatal("移動先がディレクトリではないか、存在しない");
                    Dest = null;
                }

                log.info("探査開始。");
                List<File> res = seeker.seek();
                log.info("探査完了。件数= " + res.size());

                if (res.size() > 0) {
                    SizeChecker checker = new SizeChecker(res);

                    log.info("高さ                 " + args[3]);
                    int H;
                    H = Integer.parseInt(args[3]);
                    log.info("高さ設定を完了。高さ=" + H);

                    log.info("幅                   " + args[4]);
                    int W;
                    W = Integer.parseInt(args[4]);
                    log.info("幅設定を完了。幅=" + W);
                    checker.setPixelSize(H, W);
                    log.info("サイズ設定を完了。");

                    log.info("サイズチェック開始。");
                    List<File> res2 = checker.makeList();
                    log.info("サイズチェック完了。件数= " + res2.size());

                    if (Dest!=null) {
                        Iterator<File> itf = res2.iterator();
                        while (itf.hasNext()) {
                            File F = itf.next();
                            log.info("移動します。" + F.toString());
                            FileUtils.moveFileToDirectory(F, Dest, true);
                            log.info("移動しました。" + F.toString());
                        }
                    } else {
                        log.info("移動先がnull");
                    }

                } else {
                    log.info("ファイルがありません。");
                }

            } else {
                log.info("検索先の設定に問題があります。");
            }
        } catch (NumberFormatException | IOException e) {
            log.fatal("エラーです。", e);
        }
    }
}
