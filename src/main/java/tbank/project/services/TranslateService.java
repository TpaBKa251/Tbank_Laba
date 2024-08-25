package tbank.project.services;

import jakarta.servlet.http.HttpServletRequest;
import tbank.project.dto.request.TranslateRequest;
import tbank.project.dto.response.TranslateResponse;

import java.io.IOException;

public interface TranslateService {
    TranslateResponse translate(TranslateRequest translateRequest, HttpServletRequest httpServletRequest) throws IOException;
}
