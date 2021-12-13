package com.coderace.model.entities;

import com.coderace.model.enums.DeliveryType;
import com.coderace.model.enums.ServiceType;
import org.junit.jupiter.api.Test;

class ServiceTest {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testInitialization() {
        final Service service = new Service("beta", ServiceType.WARRANTY,25L);
        final Service service2 = new Service();


        service.getId();
        service.getServiceType();
        service.getSku();
        service.getDays();

    }

}