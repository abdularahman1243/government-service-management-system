package spring.developer.gsms.dto;

import java.time.LocalDateTime;

public record ServiceRequestResponseDTO(
        String requestNo,
        String serviceCode,
        String status,
        LocalDateTime createdAt
) {}
