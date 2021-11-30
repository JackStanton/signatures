package com.application.signatures.jackson.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(value = "isFullSign", allowSetters = true)
public class LevelXml {
    public static final String LEVEL_NUM = "number";
    public static final String LEVEL_NAME = "name";
    public static final String USER_SIGN = "userSign";
    public static final String SIGN = "sign";
    public static final String OPERATION = "operation";
    public static final String ACTIVE = "active";

    @JacksonXmlProperty(localName = LEVEL_NUM)
    private int number;
    @JacksonXmlProperty(localName = LEVEL_NAME)
    private String name;
    @JacksonXmlProperty(localName = USER_SIGN)
    private List<Sign> userSign;
    @JacksonXmlProperty(localName = SIGN)
    private Boolean sign;
    @JacksonXmlProperty(localName = OPERATION)
    private String operation;
    @JacksonXmlProperty(localName = ACTIVE)
    private boolean active;


    public boolean checkUser(String login){
        return userSign.stream().anyMatch(it->it.getLogin().equals(login));
    }

    public boolean isSign(String login){
        return userSign.stream().filter(it->it.getLogin().equals(login)).anyMatch(Sign::getSign);
    }

    public boolean isFullSign(int i){
        return userSign.stream().allMatch(Sign::getSign);
    }
}
