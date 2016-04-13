package maventest.bannerremover.sizechecker;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SizeChecker {

    private final Log log = LogFactory.getLog(SizeChecker.class);
    private final List<File> ImageList;
    private ImageSize size;

    /**
     * 渡されたファイルリストに載っている画像ファイルのピクセル数が特定のサイズであるかチェックし、一致したファイルのみをリストにする。
     *
     * @param ImageList ファイルリスト
     */
    public SizeChecker(List<File> ImageList) {
        this.ImageList = ImageList;
    }

    public ImageSize getSize() {
        return size;
    }

    public void setSize(ImageSize size) {
        this.size = size;
    }

    private final MessageFormat info = new MessageFormat("ファイルのパス={0} 画像サイズ(高さ,幅)=({1},{2}) 抽出対象画像サイズ(高さ,幅)=({3},{4})");

    /**
     * リストアップを行う。
     *
     * @return 対象のサイズの画像ファイル一覧
     */
    public List<File> makeList() {

        List<File> list_output = Collections.synchronizedList(new ArrayList<File>());
        for (File F : this.ImageList) {
            StringBuilder sb = new StringBuilder();

            Object[] parameters;
            parameters = new Object[]{F.getAbsolutePath(), "UNKNOWN", "UNKNOWN", this.getSize().getHeight(), this.getSize().getWidth()};

            try {
                BufferedImage Img = ImageIO.read(F);
                parameters = new Object[]{F.getAbsolutePath(), Img.getHeight(), Img.getWidth(), this.getSize().getHeight(), this.getSize().getWidth()};

                if (Img.getHeight() == this.getSize().getHeight() && Img.getWidth() == this.getSize().getWidth()) {
                    sb.append("条件を満たすピクセルのファイルを発見。");
                    sb.append(info.format(parameters));
                    list_output.add(F);
                } else {
                    sb.append("条件を満たすピクセルのファイルではない。");
                    sb.append(info.format(parameters));
                }
                if (log.isInfoEnabled()) {
                    log.info(sb.toString());
                }
            } catch (IOException e) {
                //エラーが起きた場合、そのときのファイル名を出力する。
                log.fatal("ファイル検索中にエラー ファイル = " + F.toString(), e);
            }
        }
        return Collections.unmodifiableList(list_output);
    }
}
