/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.Bendoraitis;
        
import static edu.ktu.ds.lab2.demo.ManualTest.executeTest;
import edu.ktu.ds.lab2.utils.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;

/**
 * Aibės testavimas be Gui
 * @author Antanas
 */
public class ManualTest {
    
    static Book[] books;
    static ParsableSortedSet<Book> bSeries = new ParsableBstSet<>(Book::new, Book.byPrice);
    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        executeTest();
    }
     public static void executeTest() throws CloneNotSupportedException {
        Book b1 = new Book("JK", "Hr", 2000, 30, 20);
        Book b2 = new Book.Builder()
                .writer("ImanuelKant")
                .bookName("Critique")
                .year(2015)
                .edition(50)
                .price(35)
                .build();
        Book b3 = new Book.Builder().buildRandom();
        Book b4 = new Book("JK Hr 1500 35 15");
        Book b5 = new Book("ImanuelKant Critique 1999 55 65");
        Book b6 = new Book("Nietche Zaratrusta 1999 55 65");
        Book b7 = new Book("Aristotelis NichomakoEtika 2001 70 30");
        Book b8 = new Book("Dekartes Metafizika 1998 950 77");
        Book b9 = new Book("Platonas Valstybė 2007 364 85.3");
        
        Book b10 = new Book("Nietche Zaratrusta 1999 55 65");

        Book[] booksArray = {b9, b7, b8, b5, b1, b6, b10};

        Ks.oun("Knygų Aibė:");
        ParsableSortedSet<Book> booksSet = new ParsableBstSet<>(Book::new);

        for (Book c : booksArray) {
            booksSet.add(c);
            Ks.oun("Aibė papildoma: " + c + ". Jos dydis: " + booksSet.size());
        }
        Ks.oun("");
        Ks.oun(booksSet.toVisualizedString(""));

        ParsableSortedSet<Book> booksSetCopy = (ParsableSortedSet<Book>) booksSet.clone();

        booksSetCopy.add(b2);
        booksSetCopy.add(b3);
        booksSetCopy.add(b4);
        Ks.oun("Papildyta knygųaibės kopija:");
        Ks.oun(booksSetCopy.toVisualizedString(""));

        b9.setEdition(100);

        Ks.oun("Originalas:");
        Ks.ounn(booksSet.toVisualizedString(""));

        Ks.oun("Ar elementai egzistuoja aibėje?");
        for (Book c : booksArray) {
            Ks.oun(c + ": " + booksSet.contains(c));
        }
        Ks.oun(b2 + ": " + booksSet.contains(b2));
        Ks.oun(b3 + ": " + booksSet.contains(b3));
        Ks.oun(b4 + ": " + booksSet.contains(b4));
        Ks.oun("");

        Ks.oun("Ar elementai egzistuoja aibės kopijoje?");
        for (Book c : booksArray) {
            Ks.oun(c + ": " + booksSetCopy.contains(c));
        }
        Ks.oun(b2 + ": " + booksSetCopy.contains(b2));
        Ks.oun(b3 + ": " + booksSetCopy.contains(b3));
        Ks.oun(b4 + ": " + booksSetCopy.contains(b4));
        Ks.oun("");

        Ks.oun("Elementų šalinimas iš kopijos. Aibės dydis prieš šalinimą:  " + booksSetCopy.size());
        for (Book c : new Book[]{b2, b1, b9, b8, b5, b3, b4, b2, b7, b6, b7, b9}) {
            booksSetCopy.remove(c);
            Ks.oun("Iš knygų aibės kopijos pašalinama: " + c + ". Jos dydis: " + booksSetCopy.size());
        }
        Ks.oun("");

        Ks.oun("Knygų aibė su iteratoriumi:");
        Ks.oun("");
        for (Book c : booksSet) {
            Ks.oun(c);
        }
        Ks.oun("");
        Ks.oun("Knygų aibė AVL-medyje:");
        ParsableSortedSet<Book> booksSetAvl = new ParsableAvlSet<>(Book::new);
        for (Book c : booksArray) {
            booksSetAvl.add(c);
        }
        Ks.ounn(booksSetAvl.toVisualizedString(""));

        Ks.oun("Knygų aibė su iteratoriumi:");
        Ks.oun("");
        for (Book c : booksSetAvl) {
            Ks.oun(c);
        }

        Ks.oun("");
        Ks.oun("Knygų aibė su atvirkštiniu iteratoriumi:");
        Ks.oun("");
        Iterator iter = booksSetAvl.descendingIterator();
        while (iter.hasNext()) {
            Ks.oun(iter.next());
        }

        Ks.oun("");
        Ks.oun("Knygų aibės toString() metodas:");
        Ks.ounn(booksSetAvl);

        // Išvalome ir suformuojame aibes skaitydami iš failo
        booksSet.clear();
        booksSetAvl.clear();

        Ks.oun("");
        Ks.oun("Knygų aibė DP-medyje:");
        booksSet.load("data\\book.txt");
        Ks.ounn(booksSet.toVisualizedString(""));
        Ks.oun("Išsiaiškinkite, kodėl medis augo tik į vieną pusę.");
        Ks.oun("Medis augo, tik į vieną pusę dėl indeksų ir pagrinde vieno paskui kitą sukabinimų elementų jų neišskirstant į šakas");
        Ks.oun("");
        Ks.oun("Knygų aibė AVL-medyje:");
        booksSetAvl.load("data\\book.txt");
        Ks.ounn(booksSetAvl.toVisualizedString(""));

        Set<String> booksSet4 = BookMarket.dublicateBookWriters(booksArray);
        Ks.oun("Pasikartojančios knygos:\n" + booksSet4.toString());

        Set<String> booksSet5 = BookMarket.uniqueBooks(booksArray);
        Ks.oun("Unikalūs automobilių modeliai:\n" + booksSet5.toString());
        
        //Removeall
        BstSet<Book> bookSet = new BstSet<Book>();
                    Book b11 = new Book("Aristotelis NichomakoEtika 2005 70 30");
        Book b12 = new Book("Dekartes Metafizika 2000 950 77");
        Book b13 = new Book("Platonas Valstybė 3000 364 85.3");
        bookSet.add(b1);
        bookSet.add(b4);
        bookSet.add(b11);
        bookSet.add(b12);


        bookSet.add(b6);
        bookSet.add(b13);
        bookSet.add(b2);
        bookSet.add(b3);
        bookSet.add(b7);
        bookSet.add(b8);
        bookSet.add(b9);
        bookSet.add(b5);

        Ks.ounn(bookSet.toVisualizedString(""));
        BstSet<Book> remove = new BstSet<Book>();
        remove.add(b4);
        remove.add(b6);
        remove.add(b9);
        
        System.out.println("|-----------RemoveAll------------|");
        bookSet.removeAll(remove);
        Ks.ounn(bookSet.toVisualizedString(""));

        System.out.println("|-----------HeadSet------------|");
        Set<Book> headSet = (Set<Book>)bookSet.headSet(b8);
        Ks.ounn(headSet.toVisualizedString(""));
        
        System.out.println("|-----------Tailset------------|");
        Set<Book> tailSet = (Set<Book>)bookSet.tailSet(b8);
        Ks.ounn(tailSet.toVisualizedString(""));
        
        System.out.println("|-----------Subset------------|");
        Set<Book> subSet = (Set<Book>)bookSet.subSet(b1, b7);
        Ks.ounn(subSet.toVisualizedString(""));
        System.out.println("|-----------Subset2--Exel------------|");
        SortedSet<Book> subSet2 = (SortedSet<Book>)bookSet.subSet2(b1, true, b7, true);
        Ks.ounn(subSet2.toVisualizedString(""));
        System.out.println("|-----------Tailset2--Exel------------|");
        SortedSet<Book> tailSet2 = (SortedSet<Book>)bookSet.tailSet2(b1);
        Ks.ounn(tailSet2.toVisualizedString(""));    
        System.out.println("|-----------Tree--Height------------|");
        System.out.println("Tree Height = " + bookSet.height() + "");
        System.out.println("|-----------Iterator--Remove------------|");
        Iterator<Book> it = bookSet.iterator();
        
         while (it.hasNext()) {
             
           Book k = (Book)it.next();
            if (k.equals(b13)) {
                  it.remove();
             }
             System.out.println(k.toString());
         }
         System.out.println("/--/" + bookSet.size());
                       
         for (Book boo : bookSet) {
             System.out.println(boo.toString());
         }
         System.out.println("|-----------AddAll--Exel------------|");
         BstSet<Book> addAll = new BstSet<Book>();
         addAll.add(b5);
         BstSet<Book> elements = new BstSet<Book>();
         elements.add(b13);
         elements.add(b1);
         addAll.addAll(elements);
         Ks.ounn(addAll.toVisualizedString(""));
         System.out.println("|-----------Higher--Exel------------|");
         System.out.println(addAll.higher(b5));
         System.out.println("|-----------PollLast--Exel------------|");
         System.out.println(bookSet.pollLast().toString() + "---------");
         Ks.ounn(bookSet.toVisualizedString(""));
         System.out.println("|-----------AVLSet--Remove------------|");
         AvlSet<Book> avlSet = new AvlSet<Book>();
         avlSet.add(b1);
         avlSet.add(b4);
         avlSet.add(b11);
         avlSet.add(b12);
         
         avlSet.add(b6);
         avlSet.add(b13);
         avlSet.add(b2);
         avlSet.add(b3);
         avlSet.add(b7);
         avlSet.add(b8);
         avlSet.add(b9);
         avlSet.add(b5);
         Ks.ounn(avlSet.toVisualizedString(""));
         avlSet.remove(b4);
         Ks.ounn(avlSet.toVisualizedString(""));
    }

    static ParsableSortedSet generateSet(int kiekis, int generN) {
        books = new Book[generN];
        for (int i = 0; i < generN; i++) {
            books[i] = new Book.Builder().buildRandom();
        }
        Collections.shuffle(Arrays.asList(books));

        bSeries.clear();
        Arrays.stream(books).limit(kiekis).forEach(bSeries::add);
        return bSeries;
    }
}
