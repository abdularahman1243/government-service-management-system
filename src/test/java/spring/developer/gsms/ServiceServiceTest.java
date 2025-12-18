package spring.developer.gsms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.developer.gsms.document.ServiceDocument;
import spring.developer.gsms.dto.ServiceDTO;
import spring.developer.gsms.mapper.ServiceMapper;
import spring.developer.gsms.repository.ServiceMongoRepository;
import spring.developer.gsms.service.ServiceServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceServiceTest {

    @Mock
    private ServiceMongoRepository repository;

    @Mock
    private ServiceMapper mapper;

    @InjectMocks
    private ServiceServiceImpl service;

    @Test
    void shouldThrowExceptionWhenCodeExists() {
        ServiceDTO dto = new ServiceDTO(null, "S001", "Passport", "Gov service", true);

        when(repository.existsByCode("S001")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.createService(dto));

        verify(repository, never()).save(any());
    }

    @Test
    void shouldCreateServiceSuccessfully() {
        ServiceDTO dto = new ServiceDTO(null, "S001", "Passport", "Gov service", true);

        when(repository.existsByCode(anyString())).thenReturn(false);

        ServiceDocument docToSave = ServiceDocument.builder()
                .code("S001").name("Passport").description("Gov service").active(true)
                .build();

        ServiceDocument savedDoc = ServiceDocument.builder()
                .id("mongo-id-1").code("S001").name("Passport").description("Gov service").active(true)
                .build();

        when(mapper.toDocument(any(ServiceDTO.class))).thenReturn(docToSave);
        when(repository.save(any(ServiceDocument.class))).thenReturn(savedDoc);
        when(mapper.toDto(any(ServiceDocument.class)))
                .thenReturn(new ServiceDTO("mongo-id-1", "S001", "Passport", "Gov service", true));

        ServiceDTO result = service.createService(dto);

        assertNotNull(result);
        assertEquals("mongo-id-1", result.id());
        assertEquals("S001", result.code());

        verify(repository).existsByCode("S001");
        verify(repository).save(any(ServiceDocument.class));
    }
}
