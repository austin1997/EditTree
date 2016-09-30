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
	
	Character element;            
	Node left, right; // subtrees
	int rank;         // inorder position of this node within its own subtree.
	Code balance; 
	Node parent;  // You may want this field.
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
	 */
	public Node(Character element) {
		super();
		this.element = element;
		this.left = NULL_NODE;
		this.right = NULL_NODE;
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
	}

	public int size() {
		return -1;
	}

}