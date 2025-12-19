package spring.developer.gsms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.developer.gsms.document.ServiceDocument;
import spring.developer.gsms.dto.ServiceDTO;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    @Mapping(target = "createdAt",ignore = true)
    ServiceDTO toDto(ServiceDocument document);

    ServiceDocument toDocument(ServiceDTO dto);
}
