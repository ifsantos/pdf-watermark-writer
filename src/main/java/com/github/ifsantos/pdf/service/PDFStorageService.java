package com.github.ifsantos.pdf.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.ifsantos.PDFGenerator;
import com.github.ifsantos.pdf.collection.PDFCollection;
import com.github.ifsantos.pdf.io.ZipUtility;

@Service
public class PDFStorageService {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    PDFGenerator pdfGen;
    @Autowired
    PDFCollection col;
    @Autowired
    ZipUtility zip;


    public String store(MultipartFile f) throws IOException{
        String tmpFolder = Files.createTempDirectory("pdf").toString();

        zip.unzip(f.getInputStream(), tmpFolder);
        
        pdfGen.setInputFolder(tmpFolder);
        String generatedFilePath = pdfGen.generatePDF("licencedName", "cpf", System.currentTimeMillis());
        
        String resultID = UUID.randomUUID().toString();
        col.add(resultID, generatedFilePath);

        return resultID;
    }
}
