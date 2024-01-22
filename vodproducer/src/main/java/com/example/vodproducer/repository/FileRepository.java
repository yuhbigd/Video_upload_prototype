package com.example.vodproducer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vodproducer.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
