import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom; 
import edu.princeton.cs.algs4.StdIn;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] list;
    private int size;
    private Item[] newlist;
    private int count;
//    private LinkedList<Item> list = new LinkedList<Item>();
    
    public RandomizedQueue() {                 // construct an empty randomized queue
        list = (Item[]) new Object[2];
        count = 0;
        size = 2;
    }
    public boolean isEmpty() {                 // is the queue empty?
        return (count == 0);
    }
    public int size() {                      // return the number of items on the queue
        return count;
    }
    private void resize(int newsize){
        newlist = (Item[]) new Object[newsize];
        for(int i = 0; i < count; i++){
            newlist[i] = list[i];   
        }
        size = newsize;
        list = newlist;
    }
    public void enqueue(Item item){           // add the item
        if (item == null) throw new NullPointerException("not allwoed to add null");
        if (count == size) resize(2 * size);
        list[count] = item;
        count++;
    }    
    public Item dequeue() {                    // remove and return a random item
        if(count == 0) throw new NoSuchElementException("Stackunderflow");
        int randint  = StdRandom.uniform(0, count);
        count--;
        Item deq = list[randint];
        list[randint] = list[count];
        list[count] = null;
        
        if (count != 0 && count == size/4) resize(size/2);
        return deq;
    }
    public Item sample() {                    // return (but do not remove) a random item
        if(count == 0) throw new NoSuchElementException("Stackunderflow");
        int randint1 = StdRandom.uniform(0, count);
        return list[randint1];
    }
    public Iterator<Item> iterator(){         // return an independent iterator over items in random order
        return new RandomizedIterator();
    }
    private class RandomizedIterator implements Iterator<Item> {
        private int n;
        private int[] shuff;
        private int i;
        public RandomizedIterator(){
            i = -1;
            n = count;
            shuff = new int[n];
            for(int j = 0; j < n; j++){
                shuff[j] =j;
            }
            StdRandom.shuffle(shuff);
        }
        public boolean hasNext() {
            if (i+1 >= n) return false;
            else return true;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            i++;
            Item item = list[ shuff[i] ];
            return item;
        }
    }
//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        for (Item item : this) {
//            s.append(item);
//            s.append(' ');
//        }
//        return s.toString();
//    }
    public static void main(String[] args){   // unit testing (optional)
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        int a = 0;
        System.out.println("(" + rq.size() + " left on stack)");
        while (a < 20) {
            
            if (a % 5 == 2)
                System.out.println(rq.dequeue()+" is leaving");
            
            rq.enqueue(a);
            System.out.println(rq);
            a++;
        }
        System.out.println("(" + rq +" "+rq.size()+ " left on stack)"); 
    }
    
}



