/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.Bendoraitis;

import edu.ktu.ds.lab2.utils.Ks;
import edu.ktu.ds.lab2.utils.Parsable;

import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author Antanas
 */
public class Book implements Parsable<Book> {
    
    private static final int minYear = 1994;
    private static final int currentYear = LocalDate.now().getYear();
    private static final double minPrice = 10.0;
    private static final double maxPrice = 1200.0;
    private static final String idCode = "PS";   //  ***** nauja
    private static int serNr = 100;               //  ***** nauja

    private final String bookRegNr;
    
    
    private String writer = "";
    private String bookName = "";
    private int year = -1;
    private int edition = -1;
    private double price = -1.0;
    
    
     public Book() {
        bookRegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr
    }
     
    public Book(String writer, String bookName, int year, int edition, double price){
        bookRegNr = idCode + (serNr++); 
        this.writer = writer;
        this.bookName = bookName;
        this.year = year;
        this.edition = edition;
        this.price = price;
        validate();
    }
       
       public Book(String dataString) {
        bookRegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr
        this.parse(dataString);
        validate();
    }
    public Book(Builder builder) {
        bookRegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr
        this.writer = builder.writer;
        this.bookName = builder.bookName;
        this.year = builder.year;
        this.edition = builder.edition;
        this.price = builder.price;
        validate();
    }
    
    private String validate() {
        String errorType = "";
        if (year < minYear || year > currentYear) {
            errorType = "Netinkami leidimo metai, turi būti ["
                    + minYear + ":" + currentYear + "]";
        }
        if (price < minPrice || price > maxPrice) {
            errorType += " Kaina už leistinų ribų [" + minPrice
                    + ":" + maxPrice + "]";
        }
        return errorType;
    }
    
    @Override
    public final void parse(String data)
    {
        try {//ed-tai elementarūs duomenys atskirti tarpais
            Scanner ed = new Scanner(data);
            writer = ed.next();
            bookName = ed.next();
            year = ed.nextInt();
            edition = ed.nextInt();
            setPrice(ed.nextDouble());
        }
        catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas -> " + data);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų apie leidinį -> " + data);
        }
    }
    
    @Override
    public String toString() {  // papildyta su carRegNr
        return getBookRegNr() + "=" + writer + "_" + bookName + ":" + year + " " + getEdition() + " " + String.format("%4.1f", price);
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
    
    public String getWriter(){
        return writer;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
      public String getBookRegNr() {  //** nauja.
        return bookRegNr;
    }
      
        @Override
    public int compareTo(Book book) {
        return getBookRegNr().compareTo(book.getBookRegNr());
    }
    @Override
    public boolean equals(Object obj)
    {
        Book bk = (Book)obj;
        if (writer.equals(bk.writer) && bookName.equals(bk.bookName)) {
            if (bookRegNr == bk.bookRegNr && year == bk.year && 
                    edition == bk.edition && price == bk.price) {
                return true;
            }
            return false;
        }
        return false;
    }
    
    public static Comparator<Book> byMake = (Book k1, Book k2) -> k1.writer.compareTo(k2.writer); // pradžioje pagal rašytojus, o po to pagal knygas
    
    public static Comparator<Book> byPrice = (Book k1, Book k2) -> {
        // didėjanti tvarka, pradedant nuo mažiausios
        if (k1.price < k2.price) {
            return -1;
        }
        if (k1.price > k2.price) {
            return +1;
        }
        return 0;
    };

    public static Comparator<Book> byYearPrice = (Book k1, Book k2) -> {
        // metai mažėjančia tvarka, esant vienodiems lyginama kaina
        if (k1.year > k2.year) {
            return +1;
        }
        if (k1.year < k2.year) {
            return -1;
        }
        if (k1.price > k2.price) {
            return +1;
        }
        if (k1.price < k2.price) {
            return -1;
        }
        return 0;
    };
    
  public static class Builder {

        private final static Random RANDOM = new Random(1949);  // Atsitiktinių generatorius
        private final static String[][] BOOKS = { // galimų knygų masyvas
                {"Hr", "ImanuelKant", "323", "Critique", "13"},
                {"Nietche", "NichomakoEtika", "Metafizika", "51", "NichomakoEtika", "96"},
                {"Aristotelis", "Valstybė", "Zaratrusta"}
        };

          private String writer = "";
          private String bookName = "";
          private int year = -1;
          private int edition = -1;
          private double price = -1.0;

        public Book build() {
            return new Book(this);
        }

        public Book buildRandom() {
            int ma = RANDOM.nextInt(BOOKS.length);        // markės indeksas  0..
            int mo = RANDOM.nextInt(BOOKS[ma].length - 1) + 1;// modelio indeksas 1..
            return new Book(BOOKS[ma][0],
                    BOOKS[ma][mo],
                    1990 + RANDOM.nextInt(20),// metai tarp 1990 ir 2009
                    6000 + RANDOM.nextInt(222000),// rida tarp 6000 ir 228000
                    800 + RANDOM.nextDouble() * 88000);// kaina tarp 800 ir 88800
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder writer(String writer) {
            this.writer = writer;
            return this;
        }

        public Builder bookName(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public Builder edition(int edition) {
            this.edition = edition;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }
    }
}
