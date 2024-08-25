package tbank.project.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class YandexRequest {
    @JsonProperty("sourceLanguageCode")
    private String language1;

    @JsonProperty("targetLanguageCode")
    private String targetLanguageCode;

    @JsonProperty("texts")
    private String texts;

    @JsonProperty("folderId")
    private String folderId;
}
