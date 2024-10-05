package org.example.o2o.common.component.file;

import java.util.List;

import org.example.o2o.common.dto.file.FileDto.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileManager {
	List<UploadFileResponse> storeFiles(List<MultipartFile> files);

	UploadFileResponse storeFile(MultipartFile file);

	boolean isExistsFile(String fileName);

	void deleteFile(String fileName);
}
