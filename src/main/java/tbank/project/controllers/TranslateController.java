package tbank.project.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbank.project.dto.request.TranslateRequest;
import tbank.project.dto.response.TranslateResponse;
import tbank.project.services.TranslateService;

import java.io.IOException;

@RestController
@RequestMapping("translator/translate")
@RequiredArgsConstructor
public class TranslateController {

    private final TranslateService translateService;

    @PostMapping
    public TranslateResponse translate(@Valid @RequestBody TranslateRequest request, HttpServletRequest httpServletRequest) throws IOException {
        return translateService.translate(request, httpServletRequest);
    }

}
