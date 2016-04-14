/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover.config;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import maventest.bannerremover.sizechecker.ImageSize;

/**
 * 設定
 *
 * @author normal
 */
public final class ConfigValueChecker {

    private final File targetDir;
    private final File destDir;
    private final boolean recursive;
    private final Set<ImageSize> sizes;

    /**
     * @param targetDir ファイルの検索先ディレクトリ。
     * @param destDir 条件に当てはまるファイルの移動先ディレクトリ。
     * @param recursive 検索先ディレクトリを再起探索するか。
     * @param sizes 移動するファイルの縦ピクセルと横ピクセルの値。
     */
    public ConfigValueChecker(File targetDir, File destDir, boolean recursive, Set<ImageSize> sizes) {
        this.targetDir = new File(targetDir.getAbsolutePath());
        if (!this.targetDir.isDirectory()) {
            throw new IllegalArgumentException("検索先がディレクトリではないか、存在しない。 " + this.targetDir.getAbsolutePath());
        }
        this.destDir = new File(destDir.getAbsolutePath());
        if (!this.destDir.isDirectory()) {
            throw new IllegalArgumentException("移動先がディレクトリではないか、存在しない。 " + this.destDir.getAbsolutePath());
        }
        if (this.targetDir.equals(this.destDir)) {
            throw new IllegalArgumentException("送り側と受け側のディレクトリが同じ。");
        }
        this.recursive = recursive;
        if (sizes == null || sizes.isEmpty()) {
            throw new IllegalArgumentException("画像サイズの設定がされていない。");
        }
        Set<ImageSize> temp = new HashSet<>();
        temp.addAll(sizes);
        this.sizes = Collections.unmodifiableSet(temp);
    }

    public File getTargetDir() {
        return targetDir;
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
        try {
            final BeanInfo info = Introspector.getBeanInfo(this.getClass(), Object.class);
            final PropertyDescriptor[] props = info.getPropertyDescriptors();
            final StringBuffer buf = new StringBuffer(500);
            Object value = null;
            buf.append(getClass().getName());
            buf.append("@");  //$NON-NLS-1$
            buf.append(hashCode());
            buf.append("={");  //$NON-NLS-1$
            for (int idx = 0; idx < props.length; idx++) {
                if (idx != 0) {
                    buf.append(", ");  //$NON-NLS-1$
                }
                buf.append(props[idx].getName());
                buf.append("=");  //$NON-NLS-1$
                if (props[idx].getReadMethod() != null) {
                    value = props[idx].getReadMethod()
                            .invoke(this, new Object[]{});
                    if (value instanceof ConfigValueChecker) {
                        buf.append("@");  //$NON-NLS-1$
                        buf.append(value.hashCode());
                    } else if (value instanceof Collection) {
                        buf.append("{");  //$NON-NLS-1$
                        for (Object element : ((Collection<?>) value)) {
                            if (element instanceof ConfigValueChecker) {
                                buf.append("@");  //$NON-NLS-1$
                                buf.append(element.hashCode());
                            } else {
                                buf.append(element.toString());
                            }
                        }
                        buf.append("}");  //$NON-NLS-1$
                    } else if (value instanceof Map) {
                        buf.append("{");  //$NON-NLS-1$
                        Map<?, ?> map = (Map) value;
                        for (Object key : map.keySet()) {
                            Object element = map.get(key);
                            buf.append(key.toString()).append("=");
                            if (element instanceof ConfigValueChecker) {
                                buf.append("@");  //$NON-NLS-1$
                                buf.append(element.hashCode());
                            } else {
                                buf.append(element.toString());
                            }
                        }
                        buf.append("}");  //$NON-NLS-1$
                    } else if (value != null) {
                        buf.append(value);
                    } else {
                        buf.append("null");
                    }
                }
            }
            buf.append("}");  //$NON-NLS-1$
            return buf.toString();
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
}
