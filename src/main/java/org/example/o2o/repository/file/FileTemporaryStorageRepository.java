package org.example.o2o.repository.file;

import org.example.o2o.domain.file.FileTemporaryStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileTemporaryStorageRepository extends JpaRepository<FileTemporaryStorage, Long> {
}
