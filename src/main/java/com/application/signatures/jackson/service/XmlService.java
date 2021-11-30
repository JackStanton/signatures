package com.application.signatures.jackson.service;

import com.application.signatures.jackson.entity.DocumentXml;
import com.application.signatures.jackson.entity.LevelXml;
import com.application.signatures.jackson.entity.Sign;
import com.application.signatures.jackson.parser.XmlParser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.stream.Collectors;

@Service
public class XmlService {

    public static   DocumentXml getDocument(File file){
        return XmlParser.xmlToObject(file);
    }

    public void setSign(String fileName, String level, String login, Boolean sign){
        DocumentXml documentXml = getDocument(new File("documents/"+fileName));
        LevelXml currentLevel = documentXml.getLevelXmls().stream().filter(it -> it.getName().equals(level)).collect(Collectors.toList()).get(0);
        int countLevels = documentXml.getLevelXmls().size();
        Sign currentSign = currentLevel.getUserSign().stream().filter(it -> it.getLogin().equals(login)).collect(Collectors.toList()).get(0);
        currentSign.setSign(true);
        currentSign.setTypeSign(sign);
        if(currentLevel.getOperation().equals("null")){
            currentLevel.setSign(sign);
            currentLevel.setActive(false);
            int next = currentLevel.getNumber()+1;
            if (next < countLevels){
                documentXml.getLevelXmls().get(next).setActive(true);
            }
        }
        if(currentLevel.getOperation().equals("or")){
            sign = currentLevel.getUserSign().stream().anyMatch(Sign::getTypeSign);
            currentLevel.setSign(sign);
            if (currentLevel.getUserSign().stream().allMatch(Sign::getSign)) {
                currentLevel.setActive(false);
                int next = currentLevel.getNumber()+1;
                if (next < countLevels) documentXml.getLevelXmls().get(next).setActive(true);
            }
        }
        if(currentLevel.getOperation().equals("and")){
            sign = currentLevel.getUserSign().stream().allMatch(Sign::getTypeSign);
            currentLevel.setSign(sign);
            if (currentLevel.getUserSign().stream().allMatch(Sign::getSign)) {
                currentLevel.setActive(false);
                int next = currentLevel.getNumber()+1;
                if (next < countLevels) documentXml.getLevelXmls().get(next).setActive(true);
            }
        }
        boolean fullSign = documentXml.getLevelXmls().stream().allMatch(LevelXml::getSign);
        documentXml.setSign(fullSign);
        saveDocument(fileName, documentXml);
    }

    public static void saveDocument(String filename, DocumentXml document){
        XmlParser.objectToXml("documents/"+filename, document);
    }
}
