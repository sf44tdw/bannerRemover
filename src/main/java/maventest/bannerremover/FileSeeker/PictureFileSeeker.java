/*
 * 指定されたディレクトリ下の画像ファイルをリストアップする。
 */
package maventest.bannerremover.FileSeeker;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 *
 */
public class PictureFileSeeker {
    
    private final File SourceDir;
    private final IOFileFilter extfilter = new ImageReaderFileFilter();
    private IOFileFilter dirf = TrueFileFilter.INSTANCE;
    
    public PictureFileSeeker(File SourceDir) {
        this.SourceDir = SourceDir;
    }
    
    public synchronized boolean isRecursive() {
        boolean Ret = false;
        if (this.dirf instanceof TrueFileFilter) {
            Ret = true;
        } else if (this.dirf instanceof FalseFileFilter) {
            Ret = false;
        }
        return Ret;
    }

    /**
     * サブディレクトリも検索するか。trueをセットすると検索するようになる。デフォルトはtrue
     *
     * @param recursive セット対象
     */
    public synchronized void setRecursive(boolean recursive) {
        if (recursive == false) {
            this.dirf=FalseFileFilter.INSTANCE;
        } else {
            this.dirf=TrueFileFilter.INSTANCE;
        }
    }

    /**
     * 検索を行い、その結果を返す。
     *
     * @return 見つかった画像ファイルを示すファイルオブジェクトのリスト
     */
    public synchronized List<File> seek() {
        List<File> list = Collections.synchronizedList(new ArrayList<File>());
        Collection<File> files = FileUtils.listFiles(this.SourceDir, this.extfilter, this.dirf);
        list.addAll(files);
        return Collections.unmodifiableList(list);
    }
}
