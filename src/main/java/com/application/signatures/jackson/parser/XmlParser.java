package com.application.signatures.jackson.parser;


import com.application.signatures.jackson.entity.DocumentXml;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class XmlParser {

    public static void objectToXml(String filename, Object obj) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(filename), obj);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static DocumentXml xmlToObject(File file) {
        DocumentXml documentXml = new DocumentXml();
        try{
            XmlMapper xmlMapper = new XmlMapper();
            XMLInputFactory f = XMLInputFactory.newFactory();
            XMLStreamReader dataFromFile = f.createXMLStreamReader(new FileInputStream(file));
            documentXml = xmlMapper.readValue(dataFromFile, DocumentXml.class);
        }catch (IOException | XMLStreamException e){
            e.printStackTrace();
        }
        return documentXml;
    }
}
