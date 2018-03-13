package amazonOA;
import  java.util.*;

public class LFUCache {
    class Node{
        int count;
        Node pre;
        Node next;
        LinkedHashSet<Integer> set;

        public Node(int c){
            this.count = c;
            set = new LinkedHashSet<Integer>();
        }

    }

    class Cache{
        Node head;
        Node tail;
        public Cache(){
            head = new Node(0);
            tail = new Node(0);
            head.next = tail;
            tail.pre = head;
        }

        public Node insert(int key){
            Node node = null;
            if (head.next == tail){
                node = new Node(0);
                head.next = node;
                node.pre = head;
                node.next = tail;
                tail.pre = node;
            } else if (head.next.count == 0){
                node = head.next;
            } else if (head.next.count > 0){
                node = new Node(0);
                node.next = head.next;
                head.next.pre = node;
                head.next = node;
                node.pre = head;
            }
            node.set.add(key);
            return node;
        }

        public Node addFrequency(Node n, int key){
            Node insert = null;
            if (n.next == tail || ( n.next != tail && n.next.count > n.count + 1)){
                insert = new Node(n.count + 1);
            }
            if (insert != null){
                if (n.next != tail){
                    insert.next = n.next;
                    n.next.pre = insert;
                    insert.pre = n;
                    n.next = insert;
                } else {
                    n.next = insert;
                    insert.pre = n;
                    insert.next = tail;
                    tail.pre = insert;
                }
            } else {
                insert = n.next;
            }
            n.set.remove(key);
            insert.set.add(key);
            if (n.set.size() == 0){
                remove(n);
            }
            return insert;
        }

        public void remove(Node n){
            if (n != null){
                n.pre.next = n.next;
                n.next.pre = n.pre;
                n.next = null;
                n.pre = null;
            }
        }

        public int removeLeast(){
            int removeKey = 0;
            if (head.next != tail){
                Node least = head.next;
                removeKey = removeOld(least);
                if (least.set.size() == 0){
                    remove(least);
                }
            }
            return removeKey;
        }

        public int removeOld(Node n){
            int old = 0;
            for (int i : n.set){
                old = i;
                break;
            }
            n.set.remove(old);
            return old;
        }

        public List<Integer> getTop(int k){
            List<Integer> res = new ArrayList<Integer>();
            Node cur = tail.pre;
            while (cur != head){
                for (Integer i : cur.set){
                    if (k == 0){return res;}
                    res.add(i);
                    k--;
                }
                cur = cur.pre;
            }
            return res;
        }


    }

    Map<Integer, Node> nodeHash;
    Map<Integer, Integer> valueHash;
    Cache cache;
    int size;

    public LFUCache(int capacity) {
        nodeHash = new HashMap<Integer, Node>();
        valueHash = new HashMap<Integer, Integer>();
        cache = new Cache();
        size = capacity;
    }

    public int get(int key) {
        int res = -1;
        if (valueHash.containsKey(key)){
            Node n = nodeHash.get(key);
            n = cache.addFrequency(n, key);
            res = valueHash.get(key);
            nodeHash.put(key, n);
        }

        List<Integer> list = getTopK(2);
        for (int a : list){
            System.out.println(a);
        }
        System.out.println("*******");
        return res;
    }

    public void put(int key, int value) {
        if (valueHash.containsKey(key)){
            Node n = nodeHash.get(key);
            n = cache.addFrequency(n, key);
            nodeHash.put(key, n);
            valueHash.put(key, value);
        } else {
            if (size == valueHash.size()){
                int removeKey = cache.removeLeast();
                if (removeKey != 0){
                    valueHash.remove(removeKey);
                    nodeHash.remove(removeKey);
                    size--;
                }
            }
            Node n = cache.insert(key);
            nodeHash.put(key, n);
            valueHash.put(key,value);
        }
    }

    public List<Integer> getTopK(int k){
        List<Integer> top = cache.getTop(k);
        return top;
    }
}
