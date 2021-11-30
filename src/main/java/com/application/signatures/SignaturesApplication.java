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
        int[] arr = {1,1,2,3,1,2,1};
        for (int i = 0; i < arr.length; i++) {
            boolean active = i == 0;
            List<Sign> map = new ArrayList<>();
            String operator = "null";
            if(arr[i] == 1){
                map.add(new Sign("test", false, false));
            }else {
                if(arr[i] == 3) operator = "or";else operator = "and";
                map = new ArrayList<>();
                map.add(new Sign("test", false, false));
                map.add(new Sign("test1", false, false));
            }
            LevelXml levelXml;
            levelXml = new LevelXml(i, "level"+i,map, false,operator, active);
            list.add(levelXml);
        }
        documentXml.setLevelXmls(list);
        XmlParser.objectToXml("file1.xml", documentXml);
    }
}
