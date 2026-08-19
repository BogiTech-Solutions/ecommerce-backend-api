package com.ecommerce.controller;

import com.ecommerce.dto.AdRequest;
import com.ecommerce.entity.Ad;
import com.ecommerce.service.AdService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/ads")
@RequiredArgsConstructor
@Tag(name = "Ads", description = "Endpoints for managing product ads")
public class AdController {

  private final AdService adService;

  // GET /api/ads - Public endpoint to fetch active ads
  @GetMapping
  public ResponseEntity<List<Ad>> getActiveAds() {
    List<Ad> ads = adService.getAllActiveAds();
    return ResponseEntity.ok(ads);
  }

  // GET /api/ads/{id} - Fetch a specific ad by ID
  @GetMapping("/{id}")
  public ResponseEntity<Ad> getAdById(@PathVariable Long id) {
    Ad ad = adService.getAdById(id);
    return ResponseEntity.ok(ad);
  }

  // POST /api/ads - Create a new ad (Admin feature)
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Ad> createAd(@Valid @ModelAttribute AdRequest ad) {
    Ad createdAd = adService.createAd(ad);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
  }

  // POST /api/ads/{id} - update ad (Admin feature)
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Ad> updateAd(@PathVariable Long id, @ModelAttribute AdRequest ad) {
    Ad updatedAd = adService.updateAd(id, ad);
    return ResponseEntity.status(200).body(updatedAd);
  }

  // DELETE /api/ads/{id} - Delete an ad by ID (Admin feature)
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteAd(@PathVariable Long id) {
    adService.deleteAd(id);
    return ResponseEntity.noContent().build();
  }
}
