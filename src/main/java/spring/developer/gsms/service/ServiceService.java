package spring.developer.gsms.service;

import spring.developer.gsms.dto.ServiceDTO;

import java.util.List;

public interface ServiceService {

    List<ServiceDTO> getActiveServices();

    ServiceDTO createService(ServiceDTO dto);

    void deactivateService(String code);
}
