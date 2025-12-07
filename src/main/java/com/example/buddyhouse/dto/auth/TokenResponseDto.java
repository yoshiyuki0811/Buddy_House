package com.example.buddyhouse.dto.auth;

import lombok.Builder;


@Builder
public record TokenResponseDto(String accessToken) {

}
