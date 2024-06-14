package com.devbaktiyarov.webocr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devbaktiyarov.webocr.entity.ImageFile;

@Repository
public interface FileRepository extends JpaRepository<ImageFile, Integer>{
    Optional<ImageFile> findByName(String name);

    @Query(value ="SELECT * FROM  image_file WHERE user_id = ?1", nativeQuery=true)
    List<ImageFile> findAllByUserId(long id);

    @Query(value ="DELETE FROM  image_file WHERE name = ?1", nativeQuery=true)
    void deleteByName(String name);
}
