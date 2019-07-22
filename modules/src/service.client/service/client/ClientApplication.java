package service.client;
import service.api.NotificationService;

import java.util.List;

public class ClientApplication {
    public static void main(String...args) {
        List<NotificationService> notificationServices = NotificationService.getInstances();
        for (NotificationService service : notificationServices) {
            service.sendNotification("Hello", "123456789");
        }
    }
}
