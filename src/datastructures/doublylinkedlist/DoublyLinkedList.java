package datastructures.doublylinkedlist;

public class DoublyLinkedList {

    private Node head; // doubly linked list does not change in its constructor from singly linked
    private Node tail;
    private int length;

    class Node { //Node class fields do change with doubly linked list so there is a 'prev' field in addition to next
        // so we can travel both ways through the list, not just forward
        int value;
        Node next;
        Node prev;

        Node(int value) { //node constructor unchanged in doubly linked list, both direction fields assigned later
            this.value = value;
        }
    }

    public DoublyLinkedList(int value) {
        Node newNode = new Node(value);
        head = newNode;
        tail = newNode;
        length = 1;
    }

    public void printList() {
        Node temp = head; //both head and temp point to first node
        while (temp != null) { //while temp has something to point to, while loop continues
            System.out.println(temp.value); //print temp value
            temp = temp.next; //reassign temp to next Node or null
        }
    } //uses while loop to iterate. O(n)

    public void getHead() {
        if (head == null) {
            System.out.println("Head: null");
        } else {
            System.out.println("Head: " + head.value);
        }
    } //O(1)

    public void getTail() {
        if (head == null) {
            System.out.println("Tail: null");
        } else {
            System.out.println("Tail: " + tail.value);
        }
    } //O(1)

    public void getLength() {
        System.out.println("Length: " + length);
    } // O(1)

    public void append (int value) {
        Node newNode = new Node(value); //new node pointer and Node object created with set value but directions not assigned
        if(length == 0) { // if doubly linked list is empty, head and tail equal the newNode pointer
            head = newNode;
            tail = newNode;
        } else { //if 1+ items
            tail.next = newNode; // tail points to last Node and assigns its 'next' field as the newNode pointer
            newNode.prev = tail; // newNode points to the new node Object and assigns its 'prev' field to point back at tail
            tail = newNode; //tail reassigned to tail and newNode variables both point to appended new Node object
        }
        length++;
    } // O(1)

    public Node removeLast() {
        if(length == 0) return null;
        Node temp = tail;
        if (length == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
            temp.prev = null;
        }
        length--;
        return temp;
    } // O(1)

    public void prepend(int value) {
        Node newNode = new Node(value);
        if(length == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        length++;
    } // O(1)

    public Node removeFirst() {
        if(length == 0) return null;
        Node temp = head;
        if(length == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
            temp.next = null;
        }
        length--;
        return temp;
    } // O(1)

    public Node get(int index) {
        if (index < 0 || index >= length) return null;
        Node temp = head;
        if (index < length/2) { //since doubly linked list, we use head to search first half going forward, but
            // use tail if searching back half
            for (int i = 0; i < index; i++) { //incrementing to before prior index to assign temp to next node and return
                temp = temp.next;
            }
        } else {
            temp = tail;
            for (int i = length - 1; i > index; i--) { //decrementing to index if in back half of list
                temp = temp.prev;
            }
        }
        return temp;
    } // uses two for loops so O(2n) --> drop constant so get() is O(n)

    public boolean set(int index, int value) {
        Node temp = get(index);
        if(temp != null) {
            temp.value = value;
            return true;
        }
        return false;
    } // uses get() so set() is also O(n)

    public boolean insert(int index, int value) {
        if(index < 0 || index > length) return false;
        if(index == 0) {
            prepend(value);
            return true;
        }
        if(index == length) {
            append(value);
            return true;
        }
        Node newNode = new Node(value);
        Node before = get(index - 1); //get method returns Node at given index, so index-1 is the preceding node
        Node after = before.next; // after is effectively at the index we want to place the value at
        newNode.prev = before;
        newNode.next = after;
        before.next = newNode;
        after.prev = newNode;
        //before and after no longer point at each other and now point/get pointed at by newNode
        length++;
        return true;
    } // utilizes append() and prepend both O(1) but uses get() which is
    // O(n), drop non-dominant so insert() is O(n)

    public Node remove(int index) {
        if(index < 0 || index >= length) return null;
        if(index == 0) return removeFirst();
        if(index == length - 1) return removeLast();

        Node temp = get(index);

        temp.next.prev = temp.prev; //calls the 'previous' field of next node to point at node preceding temp
        temp.prev.next = temp.next; //calls 'next' field of preceding node to point to node after temp
        temp.next = null; // temp variable is still pointing at node before and after it, so we break it off
        // by reassigning prev and next fields to null
        temp.prev = null;

        length--;
        return temp;
    } // reuses removeFirst() and removeLast() both of which are O(1) but also uses
    // get() which is O(n) so drop non-dominants, remove() is O(n)

}

