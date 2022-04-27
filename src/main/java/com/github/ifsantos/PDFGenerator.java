package com.github.ifsantos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;


@Service
public class PDFGenerator {

	
	public String generatePDF(String sourceFolder, String outputFileName, String licencedName, String cpf) throws DocumentException, MalformedURLException, IOException, URISyntaxException {
		List<String> originalFiles = getFilesFromFolder(sourceFolder);

		Document document = new Document();
		String outputFilePath = sourceFolder+"//output//"+outputFileName+System.currentTimeMillis()+".pdf";
		PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));
		document.setPageSize(new Rectangle(1920, 1080));
		document.setMargins(0, 0, 0, 0);
		document.open();
		
		for (String originalFile : originalFiles) {

			byte[] watermarkedImageBytes = writeWatermark(readImage(sourceFolder+"//"+originalFile), licencedName, cpf).toByteArray();
			Image img = Image.getInstance(watermarkedImageBytes);			
			document.add(img);
		}
		document.close();
		return outputFilePath;
	}

	private List<String> getFilesFromFolder(String sourceFolder) {
		File folder = new File(sourceFolder);
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".png");
			}
		};
		if (folder.isDirectory()) {
			
			List<String> files = Arrays.asList(folder.list(filter));
			files.sort(getStringComparator());
			return files;
		}
		return null;
	}

	private Comparator<String> getStringComparator() {
		return new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		};
	}
	
	private BufferedImage readImage(String imagePath) {
		try {
			return ImageIO.read(new File(imagePath));
		} catch (Exception e) {
			return null;
		}
	}
	
	private ByteArrayOutputStream writeWatermark(BufferedImage image, String licencedName, String cpf) {
		
	    //get the Graphics object
	    Graphics g = image.getGraphics();
	    //set font
	    g.setFont(g.getFont().deriveFont(35f));
	    g.setColor(new Color(255, 0, 0, 150));
	    
	    
	    //display the text at the coordinates(x=50, y=150)
	    g.drawString(licencedName, 50, 150);
	    g.drawString(cpf, 50, 350);
	    
	    g.drawString(licencedName, 550, 650);
	    g.drawString(cpf, 550, 950);
	    
	    g.dispose();
	    //write the image
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    try {
			ImageIO.write(image, "png", bos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return bos;
	}
	
}
