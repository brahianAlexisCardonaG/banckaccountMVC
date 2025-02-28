package com.MVC.bankaccount.securityjwt.infrastructure.controller.request;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(@NotBlank String username,
                               @NotBlank String password) {
}