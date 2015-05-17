package co.valdeon.Tribes.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class BiMap<K, V> {

    private final HashMap<K, V> forward = new HashMap<>();
    private final HashMap<V, K> reverse = new HashMap<>();

    public void put(K a, V b) {
        forward.put(a, b);
        reverse.put(b, a);
    }

    public V getForward(K a) {
        return forward.get(a);
    }

    public K getReverse(V b) {
        return reverse.get(b);
    }

    public void remove(K a) {
        reverse.remove(forward.get(a));
        forward.remove(a);
    }

    public Set<K> forwardKeySet() {
        return forward.keySet();
    }

    public Collection<V> forwardValues() {
        return forward.values();
    }

    public Set<V> reverseKeySet() {
        return reverse.keySet();
    }

    public Collection<K> reverseValues() {
        return reverse.values();
    }

    public Set<K> keySet() {
        return forwardKeySet();
    }

    public Collection<V> values() {
        return forwardValues();
    }

    public V get(K a) {
        return getForward(a);
    }

    public boolean containsKey(K a) {
        return forward.containsKey(a);
    }

}
