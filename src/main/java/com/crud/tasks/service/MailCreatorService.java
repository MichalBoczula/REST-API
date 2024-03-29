package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private CompanyConfig companyConfig;

    public String buildTrelloCardEmail(String message) {
        final List<String> functionality =new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allow sending tasks to Trello");

        Context context = new Context();
        context.setVariable("preview", "New Task created");
        context.setVariable("message", message);
        context.setVariable("task_url", "http://localhost:8888/front19/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("goodbye", "Have a nice day");
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildTemplateMail(String message){
        Context context = new Context();
        context.setVariable("preview", "Task ToDo");
        context.setVariable("message", message);
        context.setVariable("task_url", "http://localhost:8888/front19/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("goodbye_message", "Have a nice day");
        context.setVariable("company_name", "Trello");
        return templateEngine.process("mail/mail-template", context);
    }
}
