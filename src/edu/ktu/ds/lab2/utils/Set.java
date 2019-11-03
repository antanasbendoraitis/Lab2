package edu.ktu.ds.lab2.utils;

/**
 * Interfeisu aprašomas Aibės ADT.
 *
 * @param <E> Aibės elemento duomenų tipas
 */
public interface Set<E> extends Iterable<E> {

    //Patikrinama ar aibė tuščia.
    boolean isEmpty();

    // Grąžinamas aibėje esančių elementų kiekis.
    int size();

    // Išvaloma aibė.
    void clear();

    // Aibė papildoma nauju elementu.
    void add(E element);
    
    //Iš aibės pašalinamas ir grąžinamas diždiausias elementas
    E pollLast();
    
    //Grąžinamas didesnis elementas, neradus null
    E higher(E element);

    // Pašalinamas elementas iš aibės.
    void remove(E element);

    // Patikrinama ar elementas egzistuoja aibėje.
    boolean contains(E element);
    
    //Aibės papildymas, jei papildoma grąžinama true
    boolean addAll(BstSet<? extends E> c);
    
    //Aibės sumažinimas, jei niekas nepašalinama grąžinama true
    boolean removeAll(BstSet<? extends E> c);

    // Grąžinamas aibės elementų masyvas.
    Object[] toArray();

    // Gražinamas vizualiai išdėstytas aibės elementų turinys
    String toVisualizedString(String dataCodeDelimiter);
}
