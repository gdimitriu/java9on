package service.impl;

import service.api.NotificationService;

public class SMSServiceImpl implements NotificationService {
    @Override
    public boolean sendNotification(String message, String recipient) {
        System.out.println("SMS has been sent to Recipient:: " + recipient + " with Message ::" + message);
        return true;
    }
}
