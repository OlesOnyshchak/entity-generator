package com.softserve.entity.generator.service.request.util;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser
{

    public String parseSObjectJson(String sObjectJson)
    {

        String removeRecords = "\"records\" : \\[ \\{.*? \\},";
        sObjectJson = Pattern.compile(removeRecords, Pattern.DOTALL).matcher(sObjectJson).replaceAll("");

        String removeAttributes = "\"attributes\" : \\{.*?\\},";
        sObjectJson = Pattern.compile(removeAttributes, Pattern.DOTALL).matcher(sObjectJson).replaceAll("");

        sObjectJson = sObjectJson
                .replaceAll("\\{\\n.*\"totalSize\" : .,\\n.*\"done\" :.*,", "")
                .replaceAll("\\]", "] }")
                .replaceAll("\\}\\n.*} \\] \\}\\n.*\\}", "")
                .replaceAll("\\{\\n.*\\n.*\\n.*\\{", "\\{");

        StringBuilder stringBuilder = new StringBuilder(sObjectJson);
        Pattern pattern = Pattern.compile("\"[A-Z].*__c\"");
        Matcher matcher = pattern.matcher(stringBuilder);

        while (matcher.find())
        {
            stringBuilder.setCharAt(matcher.start() + 1, Character.toLowerCase(stringBuilder.charAt(matcher.start() + 1)));
        }

         pattern = Pattern.compile("\"[A-Z].*__r\"");
         matcher = pattern.matcher(stringBuilder);

        while (matcher.find())
        {
            stringBuilder.setCharAt(matcher.start() + 1, Character.toLowerCase(stringBuilder.charAt(matcher.start() + 1)));
        }

         pattern = Pattern.compile("\"Name\"");
         matcher = pattern.matcher(stringBuilder);

        while (matcher.find())
        {
            stringBuilder.setCharAt(matcher.start() + 1, Character.toLowerCase(stringBuilder.charAt(matcher.start() + 1)));
        }

        sObjectJson = stringBuilder.toString();
        sObjectJson = sObjectJson
            .replaceAll("__c","")
            . replaceAll("__r. :", "\" : \\[ \\{");


        System.out.println("++++++++++");
        System.out.println(sObjectJson);
        System.out.println("++++++++++");

        return "{ "+sObjectJson;
    }
}
