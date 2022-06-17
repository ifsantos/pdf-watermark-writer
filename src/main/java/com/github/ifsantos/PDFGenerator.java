package com.github.ifsantos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.github.ifsantos.pdf.io.IOHandler;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PDFGenerator {
	static final String S = File.separator;
	private static Logger log = LoggerFactory.getLogger(PDFGenerator.class);
	@Autowired
	private IOHandler io;

	private List<byte[]> images = new ArrayList<>();

	private String inputFolder;

	public String generatePDF(String licencedName, String cpf, Long timestamp) {
		String outputFileName = licencedName.replace(" ", "_") + timestamp + ".pdf";
		Path outputFilePath = Path.of(inputFolder).resolve("output").resolve(outputFileName);
		
		log.info("Resolvigd output path: " + outputFilePath.toString());

		Document document = io.pdfDocumentFactory(outputFilePath);
		formatLayout(document);
		document.open();
		loadImages();
		
		images.forEach(contentImage -> { 
			byte[] watermarkedImgBytes = createWatermarkedImage(contentImage, licencedName, cpf);
			io.addImageToPdf(watermarkedImgBytes, document);
		});

		document.close();
		return outputFilePath.toString();
	}
	private void loadImages(){
		log.info("Loading images from input folder to memory");
		if (images.isEmpty()) {
			io.getFileNamesFromFolder(inputFolder, getPngFileFilter()).forEach(inputFile -> {
				images.add(io.readImage(inputFolder + S + inputFile));
			});
		}
	}

	private void formatLayout(Document document) {
		log.info("Formatting PDF layout");
			document.setPageSize(new Rectangle(1920, 1080));
		document.setMargins(0, 0, 0, 0);
	}

	private byte[] createWatermarkedImage(byte[] imageBytes, String licencedName, String cpf) {
		log.info("Creating watermarked image");
		BufferedImage watermarked = io.getBufferedImage(imageBytes);
		Graphics g = watermarked.getGraphics();
		g.setFont(g.getFont().deriveFont(35f));
		g.setColor(new Color(0, 0, 0, 125));
		drawCPF(cpf, g);
		g.setFont(g.getFont().deriveFont(45f));
		drawLicensedName(licencedName, g);
		g.dispose();

		return io.getImageBytes(watermarked);
	}

	private void drawLicensedName(String licencedName, Graphics g) {
		log.info("Drawing watermark");
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

	public String getInputFolder() {
		return inputFolder;
	}

	public void setInputFolder(String inputFolder) {
		this.inputFolder = inputFolder;
		this.images.clear();
	}
}
