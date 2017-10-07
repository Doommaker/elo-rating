package com.elorating.controller;

import com.elorating.model.Feedback;
import com.elorating.service.EmailService;
import com.elorating.service.email.EmailBuilder;
import com.elorating.service.email.EmailDirector;
import com.elorating.service.email.FeedbackEmail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Feedback", description = "Feedback API")
public class FeedbackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Value("${spring.mail.feedback.address}")
    private String feedbackAddress;

    @Autowired
    private EmailService emailService;

    @CrossOrigin
    @RequestMapping(value = "/feedback/send", method = RequestMethod.POST)
    @ApiOperation(value = "Send feedback", notes = "Send feedback email")
    public ResponseEntity<Boolean> send(@RequestBody Feedback feedback) {
        EmailDirector emailDirector = new EmailDirector();
        EmailBuilder emailBuilder = new FeedbackEmail(feedback.getSender(), feedbackAddress, feedback.getText());
        emailDirector.setBuilder(emailBuilder);
        emailService.send(emailDirector.build());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
