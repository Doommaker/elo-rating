package com.elorating.service;

import com.elorating.service.email.Email;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    boolean send(Email email);
}
