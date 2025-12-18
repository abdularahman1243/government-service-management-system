package spring.developer.gsms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.developer.gsms.document.ServiceRequestDocument;
import spring.developer.gsms.dto.ServiceRequestResponseDTO;

@Mapper(componentModel = "spring")
public interface ServiceRequestMapper {

    @Mapping(source = "serviceCode", target = "serviceCode")
    @Mapping(source = "requestNo", target = "requestNo")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "createdAt")
    ServiceRequestResponseDTO toResponseDto(ServiceRequestDocument document);
}
