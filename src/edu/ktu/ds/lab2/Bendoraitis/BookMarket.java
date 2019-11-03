/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.Bendoraitis;

import edu.ktu.ds.lab2.utils.BstSet;
import edu.ktu.ds.lab2.utils.Set;

/**
 *
 * @author Antanas
 */
public class BookMarket {
    public static Set<String> dublicateBookWriters(Book[] books){
        Set<Book> uni = new BstSet<>(Book.byMake);
        Set<String> dublicates = new BstSet<>();
        for (Book book : books) {
            int sizeBefore = uni.size();
            uni.add(book);
            if (sizeBefore == uni.size()) {
                dublicates.add(book.getWriter());
            }
        }
        return dublicates;
    }   
    
    public static Set<String> uniqueBooks(Book[] books)
    {
        Set<String> uniqueBooks = new BstSet<>();
        for (Book book : books) {
            uniqueBooks.add(book.getBookName());
        }
        return uniqueBooks;
    }
}
