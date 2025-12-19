package spring.developer.gsms.dto;

import java.time.LocalDateTime;

public record ServiceDTO(
        String id,
        String code,
        String name,
        String description,
        Boolean active,
        LocalDateTime createdAt
) {}
