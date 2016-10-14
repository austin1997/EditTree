package editortrees;

import java.util.ArrayList;
import java.util.Stack;

import org.omg.CORBA.Current;

// A height-balanced binary tree with rank that could be the basis for a text editor.

/**
 * 
 * @author Jacob Soehren, Shijun Yu, Zhihong Zhai 2016
 *
 */
public class EditTree {

	private Node root;
	private int rotationCount;
	private int size;

	/**
	 * MILESTONE 1 Construct an empty tree
	 */
	public EditTree() {
		this.size = 0;
		this.rotationCount = 0;
		this.root = Node.NULL_NODE;
	}

	/**
	 * MILESTONE 1 Construct a single-node tree whose element is cc
	 * 
	 * @param ch
	 */
	public EditTree(char ch) {
		this.root = new Node(ch, this);
		this.size = 1;
		this.rotationCount = 0;
	}

	/**
	 * MILESTONE 2 Make this tree be a copy of e, with all new nodes, but the
	 * same shape and contents.
	 * 
	 * @param e
	 */
	public EditTree(EditTree e) {
		if (e.size <= 0) {
			this.root = Node.NULL_NODE;
			return;
		}
//		this.root = new Node(e.root.element, this);
//		int temp = e.root.rank;
//		this.size = 1;
//		for (int i = 0; i < e.size(); i++) {
//			if (i == temp) continue;
//			this.add(e.get(i), i);
//		}
		this.size = e.size;
		Stack<Node> st1 = new Stack<Node>();
		Stack<Node> st2 = new Stack<Node>();
		this.root = new Node(e.root.element, this);
		this.root.rank = e.root.rank;
		this.root.balance = e.root.balance;
		Node current1 = this.root;
		Node current2 = e.root;
		
		for (int i = 0; i < this.size - 1; i++) {
			if (current2.left == Node.NULL_NODE && current2.right == Node.NULL_NODE) {
				try {
					current2 = st2.pop();
					current1 = st1.pop();
					current1.right = new Node(current2.element, current2.rank, current2.balance, current1);
					current1 = current1.right;
				} catch (Exception EmptyStackException) {
					// TODO Auto-generated catch-block stub.
					return;
				}

			} else if (current2.left == Node.NULL_NODE) {
				current2 = current2.right;
				current1.right = new Node(current2.element, current2.rank, current2.balance, current1);
				current1 = current1.right;
			} else if (current2.right == Node.NULL_NODE) {
				current2 = current2.left;
				current1.left = new Node(current2.element, current2.rank, current2.balance, current1);
				current1 = current1.left;
			} else {
				st2.push(current2.right);
				st1.push(current1);
				current2 = current2.left;
				current1.left = new Node(current2.element, current2.rank, current2.balance, current1);
				current1 = current1.left;
			} 
		}
		this.rotationCount = 0;
	}

	/**
	 * MILESTONE 3 Create an EditTree whose toString is s. This can be done in
	 * O(N) time, where N is the length of the tree (repeatedly calling insert()
	 * would be O(N log N), so you need to find a more efficient way to do this.
	 * 
	 * @param s
	 */
	public EditTree(String s) {
		if (s.length() <= 0){
			this.root = Node.NULL_NODE;
		}
		this.root = Node.NULL_NODE;
		this.root = this.root.add(s, this);
		this.size = s.length();
		this.rotationCount = 0;
	}

	/**
	 * MILESTONE 1 returns the total number of rotations done in this tree since
	 * it was created. A double rotation counts as two.
	 *
	 * @return number of rotations since tree was created.
	 */
	public int totalRotationCount() {
		return this.rotationCount; // replace by a real calculation.
	}

	public void increaseRotationCount(int n) {
		this.rotationCount += n;
	}

	/**
	 * MILESTONE 1 return the string produced by an inorder traversal of this
	 * tree
	 */
	@Override
	public String toString() {
		return this.root.toString();
	}

	/**
	 * MILESTONE 1 This one asks for more info from each node. You can write it
	 * like the arraylist-based toString() method from the BST assignment.
	 * However, the output isn't just the elements, but the elements, ranks, and
	 * balance codes. Former CSSE230 students recommended that this method,
	 * while making it harder to pass tests initially, saves them time later
	 * since it catches weird errors that occur when you don't update ranks and
	 * balance codes correctly. For the tree with node b and children a and c,
	 * it should return the string: [b1=, a0=, c0=] There are many more examples
	 * in the unit tests.
	 * 
	 * @return The string of elements, ranks, and balance codes, given in a
	 *         pre-order traversal of the tree.
	 */
	public String toDebugString() {
		if (this.size <= 0)
			return "[]";
		return "[" + this.root.toDebugString().substring(2) + "]";
	}

	/**
	 * MILESTONE 1
	 * 
	 * @param ch
	 *            character to add to the end of this tree.
	 */
	public void add(char ch) {
		// Notes:
		// 1. Please document chunks of code as you go. Why are you doing what
		// you are doing? Comments written after the code is finalized tend to
		// be useless, since they just say WHAT the code does, line by line,
		// rather than WHY the code was written like that. Six months from now,
		// it's the reasoning behind doing what you did that will be valuable to
		// you!
		// 2. Unit tests are cumulative, and many things are based on add(), so
		// make sure that you get this one correct.
		if (this.size <= 0) {
			this.root = new Node(ch, this);
			this.size++;
			return;
		}
		this.root = this.root.add(ch);
		this.size++;

	}

