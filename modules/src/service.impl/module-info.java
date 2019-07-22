module service.impl {
    requires service.api;
    /* only one provides is allowed */
    provides service.api.NotificationService with service.impl.SMSServiceImpl;
}