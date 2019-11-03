package edu.ktu.ds.lab2.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Rikiuojamos objektų kolekcijos - aibės realizacija dvejetainiu paieškos
 * medžiu.
 *
 * @param <E> Aibės elemento tipas. Turi tenkinti interfeisą Comparable<E>, arba
 *            per klasės konstruktorių turi būti paduodamas Comparator<E> interfeisą
 *            tenkinantis objektas.
 * 
 * @author darius.matulis@ktu.lt
 * @užduotis Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 */
public class BstSet<E extends Comparable<E>> implements SortedSet<E>, Cloneable {

    // Medžio šaknies mazgas
    protected BstNode<E> root = null;
    //Tarpiniams skaičiavimams
    protected BstNode<E> between = null;
    // Medžio dydis
    protected int size = 0;
    // Rodyklė į komparatorių
    protected Comparator<? super E> c = null;

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparable<E>
     */
    public BstSet() {
        this.c = Comparator.naturalOrder();
    }

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparator<E>
     *
     * @param c Komparatorius
     */
    public BstSet(Comparator<? super E> c) {
        this.c = c;
    }

    /**
     * Patikrinama ar aibė tuščia.
     *
     * @return Grąžinama true, jei aibė tuščia.
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return Grąžinamas aibėje esančių elementų kiekis.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Išvaloma aibė.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Patikrinama ar aibėje egzistuoja elementas.
     *
     * @param element - Aibės elementas.
     * @return Grąžinama true, jei aibėje egzistuoja elementas.
     */
    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in contains(E element)");
        }

        return get(element) != null;
    }

    /**
     * Aibė papildoma nauju elementu.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }

        root = addRecursive(element, root);
    }
    /**
     * 
     * @param c Aibė.
     * @return Grąžinama true, jei aibė pridėta.
     */      //-----------------------------------------------------------------------Aibės pridėjimas--------------||
    public boolean addAll(BstSet<? extends E> c)
    {
        if (c == null) {
            return false;
        }
        Iterator iter = c.iterator();
        while(iter.hasNext())
        {
            E el = (E)iter.next();
            add(el);
        }
        return true;
    }
    private BstNode<E> addRecursive(E element, BstNode<E> node) {
        if (node == null) {
            size++;
            return new BstNode<>(element);
        }

        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = addRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = addRecursive(element, node.right);
        }

        return node;
    }

    /**
     * Pašalinamas elementas iš aibės.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in remove(E element)");
        }

        root = removeRecursive(element, root);
    }

    private BstNode<E> removeRecursive(E element, BstNode<E> node) {
        if (node == null) {
            return node;
        }
        // Medyje ieškomas šalinamas elemento mazgas;
        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = removeRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = removeRecursive(element, node.right);
        } else if (node.left != null && node.right != null) {
            /* Atvejis kai šalinamas elemento mazgas turi abu vaikus.
             Ieškomas didžiausio rakto elemento mazgas kairiajame pomedyje.
             Galima kita realizacija kai ieškomas mažiausio rakto
             elemento mazgas dešiniajame pomedyje. Tam yra sukurtas
             metodas getMin(E element);
             */
            BstNode<E> nodeMax = getMax(node.left);
            /* Didžiausio rakto elementas (TIK DUOMENYS!) perkeliamas į šalinamo
             elemento mazgą. Pats mazgas nėra pašalinamas - tik atnaujinamas;
             */
            node.element = nodeMax.element;
            // Surandamas ir pašalinamas maksimalaus rakto elemento mazgas;
            node.left = removeMax(node.left);
            size--;
            // Kiti atvejai
        } else {
            node = (node.left != null) ? node.left : node.right;
            size--;
        }

        return node;
    }

    public boolean removeAll(BstSet<? extends E> c)//-----------------------------------------------Visų elementų pašalinimas--------------||
    {
        if (c == null) {
            return false;
        }
        if (!(c instanceof BstSet<?>)) {
            return false;
        }
        Iterator h = c.iterator();
        while(h.hasNext()) {
            E k = (E)h.next();
            removeRecursive(k, root);
        }
        return true;
    }
    
    private E get(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in get(E element)");
        }

        BstNode<E> node = root;
        while (node != null) {
            int cmp = c.compare(element, node.element);

            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node.element;
            }
        }

        return null;
    }

    /**
     * Pašalina maksimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> removeMax(BstNode<E> node) {
        if (node == null) {
            return null;
        } else if (node.right != null) {
            node.right = removeMax(node.right);
            return node;
        } else {
            return node.left;
        }
    }

    /**
     * Grąžina maksimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> getMax(BstNode<E> node) {
        return get(node, true);
    }

    /**
     * Grąžina minimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> getMin(BstNode<E> node) {
        return get(node, false);
    }

    private BstNode<E> get(BstNode<E> node, boolean findMax) {
        BstNode<E> parent = null;
        while (node != null) {
            parent = node;
            node = (findMax) ? node.right : node.left;
        }
        return parent;
    }

    /**
     * Grąžinamas aibės elementų masyvas.
     *
     * @return Grąžinamas aibės elementų masyvas.
     */
    @Override
    public Object[] toArray() {
        int i = 0;
        Object[] array = new Object[size];
        for (Object o : this) {
            array[i++] = o;
        }
        return array;
    }

    /**
     * Aibės elementų išvedimas į String eilutę Inorder (Vidine) tvarka. Aibės
     * elementai išvedami surikiuoti didėjimo tvarka pagal raktą.
     *
     * @return elementų eilutė
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (E element : this) {
            sb.append(element.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Medžio vaizdavimas simboliais, žiūr.: unicode.org/charts/PDF/U2500.pdf
     * Tai 4 galimi terminaliniai simboliai medžio šakos gale
     */
    private static final String[] term = {"\u2500", "\u2534", "\u252C", "\u253C"};
    private static final String rightEdge = "\u250C";
    private static final String leftEdge = "\u2514";
    private static final String endEdge = "\u25CF";
    private static final String vertical = "\u2502  ";
    private String horizontal;

    /* Papildomas metodas, išvedantis aibės elementus į vieną String eilutę.
     * String eilutė formuojama atliekant elementų postūmį nuo krašto,
     * priklausomai nuo elemento lygio medyje. Galima panaudoti spausdinimui į
     * ekraną ar failą tyrinėjant medžio algoritmų veikimą.
     *
     * @author E. Karčiauskas
     */
    @Override
    public String toVisualizedString(String dataCodeDelimiter) {
        horizontal = term[0] + term[0];
        return root == null ? ">" + horizontal
                : toTreeDraw(root, ">", "", dataCodeDelimiter);
    }

    private String toTreeDraw(BstNode<E> node, String edge, String indent, String dataCodeDelimiter) {
        if (node == null) {
            return "";
        }
        String step = (edge.equals(leftEdge)) ? vertical : "   ";
        StringBuilder sb = new StringBuilder();
        sb.append(toTreeDraw(node.right, rightEdge, indent + step, dataCodeDelimiter));
        int t = (node.right != null) ? 1 : 0;
        t = (node.left != null) ? t + 2 : t;
        sb.append(indent).append(edge).append(horizontal).append(term[t]).append(endEdge).append(
                split(node.element.toString(), dataCodeDelimiter)).append(System.lineSeparator());
        step = (edge.equals(rightEdge)) ? vertical : "   ";
        sb.append(toTreeDraw(node.left, leftEdge, indent + step, dataCodeDelimiter));
        return sb.toString();
    }

    private String split(String s, String dataCodeDelimiter) {
        int k = s.indexOf(dataCodeDelimiter);
        if (k <= 0) {
            return s;
        }
        return s.substring(0, k);
    }

    /**
     * Sukuria ir grąžina aibės kopiją.
     *
     * @return Aibės kopija.
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        BstSet<E> cl = (BstSet<E>) super.clone();
        if (root == null) {
            return cl;
        }
        cl.root = cloneRecursive(root);
        cl.size = this.size;
        return cl;
    }

    private BstNode<E> cloneRecursive(BstNode<E> node) {
        if (node == null) {
            return null;
        }

        BstNode<E> clone = new BstNode<>(node.element);
        clone.left = cloneRecursive(node.left);
        clone.right = cloneRecursive(node.right);
        return clone;
    }

    /**
     * Grąžinamas aibės poaibis iki elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis iki elemento. //poaibis kas žemiau
     */
    @Override //-----------------------------------------------------------------Grąžinamas aibės poaibis iki elemento--------------------||
    public Set<E> headSet(E element) {
        Set<E> el = new BstSet<E>();
           if (element == null) {
               return null;
           // throw new IllegalArgumentException("Element is null in get(E element)");
        }
        BstNode<E> node = root;
        while (node != null) {
            int cmp = c.compare(element, node.element);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                recTakeFrom(el, node);
                break;
            }
        }
        return el;
    }
    
   /* private void findNode(E element, BstNode<E> node, BstNode<E> backNode)
    {
         // System.out.println(node.element + "sgfa");
        //int tmp = c.compare(element, node.element);
        if ( c.compare(element, node.element) == 1) {
            System.out.println("Sg");
            //System.out.println(node.element + "sgfa");
            backNode = node.right;
              System.out.println(backNode.element + "sgfa");
        }
        
         if (node.right != null) {
                    findNode(element, node.right, backNode);
        }
        if (node.left != null) {
                    findNode(element, node.left, backNode);
        }
    }*/
    /**
     * 
     * @param el   - Aibės elementas.
     * @param node - Aibės poaibis iki elemento.
     */
    public void recTakeFrom(Set<E> el, BstNode<E> node)
    {
        if (node.right != null) {
            el.add(node.right.element);
                    recTakeFrom(el, node.right);
        }
        if (node.left != null) {
            el.add(node.left.element);
                    recTakeFrom(el, node.left);
        }
    }
    /**
     * Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     *
     * @param element1 - Pradinis aibės poaibio elementas.
     * @param element2 - Galinis aibės poaibio elementas. //Iš pirmos aibės atimame apatinę
     * @return Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     */
    @Override//-----------------------------------------------------------------Grąžinamas aibės poaibis nuo elemento element1 iki element2--------------------||
    public Set<E> subSet(E element1, E element2) {
           Set<E> el = headSet(element1);
           Set<E> el2 = tailSet(element2);
           if(el == null || el2 == null)
               return null;
           for (E e : el2) 
           {
            el.remove(e);
           }
        return el;
    }
    /**
     * 
     * @param fromElement   - Ar įtraukti pradinį aibės poaibio elementą.
     * @param fromInclusive - Pradinis aibės poaibio elementas.
     * @param toElement     - Galinis aibės poaibio elementas.
     * @param toInclusive   - Ar įtraukti galinį aibės poaibio elementą.
     * @return Grąžinamas aibės poaibis nuo(su) elemento(-u) fromInclusive iki(su) toInclusive.
     */        //-----------------------------------------------------------------------------Grąžinamas surikiuotas aibės poaibis nuo(su) elemento(-u) fromInclusive iki(su) toInclusive--------||
    public SortedSet<E> subSet2(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)
    {
        
        Set<E> set = null;
        Set<E> set2 = null;
        SortedSet<E> sort = new BstSet<E>();
        if (fromInclusive)
            set = tailSet(fromElement);
        else
            set = headSet(fromElement);
        if (toInclusive) //Atvirkščiai negu set elemento įskaitymas vyksta sukeitus headSet ir tailSet vietomis
            set2 = headSet(toElement);
        else
            set2 = tailSet(toElement);
        if(set2 != null)
        {
            for (E e : set2) {
                set.remove(e);
            }
            for (E e : set) {
                sort.add(e);
            }
        }
        return sort;
    }

    /**
     * Grąžinamas aibės poaibis iki elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis nuo elemento.
     */
    @Override  //-----------------------------------------------------------------------------Grąžinamas aibės poaibis nuo elementoe--------||
    public Set<E> tailSet(E element) {
        Set<E> el = new BstSet<E>();
        if (element == null) {
            //throw new IllegalArgumentException("Element is null in get(E element)");
            return null;
        }
        el.add(element);
        BstNode<E> node = root;
        while (node != null) {
            int cmp = c.compare(element, node.element);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {

                recTakeFrom(el, node);
                break;
            }
        }
        return el;
    }
    /**
     * Grąžinamas surikiuotas aibės poaibis iki elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas surikiuotas aibės poaibis nuo elemento.
     *///-------------------------------------------------------------------------Grąžinamas surikiuotas aibės poaibis nuo elemento------------||
    public SortedSet<E> tailSet2(E element)
    {
        SortedSet<E> sort =  new BstSet<E>();
        Set<E> el = tailSet(element);
        for (E e : el) {
            sort.add(e);
        }
        return sort;
    }
    /**
     * 
     * @return Grąžinamas medžio aukštis.
     *///-------------------------------------------------------------------------Grąžinamas medžio aukštis------------||
    public int height()
    {
        if (root == null) {
            return 0;
        }
        else if (root.left == null && root.right == null) {
            return 1;
        }
        BstNode<E> node = root;
        int max = 0;
        List<Integer> d = new ArrayList<Integer>();
        recHeight(root, d, 1);
        System.out.print("--------------------");
        for (Integer heights : d) {
            System.out.print(heights + " ");
            if (heights > max) {
                max = heights;
            }
        }
        return max;
    }
        /**
     * 
     * @param node
     * @param height 
     */
    public void recHeight(BstNode<E> node, List<Integer> height, int count)
    {
        count++;
        if (node.right != null) {
            height.add(count);
                    recHeight(node.right, height, count);
        }
        if (node.left != null) {
            height.add(count);
                    recHeight(node.left, height, count);
        }
    }
    /**
     * 
     * @param element - Aibės elementas.
     * @return Grąžinamas didesnis aibės elementas, neradus null.
     *///-------------------------------------------------------------------------Grąžinamas didesnis aibės elementas, neradus null------------||
    public E higher(E element)
    {
        if (element == null) {
            return null;
        }
        BstNode<E> node = root;
        while (node != null) {
            int cmp = c.compare(element, node.element);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node.right == null ? null : node.right.element;
            }
        }
        return null;
    }
    /**
     * 
     * @return Grąžina ir pašalina diždiausią elementą.
     *///-------------------------------------------------------------------------Grąžina ir pašalina diždiausią elementą.------------||
    public E pollLast()
    {
        if(root == null)
            return null;
        else if(root.right == null && root.left == null)
        {
            between = new BstNode<E>(root.element);
            size = 0;
            root = null;
            return between.element;
        }
        else if(root.right == null && root.left != null)
        {
            between = new BstNode<E>(root.element);
            root = root.left;
            size--;
            return between.element;
        }
        else
        {
            between = root;
            recMax(root);
            if (higher(between.element) != null) {
                between = new BstNode<E>(higher(between.element));
            }
            removeRecursive(between.element, root);
        }
        return between.element;
    }
    /**
     * 
     * @param node Pradinis aibės elementas
     */
    public void recMax(BstNode<E> node)
    {
        if (node.right != null) {
            between = node.right.element.compareTo(between.element) > 0 ? node.right : between;
                    recMax(node.right);
        }
        if (node.left != null) {
            between = node.left.element.compareTo(between.element) > 0 ? node.left : between;
                    recMax(node.left);
        }
    }
    /**
     * Grąžinamas tiesioginis iteratorius.
     *
     * @return Grąžinamas tiesioginis iteratorius.
     */
    @Override
    public Iterator<E> iterator() {
        return new IteratorBst(true);
    }

    /**
     * Grąžinamas atvirkštinis iteratorius.
     *
     * @return Grąžinamas atvirkštinis iteratorius.
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new IteratorBst(false);
    }
    
/**
     * 
     * @param fromElement   - Ar įtraukti pradinį aibės poaibio elementą.
     * @param fromInclusive - Pradinis aibės poaibio elementas.
     * @param toElement     - Galinis aibės poaibio elementas.
     * @param toInclusive   - Ar įtraukti galinį aibės poaibio elementą.
     * @return Grąžinamas aibės poaibis nuo(su) elemento(-u) fromInclusive iki(su) toInclusive.
     */        //-----------------------------------------------------------------------------Grąžinamas aibės poaibis nuo(su) elemento(-u) fromInclusive iki(su) toInclusive--------||
    @Override
    public Set<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        Set<E> set = null;
        Set<E> set2 = null;
        if (fromInclusive)
            set = tailSet(fromElement);
        else
            set = headSet(fromElement);
        if (toInclusive) //Atvirkščiai negu set elemento įskaitymas vyksta sukeitus headSet ir tailSet vietomis
            set2 = headSet(toElement);
        else
            set2 = tailSet(toElement);
        for (E e : set2) {
            set.remove(e);
        }
        return set;
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Vidinė objektų kolekcijos iteratoriaus klasė. Iteratoriai: didėjantis ir
     * mažėjantis. Kolekcija iteruojama kiekvieną elementą aplankant vieną kartą
     * vidine (angl. inorder) tvarka. Visi aplankyti elementai saugomi steke.
     * Stekas panaudotas iš java.util paketo, bet galima susikurti nuosavą.
     */
    private class IteratorBst implements Iterator<E> {

        private Stack<BstNode<E>> stack = new Stack<>();
        // Nurodo iteravimo kolekcija kryptį, true - didėjimo tvarka, false - mažėjimo
        private boolean ascending;
        // Nurodo einamojo medžio elemento tėvą. Reikalingas šalinimui.
        private BstNode<E> parent = root;
        
        private BstNode<E> n = null;
        
        private BstNode<E> parent2 = root;
                
        private int remov = 0;
        private int size2 = 0;

        IteratorBst(boolean ascendingOrder) {
            this.ascending = ascendingOrder;
            this.toStack(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public E next() {
            if (!stack.empty()) {
               // System.out.println( "--------------------222");
                size2++;
                if (remov == 0) //Šalinimas iš vidurio
                       n = stack.pop();
                else if(remov == 1)
                {
                    remov = 0;
                    removeRecursive(n.element, root);
                    // Grąžinamas paskutinis į steką patalpintas elementas
                    n = stack.pop();
                }
                // Atsimenama tėvo viršunė. Reikia remove() metodui
                parent = (!stack.empty()) ? stack.peek() : root;
                BstNode<E> node = (ascending) ? n.right : n.left;
                // Dešiniajame n pomedyje ieškoma minimalaus elemento,
                // o visi paieškos kelyje esantys elementai talpinami į steką
                toStack(node);
                return n.element;
            } else { // Jei stekas tuščias
                return null;
            }
        }
 
        @Override //-------------------------------------------------------------------------Iteracinis šalinimo metodas------------||
        public void remove() {
            remov = 1; //nukreipiamas vidinių elementų šalinimas
           /* if (size2 == 1) {
               // n.element = null;
               System.out.println(n.element.toString() + "------");
                removeRecursive(n.element, root);
               // next();
            }*/
            if (size2 == size) { //šalinamas paskutinis elementas
                removeRecursive(n.element, root);  
            }
            //throw new UnsupportedOperationException("Studentams reikia realizuoti remove()");
        }

        private void toStack(BstNode<E> n) {
            while (n != null) {
                stack.push(n);
                n = (ascending) ? n.left : n.right;
            }
        }
    }

    /**
     * Vidinė kolekcijos mazgo klasė
     *
     * @param <N> mazgo elemento duomenų tipas
     */
    protected class BstNode<N> {

        // Elementas
        protected N element;
        // Rodyklė į kairįjį pomedį
        protected BstNode<N> left;
        // Rodyklė į dešinįjį pomedį
        protected BstNode<N> right;

        protected BstNode() {
        }

        protected BstNode(N element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }
    }
}
