import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@ExtendWith(MockitoExtension.class)
class CacheTest {

    /*
        CacheUtils.createFactory probably is not the best solution but
        I wanted to create cache by providing CacheEvictionType as a parameter
     */
    @Test
    void testLruCapacity4() {
        CacheFactory<Integer, Integer> factory = CacheUtils.createFactory(CacheEvictionType.LRU);
        Cacheable<Integer, Integer> cache = factory.createCache(4);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);

        assertNull(cache.get(1));
        assertNotNull(cache.get(5));

        cache.put(6, 6);
        assertNull(cache.get(2));
        assertNotNull(cache.get(6));
    }

    @Test
    void testLruCapacity3() {
        CacheFactory<String, String> factory = CacheUtils.createFactory(CacheEvictionType.LRU);
        Cacheable<String, String> cache = factory.createCache(3);

        cache.put("Cat", "Cat");
        cache.put("Dog", "Dog");
        cache.put("Rat", "Rat");
        cache.put("Lion", "Lion");

        assertNull(cache.get("Cat"));
        assertNotNull(cache.get("Lion"));

    }

    @Test
    void testLfuCapacity3() {
        CacheFactory<Integer, Integer> factory = CacheUtils.createFactory(CacheEvictionType.LFU);
        Cacheable<Integer, Integer> cache = factory.createCache(3);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.get(3);
        cache.get(2);
        cache.put(4,4);
        assertNull(cache.get(1));
    }

    @Test
    void testLfuCapacity4() {
        CacheFactory<String, String> factory = CacheUtils.createFactory(CacheEvictionType.LFU);
        Cacheable<String, String> cache = factory.createCache(4);
        cache.put("Cat", "Cat");
        cache.put("Dog", "Dog");
        cache.put("Rat", "Rat");
        cache.put("Lion", "Lion");

        cache.get("Lion");
        cache.get("Lion");
        cache.get("Rat");
        cache.get("Dog");

        cache.put("Pig", "Pig");

        Assertions.assertNull(cache.get("Cat"));

        cache.put("Sparrow", "Sparrow");

        Assertions.assertNull(cache.get("Pig"));

    }

}