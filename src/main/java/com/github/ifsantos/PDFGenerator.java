package com.github.ifsantos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PDFGenerator {

	public String generatePDF(String inputFolder, String licencedName, String cpf)  {
		String outputFileName = licencedName.replace(" ", "_");
		Document document = new Document();
		String outputFolder = inputFolder + "//output//";
		String outputFilePath = outputFolder + outputFileName + System.currentTimeMillis() + ".pdf";
		try {
			Files.createDirectories(Paths.get(outputFolder));
			PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));
			document.setPageSize(new Rectangle(1920, 1080));
			document.setMargins(0, 0, 0, 0);
			document.open();
			for (String inputFile : getFilesFromFolder(inputFolder)) {
				byte[] watermarkedImageBytes = writeWatermark(readImage(inputFolder + "//" + inputFile), licencedName, cpf).toByteArray();
				Image img = Image.getInstance(watermarkedImageBytes);			
				document.add(img);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		document.close();
		return outputFilePath;
	}

	private List<String> getFilesFromFolder(String sourceFolder) {
		File folder = new File(sourceFolder);
		if (folder.isDirectory()) {
			List<String> files = Arrays.asList(folder.list(getPngFileFilter()));
			files.sort(getStringComparator());
			return files;
		}
		return null;
	}

	private BufferedImage readImage(String imagePath) {
		try {
			return ImageIO.read(new File(imagePath));
		} catch (Exception e) {
			return null;
		}
	}
	
	private ByteArrayOutputStream writeWatermark(BufferedImage image, String licencedName, String cpf) {
	    Graphics g = image.getGraphics();
	    g.setFont(g.getFont().deriveFont(35f));
	    g.setColor(new Color(0, 0, 0, 125));
	    drawCPF(cpf, g);
	    g.setFont(g.getFont().deriveFont(45f));
	    drawLicensedName(licencedName, g);
	    g.dispose();
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    try {
			ImageIO.write(image, "png", bos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return bos;
	}

	private void drawLicensedName(String licencedName, Graphics g) {
		g.drawString(licencedName, 550, 250);
	    g.drawString(licencedName, 550, 750);
	}

	private void drawCPF(String cpf, Graphics g) {
		g.drawString(cpf, 550, 350);
	    g.drawString(cpf, 550, 850);
	}
	
	private FilenameFilter getPngFileFilter() {
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".png");
			}
		};
	}
	
	private Comparator<String> getStringComparator() {
		return new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		};
	}
}
