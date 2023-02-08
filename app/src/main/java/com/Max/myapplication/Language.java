package com.Max.myapplication;

public class Language {
    private String language;
    private String name;
    private String flag;

    public Language(String language, String name) {
        this.language = language;
        this.name = name;
        this.flag = countryCodeToEmoji(language);
    }

    public String getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }
    public String countryCodeToEmoji(String code) {

        // offset between uppercase ASCII and regional indicator symbols
        int OFFSET = 127397;

        // validate code
        if(code == null || code.length() != 2) {
            return "";
        }

        //fix for uk -> gb
        if (code.equalsIgnoreCase("uk")) {
            code = "gb";
        }

        // convert code to uppercase
        code = code.toUpperCase();

        StringBuilder emojiStr = new StringBuilder();

        //loop all characters
        for (int i = 0; i < code.length(); i++) {
            emojiStr.appendCodePoint(code.charAt(i) + OFFSET);
        }

        // return emoji
        return emojiStr.toString();
    }

    public String toString() {
        return flag + "  "+ name  ;
    }

}
