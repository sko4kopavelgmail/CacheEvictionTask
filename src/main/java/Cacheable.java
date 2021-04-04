public interface Cacheable<K, T> {

    void put(K key, T value);

    T get(K key);

}
