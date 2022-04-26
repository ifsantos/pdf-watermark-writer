package com.github.ifsantos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFGenerartor {

	
	public void generatePDF(String sourceFolder, String outputFileName) throws DocumentException, MalformedURLException, IOException, URISyntaxException {
		List<String> sourceFiles = getFilesFromFolder(sourceFolder);
		Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(outputFileName));
		document.open();
		Image img = Image.getInstance(path.toAbsolutePath().toString());
		
		document.add(img);

		document.close();
		
	}

	private List<String> getFilesFromFolder(String sourceFolder) {
		// TODO Auto-generated method stub
		return null;
	}
	private BufferedImage readImage(String imagePath) {
		try {
			return ImageIO.read(new File("image.png"));
		} catch (Exception e) {
			return null;
		}
	}
	private OutputStream writeWtermark(BufferedImage image, String studentName, String cpf) {
		
	    //get the Graphics object
	    Graphics g = image.getGraphics();
	    //set font
	    g.setFont(g.getFont().deriveFont(25f));
	    g.setColor(Color.RED);
	    
	    //display the text at the coordinates(x=50, y=150)
	    g.drawString(studentName, 50, 150);
	    g.drawString(cpf, 50, 350);
	    
	    g.drawString(studentName, 50, 650);
	    g.drawString(cpf, 50, 950);
	    
	    g.dispose();
	    //write the image
	    OutputStream bos = new ByteArrayOutputStream();
	    try {
			ImageIO.write(image, "png", bos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return bos;
	}
	
}
