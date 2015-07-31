package com.softserve.entity.generator.service;

import com.softserve.entity.generator.config.ServiceMockConfig;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.repository.CrudRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {ServiceMockConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseServiceTest
{
    @Autowired
    @InjectMocks
    @Qualifier("baseServiceImpl")
    private BaseService<Entity> service;

    @Autowired
    @Qualifier("baseRepositoryMock")
    private CrudRepository<Entity> repository;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave()
    {
        Entity entity = new Entity("EntityId", "New table", "NEW_TABLE");
        service.save(entity);

        verify(repository).save(entity);
    }

    @Test
    public void testMerge()
    {
        Entity entity = new Entity("EntityId", "New table", "NEW_TABLE");
        service.merge(entity);

        verify(repository).merge(entity);
    }

    @Test
    public void testDelete()
    {
        Entity entity = new Entity("EntityId", "New table", "NEW_TABLE");
        service.delete(entity);

        verify(repository).delete(entity);
    }

}
