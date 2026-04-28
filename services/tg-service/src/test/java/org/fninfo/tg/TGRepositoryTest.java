package org.fninfo.tg;

import org.fninfo.tg.exception.RedisException;
import org.fninfo.tg.repo.TGRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.SetOperations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TGRepositoryTest {
    @Mock
    private StringRedisTemplate stringRedisTemplate;
    @InjectMocks
    private TGRepository tgRepository;

    @Test
    public void testAddElementSetSuccess() {
        SetOperations<String, String> setOps = mock(SetOperations.class);
        when(stringRedisTemplate.opsForSet()).thenReturn(setOps);
        when(setOps.add(eq("tg:mykey"), eq("element1"))).thenReturn(1L);

        boolean result = tgRepository.addElementSet("mykey", "element1");
        assertTrue(result);
    }

    @Test
    public void testAddElementSetFailure() {
        SetOperations<String, String> setOps = mock(SetOperations.class);
        when(stringRedisTemplate.opsForSet()).thenReturn(setOps);
        when(setOps.add(eq("tg:mykey"), eq("element1"))).thenReturn(0L);

        boolean result = tgRepository.addElementSet("mykey", "element1");
        assertFalse(result);
    }

    @Test
    public void testAddElementSetException() {
        SetOperations<String, String> setOps = mock(SetOperations.class);
        when(stringRedisTemplate.opsForSet()).thenReturn(setOps);
        when(setOps.add(eq("tg:mykey"), eq("element1"))).thenThrow(new RuntimeException("Redis down"));

        assertThrows(RedisException.class, () -> tgRepository.addElementSet("mykey", "element1"));
    }

    @Test
    public void testDeleteElementSetSuccess() {
        SetOperations<String, String> setOps = mock(SetOperations.class);
        when(stringRedisTemplate.opsForSet()).thenReturn(setOps);
        when(setOps.remove(eq("tg:mykey"), eq("element1"))).thenReturn(1L);

        boolean result = tgRepository.deleteElementSet("mykey", "element1");
        assertTrue(result);
    }

    @Test
    public void testDeleteElementSetFailure() {
        SetOperations<String, String> setOps = mock(SetOperations.class);
        when(stringRedisTemplate.opsForSet()).thenReturn(setOps);
        when(setOps.remove(eq("tg:mykey"), eq("element1"))).thenReturn(0L);

        boolean result = tgRepository.deleteElementSet("mykey", "element1");
        assertFalse(result);
    }

    @Test
    public void testAddElementSuccess() {
        ValueOperations<String, String> valueOps = mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOps);
        doNothing().when(valueOps).set(eq("tg:mykey"), eq("value123"));

        boolean result = tgRepository.addElement("mykey", "value123");
        assertTrue(result);
    }

    @Test
    public void testAddElementException() {
        ValueOperations<String, String> valueOps = mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOps);
        doThrow(new RuntimeException("Connection failed")).when(valueOps).set(eq("tg:mykey"), eq("value123"));

        boolean result = tgRepository.addElement("mykey", "value123");
        assertFalse(result);
    }

    @Test
    public void testGetIDs() {
        SetOperations<String, String> setOps = mock(SetOperations.class);
        when(stringRedisTemplate.opsForSet()).thenReturn(setOps);
        when(setOps.members("tg:mykey")).thenReturn(Set.of("el1", "el2"));

        Set<String> result = tgRepository.getIDs("mykey");
        assertEquals(2, result.size());
        assertTrue(result.contains("el1"));
    }

    @Test
    public void testGetID() {
        ValueOperations<String, String> valueOps = mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get("tg:mykey")).thenReturn("storedValue");

        String result = tgRepository.getID("mykey");
        assertEquals("storedValue", result);
    }

    @Test
    public void testGetPhoto() {
        ValueOperations<String, String> valueOps = mock(ValueOperations.class);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get("tg:photo:logo")).thenReturn("AgACAgIAASAjshqwAS21AS21sd2");

        String result = tgRepository.getPhoto("photo:logo");
        assertEquals("AgACAgIAASAjshqwAS21AS21sd2", result);
    }
}
