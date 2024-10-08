package org.example.o2o.api.service.order;

import java.util.Map;

import org.example.o2o.domain.order.OrderStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderNotificationService {

	@Value("${notification.url:127.0.0.1:8080}")
	private String targetUrl;

	public void sendStatusChangeNotification(Long orderId, OrderStatus status) {
		String url = String.format("%s?orderId=%s&status=%s", targetUrl, orderId, status);
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PATCH, null, Map.class);
			HttpStatusCode statusCode = response.getStatusCode();
			if (!statusCode.is2xxSuccessful()) {
				// 상태 변경 실패
				throw new RuntimeException((String)response.getBody().get("message"));
			}
		} catch (RestClientException e) {
			log.error(e.getMessage(), e);
		}
	}
}
