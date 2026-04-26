package org.fninfo.info;

import org.fninfo.info.dto.InfoRequest;
import org.fninfo.info.entity.Info;
import org.fninfo.info.repo.InfoRepository;
import org.fninfo.info.service.InfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class InfoServiceTest {
    @Mock
    private InfoRepository infoRepository;
    @InjectMocks
    private InfoService infoService;

    @Test
    public void testAddInfo() {
        InfoRequest req = new InfoRequest("test1","text",false,"go.png");
        Info saved = new Info(1,"test1",false,"text","go.png");

        when(infoRepository.save(any(Info.class))).thenReturn(saved);

        boolean result = infoService.addInfo(req);
        assertTrue(result);
    }

    @Test
    public void testDeleteInfo() {
        InfoRequest req = new InfoRequest("test1","text",false,"go.png");
        Info saved = new Info(1,"test1",false,"text","go.png");

        when(infoRepository.save(any(Info.class))).thenReturn(saved);
        when(infoRepository.findByNameEventAndStatusOf("test1",false)).thenReturn(saved);
        infoService.addInfo(req);
        boolean result = infoService.deleteInfo(req);
        assertTrue(result);
    }

    @Test
    public void testAddInfoException() {
        InfoRequest req = new InfoRequest("test1","text",false,"go.png");

        when(infoRepository.save(any(Info.class))).thenThrow(new RuntimeException("error"));
        boolean result = infoService.addInfo(req);

        assertFalse(result);

    }

    @Test
    public void testDeleteInfoNull() {
        InfoRequest req = new InfoRequest("test1","text",false,"go.png");
        when(infoRepository.findByNameEventAndStatusOf("test1",false)).thenReturn(null);
        boolean result = infoService.deleteInfo(req);
        assertFalse(result);
    }

    @Test
    public void testDeleteInfoException() {
        InfoRequest req = new InfoRequest("test1","text",false,"go.png");
        Info saved = new Info(1,"test1",false,"text","go.png");
        when(infoRepository.save(any(Info.class))).thenReturn(saved);
        when(infoRepository.findByNameEventAndStatusOf("test1",false)).thenThrow(new RuntimeException());
        infoService.addInfo(req);
        boolean result = infoService.deleteInfo(req);
        assertFalse(result);
    }

}
