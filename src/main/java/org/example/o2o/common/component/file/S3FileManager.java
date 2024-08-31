package org.example.o2o.common.component.file;

import java.util.List;

import org.example.o2o.common.dto.file.FileDto.UploadFileResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class S3FileManager implements FileManager {

	@Override
	public List<UploadFileResponse> storeFiles(List<MultipartFile> files) {
		return List.of();
	}

	@Override
	public UploadFileResponse storeFile(MultipartFile file) {
		return null;
	}
}
