package com.application.signatures;

import com.application.signatures.jackson.entity.LevelXml;
import com.application.signatures.jackson.entity.DocumentXml;
import com.application.signatures.jackson.entity.Sign;
import com.application.signatures.jackson.parser.XmlParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class SignaturesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignaturesApplication.class, args);
        DocumentXml documentXml = new DocumentXml();
        List<LevelXml> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<Sign> map = new ArrayList<>();
            map.add(new Sign("test", false));
            LevelXml levelXml = new LevelXml("level1",map, false);
            list.add(levelXml);
        }
        for (int i = 0; i < 2; i++) {
            List<Sign> map = new ArrayList<>();
            map.add(new Sign("test", false));
            map.add(new Sign("test1", false));
            LevelXml levelXml = new LevelXml("level1",map, false);
            list.add(levelXml);
        }
        documentXml.setLevelXmls(list);
        XmlParser xmlParser = new XmlParser();
        xmlParser.objectToXml("file.xml", documentXml);
    }
}
