public class CacheUtils {

    public static CacheFactory createFactory(CacheEvictionType type) {
        switch (type) {
            case LFU: return new LFUCacheFactory();
            case LRU: return new LRUCacheFactory();
            default: throw new RuntimeException("Unable to determinate cache eviction type");
        }
    }

}
