package com.app.controller.dto;

import com.app.config.model.Persona;
import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest  (@NotBlank  String username,
                                 @NotBlank  String password) {
}
