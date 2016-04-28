/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover.config;

import com.moandjiezana.toml.Toml;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import maventest.bannerremover.sizechecker.ImageSize;
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

    private static enum CONFIG_FILE_KEY {
        DIRECTORY("directory"),
        SOURCE_DIR(DIRECTORY, "source"),
        DEST_DIR(DIRECTORY, "dest"),
        RECURSIVE("recursive"),
        RECURSIVE_FLAG(RECURSIVE, "recursive"),
        IMAGE_SIZE("imagesize"),
        HEIGHT(IMAGE_SIZE, "Height"),
        WIDTH(IMAGE_SIZE, "Width");

        private final String key_full;
        private final String lastKey;

        private CONFIG_FILE_KEY(String key) {
            this(null, key);
        }

        private CONFIG_FILE_KEY(CONFIG_FILE_KEY parent, String key) {

            CONFIG_FILE_KEY parent_t = parent;
            String key_t = key;
            this.lastKey = key_t;
            Deque<String> stack = new ArrayDeque<>();
            stack.add(key_t);
            if (parent_t != null) {
                stack.add(".");
                stack.add(parent_t.getKey());
            }
            StringBuilder sb = new StringBuilder();
            Iterator<String> e = stack.descendingIterator();
            while (e.hasNext()) {
                sb.append(e.next());
            }
            this.key_full = sb.toString();
        }

        public String getLastKey() {
            return lastKey;
        }

        public String getKey() {
            return this.key_full;
        }

        @Override
        public String toString() {
            return "CONFIG_FILE_KEY{" + "key_full=" + key_full + ", lastKey=" + lastKey + '}';
        }

    }

    private final File sourceDir;
    private final File destDir;
    private final boolean recursive;
    private final Set<ImageSize> sizes;

    /**
     * @param configFile 設定ファイルのパス
     */
    public ConfigLoader(File configFile) {
        File t_file = new File(configFile.getAbsolutePath());
        if (!t_file.isFile()) {
            throw new IllegalArgumentException("設定ファイルが見つからない。 " + t_file.getAbsolutePath());
        }
        log.info("設定ファイルロード開始。ファイル=" + t_file.getAbsolutePath());

        Toml toml = null;
        toml = new Toml().read(t_file);

        this.sourceDir = new File(toml.getString(CONFIG_FILE_KEY.SOURCE_DIR.getKey()));
        if (!this.sourceDir.isDirectory()) {
            throw new IllegalArgumentException("検索先がディレクトリではないか、存在しない。 " + this.sourceDir.getAbsolutePath());
        }

        this.destDir = new File(toml.getString(CONFIG_FILE_KEY.DEST_DIR.getKey()));
        if (!this.destDir.isDirectory()) {
            throw new IllegalArgumentException("移動先がディレクトリではないか、存在しない。 " + this.destDir.getAbsolutePath());
        }

        if (this.sourceDir.equals(this.destDir)) {
            throw new IllegalArgumentException("送り側と受け側のディレクトリが同じ。");
        }
        log.info("検索先=" + this.sourceDir.getAbsolutePath());
        log.info("移動先=" + this.destDir.getAbsolutePath());

        //省略されている場合はサブディレクトリ探索を行うものとする。
        this.recursive = toml.getBoolean(CONFIG_FILE_KEY.RECURSIVE_FLAG.getKey(), true);
        log.info("サブディレクトリ探索実施フラグ=" + this.recursive);

        Set<ImageSize> sizes_t = new HashSet<>();

        List<HashMap<String, Long>> sizes_Raw = toml.getList(CONFIG_FILE_KEY.IMAGE_SIZE.getKey());
        if ((sizes_Raw == null) || (sizes_Raw.isEmpty())) {
            throw new IllegalArgumentException("画像サイズの設定がされていない。");
        }
        for (HashMap<String, Long> size_Raw : sizes_Raw) {
            int h = new Integer(size_Raw.get(CONFIG_FILE_KEY.HEIGHT.getLastKey()).toString());
            int w = new Integer(size_Raw.get(CONFIG_FILE_KEY.WIDTH.getLastKey()).toString());
            ImageSize is = new ImageSize(h, w);
            log.info("選択対象画像ピクセル=" + is);
            sizes_t.add(is);
        }
        this.sizes = Collections.unmodifiableSet(sizes_t);
        log.info("設定ファイルロード完了。ファイル=" + t_file.getAbsolutePath());
    }

    public File getSourceDir() {
        return sourceDir;
    }

    public File getDestDir() {
        return destDir;
    }

    public boolean isRecursive() {
        return recursive;
    }

    public Set<ImageSize> getSizes() {
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
