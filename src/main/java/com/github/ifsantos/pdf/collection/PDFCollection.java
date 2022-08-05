package com.github.ifsantos.pdf.collection;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Scope("singleton")
public class PDFCollection {
    Logger log = LoggerFactory.getLogger(getClass());
    private Map<String,String> collection;

    public PDFCollection(){
        this.collection = new HashMap<>();
        log.debug("PDFCollection being instantiated...");
    }

    public void add(String id, String path){
        collection.put(id, path);
    }

    public byte[] getFile(String id){
        log.debug("-> Getting file for id: {}", id);
        collection.entrySet().stream().forEach(a -> log.debug("-> '{}': '{}'' ",a.getKey(), a.getValue()));
        
        try(FileInputStream fis = new FileInputStream(collection.get(id))){
            return IOUtils.toByteArray(fis);
        } catch (IOException e) {
            throw new FileNotFoundException(e, id);
        }
    }
    
    public static class FileNotFoundException extends ResponseStatusException{
        public FileNotFoundException(Throwable t, String id){
            super(HttpStatus.NOT_FOUND, String.format("File ID %s Not Found",id),t);
        }
    }

    public Map<String, String> getCollection() {
        return collection;
    }
}   
