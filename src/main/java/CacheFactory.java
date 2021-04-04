public interface CacheFactory<K, T> {

    Cacheable<K, T> createCache(int capacity);

}
