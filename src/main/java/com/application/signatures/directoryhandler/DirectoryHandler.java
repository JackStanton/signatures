package com.application.signatures.directoryhandler;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Component
public class DirectoryHandler {

    public static List<File> getFiles(String path){
        File dir = new File(path);
        File[] arrFiles = dir.listFiles();
        if(arrFiles != null) return Arrays.asList(arrFiles);
        return null;
    }
}
