package com.devbaktiyarov.webocr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devbaktiyarov.webocr.model.ImageFile;

@Repository
public interface FileRepository extends JpaRepository<ImageFile, Integer>{
}
