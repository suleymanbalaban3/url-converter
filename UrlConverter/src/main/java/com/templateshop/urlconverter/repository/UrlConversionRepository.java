package com.templateshop.urlconverter.repository;

import com.templateshop.urlconverter.model.UrlConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlConversionRepository extends JpaRepository<UrlConversion, Long> {
}
