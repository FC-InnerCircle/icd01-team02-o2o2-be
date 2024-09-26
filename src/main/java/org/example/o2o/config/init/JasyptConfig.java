package org.example.o2o.config.init;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.NoIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

	@Value("${jasypt.encryptor.password}")
	private String password;

	@Bean("jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

		encryptor.setPassword(password);
		encryptor.setSaltGenerator(new RandomSaltGenerator());
		encryptor.setIvGenerator(new NoIvGenerator());

		return encryptor;
	}
}
