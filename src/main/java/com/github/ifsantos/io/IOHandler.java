package com.github.ifsantos.io;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class IOHandler {
	public static final String S = File.separator;
	
	String outputFilePath;
	

	public BufferedImage readImage(String imagePath) {
		try {
			return ImageIO.read(new File(imagePath));
		} catch (Exception e) {
			return null;
		}
	}

	public void writeImage(BufferedImage image, ByteArrayOutputStream bos) {
		try {
			ImageIO.write(image, "png", bos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getFileNamesFromFolder(String sourceFolder, FilenameFilter fileType) {
		return Arrays.stream(new File(sourceFolder).list(fileType)).sorted().collect(Collectors.toList());
	}

	public Document pdfDocumentFactory(String outputFolder, String outputFileName) {
		Document document = new Document();
		try {
			Files.createDirectories(Paths.get(outputFolder));
			PdfWriter.getInstance(document, new FileOutputStream(outputFolder + S + outputFileName));
		} catch (Exception e) {
			return null;
		}
		return document;
	}

	public String getOutputFilePath() {
		return this.outputFilePath;
	}

}
