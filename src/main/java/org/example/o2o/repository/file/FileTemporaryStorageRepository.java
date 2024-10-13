package org.example.o2o.repository.file;

import java.util.List;

import org.example.o2o.domain.file.FileTemporaryStorage;
import org.example.o2o.domain.file.FileTemporaryStorage.FileSyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileTemporaryStorageRepository extends JpaRepository<FileTemporaryStorage, Long> {

	List<FileTemporaryStorage> findBySyncStatusAndFullPathIn(FileSyncStatus syncStatus, List<String> fullPath);
}
