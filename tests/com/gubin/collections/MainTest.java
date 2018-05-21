package com.gubin.collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MainTest {

    private HashMap map;

    @Before
    public void setUp() {
        map = new HashMap(0.75);
    }

    @After
    public void tearDown() {
        map = null;
    }

    @Test
    public void testMain() {
        map.put("key", "value");
        Assert.assertNotNull(map.get("key"));
    }

    @Test
    public void testChangeSizeWhenLoadFactorNotEksceed() {
        map = new HashMap(0.1);
        Key key1 = new Key(1);
        Key key2 = new Key(2);

        map.put(key1, "111");
        int sizeBefore = map.getListsCount();
        map.put(key2, "222");
        int sizeAfter = map.getListsCount();
        Assert.assertTrue(sizeAfter > sizeBefore);
    }

    @Test
    public void testRemoveElement() {
        map.put("key", "value");
        Assert.assertNotNull(map.get("key"));
        map.remove("key");
        Assert.assertNull(map.get("key"));
    }

    @Test
    public void testEmptyMap() {
        Assert.assertEquals(map.getMapSize(), 0);
    }

    @Test
    public void testPutMap() {
        map.put("2", "111");
        Assert.assertEquals(map.getMapSize(), 1);
    }

    @Test
    public void testSipleAddWithoutReloadArray() {
        map = new HashMap(0.2);
        Key key1 = new Key(1);
        Key key2 = new Key(2);

        map.put(key1, "111");
        int sizeBefore = map.getListsCount();
        map.put(key2, "222");
        int sizeAfter = map.getListsCount();
        Assert.assertEquals(sizeAfter, sizeBefore);
    }
}

class Key {
    int hash;

    public Key(int hash) {
        this.hash = hash;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() == this.getClass()) {
            if (((Key) obj).hash == this.hash) {
                return true;
            }
        }
        return false;
    }

}
