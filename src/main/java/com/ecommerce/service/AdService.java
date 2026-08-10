package com.ecommerce.service;

import com.ecommerce.entity.Ad;
import com.ecommerce.repository.AdRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdService {

  private final AdRepository adRepository;

  public AdService(AdRepository adRepository) {
    this.adRepository = adRepository;
  }

  public List<Ad> getAllActiveAds() {
    return adRepository.findByActiveTrue();
  }

  public Ad getAdById(Long id) {
    return adRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Ad not found with id: " + id));
  }

  public Ad createAd(Ad ad) {
    return adRepository.save(ad);
  }

  public void deleteAd(Long id) {
    if (!adRepository.existsById(id)) {
      throw new RuntimeException("Ad not found with id: " + id);
    }
    adRepository.deleteById(id);
  }
}
