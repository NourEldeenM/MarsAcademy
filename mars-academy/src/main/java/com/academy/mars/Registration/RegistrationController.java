package com.academy.mars.Registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @GetMapping("/signup")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

}
