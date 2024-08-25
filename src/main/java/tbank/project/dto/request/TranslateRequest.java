package tbank.project.dto.request;

import jakarta.validation.constraints.NotNull;

public record TranslateRequest(
        @NotNull(message = "Мы сами должны угадать с какого переводить?")
        String language1,

        @NotNull(message = "МЫ сами должны угадать на какой переводить")
        String language2,

        @NotNull(message = "Пустота на всех языках одинакова")
        String request
){}
