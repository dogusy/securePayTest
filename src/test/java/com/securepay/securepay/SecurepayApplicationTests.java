package com.securepay.securepay;

import com.securepay.securepay.controller.CardServiceTest;
import com.securepay.securepay.controller.CustomerServiceTest;
import com.securepay.securepay.controller.PaymentServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectClasses({ CustomerServiceTest.class, PaymentServiceTest.class, CardServiceTest.class})
class SecurepayApplicationTests {
}
