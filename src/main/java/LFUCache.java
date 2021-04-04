import java.util.HashMap;
import java.util.Map;

public class LFUCache<K, T> implements Cacheable<K, T> {

    Map<K, Node> nodeMap;
    Map<Integer, MyLinkedList> frequencyMap;
    int capacity, minFrequency;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        nodeMap = new HashMap<>();
        frequencyMap = new HashMap<>();
        minFrequency = 0;
    }

    @Override
    public void put(K key, T value) {
        if (capacity == 0) {
            return;
        }
        if (nodeMap.containsKey(key)) {
            Node node = nodeMap.get(key);
            node.value = value;
            get(key);
            return;
        }

        if (nodeMap.size() == capacity) {
            MyLinkedList frequencyList = frequencyMap.get(minFrequency);
            Node node = frequencyList.removeTail();
            nodeMap.remove(node.key);
            if (frequencyList.size == 0) frequencyMap.remove(minFrequency);
        }

        Node newNode = new Node(key, value);
        minFrequency = 1;
        nodeMap.put(key, newNode);
        MyLinkedList minFrequencyList = frequencyMap.getOrDefault(1, new MyLinkedList());
        minFrequencyList.addHead(newNode);
        frequencyMap.putIfAbsent(1, minFrequencyList);
    }

    @Override
    public T get(K key) {
        Node node = nodeMap.get(key);
        if (node == null) {
            return null;
        }

        MyLinkedList frequencyList = frequencyMap.get(node.count);
        frequencyList.remove(node);

        if (frequencyList.size == 0) {
            frequencyMap.remove(node.count);
            if (node.count == minFrequency) {
                minFrequency++;
            }
        }

        node.count++;
        frequencyList = frequencyMap.getOrDefault(node.count, new MyLinkedList());
        frequencyList.addHead(node);
        frequencyMap.putIfAbsent(node.count, frequencyList);

        return node.value;
    }

    private class Node {
        K key;
        T value;
        int count;
        Node previous, next;

        public Node(K key, T value) {
            this.key = key;
            this.value = value;
            count = 1;
        }

        public Node() {
            key = null;
            value = null;
        }
    }

    class MyLinkedList {
        public Node head = new Node();
        public Node tail = new Node();
        public int size = 0;

        public MyLinkedList() {
            this.head.next = tail;
            this.tail.previous = head;
        }

        public void addHead(Node node) {
            tail.previous.next = node;
            node.previous = tail.previous;
            node.next = tail;
            tail.previous = node;
            size++;
        }

        public void remove(Node node) {
            node.previous.next = node.next;
            node.next.previous = node.previous;
            size--;
        }

        public Node removeTail() {
            Node node = head.next;
            remove(node);
            return node;
        }
    }
}
