package service.api;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public interface NotificationService {

    public static List<NotificationService> getInstances() {
        ServiceLoader<NotificationService> services = ServiceLoader.load(NotificationService.class);
        List<NotificationService> list = new ArrayList<>();
        services.iterator().forEachRemaining(list::add);
        return list;
    }

    boolean sendNotification(String message, String recipient);
}
