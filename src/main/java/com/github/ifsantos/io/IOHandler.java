package com.github.ifsantos.io;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class IOHandler {
	private static Logger log = LoggerFactory.getLogger(IOHandler.class);

	public BufferedImage readImage(String imagePath) {
		log.info("Reading image file "+imagePath);
		try {
			return ImageIO.read(new File(imagePath));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] getImageBytes(BufferedImage image) {
		log.info("Getting image bytes");
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos);
			return bos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void addImageToPdf(byte[] imageBytes, Document document) {
		log.info("Adding image to PDF Document");
		try {
			Image img = Image.getInstance(imageBytes);
			document.add(img);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> getFileNamesFromFolder(String sourceFolder, FilenameFilter fileType) {
		log.info("Listing contents from input folder");
		return Arrays.stream(new File(sourceFolder).list(fileType)).sorted().collect(Collectors.toList());
	}

	public Document pdfDocumentFactory(Path outputFilePath) {
		log.info("Creating PDF Document file");
		Document document = new Document();
		try {
			Files.createDirectories(outputFilePath.getParent());
			PdfWriter.getInstance(document, new FileOutputStream(outputFilePath.toFile()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return document;
	}
}
