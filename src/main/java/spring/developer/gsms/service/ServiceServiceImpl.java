package spring.developer.gsms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import spring.developer.gsms.document.ServiceDocument;
import spring.developer.gsms.dto.ServiceDTO;
import spring.developer.gsms.exception.BusinessException;
import spring.developer.gsms.exception.ResourceNotFoundException;
import spring.developer.gsms.mapper.ServiceMapper;
import spring.developer.gsms.repository.ServiceMongoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceMongoRepository repository;
    private final ServiceMapper mapper;

    // ===================== CACHE =====================
    @Override
    @Cacheable(value = "services", key = "'active'")
    public List<ServiceDTO> getActiveServices() {
        return repository.findByActiveTrue()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    // ===================== ADMIN ACTIONS =====================
    @Override
    @CacheEvict(value = "services", key = "'active'")
    public ServiceDTO createService(ServiceDTO dto) {

        if (repository.existsByCode(dto.code())) {
            throw new BusinessException(
                    "Service code already exists",
                    spring.developer.gsms.exception.ApiErrorCode.BUSINESS_ERROR
            );
        }

        ServiceDocument document = mapper.toDocument(dto);
        document.setActive(true);
        document.setCreatedAt(LocalDateTime.now());

        repository.save(document);

        return mapper.toDto(document);
    }

    @Override
    @CacheEvict(value = "services", key = "'active'")
    public void deactivateService(String code) {

        ServiceDocument service = repository.findByCode(code)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Service not found"));

        service.setActive(false);
        repository.save(service);
    }
}
