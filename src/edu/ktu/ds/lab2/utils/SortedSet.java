package edu.ktu.ds.lab2.utils;

import java.util.Iterator;

public interface SortedSet<E> extends Set<E> {

    /**
     * Grąžinamas aibės poaibis iki elemento data.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis iki elemento data.
     */
    Set<E> headSet(E element);

    /**
     * Grąžinamas aibės poaibis nuo elemento data1 iki data2.
     *
     * @param element1 - pradinis aibės poaibio elementas.
     * @param element2 - galinis aibės poaibio elementas.
     * @return Grąžinamas aibės poaibis nuo elemento data1 iki data2.
     */
    Set<E> subSet(E element1, E element2);
    
    /**
     * 
     * @param fromElement   - pradinis aibės poaibio elementas.
     * @param fromInclusive - ar įskaičiuoti pradinį aibės elementą.
     * @param toElement     - galinis aibės poaibio elementas
     * @param toInclusive   - ar įskaičiuoti galinį aibės elementą.
     * @return 
     */
    Set<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive);
    
    
    /**
     * 
     * @param fromElement   - Ar įtraukti pradinį aibės poaibio elementą.
     * @param fromInclusive - Pradinis aibės poaibio elementas.
     * @param toElement     - Galinis aibės poaibio elementas.
     * @param toInclusive   - Ar įtraukti galinį aibės poaibio elementą.
     * @return Grąžinamas aibės poaibis nuo(su) elemento(-u) fromInclusive iki(su) toInclusive.
     */
    SortedSet<E> subSet2(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive);

    /**
     * Grąžinamas aibės poaibis iki elemento data.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis nuo elemento data.
     */
    Set<E> tailSet(E element);

    /**
     * Grąžinamas atvirkštinis iteratorius.
     *
     * @return Grąžinamas atvirkštinis iteratorius.
     */
    Iterator<E> descendingIterator();
}
