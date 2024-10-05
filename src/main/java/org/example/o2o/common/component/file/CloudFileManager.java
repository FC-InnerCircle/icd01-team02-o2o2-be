package org.example.o2o.common.component.file;

import org.example.o2o.common.dto.file.FileDto.PreSignedUrlRequest;
import org.example.o2o.common.dto.file.FileDto.PreSignedUrlResponse;

public interface CloudFileManager extends FileManager {
	PreSignedUrlResponse generatePreSignedUrl(PreSignedUrlRequest fileName);
}
