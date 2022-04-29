package com.github.ifsantos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ifsantos.io.IOHandler;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;

@Service
public class PDFGenerator {
	@Autowired
	private IOHandler io;

	String S = File.separator;

	public String generatePDF(String inputFolder, String licencedName, String cpf, Long timestamp) {
		String outputFileName = licencedName.replace(" ", "_") + timestamp + ".pdf";
		String outputFilePath = inputFolder + S + "output" + S + outputFileName;

		Document document = io.pdfDocumentFactory(outputFilePath);
		formatLayout(document);
		document.open();

		io.getFileNamesFromFolder(inputFolder, getPngFileFilter()).forEach(inputFile -> {
			BufferedImage contentImage = io.readImage(inputFolder + S + inputFile);
			drawWatermark(contentImage, licencedName, cpf);
			byte[] watermarkedImgBytes = io.getImageBytes(contentImage);
			io.addImageToPdf(watermarkedImgBytes, document);
		});

		document.close();
		return outputFilePath;
	}

	private void formatLayout(Document document) {
		document.setPageSize(new Rectangle(1920, 1080));
		document.setMargins(0, 0, 0, 0);
	}

	private void drawWatermark(BufferedImage image, String licencedName, String cpf) {
		Graphics g = image.getGraphics();
		g.setFont(g.getFont().deriveFont(35f));
		g.setColor(new Color(0, 0, 0, 125));
		drawCPF(cpf, g);
		g.setFont(g.getFont().deriveFont(45f));
		drawLicensedName(licencedName, g);
		g.dispose();
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
}
