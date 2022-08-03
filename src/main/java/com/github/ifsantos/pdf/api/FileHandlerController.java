package com.github.ifsantos.pdf.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.ifsantos.pdf.collection.PDFCollection;
import com.github.ifsantos.pdf.service.PDFStorageService;

@RestController
@RequestMapping("api/file")
public class FileHandlerController {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    PDFCollection pdfCollection;
    @Autowired
    PDFStorageService pdfService;
    
    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] getPDFFile(@RequestParam("id") String id) {
        return pdfCollection.getFile(id);
    }

    @PostMapping(value = "/pdf", 
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> createPdfFile(@RequestParam("file") MultipartFile f){
        
        log.debug("Filesize: {}",f.getSize());
        log.debug("ContentType: {}",f.getContentType());
        log.debug("OriginalFilename: {}",f.getOriginalFilename());
        log.debug("Name: {}",f.getName());
        log.debug("Resource exists: {}",f.getResource().exists());
        log.debug("Resource Filename: {}",f.getResource().getFilename());
        log.debug("========================");
        String fileID = null;
        try {
            fileID = pdfService.store(f);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Impossible to create pdf file", e);
        }
        Map<String,String> result = new HashMap<>();
        result.put("id", fileID);
		return result;
    }

}