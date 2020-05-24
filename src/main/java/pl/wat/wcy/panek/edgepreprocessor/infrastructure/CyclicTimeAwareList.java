package pl.wat.wcy.panek.edgepreprocessor.infrastructure;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class CyclicTimeAwareList<T> implements Collection<T> {
    private final List<T> values;
    private final int size;
    private final Lock lock;
    @Getter
    private LocalDateTime lastUpdate;

    public CyclicTimeAwareList(int size) {
        this.values = new ArrayList<T>(size);
        this.size = size;
        this.lastUpdate = LocalDateTime.now();
        this.lock = new ReentrantLock();
    }

    @Override
    public boolean add(T t) {
        lock.lock();
        try {
            if (values.size() < size) {
                update();
                return this.values.add(t);
            } else {
                update();
                this.values.remove(0);
                return this.values.add(t);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        update();
        return values.remove(o);
    }

    private void update() {
        this.lastUpdate = LocalDateTime.now();
    }

    public List<T> sortedCopy() {
        lock.lock();
        try {
            return values.stream().sorted().collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        this.values.clear();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return values.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }

    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return values.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return values.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return values.removeAll(c);
    }
}
