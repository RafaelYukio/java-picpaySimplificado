package com.picpaySimplificado.services;

import com.picpaySimplificado.domain.user.User;
import com.picpaySimplificado.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message ) throws Exception {
        NotificationDTO notificationRequest = new NotificationDTO(user.getEmail(), message);

        ResponseEntity<Map> response = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest,Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Enviando notificação!");
        }
        else {
            Map<String, String> body = response.getBody();
            String errorStatus = body.get("status");
            String errorMessage = body.get("message");

            throw new Exception("Serviço de notificação fora do ar! Status: " + errorStatus + " Message: " + errorMessage);
        }
    }
}
