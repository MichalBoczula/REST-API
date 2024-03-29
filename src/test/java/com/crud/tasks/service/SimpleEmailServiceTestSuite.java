//package com.crud.tasks.service;
//
//import com.crud.tasks.domain.Mail;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessagePreparator;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//@RunWith(MockitoJUnitRunner.class)
//public class SimpleEmailServiceTestSuite {
//    @InjectMocks
//    private SimpleEmailService simpleEmailService;
//    @Mock
//    private JavaMailSender javaMailSender;
//
//    @Test
//    public void shouldSendMail(){
//        //Given
//        final Mail mail = new Mail("test@test.com", "Test", "Test Message");
//        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo(mail.getMailTo());
//        simpleMailMessage.setSubject(mail.getSubject());
//        simpleMailMessage.setText(mail.getMessage());
//        //When
//        simpleEmailService.send(mail);
//        //Then
//        verify(javaMailSender, times(1)).send(simpleMailMessage);
//    }
//
//}