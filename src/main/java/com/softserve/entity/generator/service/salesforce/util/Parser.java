package com.softserve.entity.generator.service.salesforce.util;

import org.apache.log4j.Logger;

import java.util.regex.Pattern;

public class Parser
{
    private static final Logger logger = Logger.getLogger(Parser.class);

    public static String parseSObjectJson(String sObjectJson)
    {
        sObjectJson = Pattern.compile("\"records\" : \\[ \\{.*? \\},", Pattern.DOTALL)
                .matcher(sObjectJson)
                .replaceAll("");

        sObjectJson = Pattern.compile("\"attributes\" : \\{.*?\\},", Pattern.DOTALL)
                .matcher(sObjectJson)
                .replaceAll("");

        sObjectJson = sObjectJson
                .replaceAll("\\{\\n.*\"totalSize\" : \\d+,\\n.*\"done\" :.*,", "")
                .replaceAll("\\]", "] }")
                .replaceAll("\\}\\n.*} \\] \\}\\n.*\\}", "")
                .replaceAll("\\{\\n.*\\n.*\\n.*\\{", "\\{");

        sObjectJson = FiledFormatter.toJavaStyle(sObjectJson)
                .replaceAll("\\{ null\\n.*\\} \\] \\}", "\\]");

        System.out.println(sObjectJson);

        return "{ " + sObjectJson;
    }
}
