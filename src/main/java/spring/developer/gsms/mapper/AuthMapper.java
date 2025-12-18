package spring.developer.gsms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.developer.gsms.dto.auth.RegisterRequestDTO;
import spring.developer.gsms.entity.UserModel;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserModel toEntity(RegisterRequestDTO dto);
}

