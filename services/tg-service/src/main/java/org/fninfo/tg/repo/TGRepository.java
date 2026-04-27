package org.fninfo.tg.repo;

import org.fninfo.tg.exception.RedisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class TGRepository {
    private final StringRedisTemplate stringRedisTemplate;

    public TGRepository(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public boolean addElementSet(String id, String element) {
        try {
            Long res = stringRedisTemplate.opsForSet().add("tg:" + id.toLowerCase(), element);
            return (res != null) && res > 0;
        }
        catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public boolean deleteElementSet(String id, String element) {
        try {
            Long res = stringRedisTemplate.opsForSet().remove("tg:" + id.toLowerCase(), element);
            return (res != null) && res > 0;
        }
        catch (Exception e) {
            throw new RedisException(e.getMessage());
        }
    }

    public boolean addElement(String id, String element) {
        try {
            stringRedisTemplate.opsForValue().set("tg:" + id.toLowerCase(), element);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Set<String> getIDs(String key) {
        return stringRedisTemplate.opsForSet().members("tg:" + key.toLowerCase());
    }

    public String getID(String key) {
        return stringRedisTemplate.opsForValue().get("tg:" + key.toLowerCase());
    }

    public String getPhoto(String key) {
        return stringRedisTemplate.opsForValue().get("tg:" + key.toLowerCase());
    }
 }
