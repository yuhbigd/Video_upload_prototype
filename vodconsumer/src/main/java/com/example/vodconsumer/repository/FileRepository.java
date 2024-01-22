package com.example.vodconsumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vodconsumer.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
