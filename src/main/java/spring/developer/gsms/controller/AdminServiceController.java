package spring.developer.gsms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring.developer.gsms.dto.ServiceDTO;
import spring.developer.gsms.service.ServiceService;
import spring.developer.gsms.utils.ApiResponse;

@RestController
@RequestMapping("/api/admin/services")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
public class AdminServiceController {

    private final ServiceService service;

    @PostMapping
    public ApiResponse<ServiceDTO> create(@RequestBody ServiceDTO dto) {
        return ApiResponse.success(
                "Service created",
                service.createService(dto)
        );
    }

    @PostMapping("/{code}/deactivate")
    public ApiResponse<Void> deactivate(@PathVariable String code) {
        service.deactivateService(code);
        return ApiResponse.success("Service deactivated", null);
    }
}
