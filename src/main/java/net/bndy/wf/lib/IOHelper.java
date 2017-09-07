/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.lib;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class IOHelper {
	public static void createThumbnail(String filename, int thumbWidth, String outFilename) throws IOException {
		BufferedImage originalBufferedImage = null;
		try {
			File originFile = new File(filename);
			String imageType = originFile.getName().substring(originFile.getName().lastIndexOf(".") + 1);
			originalBufferedImage = ImageIO.read(originFile);

			int thumbnailWidth = thumbWidth;

			int widthToScale, heightToScale;
			if (originalBufferedImage.getWidth() > originalBufferedImage.getHeight()) {

				heightToScale = (int) (1.1 * thumbnailWidth);
				widthToScale = (int) ((heightToScale * 1.0) / originalBufferedImage.getHeight()
						* originalBufferedImage.getWidth());

			} else {
				widthToScale = (int) (1.1 * thumbnailWidth);
				heightToScale = (int) ((widthToScale * 1.0) / originalBufferedImage.getWidth()
						* originalBufferedImage.getHeight());
			}

			BufferedImage resizedImage = new BufferedImage(widthToScale, heightToScale,
					originalBufferedImage.getType());
			Graphics2D g = resizedImage.createGraphics();

			g.setComposite(AlphaComposite.Src);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g.drawImage(originalBufferedImage, 0, 0, widthToScale, heightToScale, null);
			g.dispose();

			int x = (resizedImage.getWidth() - thumbnailWidth) / 2;
			int y = (resizedImage.getHeight() - thumbnailWidth) / 2;

			if (x < 0 || y < 0) {
				throw new IllegalArgumentException("Width of new thumbnail is bigger than original image");
			}
			BufferedImage thumbnailBufferedImage = resizedImage.getSubimage(x, y, thumbnailWidth, thumbnailWidth);
			ImageIO.write(thumbnailBufferedImage, imageType.toUpperCase(), new File(outFilename));
		} catch (IOException ioe) {
			throw ioe;
		}
	}
}