	/**
	 * MILESTONE 1
	 * 
	 * @param ch
	 *            character to add
	 * @param pos
	 *            character added in this inorder position
	 * @throws IndexOutOfBoundsException
	 *             id pos is negative or too large for this tree
	 */
	public void add(char ch, int pos) throws IndexOutOfBoundsException {
		if (pos > this.size || pos < 0)
			throw new IndexOutOfBoundsException();
		if (pos == this.size) {
			this.add(ch);
			return;
		}
		this.root = this.root.add(ch, pos);
		this.size++;
	}

	/**
	 * MILESTONE 1
	 * 
	 * @param pos
	 *            position in the tree
	 * @return the character at that position
	 * @throws IndexOutOfBoundsException
	 */
	public char get(int pos) throws IndexOutOfBoundsException {
		if (pos >= this.size || pos < 0)
			throw new IndexOutOfBoundsException();

		return this.getNode(pos).element;
	}
	
	public Node getNode(int pos) throws IndexOutOfBoundsException{
		if (pos >= this.size || pos < 0)
			throw new IndexOutOfBoundsException();

		return this.root.get(pos);
	}
	

	/**
	 * MILESTONE 1
	 * 
	 * @return the height of this tree
	 */
	public int height() {
		return this.root.height();
	}

	/**
	 * MILESTONE 2
	 * 
	 * @return the number of nodes in this tree
	 */
	public int size() {
		return this.size; // replace by a real calculation.
	}

	/**
	 * MILESTONE 2
	 * 
	 * @param pos
	 *            position of character to delete from this tree
	 * @return the character that is deleted
	 * @throws IndexOutOfBoundsException
	 */
	public char delete(int pos) throws IndexOutOfBoundsException {
		// Implementation requirement:
		// When deleting a node with two children, you normally replace the
		// node to be deleted with either its in-order successor or predecessor.
		// The tests assume assume that you will replace it with the
		// *successor*.
		if (pos >= this.size || pos < 0)
			throw new IndexOutOfBoundsException();
		char temp = this.root.get(pos).element;
		this.root = this.root.delete(pos);
		this.size--;
		return temp; // replace by a real calculation.
	}

	/**
	 * MILESTONE 3, EASY This method operates in O(length*log N), where N is the
	 * size of this tree.
	 * 
	 * @param pos
	 *            location of the beginning of the string to retrieve
	 * @param length
	 *            length of the string to retrieve
	 * @return string of length that starts in position pos
	 * @throws IndexOutOfBoundsException
	 *             unless both pos and pos+length-1 are legitimate indexes
	 *             within this tree.
	 */
	public String get(int pos, int length) throws IndexOutOfBoundsException {
		if(pos + length > this.size || pos < 0) throw new IndexOutOfBoundsException();
		StringBuilder str = new StringBuilder(length);
		for(int i = pos; i < pos + length; i ++){
			str.append(this.get(i));
		}
		return str.toString();
	}

	/**
	 * MILESTONE 3, MEDIUM - SEE PAPER REFERENCED IN SPEC FOR ALGORITHM! Append
	 * (in time proportional to the log of the size of the larger tree) the
	 * contents of the other tree to this one. Other should be made empty after
	 * this operation.
	 * 
	 * @param other
	 * @throws IllegalArgumentException
	 *             if this == other
	 */
	public void concatenate(EditTree other) throws IllegalArgumentException {
		if (this == other) throw new IllegalArgumentException();
		this.root = this.root.concatenate(other);
		other.root = Node.NULL_NODE;
	}

	/**
	 * MILESTONE 3: DIFFICULT This operation must be done in time proportional
	 * to the height of this tree.
	 * 
	 * @param pos
	 *            where to split this tree
	 * @return a new tree containing all of the elements of this tree whose
	 *         positions are >= position. Their nodes are removed from this
	 *         tree.
	 * @throws IndexOutOfBoundsException
	 */
	public EditTree split(int pos) throws IndexOutOfBoundsException {
		return null; // replace by a real calculation.
	}

	/**
	 * MILESTONE 3: JUST READ IT FOR USE OF SPLIT/CONCATENATE This method is
	 * provided for you, and should not need to be changed. If split() and
	 * concatenate() are O(log N) operations as required, delete should also be
	 * O(log N)
	 * 
	 * @param start
	 *            position of beginning of string to delete
	 * 
	 * @param length
	 *            length of string to delete
	 * @return an EditTree containing the deleted string
	 * @throws IndexOutOfBoundsException
	 *             unless both start and start+length-1 are in range for this
	 *             tree.
	 */
	public EditTree delete(int start, int length) throws IndexOutOfBoundsException {
		if (start < 0 || start + length >= this.size())
			throw new IndexOutOfBoundsException(
					(start < 0) ? "negative first argument to delete" : "delete range extends past end of string");
		EditTree t2 = this.split(start);
		EditTree t3 = t2.split(length);
		this.concatenate(t3);
		return t2;
	}

	/**
	 * MILESTONE 3 Don't worry if you can't do this one efficiently.
	 * 
	 * @param s
	 *            the string to look for
	 * @return the position in this tree of the first occurrence of s; -1 if s
	 *         does not occur
	 */
	public int find(String s) {
		return -2;
	}

	/**
	 * MILESTONE 3
	 * 
	 * @param s
	 *            the string to search for
	 * @param pos
	 *            the position in the tree to begin the search
	 * @return the position in this tree of the first occurrence of s that does
	 *         not occur before position pos; -1 if s does not occur
	 */
	public int find(String s, int pos) {
		return -2;
	}

	/**
	 * @return The root of this tree.
	 */
	public Node getRoot() {
		return this.root;
	}
}
