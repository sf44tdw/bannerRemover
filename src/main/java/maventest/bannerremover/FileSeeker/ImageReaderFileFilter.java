/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover.FileSeeker;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageReaderSpi;
import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * 読み込み可能な画像ファイルを拡張子でフィルタするFileFilter。拡張子が大文字の場合に対応できないらしい。
 * 元:http://d.hatena.ne.jp/NAT_programming/20070108/1168271939
 */
public class ImageReaderFileFilter implements IOFileFilter {

    /**
     * 対応するファイル拡張子(ドット「.」も含む)
     */
    private final Set<String> m_suffixes = new HashSet<>();

    public ImageReaderFileFilter() {
        // 利用可能なImageReaderのSPIから、対応しているファイル拡張子を集める
        IIORegistry registry = IIORegistry.getDefaultInstance();
        Iterator<ImageReaderSpi> spiItr
                = registry.getServiceProviders(ImageReaderSpi.class, false);
        List<String> suffixList = new LinkedList<String>();
        while (spiItr.hasNext()) {
            ImageReaderSpi spi = spiItr.next();
            suffixList.addAll(Arrays.asList(spi.getFileSuffixes()));
        }
        m_suffixes.addAll(suffixList);
    }

    public String getExtension(String fileName) {
        if (fileName == null) {
            return null;
        }

        // 最後の『 . 』の位置を取得します。
        int lastDotPosition = fileName.lastIndexOf(".");

        // 『 . 』が存在する場合は、『 . 』以降を返します。
        if (lastDotPosition != -1) {
            return fileName.substring(lastDotPosition + 1);
        }
        return null;
    }

    public String getExtension(File filePath) {

        if (filePath == null) {
            throw new NullPointerException("対象のファイルが指定されていません。");
        }

        if (filePath.isDirectory()) {
            MessageFormat msg = new MessageFormat("ディレクトリです。パス={0}");
            Object[] parameters = {filePath.getAbsolutePath()};
            throw new IllegalStateException(msg.format(parameters));
        }

        // パス名が示すファイルの名前を返します。
        String fileName = filePath.getName();
        return getExtension(fileName);
    }

    /**
     * 対応する拡張子のファイルか判定する。
     *
     * @param target
     * @return 対応する拡張子ならtrue、それ以外はfalseを返す。
     */
    @Override
    public boolean accept(File target) {
        return this.m_suffixes.contains(this.getExtension(target));
    }

    @Override
    public boolean accept(File file, String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
