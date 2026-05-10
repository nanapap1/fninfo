package org.fninfo.epicapi.repo;

import io.lettuce.core.RedisCommandExecutionException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class AlertRepository {
    private final StringRedisTemplate stringRedisTemplate;

    public AlertRepository(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void addAlerts(String theaterId, Set<String> alert){
        try {
            if (!alert.isEmpty()) {
                stringRedisTemplate.delete("alerts:" + theaterId);
                stringRedisTemplate.opsForSet().add("alerts:" + theaterId, alert.toArray(new String[0]));
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to add alerts for theater: " + theaterId, e);}
    }

    public Set<String> getAlerts(String theaterId) {
        try {
            return stringRedisTemplate.opsForSet().members("alerts:" + theaterId);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to get alerts for theater: " + theaterId, e);}
    }

    public boolean compare(String theaterId, Set<String> alert) {
        try {
            Long size = stringRedisTemplate.opsForSet().size("alerts:" + theaterId);
            if (size == null && alert.isEmpty()) return true;
            this.addAlerts(String.format("test:%s",theaterId),alert);
            Set<String> intersection = stringRedisTemplate.opsForSet().intersect("alerts:" + theaterId,"alerts:" + "test:" + theaterId);
            return (intersection.size() == alert.size()) && (intersection.size() == (size != null ? size : 0));
        }
        catch (Exception e) {
            return false;
        }
    }

}
