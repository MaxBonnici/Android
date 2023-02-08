package com.Max.myapplication;

public class Traduction {
    public String translatedText;
    public String sourceText;
    public String sourceLanguage;
    public String finalLanguage;

    public Traduction(String translatedText, String sourceText, String sourceLanguage, String finalLanguage) {
        this.translatedText = translatedText;
        this.sourceText = sourceText;
        this.sourceLanguage = sourceLanguage;
        this.finalLanguage = finalLanguage;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public String getFinalLanguage() {
        return finalLanguage;
    }

    public String getSourceText() {
        return sourceText;
    }

    public String getTranslatedText() {
        return translatedText;
    }
}

