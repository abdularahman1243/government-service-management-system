package spring.developer.gsms.dto;

public record ServiceDTO(
        String id,
        String code,
        String name,
        String description,
        Boolean active
) {}
