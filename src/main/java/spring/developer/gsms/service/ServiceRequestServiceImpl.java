package spring.developer.gsms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import spring.developer.gsms.document.ServiceRequestDocument;
import spring.developer.gsms.dto.CreateServiceRequestDTO;
import spring.developer.gsms.dto.PageResponseDTO;
import spring.developer.gsms.dto.ServiceRequestResponseDTO;
import spring.developer.gsms.exception.ResourceNotFoundException;
import spring.developer.gsms.mapper.ServiceRequestMapper;
import spring.developer.gsms.repository.ServiceMongoRepository;
import spring.developer.gsms.repository.ServiceRequestMongoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final ServiceRequestMongoRepository requestRepository;
    private final ServiceMongoRepository serviceRepository;
    private final ServiceRequestMapper mapper;

    @Override
    public ServiceRequestResponseDTO submitRequest(
            CreateServiceRequestDTO dto,
            Long citizenId
    ) {

        serviceRepository.findByCode(dto.serviceCode())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        ServiceRequestDocument document =
                ServiceRequestDocument.builder()
                        .requestNo(generateRequestNo())
                        .serviceCode(dto.serviceCode())
                        .citizenId(citizenId)
                        .status("SUBMITTED")
                        .details(dto.details())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

        requestRepository.save(document);

        return mapper.toResponseDto(document);
    }

    private String generateRequestNo() {
        return "GSMS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public PageResponseDTO<ServiceRequestResponseDTO> getMyRequests(
            Long citizenId,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by("createdAt").descending());

        Page<ServiceRequestDocument> pageResult =
                requestRepository.findByCitizenId(citizenId, pageable);

        List<ServiceRequestResponseDTO> content =
                pageResult.getContent()
                        .stream()
                        .map(mapper::toResponseDto)
                        .toList();

        return new PageResponseDTO<>(
                content,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.isLast()
        );
    }
    @Override
    public List<ServiceRequestResponseDTO> getPendingRequests() {

        return requestRepository.findByStatus("SUBMITTED")
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @Override
    public void approve(String requestNo, Long officerId) {

        ServiceRequestDocument request = requestRepository.findByRequestNo(requestNo)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        String fromStatus = request.getStatus();
        request.setStatus("APPROVED");
        request.setUpdatedAt(LocalDateTime.now());

        addHistory(request, fromStatus, "APPROVED", officerId, "Approved");

        requestRepository.save(request);
    }

    @Override
    public void reject(String requestNo, Long officerId, String comment) {

        ServiceRequestDocument request = requestRepository.findByRequestNo(requestNo)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        String fromStatus = request.getStatus();
        request.setStatus("REJECTED");
        request.setUpdatedAt(LocalDateTime.now());

        addHistory(request, fromStatus, "REJECTED", officerId, comment);

        requestRepository.save(request);
    }

    private void addHistory(
            ServiceRequestDocument request,
            String from,
            String to,
            Long by,
            String comment
    ) {
        if (request.getHistory() == null) {
            request.setHistory(new ArrayList<>());
        }

        request.getHistory().add(
                new ServiceRequestDocument.StatusHistory(
                        from,
                        to,
                        by,
                        LocalDateTime.now(),
                        comment
                )
        );
    }

}
