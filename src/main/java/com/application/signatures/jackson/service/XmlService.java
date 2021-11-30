package com.application.signatures.jackson.service;

import com.application.signatures.jackson.entity.DocumentXml;
import com.application.signatures.jackson.entity.LevelXml;
import com.application.signatures.jackson.parser.XmlParser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class XmlService {

    private final XmlParser xmlParser;

    public XmlService(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    public DocumentXml getDocument(File file){
        return xmlParser.xmlToObject(file);
    }

    public void saveDocument(String filename, List<LevelXml> document){
        xmlParser.objectToXml(filename, document);
    }
}
