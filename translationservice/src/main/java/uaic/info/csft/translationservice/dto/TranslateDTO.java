package uaic.info.csft.translationservice.dto;

import lombok.Data;

@Data
public class TranslateDTO {

    private String sourceLanguage;
    private String targetLanguage;
    private String text;
}
