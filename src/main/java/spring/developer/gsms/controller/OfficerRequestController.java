package spring.developer.gsms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.developer.gsms.dto.ServiceRequestResponseDTO;
import spring.developer.gsms.security.CustomUserDetails;
import spring.developer.gsms.service.ServiceRequestService;
import spring.developer.gsms.utils.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/officer/requests")
@RequiredArgsConstructor
@PreAuthorize("hasRole('OFFICER')")
public class OfficerRequestController {

    private final ServiceRequestService service;

    @GetMapping("/pending")
    public ApiResponse<List<ServiceRequestResponseDTO>> pending() {
        return ApiResponse.success(
                "Pending requests fetched",
                service.getPendingRequests()
        );
    }

    @PostMapping("/{requestNo}/approve")
    public ApiResponse<Void> approve(
            @PathVariable String requestNo,
            @AuthenticationPrincipal CustomUserDetails officer
    ) {
        service.approve(requestNo, officer.getId());
        return ApiResponse.success("Request approved", null);
    }

    @PostMapping("/{requestNo}/reject")
    public ApiResponse<Void> reject(
            @PathVariable String requestNo,
            @RequestParam String comment,
            @AuthenticationPrincipal CustomUserDetails officer
    ) {
        service.reject(requestNo, officer.getId(), comment);
        return ApiResponse.success("Request rejected", null);
    }
}

