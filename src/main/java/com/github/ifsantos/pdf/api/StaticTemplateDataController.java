package com.github.ifsantos.pdf.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.ifsantos.pdf.collection.PDFCollection;
import com.github.ifsantos.pdf.service.PDFStorageService;

@Controller
public class StaticTemplateDataController {
    @Autowired
    PDFCollection col;
    @Autowired
    PDFStorageService pdfService;


    @GetMapping("/")
    public String getCollection(Model model){
        model.addAttribute("files", col.getCollection().keySet());
        return "uploadForm";
    }

    @PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile f, RedirectAttributes redirectAttributes) {
        String fileID;      
		try {
            fileID = pdfService.store(f);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Impossible to create pdf file", e);
        }
		redirectAttributes.addFlashAttribute("message",
				String.format("You successfully uploaded %s!", fileID));
        redirectAttributes.addFlashAttribute("files",col.getCollection().keySet().toArray());
		return "redirect:/";
	}


}
