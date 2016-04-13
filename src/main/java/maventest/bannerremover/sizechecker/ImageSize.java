/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maventest.bannerremover.sizechecker;

/**
 * 画像のサイズを指定する。
 * @author normal
 */
public class ImageSize {

    private final int Height;
    private final int Width;
/**
 * 
     * @param Height 高さ方向のピクセル数。
     * @param Width 幅方向のピクセル数。
 */
    public ImageSize(int Height, int Width) {
        this.Height = Height;
        this.Width = Width;
    }

    public int getHeight() {
        return Height;
    }

    public int getWidth() {
        return Width;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.Height;
        hash = 97 * hash + this.Width;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImageSize other = (ImageSize) obj;
        if (this.Height != other.Height) {
            return false;
        }
        if (this.Width != other.Width) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ImageSize{" + "Height=" + Height + ", Width=" + Width + '}';
    }

}
