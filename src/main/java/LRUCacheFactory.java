public class LRUCacheFactory<K, T> implements CacheFactory<K, T> {

    @Override
    public Cacheable<K, T> createCache(int capacity) {
        return new LRUCache<>(capacity);
    }

}
