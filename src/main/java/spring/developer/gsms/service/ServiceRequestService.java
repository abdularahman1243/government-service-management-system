package spring.developer.gsms.service;

import spring.developer.gsms.dto.CreateServiceRequestDTO;
import spring.developer.gsms.dto.PageResponseDTO;
import spring.developer.gsms.dto.ServiceRequestResponseDTO;

import java.util.List;

public interface ServiceRequestService {

    ServiceRequestResponseDTO submitRequest(
            CreateServiceRequestDTO dto,
            Long citizenId
    );

    PageResponseDTO<?> getMyRequests(
            Long citizenId,
            int page,
            int size
    );
}
