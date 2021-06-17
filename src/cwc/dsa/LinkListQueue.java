package cwc.dsa;

/**
 * This is the node added to the linked list queue
 * @author Stefan Babukov
 */
class Node<E> {
    E data;
    Node<E> next;
     Node(E e){
         this.data = e;
         this.next = null;
     }
}
/**
 * This class creates a linked list queue 
 * @author Stefan Babukov
 * @param <E> generic class for the data stored in each node
 */
public class LinkListQueue<E> {
    public Node<E> front; //this is also the head
    private Node<E> rear; 
    private int length;
    /**
     * creates an empty list
     */
    public void createList(){
        front = null;
        length = 0;
        rear = null;
    }
    
    public int length(){
        return length;
    }
    
    public boolean isEmpty(){
        return (length==0);
    }
    /**
     * displays the nodes in the queue
     */
    public void displayQueue(){
        Node<E> current = front;
        while(current!=null){
            String[] data = (String[]) current.data;
            System.out.println(data[0] + " " +  data[1] + " " + data[2]);
            current = current.next;
        }
    }
    /**
     * Puts another item at the rear of the queue
     * @param item to be enqueued 
     */
    public void enqueue(E item){
        Node<E> newNode = new Node<E>(item);
        if(isEmpty()){
          front = newNode;
        }else{
          rear.next = newNode;
        }
        rear = newNode;
        length++;
    }
    /**
     * Dequeues the front node and moves the front 1 place further
     * @return the front node of the queue
     */
    public E dequeue(){
        if(isEmpty()){
           throw new NullPointerException("Queue is empty, add items first!");
        }
        E data = front.data;
        front = front.next;
        length--;
        return data;
    }
    /**
     * Does not remove the node at the front, only returns it
     * @return the front node
     */
    public E peek(){
        if(isEmpty()){
          throw new NullPointerException("Queue is empty, add items first!");
        }
        return front.data;
    }
}
