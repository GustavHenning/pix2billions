package pix2billions.ghenning.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Converts an image to a 'They are Billion' map.
 * 
 * This program may NOT be used for any illegal, cheating or otherwise harmful
 * purposes to any individual, corporation or other worldly entity. I, the
 * author, do NOT take any responsibility for the use of this program, it is
 * strictly for educational purposes only.
 * 
 * @author Gustav Henning 28/12/2017
 *
 */
public class ImageToMap {

	private static final double FORTY_FIVE_DEG = Math.PI / 4;

	private static Color brightPink = new Color(255, 0, 255, 255);
	// none, water, earth, grass, stone, iron, oil, gold, road, wood, mountain,
	// sky, abyss
	private static Color colors[] = { new Color(255, 255, 255, 0), new Color(25, 25, 112, 255),
			new Color(245, 222, 179, 255), new Color(124, 252, 0, 255), new Color(169, 169, 169, 255),
			new Color(135, 206, 250, 255), new Color(128, 0, 128, 255), new Color(255, 215, 0, 255),
			new Color(255, 255, 255, 255), new Color(0, 100, 0, 255), new Color(0, 0, 0, 255),
			new Color(30, 144, 255, 255), new Color(255, 69, 0, 255) };

	/*
	 * credit:
	 * https://memorynotfound.com/java-resize-image-fixed-width-height-example/
	 */
	private static BufferedImage resize(BufferedImage img, int width, int height) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	/*
	 * credit:
	 * https://stackoverflow.com/questions/19849670/java-document-image-scaling-
	 * and-rotation
	 */
	private static BufferedImage rotate(BufferedImage image, double radians) {
		AffineTransform transform;
		double tx = (image.getHeight() - image.getWidth()), ty = (image.getWidth() - image.getHeight()) + 75;
		transform = AffineTransform.getTranslateInstance(tx, ty);
		transform.rotate(radians, image.getWidth() / 2, image.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		BufferedImage transformedImage = op.createCompatibleDestImage(image, image.getColorModel());
		return op.filter(image, transformedImage);
	}

	/*
	 * credits:
	 * https://alvinalexander.com/blog/post/java/getting-rgb-values-for-each-
	 * pixel-in-image-using-java-bufferedi
	 */
	private static Color getColor(int pixel) {
		int a = (pixel >> 24) & 0xff;
		int r = (pixel >> 16) & 0xff;
		int g = (pixel >> 8) & 0xff;
		int b = (pixel) & 0xff;
		return new Color(r, g, b, a);
	}

	/* 4d pythagoras distance, of r, g, b and alpha */
	private static Double dist(Color c1, Color c2) {
		return Math.sqrt((c1.getRed() - c2.getRed()) ^ 2 + (c1.getGreen() - c2.getGreen())
				^ 2 + (c1.getBlue() - c2.getBlue()) ^ 2 + (c1.getAlpha() - c2.getAlpha()) ^ 2);
	}

	private static int closestColor(Color imgColor) {
		Color candidate = null;
		double dist = Double.MAX_VALUE;

		for (Color can : colors) {
			double d = dist(can, imgColor);
			if (d < dist) {
				candidate = can;
				dist = d;
			}
		}
		return candidate.getRGB();
	}

	private static BufferedImage translateColors(BufferedImage image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (image.getRGB(x, y) == 0) {
					image.setRGB(x, y, brightPink.getRGB());
				} else {
					image.setRGB(x, y, closestColor(getColor(image.getRGB(x, y))));
				}
			}
		}
		return image;
	}

	private static void imageToMap(String path) throws IOException {
		File f = new File(path);
		BufferedImage image = ImageIO.read(f);
		/* Resize it to 150x200 (px) */
		image = resize(image, 150, 200);
		/* rotate the image 45 deg */
		image = rotate(image, FORTY_FIVE_DEG);
		image = resize(image, 256, 256); // we lost some w/h during translation
		/* translate colors */
		image = translateColors(image);
		/* write to a 256x256 output image */
		writeToFile(image, f.getName());
	}

	private static void writeToFile(BufferedImage image, String name) {
		// Strip the file ending if it has any and put a "map-" in front
		String finalName = "map-" + name;
		try {
			File outputfile = new File(finalName);
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
			System.err.println("Couldn't save file " + finalName);
			System.err.println(e);
		}

	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Please provide the path(s) to the image file you want to convert.");
		} else {
			for (String arg : args) {
				try {
					imageToMap(arg);
				} catch (IOException e) {
					System.err.println("Cannot parse " + arg + ". Wrong path or not an image?");
					System.err.println(e);
				}
			}
		}
	}

}
