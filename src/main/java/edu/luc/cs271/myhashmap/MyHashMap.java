package edu.luc.cs271.myhashmap;

import java.util.*;

/**
 * A generic HashMap custom implementation using chaining. Concretely, the table is an ArrayList of
 * chains represented as LinkedLists.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class MyHashMap<K, V> implements Map<K, V> {

  private static final int DEFAULT_TABLE_SIZE = 11; // a prime

  private List<List<Entry<K, V>>> table;

  public MyHashMap() {
    this(DEFAULT_TABLE_SIZE);
  }

  public MyHashMap(final int tableSize) {
    // allocate a table of the given size
    table = new ArrayList<>(tableSize);
    // then create an empty chain at each position
    for (int i = 0; i < tableSize; i += 1) {
      table.add(new LinkedList<>());
    }
  }

  @Override
  public int size() {
    // DONE add the sizes of all the chains
    int result = 0;
    for(int i = 0; i < table.size(); i++){
       result = result + table.get(i).size();
    }
    return result;
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public boolean containsKey(final Object key) {
    // DONE follow basic approach of remove below (though this will be much simpler)
    
    final int index = calculateIndex(key);
    final Iterator<Entry<K, V>> iter = table.get(index).iterator();
    while (iter.hasNext()) {
      final Entry<K, V> entry = iter.next();
      if (entry.getKey().equals(key)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean containsValue(final Object value) {
    // DONE follow basic approach of remove below (though this will be much simpler)
    for(int i = 0; i < table.size(); i++){
    final Iterator<Entry<K, V>> iter = table.get(i).iterator();
    while (iter.hasNext()) {
      final Entry<K, V> entry = iter.next();
      if (entry.getValue().equals(value)) {
        return true;
      }
    }
    }
    return false;
  }

  @Override
  public V get(final Object key) {
    // DONE follow basic approach of remove below (though this will be simpler)
    
    final int index = calculateIndex(key);
    final Iterator<Entry<K, V>> iter = table.get(index).iterator();
    while (iter.hasNext()) {
      final Entry<K, V> entry = iter.next();
      if (entry.getKey().equals(key)) {
        
        return entry.getValue();
      }
    }

    return null;
  }

  @Override
  public V put(final K key, final V value) {
    // DONE follow basic approach of remove below (this will be similar)
    final int index = calculateIndex(key);
    final Iterator<Entry<K, V>> iter = table.get(index).iterator();
    while (iter.hasNext()) {
      final Entry<K, V> entry = iter.next();
      if (entry.getKey().equals(key)) {
        final V oldValue = entry.getValue();
        entry.setValue(value);
        return oldValue;
      }
    }
     table.get(index).add(new AbstractMap.SimpleEntry<K,V>(key,value));
    
    
    return null;
  }

  @Override
  public V remove(final Object key) {
    final int index = calculateIndex(key);
    final Iterator<Entry<K, V>> iter = table.get(index).iterator();
    while (iter.hasNext()) {
      final Entry<K, V> entry = iter.next();
      if (entry.getKey().equals(key)) {
        final V oldValue = entry.getValue();
        iter.remove();
        return oldValue;
      }
    }
    return null;
  }

  @Override
  public void putAll(final Map<? extends K, ? extends V> m) {
    // TODO add each entry in m's entrySet
    for(Entry<? extends K, ? extends V> e : m.entrySet()){
      this.put(e.getKey(), e.getValue());
    }

  }

  @Override
  public void clear() {
    // DONE clear each chain
    for(int i = 0; i < table.size();i++){
      table.get(i).clear();
    }

  }

  /** The resulting keySet is not "backed" by the Map, so we keep it unmodifiable. */
  @Override
  public Set<K> keySet() {
    final Set<K> result = new HashSet<>();
    // DONE populate the set
    for(int index = 0; index < table.size(); index++){
     final Iterator<Entry<K, V>> iter = table.get(index).iterator();
     while (iter.hasNext()) {
      final Entry<K, V> entry = iter.next();
      result.add(entry.getKey());
    }
    }
    return Collections.unmodifiableSet(result);
  }

  /** The resulting values collection is not "backed" by the Map, so we keep it unmodifiable. */
  @Override
  public Collection<V> values() {
    final List<V> result = new LinkedList<>();
    // DONE populate the list
    for(int index = 0; index < table.size(); index++){
     final Iterator<Entry<K, V>> iter = table.get(index).iterator();
     while (iter.hasNext()) {
      final Entry<K, V> entry = iter.next();
      result.add(entry.getValue());
    }
    }


    return Collections.unmodifiableCollection(result);
  }

  /** The resulting entrySet is not "backed" by the Map, so we keep it unmodifiable. */
  @Override
  public Set<Entry<K, V>> entrySet() {
    final Set<Entry<K, V>> result = new HashSet<>();
    // DONE populate the set
    for(int i = 0; i < table.size(); i ++){
      for(Entry<K,V> e : table.get(i)){
        result.add(e);
      }
    } 
    return Collections.unmodifiableSet(result);
  }

  @Override
  public String toString() {
    // DONE return the string representation of the underlying table
    return table.toString();
  }

  public boolean equals(final Object that) {
    if (this == that) {
      return true;
    } else if (!(that instanceof Map)) {
      return false;
    } else {
      // TODO simply compare the entry sets
      
      /*for(Entry<K,V> e : this.entrySet()){
        if(!(that.containsKey(e.getKey()))) return false;
      }*/
      //if(this.entrySet() == that.entrySet()) return true;
      
      return true;
    }
  }

  private int calculateIndex(final Object key) {
    // positive remainder (as opposed to %)
    // required in case hashCode is negative!
    return Math.floorMod(key.hashCode(), table.size());
  }
}
