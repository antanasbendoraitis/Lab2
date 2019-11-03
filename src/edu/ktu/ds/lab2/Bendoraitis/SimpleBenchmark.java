/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.Bendoraitis;

import edu.ktu.ds.lab2.gui.ValidationException;
import edu.ktu.ds.lab2.utils.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Antanas
 */
public class SimpleBenchmark {
    
    public static final String FINISH_COMMAND = "                       ";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("edu.ktu.ds.lab2.gui.messages");

   // private static final String[] BENCHMARK_NAMES = {"addBstRec", "addBstIte", "addAvlRec", "removeBst"};
    private static final String[] BENCHMARK_NAMES = {"addBstRec", "addAllBstRec",
        "tailSetBstRec", "pollLastBstSet", "higherBstSet", "removeAllBstSet", "subSetBstSet", "TreeSetAdd", "HashSetAdd", "TreeSetRemove", "HashSetRemove"};
    private static final int[] COUNTS = {10000, 20000, 40000, 80000};

    private final Timekeeper timeKeeper;
    private final String[] errors;

    private final SortedSet<Book> cSeries = new BstSet<>(Book.byPrice);
    private final TreeSet<Book> cSeries2 = new TreeSet<>(Book.byPrice);
    private final HashSet<Book> cSeries3 = new HashSet<>();
    private final SortedSet<Book> cSeriesPagalb = new BstSet<>(Book.byPrice); //Papildomas

    /**
     * For console benchmark
     */
    public SimpleBenchmark() {
        timeKeeper = new Timekeeper(COUNTS);
        errors = new String[]{
                MESSAGES.getString("badSetSize"),
                MESSAGES.getString("badInitialData"),
                MESSAGES.getString("badSetSizes"),
                MESSAGES.getString("badShuffleCoef")
        };
    }

    /**
     * For Gui benchmark
     *
     * @param resultsLogger
     * @param semaphore
     */
    public SimpleBenchmark(BlockingQueue<String> resultsLogger, Semaphore semaphore) {
        semaphore.release();
        timeKeeper = new Timekeeper(COUNTS, resultsLogger, semaphore);
        errors = new String[]{
                MESSAGES.getString("badSetSize"),
                MESSAGES.getString("badInitialData"),
                MESSAGES.getString("badSetSizes"),
                MESSAGES.getString("badShuffleCoef")
        };
    }

    public static void main(String[] args) {
        executeTest();
    }

    public static void executeTest() {
        // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
        Ks.out("Greitaveikos tyrimas:\n");
        new SimpleBenchmark().startBenchmark();
    }

    public void startBenchmark() {
        try {
            benchmark();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    private void benchmark() throws InterruptedException {
        try {
            Book b5 = new Book("ImanuelKant Critique 1999 55 65");
            Book b8 = new Book("Dekartes Metafizika 1998 950 77");
            Runtime runtime = Runtime.getRuntime();
            for (int k : COUNTS) {
                Book[] books = new BooksGenerator().generateShuffle(k, 1.0);
                cSeries.clear();
                cSeries2.clear();
                cSeries3.clear();
                Arrays.stream(books).forEach(cSeries::add);
                timeKeeper.startAfterPause();

                timeKeeper.start();
                long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                Arrays.stream(books).forEach(cSeries::add);
                long usedMemory = runtime.totalMemory() - runtime.freeMemory() - usedMemoryBefore;
                timeKeeper.finish(BENCHMARK_NAMES[0]);
                
                usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                Arrays.stream(books).forEach((c) -> cSeries.addAll((BstSet)cSeriesPagalb));
                long usedMemory1 = runtime.totalMemory() - runtime.freeMemory() - usedMemoryBefore;
                timeKeeper.finish(BENCHMARK_NAMES[1]);
                
                usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                Arrays.stream(books).forEach((c) -> cSeries.tailSet(b8));
                long usedMemory2 = runtime.totalMemory() - runtime.freeMemory() - usedMemoryBefore;
                timeKeeper.finish(BENCHMARK_NAMES[2]);
                
                usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                Arrays.stream(books).forEach((c) -> cSeries.pollLast());
                long usedMemory3 = runtime.totalMemory() - runtime.freeMemory() - usedMemoryBefore;
                timeKeeper.finish(BENCHMARK_NAMES[3]);
                
                usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                Arrays.stream(books).forEach((c) -> cSeries.higher(b5));
                long usedMemory4 = runtime.totalMemory() - runtime.freeMemory() - usedMemoryBefore;
                timeKeeper.finish(BENCHMARK_NAMES[4]);
                
                usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                Arrays.stream(books).forEach((c) -> cSeries.removeAll((BstSet)cSeriesPagalb));
                long usedMemory5 = runtime.totalMemory() - runtime.freeMemory() - usedMemoryBefore;
                timeKeeper.finish(BENCHMARK_NAMES[5]);
                
                usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                Arrays.stream(books).forEach((c) -> cSeries.subSet2(b5, false, b8, false));
                long usedMemory6 = runtime.totalMemory() - runtime.freeMemory() - usedMemoryBefore;
                timeKeeper.finish(BENCHMARK_NAMES[6]);
                
                usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                Arrays.stream(books).forEach(cSeries2::add);
                long usedMemory7 = runtime.totalMemory() - runtime.freeMemory() - usedMemoryBefore;
                timeKeeper.finish(BENCHMARK_NAMES[7]);
                
                usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                Arrays.stream(books).forEach(cSeries3::add);
                long usedMemory8 = runtime.totalMemory() - runtime.freeMemory() - usedMemoryBefore;
                timeKeeper.finish(BENCHMARK_NAMES[8]);
                
                usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                Arrays.stream(books).forEach(cSeries2::remove);
                long usedMemory9 = runtime.totalMemory() - runtime.freeMemory() - usedMemoryBefore;
                timeKeeper.finish(BENCHMARK_NAMES[9]);
                
                usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
                Arrays.stream(books).forEach(cSeries3::remove);
                long usedMemory10 = runtime.totalMemory() - runtime.freeMemory() - usedMemoryBefore;
                timeKeeper.finish(BENCHMARK_NAMES[10]);
                timeKeeper.seriesFinish();
                System.out.println("Memory increased: 1| " + usedMemory + " 2| " 
                    + usedMemory1 + " 3| " + usedMemory2 + " 4| " + usedMemory3 
                    + " 5| " + usedMemory4 + " 6| " + usedMemory5 + " 7| " 
                    + usedMemory6 + " 8| " + usedMemory7);
            }
            timeKeeper.logResult(FINISH_COMMAND);
        } catch (ValidationException e) {
            if (e.getCode() >= 0 && e.getCode() <= 3) {
                timeKeeper.logResult(errors[e.getCode()] + ": " + e.getMessage());
            } else if (e.getCode() == 4) {
                timeKeeper.logResult(MESSAGES.getString("allSetIsPrinted"));
            } else {
                timeKeeper.logResult(e.getMessage());
            }
        }
    }
}
