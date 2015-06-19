package com.softserve.entity.generator.procedure.applier;

import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.StringWriter;
import java.util.*;

@Service
public class Applier
{
    private static final Logger logger = Logger.getLogger(Applier.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createTable(Entity entity)
    {
        List<String> tableName = new ArrayList<String>();
        Query tableQuery = entityManager.createNativeQuery(
                "SELECT cast(name as varchar) FROM sys.tables"
        );

        for (Object table : tableQuery.getResultList())
        {
            tableName.add((String) table);
        }

        List<String> proceduresName = new ArrayList<String>();
        Query procQuery = entityManager.createNativeQuery(
                "SELECT cast(name as varchar) FROM EntityGenerator.sys.procedures"
        );


        for (Object procedure : procQuery.getResultList())
        {
            proceduresName.add((String) procedure);
        }

        VelocityEngine velocityEngine = getVelocityEngine();
        velocityEngine.init();
        Template templateCreate = velocityEngine.getTemplate("velocity.template/ProcedureCreator.vm");
        VelocityContext context = new VelocityContext();
        Map<String, String> columns = new TreeMap<String, String>();

        context.put("procedureName", "myProcedure");
        context.put("tableName", entity.getTableName());
        context.put("columns", columns);
        context.put("procedures", proceduresName);
        context.put("containProcedure", false);
        context.put("containTable", false);
        context.put("tables", tableName);

        for (Field field : entity.getFields())
        {
            columns.put(field.getColumnName(),  field.getType());
        }

        StringWriter writer = new StringWriter();
        templateCreate.merge(context, writer);

        String sqlQuery = writer.toString();
        System.out.println(writer.toString());

        entityManager.createNativeQuery(sqlQuery).executeUpdate();
        entityManager.close();
    }

    private VelocityEngine getVelocityEngine()
    {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return velocityEngine;
    }


}
