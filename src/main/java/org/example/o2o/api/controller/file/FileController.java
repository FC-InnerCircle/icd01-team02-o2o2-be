package org.example.o2o.api.controller.file;

import org.example.o2o.api.docs.file.FileDocsController;
import org.example.o2o.api.dto.file.request.FileSaveRequest;
import org.example.o2o.api.dto.file.response.FileConfirmResponse;
import org.example.o2o.api.dto.file.response.FileRemoveResponse;
import org.example.o2o.api.dto.file.response.FileSaveResponse;
import org.example.o2o.api.service.file.FileService;
import org.example.o2o.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/file")
public class FileController implements FileDocsController {

	private final FileService fileService;

	@PostMapping()
	public ApiResponse<FileSaveResponse> generatePreSignedUrl(@RequestBody @Valid FileSaveRequest fileSaveRequest) {
		FileSaveResponse fileSaveResponse = fileService.generatePreSignedUrl(fileSaveRequest);
		return ApiResponse.success(fileSaveResponse);
	}

	@GetMapping("/{fileId}")
	public ApiResponse<FileConfirmResponse> confirmPreSignedUrl(@PathVariable @Valid Long fileId) {
		FileConfirmResponse fileConfirmResponse = fileService.confirmPreSignedUrl(fileId);
		return ApiResponse.success(fileConfirmResponse);
	}

	@DeleteMapping("/{fileId}")
	public ApiResponse<FileRemoveResponse> deleteFile(@PathVariable @Valid Long fileId) {
		FileRemoveResponse fileRemoveResponse = fileService.deleteFile(fileId);
		return ApiResponse.success(fileRemoveResponse);
	}

}
