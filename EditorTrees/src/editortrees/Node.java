package editortrees;

// A node in a height-balanced binary tree with rank.
// Except for the NULL_NODE (if you choose to use one), one node cannot
// belong to two different trees.

public class Node {
	
	
	public static Node NULL_NODE = new Node(null);
	enum Code {
		SAME, LEFT, RIGHT;
		// Used in the displayer and debug string
		public String toString() {
			switch (this) {
				case LEFT:
					return "/";
				case SAME:
					return "=";
				case RIGHT:
					return "\\";
				default:
					throw new IllegalStateException();
			}
		}
	}
	
	// The fields would normally be private, but for the purposes of this class, 
	// we want to be able to test the results of the algorithms in addition to the
	// "publicly visible" effects
	
	private Character element;            
	private Node left, right; // subtrees
	private int rank;         // inorder position of this node within its own subtree.
	private Code balance; 
	private Node parent;  // You may want this field.
	// Feel free to add other fields that you find useful

	// You will probably want to add several other methods

	// For the following methods, you should fill in the details so that they work correctly
	
	
	
	public int height() {
		if(this == NULL_NODE) return -1;
		int left = this.left.height();
		int right = this.right.height();
		return left > right ? left + 1 : right + 1;
	}
	
	
	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 * @param element
	 * @param rank
	 */
	public Node(Character element, int rank) {
		super();
		this.element = element;
		this.rank = rank;
		this.left = NULL_NODE;
		this.right = NULL_NODE;
		this.balance = Code.SAME;
	}


	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 * @param element
	 * @param parent
	 */
	public Node(Character element, Node parent) {
		super();
		this.element = element;
		this.parent = parent;
		this.left = NULL_NODE;
		this.right = NULL_NODE;
		this.balance = Code.SAME;
	}


	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 * @param element
	 */
	public Node(Character element) {
		super();
		this.element = element;
		this.left = NULL_NODE;
		this.right = NULL_NODE;
		this.balance = Code.SAME;
	}



	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 * @param element
	 * @param left
	 * @param right
	 */
	public Node(Character element, Node left, Node right) {
		super();
		this.element = element;
		this.left = left;
		this.right = right;
		this.balance = Code.SAME;
	}
	
	private Node rotateLeft(Node parent, Node child){
		parent.right = child.left;
		child.left = parent;
		child.balance = Code.SAME;
		parent.balance = Code.SAME;
		return child;
	}
	
	private Node rotateRight(Node parent, Node child){
		parent.left = child.right;
		child.right = parent;
		child.balance = Code.SAME;
		parent.balance = Code.SAME;
		return child;
	}

	public int size() {
		if (this == NULL_NODE) return 0;
		return this.left.size() + this.right.size() + 1;
	}


	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param ch
	 * @return
	 */
	public Node add(char ch) {
		// TODO Auto-generated method stub.
		if(this == NULL_NODE) {
			Node temp = new Node(ch, this);
			temp.rank = this.rank + 1;
			return temp;
		}
		this.right = this.right.add(ch);
		if (this.balance.equals("=")) {
			this.balance = Code.RIGHT;
		}else if (this.balance.equals("\\")){
			
			return this.rotateLeft(this, this.right);
		}else {
			this.balance = Code.SAME;
		}
		return this;
	}

}