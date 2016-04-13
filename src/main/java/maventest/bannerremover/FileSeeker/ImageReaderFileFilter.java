/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bannerremover.FileSeeker;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageReaderSpi;
import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * 読み込み可能な画像ファイルを拡張子でフィルタするFileFilter。拡張子が大文字の場合に対応できないらしい。
 * 元:http://d.hatena.ne.jp/NAT_programming/20070108/1168271939
 */
public class ImageReaderFileFilter implements IOFileFilter {
    /** 対応するファイル拡張子(ドット「.」も含む) */
    private String[] m_suffixes;

    /** コンストラクタ */
    public ImageReaderFileFilter() {
        // 利用可能なImageReaderのSPIから、対応しているファイル拡張子を集める
        IIORegistry registry = IIORegistry.getDefaultInstance();
        Iterator<ImageReaderSpi> spiItr = 
            registry.getServiceProviders(ImageReaderSpi.class, false);
        List<String> suffixList = new LinkedList<>();
        while(spiItr.hasNext()) {
            ImageReaderSpi spi = spiItr.next();
            suffixList.addAll(Arrays.asList(spi.getFileSuffixes()));
        }
        m_suffixes = suffixList.toArray(new String[0]);
    }
    
    /**
     * 対応する拡張子のファイルか判定する。
     * @return 対応する拡張子ならtrue、それ以外はfalseを返す。
     */
    @Override
    public boolean accept(File file) {
        for(String suffix : m_suffixes) {
            if(file.getPath().endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean accept(File file, String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}