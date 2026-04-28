package org.fninfo.tg;
import org.fninfo.tg.dto.SubRequest;
import org.fninfo.tg.repo.TGRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatIdServiceTest {
    @Mock
    private TGRepository tgRepository;
    @InjectMocks
    private ChatIdService chatIdService;

    @Test
    public void testSetMainId() {
        String chatId = "123456789";
        when(tgRepository.addElement("test:send", chatId)).thenReturn(true);
        boolean result = chatIdService.setMainId(chatId);
        assertTrue(result);
    }

    @Test
    public void testSetMainIdFailure() {
        String chatId = "123456789";
        when(tgRepository.addElement("test:send", chatId)).thenReturn(false);
        boolean result = chatIdService.setMainId(chatId);
        assertFalse(result);
    }

    @Test
    public void testAddListener() {
        SubRequest req = new SubRequest("user123", "news","fortnite");
        when(tgRepository.addElementSet(any(), any())).thenReturn(true);
        boolean result = chatIdService.addListener(req);
        assertTrue(result);
    }

    @Test
    public void testAddListenerFailure() {
        SubRequest req = new SubRequest("user123", "news","fortnite");
        when(tgRepository.addElementSet(any(), any())).thenReturn(false);
        boolean result = chatIdService.addListener(req);
        assertFalse(result);
    }

    @Test
    public void testDeleteListener() {
        SubRequest req = new SubRequest("user123", "news","fortnite");
        when(tgRepository.deleteElementSet(any(), any())).thenReturn(true);
        boolean result = chatIdService.deleteListener(req);
        assertTrue(result);
    }

    @Test
    public void testDeleteListenerFailure() {
        SubRequest req = new SubRequest("user123", "news","fortnite");
        when(tgRepository.deleteElementSet(any(), any())).thenReturn(false);
        boolean result = chatIdService.deleteListener(req);
        assertFalse(result);
    }
}