module service.impl {
    requires service.api;
    provides service.api.NotificationService with service.impl.SMSServiceImpl;
}