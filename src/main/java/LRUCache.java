import java.util.*;

public class LRUCache<K, T> implements Cacheable<K, T> {

    private final int capacity;
    private final Map<K, Node> map;
    private final MyLinkedList queue;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
        queue = new MyLinkedList();
    }

    @Override
    public T get(final K key) {
        Node node = map.get(key);
        if (Objects.isNull(node)) {
            return null;
        }
        queue.moveToHead(node);
        return map.get(key).value;
    }

    @Override
    public void put(final K key, final T value) {
        Node oldNode = map.get(key);
        if (Objects.nonNull(oldNode)) {
            oldNode.value = value;
            queue.moveToHead(oldNode);
        }
        if (capacity == map.size()) {
            map.remove(queue.getTailKey());
            queue.removeTail();
        }
        Node newNode = new Node(key, value);
        queue.addHead(newNode);
        map.put(key, newNode);
    }

    private class Node {
        K key;
        T value;
        Node previous, next;

        public Node(K key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    private class MyLinkedList {
        private Node head, tail;

        public MyLinkedList() {
            head = tail = null;
        }

        //method for debugging
        public List<Node> findAll() {
            List<Node> result = new ArrayList<>();
            Node currentNode = head;

            while (Objects.nonNull(currentNode)) {
                result.add(currentNode);
                currentNode = currentNode.next;
            }

            return result;
        }

        public void addHead(final Node node) {
            if (Objects.isNull(tail)) {
                head = tail = node;
                return;
            }
            node.next = head;
            head.previous = node;
            head = node;
        }

        public void moveToHead(final Node node) {
            if (head == node) {
                return;
            }

            if (node == tail) {
                tail = tail.previous;
                tail.next = null;
            } else {
                node.previous.next = node.next;
                node.next.previous = node.previous;
            }

            node.previous = null;
            node.next = head;
            head.previous = node;
            head = node;

        }

        public K getTailKey() {
            return tail.key;
        }

        public void removeTail() {
            if (Objects.isNull(tail)) {
                return;
            }
            if (head == tail) {
                head = tail = null;
            } else {
                tail = tail.previous;
                tail.next = null;
            }
        }

    }


}
