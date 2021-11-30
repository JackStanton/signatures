package com.application.signatures.jackson.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LevelXml {
    public static final String LEVEL_NAME = "name";
    public static final String USER_SIGN = "userSign";
    public static final String SIGN = "sign";

    @JacksonXmlProperty(localName = LEVEL_NAME)
    private String name;
    @JacksonXmlProperty(localName = USER_SIGN)
    private List<Sign> userSign;
    @JacksonXmlProperty(localName = SIGN)
    private Boolean sign;
}
