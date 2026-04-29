package org.fninfo.info;


import org.fninfo.info.entity.Info;
import org.fninfo.info.repo.InfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class InfoJPATest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private InfoRepository infoRepository;

    @Test
    public void testFind() {
        testEntityManager.persist(new Info("test1",true,"text","go.png"));
        testEntityManager.persist(new Info("test122",false,"text","go21.png"));
        testEntityManager.persist(new Info("test122",true,"text","go123.png"));
        Info infoTrue = infoRepository.findByNameEventAndStatusOf("test122",true);
        Info infoFalse = infoRepository.findByNameEventAndStatusOf("test122",false);
        testEntityManager.flush();
        assertEquals("go123.png",infoTrue.getImage());
        assertEquals("go21.png",infoFalse.getImage());
    }
}
