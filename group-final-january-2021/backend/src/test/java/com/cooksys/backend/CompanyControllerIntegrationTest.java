package com.cooksys.backend;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cooksys.backend.controllers.CompanyController;
@ExtendWith(SpringExtension.class)
//@RunWith(SpringRunner.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerIntegrationTest {

}
