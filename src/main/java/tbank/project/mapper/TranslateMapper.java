package tbank.project.mapper;

import org.springframework.stereotype.Component;
import tbank.project.entity.Translator;
import tbank.project.dto.response.TranslateResponse;

@Component
public class TranslateMapper {

    public static TranslateResponse mapTranslateResponse(Translator translator){
        return new TranslateResponse(
                translator.getAnswer()
        );
    }
}
