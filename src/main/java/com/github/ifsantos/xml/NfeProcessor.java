package com.github.ifsantos.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBElement;

import br.inf.portalfiscal.nfe.TNfeProc;

public class NfeProcessor {
    TNfeProc nfe;
    String path = "C:\\code_home\\github\\pdf-watermark-writer\\src\\main\\resources\\v4.00-procNFe.xml";

    public void run() throws Exception{
        System.out.println("loading XML...");
        
        JAXBContext jaxbContext = JAXBContext.newInstance(TNfeProc.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<TNfeProc> el = (JAXBElement<TNfeProc>) jaxbUnmarshaller.unmarshal(new File(path));
        nfe = el.getValue();
        
        String xNome = nfe.getNFe().getInfNFe().getEmit().getXNome();
        System.out.println("Emissor da NOTA: " + xNome);
        String cpl = nfe.getNFe().getInfNFe().getInfAdic().getInfCpl();
        System.out.println("Info da NOTA: " + cpl);
    }
    
}
