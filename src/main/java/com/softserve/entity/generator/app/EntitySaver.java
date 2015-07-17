package com.softserve.entity.generator.app;

import com.softserve.entity.generator.app.util.Authenticator;
import com.softserve.entity.generator.config.AppConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.service.EntityService;
import com.softserve.entity.generator.salesforce.EntityRequester;
import com.softserve.entity.generator.salesforce.SalesforceAuthenticator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntitySaver
{
    private static final Logger logger = Logger.getLogger(EntitySaver.class);

    @Autowired
    private EntityService entityService;

    public static void main(String[] args)
    {
        SalesforceAuthenticator salesforceAuthenticator = Authenticator.login(args);

        EntityRequester entityRequester = new EntityRequester(salesforceAuthenticator);

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        EntitySaver entitySaver = applicationContext.getBean(EntitySaver.class);

        entitySaver.saveEntities(entityRequester.getAllEntities());
    }

    public void saveEntities(List<Entity> receivedEntities)
    {
        entityService.resolveDeleted(receivedEntities);
        for (Entity receivedEntity : receivedEntities)
        {
            entityService.merge(receivedEntity);
        }
    }
}