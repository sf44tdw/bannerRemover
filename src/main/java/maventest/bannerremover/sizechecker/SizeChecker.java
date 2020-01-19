package maventest.bannerremover.sizechecker;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.slf4j.Logger;

import loggerconfigurator.LoggerConfigurator;
//      壊れたファイルを読み込むとこんなエラーが出る。
//      at java.util.ArrayList.elementData(ArrayList.java:418)
//	at java.util.ArrayList.get(ArrayList.java:431)
//	at com.sun.imageio.plugins.jpeg.JPEGImageReader.checkTablesOnly(JPEGImageReader.java:378)
//	at com.sun.imageio.plugins.jpeg.JPEGImageReader.gotoImage(JPEGImageReader.java:481)
//	at com.sun.imageio.plugins.jpeg.JPEGImageReader.readHeader(JPEGImageReader.java:602)
//	at com.sun.imageio.plugins.jpeg.JPEGImageReader.readInternal(JPEGImageReader.java:1059)
//	at com.sun.imageio.plugins.jpeg.JPEGImageReader.read(JPEGImageReader.java:1039)
//	at javax.imageio.ImageIO.read(ImageIO.java:1448)
//	at javax.imageio.ImageIO.read(ImageIO.java:1308)
import maventest.bannerremover.checker.PictureChecker;

public class SizeChecker implements PictureChecker {

	private final Logger log = LoggerConfigurator.getCallerLogger();
	private final List<File> ImageList;
	private Set<ImageSize_IM> sizes;

	/**
	 * 渡されたファイルリストに載っている画像ファイルのピクセル数が特定のサイズであるかチェックし、一致したファイルのみをリストにする。
	 *
	 * @param ImageList ファイルリスト
	 */
	public SizeChecker(List<File> ImageList) {
		this.ImageList = ImageList;
	}

	public Set<ImageSize_IM> getSizes() {
		return sizes;
	}

	public void setSizes(Set<ImageSize_IM> sizes) {
		Set<ImageSize_IM> temp = new HashSet<>();
		temp.addAll(sizes);
		this.sizes = Collections.unmodifiableSet(temp);
	}

	/**
	 * 指定されたピクセル数の画像ファイル一覧を作成する。
	 * @return 条件に該当する画像ファイルの一覧。
	 */
	@Override
	public Set<File> makeList() {
		Set<File> set_output = Collections.synchronizedSet(new HashSet<File>());
		for (File F : this.ImageList) {
			ImageSize_IM s = null;
			BufferedImage Img = null;
			try {
				Img = ImageIO.read(F);
				s = new ImageSize_IM(Img.getHeight(), Img.getWidth());
			} catch (IOException e) {
				s = null;
				//エラーが起きた場合、そのときのファイル名を出力する。
				log.error("ファイル読み込み中にエラー。 ファイル = " + F.toString(), e);
			} catch (ArrayIndexOutOfBoundsException e) {
				s = null;
				log.error("壊れたファイルを読み込んだ可能性あり。 ファイル = " + F.toString(), e);
			}
			if (s != null) {
				StringBuilder sb = new StringBuilder();
				if (set_output.contains(F)) {
					sb.append("別の条件をすでに満たしているファイル。");
				} else if (this.getSizes().contains(s)) {
					sb.append("条件を満たすピクセルのファイルを発見。");
					sb.append("ピクセル(高さ,幅)=[");
					sb.append(s.getHeight());
					sb.append(",");
					sb.append(s.getWidth());
					sb.append("] ");
					set_output.add(F);
				} else {
					sb.append("条件を満たすピクセルのファイルではない。");
				}

				sb.append(F.getAbsolutePath());
				if (log.isInfoEnabled()) {
					log.info(sb.toString());
				}

				sb = null;
			}

		}

		return Collections.unmodifiableSet(set_output);
	}
}
