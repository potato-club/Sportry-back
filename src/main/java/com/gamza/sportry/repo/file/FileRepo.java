package com.gamza.sportry.repo.file;

import com.gamza.sportry.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<FileEntity, Long> {
}
