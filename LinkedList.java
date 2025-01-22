/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		first = null;
		last = first;
		size = 0;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index > size || first == null) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		ListIterator itr = iterator();
		int i = 0;
		while(itr.hasNext() && i < index) {
			itr.next();
			i++;
		}
		return itr.current;
	}

	public Node getFirst() {
		return first;
	}

	public Node getLast() {
		return last;
	}
	
	public int getSize() {
		return size;
	}
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}

		if (index == size) {
			addLast(block);
			return;
		}

		if (index == 0) {
			addFirst(block);
			return;
		}

		Node pointer = first;
		
		for (int i = 0; i < index - 1 ;i++) {
			pointer = pointer.next;
		}

		Node nextNode = pointer.next;
		pointer.next = new Node(block);
		pointer.next.next = nextNode;
		size++;
		return;
	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		Node newNode = new Node(block);


		if (last == null) {
			first = newNode;
			last = first;
			size++;
			return;
		}

		last.next = newNode;
		last = newNode;
		size++;
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's first element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		Node newNode = new Node(block);

		if (first == null) {
			first = newNode;
			last = first;
			size++;
			return;
		}

		newNode.next = first;
		first = newNode;
		size++;
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {

		Node node =  getNode(index);
		if (node == null) return null;
		return node.block;
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		ListIterator itr = iterator();
		int index = 0;

		while(itr.hasNext()) {
			if (itr.next() == block) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public void removeLast() {
		ListIterator itr = iterator();
		while (itr.current.next != last) {
			itr.next();
		}
		itr.current.next = null;
		last = itr.current;
		size--;
		return;
	}

	public void removeFirst() {

		Node temp = first;
		first = first.next;
		temp = null;
		size--;
		if (size == 0) {
			last = null;
		}
		return;
	}

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {

		if (node == null) throw new NullPointerException("node cannot be null");
		if (first == null) throw new NullPointerException("List is empty");

		if (node == first && node == last) {
			first = null;
			last = null;
			size--;
			return;
		}

		if (node == last) {
			removeLast();
		}

		if (node == first) {
			removeFirst();
		}

		ListIterator itr = iterator();

		while (itr.current.next != null) {
			if (itr.current.next == node) {
				Node temp = itr.current.next;
				itr.current.next = temp.next;
				temp = null;
				size--;
			}
			itr.next();
		}
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {

		if (index < 0 || index > size) throw new IllegalArgumentException("index must be between 0 and size");
		if (0 == size)  throw new IndexOutOfBoundsException("List is empty.");

		Node pointer = first;
		for (int i = 0; i < index; i++) {
			pointer = pointer.next;
		}

		remove(pointer);
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		int index = indexOf(block);
		Node node = getNode(index);

		remove(node);
	}	

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator(){
		return new ListIterator(first);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	@Override
	public String toString() {
		String str = "";
		ListIterator itr = iterator();
		while (itr.hasNext()) {
			str += itr.next() + " ";
		}

		return str;
	}
}