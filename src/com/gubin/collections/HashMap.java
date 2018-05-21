package com.gubin.collections;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HashMap {
    double loadFactor = 0.75;
    List<Entry>[] table = new List[10];

    public HashMap(double loadFactor) {
        this.loadFactor = loadFactor;
        initArray(table);
    }


    private void initArray(List<Entry>[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = new LinkedList<>();
        }
    }

    public Entry get(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }

        int index = key.hashCode() % table.length;
        List<Entry> currentList = table[index];
        for (Entry current : currentList) {
            if (current.key.equals(key)) {
                return current;
            }
        }
        return null;
    }

    public Entry put(Object key, Object value) {
        Entry removed = putInArray(key, value, table);
        if (isArrayLoad(table)) {
            table = reloadList(table);
        }
        return removed;
    }

    int getListsCount() {
        return table.length;
    }

    int getMapSize() {
        int k = 0;
        for (List<Entry> le : table) {
            for (Entry entry : le) {
                k++;
            }
        }
        return k;
    }

    private Entry putInArray(Object key, Object value, List<Entry>[] newTable) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        Entry removed = null;
        int index = key.hashCode() % newTable.length;
        List<Entry> currentList = newTable[index];
        Iterator<Entry> it = currentList.iterator();
        while (it.hasNext()) {
            Entry current = it.next();
            if (current.key.equals(key)) {
                removed = current;
                it.remove();
            }
        }
        currentList.add(new Entry(key, value));
        return removed;
    }


    public Entry remove(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        Entry removed = null;
        int index = key.hashCode() % table.length;
        List<Entry> currentList = table[index];
        Iterator<Entry> it = currentList.iterator();

        while (it.hasNext()) {
            Entry current = it.next();
            if (current.key.equals(key)) {
                removed = current;
                it.remove();
            }
        }
        return removed;
    }

    private List<Entry>[] reloadList(List<Entry>[] input) {
        int newSize = input.length * 3 / 2 + 1;
        List<Entry>[] newArray = new List[newSize];
        initArray(newArray);

        for (List<Entry> le : input) {
            for (Entry entry : le) {
                putInArray(entry.key, entry.value, newArray);
            }
        }
        return newArray;
    }

    private boolean isArrayLoad(List<Entry>[] array) {
        int len = 0;
        for (List<Entry> le : array) {
            if (!le.isEmpty()) {
                len++;
            }
        }
        if (len > loadFactor * array.length) return true;
        return false;
    }

    private static class Entry {
        private final Object key;
        private final Object value;

        public Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

    }
}
