package bannerremover;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SizeChecker {

    private Log log = LogFactory.getLog(SizeChecker.class);
    private final List<File> ImageList;
    private int Height;
    private int Width;

    /**
     * 渡されたファイルリストに載っている画像ファイルのピクセル数が特定のサイズであるかチェックし、一致したファイルのみをリストにする。
     *
     * @param ImageList ファイルリスト
     */
    public SizeChecker(List<File> ImageList) {
        this.ImageList = ImageList;
    }

    /**
     * リストアップ対象となる縦横のピクセルのサイズを設定する。
     *
     * @param Height 高さ
     * @param Width 幅
     */
    public synchronized void setPixelSize(int Height, int Width) {
        this.Height = Height;
        this.Width = Width;
    }

    public int getHeight() {
        return Height;
    }

    public int getWidth() {
        return Width;
    }

    /**
     * リストアップを行う。
     *
     * @return 対象のサイズの画像ファイル一覧
     */
    public synchronized List<File> listUp() {
        List<File> list_output = Collections.synchronizedList(new ArrayList<File>());
        Iterator<File> itf = this.ImageList.iterator();
        while (itf.hasNext()) {
            File F = itf.next();
            try {
                BufferedImage Img = ImageIO.read(F);
                if (Img.getHeight() == this.Height && Img.getWidth() == this.Width) {
                    if (log.isInfoEnabled()) {
                        log.info("該当するピクセル数の画像ファイルです。 " + F.toString());
                    }
                    list_output.add(F);
                } else {
                    if (log.isInfoEnabled()) {
                        log.info("該当するピクセル数の画像ファイルではありません " + F.toString());
                    }
                }
            } catch (IOException e) {
                //エラーが起きた場合、そのときのファイル名を出力する。
                log.fatal("ファイル検索中にエラー ファイル = " + F.toString(), e);
            }
        }
        return list_output;
    }
}
