package com.spomprt.blinkify.repository;

import com.spomprt.blinkify.domain.UrlLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlLinkRepository extends JpaRepository<UrlLink, String> {
}
