package com.ecommerce.controller;

import com.ecommerce.dto.user.ChangePasswordRequest;
import com.ecommerce.dto.user.UpdateProfileRequest;
import com.ecommerce.dto.user.UpdateRoleRequest;
import com.ecommerce.dto.user.UserProfileResponse;
import com.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // --- Authenticated User Endpoints ---

  @GetMapping("/me")
  public ResponseEntity<UserProfileResponse> getCurrentUser(
      @AuthenticationPrincipal UserDetails userDetails) {
    return ResponseEntity.ok(userService.getCurrentUserProfile(userDetails.getUsername()));
  }

  @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<UserProfileResponse> updateProfile(
      @AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute UpdateProfileRequest request) { // Changed @RequestBody to @ModelAttribute

    return ResponseEntity.ok(userService.updateProfile(userDetails.getUsername(), request));
  }

  @PatchMapping("/me/password")
  public ResponseEntity<Void> changePassword(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestBody ChangePasswordRequest request) {
    userService.changePassword(userDetails.getUsername(), request);
    return ResponseEntity.noContent().build();
  }

  // --- Admin Endpoints ---

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Page<UserProfileResponse>> getAllUsers(
      @ParameterObject
          @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
          Pageable pageable) {
    return ResponseEntity.ok(userService.getAllUsers(pageable));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @PatchMapping("/{id}/role")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserProfileResponse> updateUserRole(
      @PathVariable Long id, @Valid @RequestBody UpdateRoleRequest request) {
    return ResponseEntity.ok(userService.updateUserRole(id, request));
  }

  @PatchMapping("/{id}/status")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserProfileResponse> toggleUserStatus(
      @PathVariable Long id, @RequestParam boolean enabled) {
    return ResponseEntity.ok(userService.toggleUserStatus(id, enabled));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}
