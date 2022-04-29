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
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class IOHandler {

	public BufferedImage readImage(String imagePath) {
		try {
			return ImageIO.read(new File(imagePath));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] getImageBytes(BufferedImage image) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", bos);
			return bos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void addImageToPdf(byte[] imageBytes, Document document) {
		try {
			Image img = Image.getInstance(imageBytes);
			document.add(img);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> getFileNamesFromFolder(String sourceFolder, FilenameFilter fileType) {
		return Arrays.stream(new File(sourceFolder).list(fileType)).sorted().collect(Collectors.toList());
	}

	public Document pdfDocumentFactory(String outputFilePath) {
		Document document = new Document();
		try {
			Files.createDirectories(Paths.get(outputFilePath).getParent());
			PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return document;
	}
}