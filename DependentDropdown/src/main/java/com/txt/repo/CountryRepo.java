package com.txt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.txt.entity.countryEntity;

public interface CountryRepo extends JpaRepository<countryEntity, Integer>{
}
