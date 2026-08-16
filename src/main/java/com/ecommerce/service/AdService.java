package com.ecommerce.service;

import com.ecommerce.dto.AdRequest;
import com.ecommerce.entity.Ad;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.repository.AdRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AdService {
  private final FileUploadService uploadService;
  private final AdRepository adRepository;

  public List<Ad> getAllActiveAds() {
    return adRepository.findByActiveTrue();
  }

  public Ad getAdById(Long id) {
    return adRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Ad not found with id: " + id));
  }

  public Ad createAd(AdRequest adrequest) {
    if (adrequest.getImage() == null || adrequest.getImage().isEmpty())
      throw new BadRequestException("Ad Image is required!");

    final String fileName = uploadService.storeFile(adrequest.getImage());

    String fileDownloadUri =
        ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/uploads/")
            .path(fileName)
            .toUriString();

    Ad ad =
        Ad.builder()
            .title(adrequest.getTitle())
            .targetUrl(adrequest.getTargetUrl())
            .active(adrequest.isActive())
            .description(adrequest.getDescription())
            .imageUrl(fileDownloadUri)
            .build();

    return adRepository.save(ad);
  }

  public void deleteAd(Long id) {
    if (!adRepository.existsById(id)) {
      throw new RuntimeException("Ad not found with id: " + id);
    }
    adRepository.deleteById(id);
  }

  public Ad updateAd(Long id, AdRequest ad) {
    Ad existing =
        adRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Ad not found with id: " + id));
    if (!ad.getTitle().isEmpty()) existing.setTitle(ad.getTitle());
    if (!ad.getTargetUrl().isEmpty()) existing.setTargetUrl(ad.getTargetUrl());
    if (!ad.getDescription().isEmpty()) existing.setTargetUrl(ad.getDescription());

    if (ad.getImage() != null && !ad.getImage().isEmpty()) {

      final String fileName = uploadService.storeFile(ad.getImage());

      String fileDownloadUri =
          ServletUriComponentsBuilder.fromCurrentContextPath()
              .path("/uploads/")
              .path(fileName)
              .toUriString();

      existing.setImageUrl(fileDownloadUri);
    }

    return adRepository.save(existing);
  }
}
