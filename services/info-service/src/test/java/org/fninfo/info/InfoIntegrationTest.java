package org.fninfo.info;

import org.fninfo.info.dto.InfoRequest;
import org.fninfo.info.entity.Info;
import org.fninfo.info.repo.InfoRepository;
import org.fninfo.info.service.InfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InfoIntegrationTest {
    @Autowired
    private final InfoService infoService;
    private final InfoRepository infoRepository;

    @Autowired
    public InfoIntegrationTest(InfoService infoService, InfoRepository infoRepository) {
        this.infoService = infoService;
        this.infoRepository = infoRepository;
    }

    @Test
    public void testIntegration() {
        InfoRequest req = new InfoRequest("test1","text",false,"go.png");
        assertTrue(infoService.addInfo(req));
        Info info = infoRepository.findByNameEventAndStatusOf("test1",false);
        assertEquals("go.png",info.getImage());
        assertTrue(infoService.deleteInfo(req));
        assertNull(infoRepository.findByNameEventAndStatusOf("test1",false));
    }
}
