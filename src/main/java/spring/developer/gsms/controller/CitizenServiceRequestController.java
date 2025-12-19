package spring.developer.gsms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.developer.gsms.dto.CreateServiceRequestDTO;
import spring.developer.gsms.dto.PageResponseDTO;
import spring.developer.gsms.dto.ServiceRequestResponseDTO;
import spring.developer.gsms.security.CustomUserDetails;
import spring.developer.gsms.service.ServiceRequestService;
import spring.developer.gsms.utils.ApiResponse;

@RestController
@RequestMapping("/api/citizen/requests")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CITIZEN')")
public class CitizenServiceRequestController {

    private final ServiceRequestService service;

    @PostMapping
    public ApiResponse<ServiceRequestResponseDTO> submit(
            @RequestBody CreateServiceRequestDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ApiResponse.success(
                "Request submitted successfully",
                service.submitRequest(dto, user.getId())
        );
    }

    @GetMapping
    public ApiResponse<PageResponseDTO<?>> myRequests(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.success(
                "Requests fetched successfully",
                service.getMyRequests(user.getId(), page, size)
        );
    }
}
