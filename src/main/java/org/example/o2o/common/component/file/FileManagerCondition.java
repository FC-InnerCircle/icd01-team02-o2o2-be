package org.example.o2o.common.component.file;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.s3.AmazonS3;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class FileManagerCondition {

	private final AmazonS3 amazonS3;

	@Bean
	@ConditionalOnProperty(name = "local", havingValue = "local")
	public FileManager localFileManager() {
		return new LocalFileManager();
	}

	@Bean
	public CloudFileManager s3FileManager() {
		return new S3FileManager(amazonS3);
	}
}
