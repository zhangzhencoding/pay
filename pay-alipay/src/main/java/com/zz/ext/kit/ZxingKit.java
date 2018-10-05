package com.zz.ext.kit;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.zz.util.IOUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

/**
 * google 开源图形码工具Zxing使用
 */
public class ZxingKit {
	/**
	 * Zxing图形码生成工具
	 *
	 * @param contents
	 *            内容
	 * @param barcodeFormat
	 *            BarcodeFormat对象
	 * @param format
	 *            图片格式，可选[png,jpg,bmp]
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param margin
	 *            边框间距px
	 * @param saveImgFilePath
	 *            存储图片的完整位置，包含文件名
	 * @return {boolean}
	 */
	public static boolean encode(String contents, BarcodeFormat barcodeFormat, Integer margin,
			ErrorCorrectionLevel errorLevel, String format, int width, int height, String saveImgFilePath) {
		Boolean bool = false;
		BufferedImage bufImg;
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, errorLevel);
		hints.put(EncodeHintType.MARGIN, margin);
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		try {
			// contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, barcodeFormat, width, height, hints);
			MatrixToImageConfig config = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
			bufImg = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
			bool = writeToFile(bufImg, format, saveImgFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	/**
	 *
	 * @param outputStream
	 * 			可以来自response，也可以来自文件
	 * @param contents
	 *			内容
	 * @param barcodeFormat
	 * 			BarcodeFormat对象
	 * @param margin
	 * 			图片格式，可选[png,jpg,bmp]
	 * @param errorLevel
	 *			纠错级别 一般为：ErrorCorrectionLevel.H
	 * @param format
	 * 			图片格式，可选[png,jpg,bmp]
	 * @param width
	 * 			宽
	 * @param height
	 * 			高
	 * 	eg:
	 * 		ZxingKit.encodeOutPutSteam(response.getOutputStream(), qrCodeUrl, BarcodeFormat.QR_CODE, 3, ErrorCorrectionLevel.H, "png", 200, 200);
	 */
	public static void encodeOutPutSteam(OutputStream outputStream, String contents, BarcodeFormat barcodeFormat, Integer margin, ErrorCorrectionLevel errorLevel, String format, int width, int height) {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, errorLevel);
		hints.put(EncodeHintType.MARGIN, margin);
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		try {
			BitMatrix bitMatrix = (new MultiFormatWriter()).encode(contents, barcodeFormat, width, height, hints);
			MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(outputStream);
		}

	}

	/**
	 * @param srcImgFilePath
	 *            要解码的图片地址
	 * @return {Result}
	 */
	@SuppressWarnings("finally")
	public static Result decode(String srcImgFilePath) {
		Result result = null;
		BufferedImage image;
		try {
			File srcFile = new File(srcImgFilePath);
			image = ImageIO.read(srcFile);
			if (null != image) {
				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

				Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
				hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
				result = new MultiFormatReader().decode(bitmap, hints);
			} else {
				throw new IllegalArgumentException ("Could not decode image.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}

	/**
	 * 将BufferedImage对象写入文件
	 *
	 * @param bufImg
	 *            BufferedImage对象
	 * @param format
	 *            图片格式，可选[png,jpg,bmp]
	 * @param saveImgFilePath
	 *            存储图片的完整位置，包含文件名
	 * @return {boolean}
	 */
	@SuppressWarnings("finally")
	public static boolean writeToFile(BufferedImage bufImg, String format, String saveImgFilePath) {
		Boolean bool = false;
		try {
			bool = ImageIO.write(bufImg, format, new File(saveImgFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return bool;
		}
	}


	/**
	 * 生成彩色的二维码到文件
	 * @param content 原内容
	 * @param width 宽度
	 * @param height 高度
	 * @param destImagePath 生成文件地址
	 * @param imageType 生成图片类型
	 */
	public static void encode(String content, int width, int height, String destImagePath,String imageType,int colorIndex) {
		try {
			//生成图片文件
			ImageIO.write(genBarcode(content, width, height,colorIndex), imageType, new File(destImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成彩色的二维码到输出流
	 * @param content 原内容
	 * @param width 宽度
	 * @param height 高度
	 * @param response 一般response获取输出流
	 * @param imageType 生成图片类型
	 */
	public static void encode(String content, int width, int height, HttpServletResponse response,String imageType,int colorIndex) {
		try {
			//生成图片文件
			ImageIO.write(genBarcode(content, width, height,colorIndex), imageType, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到BufferedImage
	 *
	 * @param content 二维码显示的文本
	 * @param width   二维码的宽度
	 * @param height  二维码的高度
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	private static BufferedImage genBarcode(String content, int width, int height,int colorIndex) throws WriterException, IOException {
		// 二维码写码器
		MultiFormatWriter mutiWriter = new MultiFormatWriter();
		//定义二维码内容参数
		Map<EncodeHintType, Object> hints = new HashMap<>();
		//设置字符集编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		//设置容错等级，在这里我们使用M级别
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		//边距
		hints.put(EncodeHintType.MARGIN,1);
		// 生成二维码，参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
		BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

		// 二维矩阵转为一维像素数组
		int halfW = matrix.getWidth() / 2;
		int halfH = matrix.getHeight() / 2;
		int[] pixels = new int[width * height];

		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				// 二维码颜色（RGB）
				int num1 = (int) (50 - (50.0 - 13.0) / matrix.getHeight()
						* (y + 1));
				int num2 = (int) (165 - (165.0 - 72.0) / matrix.getHeight()
						* (y + 1));
				int num3 = (int) (162 - (162.0 - 107.0)
						/ matrix.getHeight() * (y + 1));
				Color color = new Color(num1, num2, num3);
				int colorInt = color.getRGB();
				// 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
//				int [] colors = {
//						//赤色
//						new Color(255, 0, 0 ).getRGB(),
//						//橙色
//						new Color(255, 165, 0 ).getRGB(),
//						//黄色
//						new Color(255, 255, 0).getRGB(),
//						//绿色
//						new Color(0, 255, 0 ).getRGB(),
//						//青色
//						new Color(0, 255, 255).getRGB(),
//						//蓝色
//						new Color(0, 0, 255 ).getRGB(),
//						//紫色
//						new Color(139, 0, 255 ).getRGB(),
//						//黑色
//						new Color(0, 0, 0 ).getRGB()
//				};
				pixels[y * width + x] = matrix.get(x, y) ? 0x000000:0xffffff;// 0x000000:0xffffff
			}
		}

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.getRaster().setDataElements(0, 0, width, height, pixels);

		return image;
	}



	/**
	 * 生成彩色带图片的二维码写入到文件
	 * @param content 原内容
	 * @param width 宽度
	 * @param height  高度
	 * @param srcImagePath  二维码中嵌套的图片原地址
	 * @param destImagePath 生成文件地址
	 * @param imageType 输出图片类型 png gif jpg
	 */
	public static void encode(String content, int width, int height, String srcImagePath, String destImagePath,String imageType) {
		try {
			//生成图片文件
			ImageIO.write(genBarcode(content, width, height, srcImagePath), imageType, new File(destImagePath));
			System.out.println("二维码生成成功！");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成彩色带图片的二维码写入到输出流
	 * @param content 原内容
	 * @param width 宽度
	 * @param height 高度
	 * @param srcImagePath 二维码内图片原地址
	 * @param response 输出流获取
	 * @param imageType 输出图片类型 png gif jpg
	 */
	public static void encode(String content, int width, int height, String srcImagePath, HttpServletResponse response,String imageType) {
		try {
			//生成图片文件
			ImageIO.write(genBarcode(content, width, height, srcImagePath), imageType, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到BufferedImage
	 *
	 * @param content 二维码显示的文本
	 * @param width   二维码的宽度
	 * @param height  二维码的高度
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	private static BufferedImage genBarcode(String content, int width,int height, String srcImagePath) throws WriterException,IOException {
		// 图片宽度的一半
		 int IMAGE_WIDTH = 100;
		 int IMAGE_HEIGHT = 100;
		 int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
		 int FRAME_WIDTH = 2;
		 //二维码写码器
		MultiFormatWriter mutiWriter = new MultiFormatWriter();
		// 读取源图像
		BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH,
				IMAGE_HEIGHT, true);
		int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
		for (int i = 0; i < scaleImage.getWidth(); i++) {
			for (int j = 0; j < scaleImage.getHeight(); j++) {
				srcPixels[i][j] = scaleImage.getRGB(i, j);//图片的像素点
			}
		}
		//编码
		Map hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		//设置容错等级，在这里我们使用M级别
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		// 生成二维码
		BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

		// 二维矩阵转为一维像素数组
		int halfW = matrix.getWidth() / 2;
		int halfH = matrix.getHeight() / 2;
		int[] pixels = new int[width * height];

		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				// 左上角颜色,根据自己需要调整颜色范围和颜色
                /*if (x > 0 && x < 170 && y > 0 && y < 170) {
                    Color color = new Color(231, 144, 56);
                    int colorInt = color.getRGB();
                    pixels[y * width + x] = matrix.get(x, y) ? colorInt
                            : 16777215;
                } else*/
				// 读取图片
				if (x > halfW - IMAGE_HALF_WIDTH
						&& x < halfW + IMAGE_HALF_WIDTH
						&& y > halfH - IMAGE_HALF_WIDTH
						&& y < halfH + IMAGE_HALF_WIDTH) {
					pixels[y * width + x] = srcPixels[x - halfW
							+ IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
				}
				// 在图片四周形成边框
				else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
						&& x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
						&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
						+ IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH
						&& x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
						&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
						+ IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
						&& x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
						&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
						- IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
						&& x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
						&& y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
						+ IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
					pixels[y * width + x] = 0xfffffff;
				} else {
					// 二维码颜色（RGB）
					int num1 = (int) (50 - (50.0 - 13.0) / matrix.getHeight()
							* (y + 1));
					int num2 = (int) (165 - (165.0 - 72.0) / matrix.getHeight()
							* (y + 1));
					int num3 = (int) (162 - (162.0 - 107.0)
							/ matrix.getHeight() * (y + 1));
					Color color = new Color(num1, num2, num3);
					int colorInt = color.getRGB();
					// 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
					pixels[y * width + x] = matrix.get(x, y) ? colorInt : 16777215;// 0x000000:0xffffff
				}
			}
		}

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		image.getRaster().setDataElements(0, 0, width, height, pixels);

		return image;
	}

	/**
	 * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
	 *
	 * @param srcImageFile 源文件地址
	 * @param height       目标高度
	 * @param width        目标宽度
	 * @param hasFiller    比例不对时是否需要补白：true为补白; false为不补白;
	 * @throws IOException
	 */
	private static BufferedImage scale(String srcImageFile, int height, int width, boolean hasFiller) throws IOException {
		double ratio = 0.0; // 缩放比例
		File file = new File(srcImageFile);
		BufferedImage srcImage = ImageIO.read(file);
		Image destImage = srcImage.getScaledInstance(width, height,
				BufferedImage.SCALE_SMOOTH);
		// 计算比例
		if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
			if (srcImage.getHeight() > srcImage.getWidth()) {
				ratio = (new Integer(height)).doubleValue()
						/ srcImage.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue()
						/ srcImage.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(
					AffineTransform.getScaleInstance(ratio, ratio), null);
			destImage = op.filter(srcImage, null);
		}
		if (hasFiller) {// 补白
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic = image.createGraphics();
			graphic.setColor(Color.white);
			graphic.fillRect(0, 0, width, height);
			if (width == destImage.getWidth(null))
				graphic.drawImage(destImage, 0, (height - destImage.getHeight(null)) / 2,
						destImage.getWidth(null), destImage.getHeight(null),
						Color.white, null);//画图片
			else
				graphic.drawImage(destImage,(width - destImage.getWidth(null)) / 2, 0,destImage.getWidth(null), destImage.getHeight(null),
						Color.white, null);
			graphic.dispose();
			destImage = image;
		}
		return (BufferedImage) destImage;
	}


	public static void main(String[] args) {
//		String saveImgFilePath = "D://zxing.png";
//		Boolean encode = encode("我是Javen205", BarcodeFormat.QR_CODE, 3, ErrorCorrectionLevel.H, "png", 200, 200,
//				saveImgFilePath);
//		if (encode) {
//			Result result = decode(saveImgFilePath);
//			String text = result.getText();
//			System.out.println(text);
//		}
		System.out.println(new Random().nextInt(4));
	}
}
