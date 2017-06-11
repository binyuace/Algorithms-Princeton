import java.util.Iterator;
import java.util.NoSuchElementException;
// 10 Jun 2017 
// Bin Yu

public class Deque<Item> implements Iterable<Item> {
   private DequeNode<Item> first;
   private DequeNode<Item> last;
   private int count;
  
   private class DequeNode<Item> { 
       private Item info; 
       private DequeNode<Item> next;
       private DequeNode<Item> prev;
   }
   
   public Deque(){                           // construct an empty deque
       first = null;
       last = null;
       count = 0;
   }
   public boolean isEmpty(){                 // is the deque empty?
       return (first == null && last == null);
   }
   public int size(){                        // return the number of items on the deque
       return count;
   }
   public void addFirst(Item item) {          // add the item to the front
       DequeNode<Item> oldfirst = first;
       first = new DequeNode<Item>();       
       first.info = item;       
       first.next = oldfirst;
       if(oldfirst != null) oldfirst.prev = first;
       if (count==0) last = first;
       count++;
   }
   public void addLast(Item item) {           // add the item to the end
       DequeNode<Item> oldlast = last;
       last = new DequeNode<Item>();
       last.info = item;
       last.prev = oldlast;
       if(oldlast!= null) oldlast.next = last;
       if(count == 0) first = last;
       count++;
   }    
   public Item removeFirst(){                // remove and return the item from the front
       if(count == 0) throw new NoSuchElementException();
       Item item = first.info;
       first = first.next;
       first.prev = null;
       count--;
       return item;
       
   }
   public Item removeLast() {                // remove and return the item from the end
       Item item = last.info;
       last = last.prev;
       last.next = null;
       count--;
       return item;
   }
   public Iterator<Item> iterator(){
       return new DequerIterator<Item>(first);
   
   }
//   public String toString() {
//        StringBuilder s = new StringBuilder();
//        for (Item item : this) {
//            s.append(item);
//            s.append(' ');
//        }
//        return s.toString();
//    }
//       
   private class DequerIterator<Item> implements Iterator<Item> {
        private DequeNode<Item> current;
        public DequerIterator(DequeNode<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.info;
            current = current.next; 
            return item;
        }
   }
   public static void main(String[] args) {  // unit testing (optional)
      Deque<String> deq = new Deque<String>();
      int n = args.length;
      for(int i = 0; i < n; i++){
          if(args[i].equals("-") ) deq.removeFirst();
          if(args[i].equals(".") ) deq.removeLast();    
          if(!args[i].equals("-") && !args[i].equals(".")){
              if(i%2==0) deq.addFirst(args[i]);
              else deq.addLast(args[i]);
          }
      }
      System.out.println(deq);
      System.out.println(deq.size());
//      System.out.println(deq.first);
  }

} 


  
  