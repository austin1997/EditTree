package editortrees;

import java.util.ArrayList;

// A node in a height-balanced binary tree with rank.
// Except for the NULL_NODE (if you choose to use one), one node cannot
// belong to two different trees.

/**
 * 
 * @author Jacob Soehren, Shijun Yu, Zhihong Zhai 2016
 *
 */
public class Node {

	public static final Node NULL_NODE = new Node();

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
	// we want to be able to test the results of the algorithms in addition to
	// the
	// "publicly visible" effects
	private EditTree tree;
	public Character element;
	Node left, right; // subtrees
	private int rank; // in-order position of this node within its own subtree.
	private Code balance;
	private Node parent; // You may want this field.
	// Feel free to add other fields that you find useful

	// You will probably want to add several other methods

	// For the following methods, you should fill in the details so that they
	// work correctly

	public Node() {
		// empty constructor for NULL_NODE creation
	}

	/**
	 * Node constructor with element and parent Node as parameters
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
		this.tree = this.parent.tree;
	}

	/**
	 * Node constructor with element and Editor Tree as parameters
	 *
	 * @param element
	 * @param tree
	 */
	public Node(Character element, EditTree tree) {
		super();
		this.element = element;
		this.left = NULL_NODE;
		this.right = NULL_NODE;
		this.balance = Code.SAME;
		this.rank = 0;
		this.tree = tree;
	}

	public int height() {
		if (this == NULL_NODE) {
			return -1;
		}
		return 1 + Math.max(this.left.height(), this.right.height());
	}

	/**
	 * perform the single left rotation and change the balance code and rank
	 * 
	 * @param parent
	 * @param child
	 * @return root of the new subtree
	 */
	private static Node rotateLeft(Node parent, Node child) {
		child.left.parent = parent;
		child.parent = parent.parent;
		parent.parent = child;
		///////////////////////////
		parent.right = child.left;
		child.left = parent;
		if (child.balance != Code.SAME) {
			child.balance = Code.SAME;
			parent.balance = Code.SAME;
		}else {
			child.balance = Code.LEFT;
			parent.balance = Code.RIGHT;
		}
		child.rank += parent.rank + 1;
		return child;
	}

	/**
	 * perform the single right rotation and change the balance code and rank
	 * 
	 * @param parent
	 * @param child
	 * @return root of the new subtree
	 */
	private static Node rotateRight(Node parent, Node child) {
		child.right.parent = parent;
		child.parent = parent.parent;
		parent.parent = child;
		///////////////////////
		parent.left = child.right;
		child.right = parent;
		if (child.balance != Code.SAME) {
			child.balance = Code.SAME;
			parent.balance = Code.SAME;
		}else {
			child.balance = Code.RIGHT;
			parent.balance = Code.LEFT;
		}
		parent.rank = parent.rank - child.rank - 1;

		return child;
	}

	/**
	 * Create a new node with the given character and add it to the end of the
	 * tree, also handle rebalance if necessary
	 *
	 * @param ch
	 * @return the root of the new tree
	 */
	public Node add(char ch) {
		// add to the right child of current Node
		if (this.right == NULL_NODE) {
			Node temp = new Node(ch, this);
			this.right = temp;
			if (this.balance == Code.SAME)
				this.balance = Code.RIGHT;
			else
				this.balance = Code.SAME;
			return this;
		}
		Code temp = this.right.balance;
		// recurse to the end of tree(position to add the Node)
		this.right = this.right.add(ch);
		// check balance and handle rotation if necessary
		if (this.right.balance != temp && this.right.balance != Code.SAME) {
			return rotateHandler(this, Code.RIGHT);
		}
		return this;
	}

	/**
	 * Create a new node with the given character and add it to the given
	 * position, also handle rebalance if necessary
	 *
	 * @param ch
	 * @param pos
	 * @return the root of the new tree
	 */
	public Node add(char ch, int pos) {
		// the position is at the 
		if (this.rank == pos) { 
			this.rank++;
			if (this.left == NULL_NODE) {
				this.left = new Node(ch, this);
				if (this.balance == Code.SAME)
					this.balance = Code.LEFT;
				else
					this.balance = Code.SAME;
				return this;
			} else {
				Code temp = this.left.balance;
				// recurse to the end of tree(position to add the Node)
				this.left = this.left.add(ch, pos);
				// check balance and handle rotation if necessary
				if (this.left.balance != temp && this.left.balance != Code.SAME) {
					return rotateHandler(this, Code.LEFT);
				}
			}
		} else if (this.rank > pos) {
			this.rank++;
			Code temp = this.left.balance;
			// recurse to the end of tree(position to add the Node)
			this.left = this.left.add(ch, pos);
			// check balance and handle rotation if necessary
			if (this.left.balance != temp && this.left.balance != Code.SAME) {
				return rotateHandler(this, Code.LEFT);
			}
		} else {
			if (this.right == NULL_NODE) {
				return this.add(ch);
			}
			Code temp = this.right.balance;
			// recurse to the end of tree(position to add the Node)
			this.right = this.right.add(ch, pos - this.rank - 1);
			// check balance and handle rotation if necessary
			if (this.right.balance != temp && this.right.balance != Code.SAME) {
				return rotateHandler(this, Code.RIGHT);
			}
		}
		return this;
	}

	/**
	 * Handle the rotation needed for a given node
	 * 
	 * @param node the node needed to check
	 * @param code the direction to tilt
	 * @return the root of the new subtree
	 */
	private static Node rotateHandler(Node node, Code code) {
		if (code == Code.SAME) {// don't need to tilt
			return node;
		}
		Code currentCode = node.balance;

		if (currentCode == Code.SAME) { // currently not tilted
			node.balance = code;
			return node;
		} else if (currentCode == Code.RIGHT) {// currently tilted right
			if (code == Code.LEFT) { // need to tilt left
				node.balance = Code.SAME;
				return node;
			} else { 
				Code temp = node.right.balance;
				if (temp == Code.RIGHT || temp == Code.SAME) { // check which rotation is needed
					node.tree.increaseRotationCount(1);
					return rotateLeft(node, node.right);
				} else {
					node.tree.increaseRotationCount(2);
					// node.right = rotateRight(node.right, node.right.left);
					return doubleRotateLeft(node);
				}
			}
		} else { // currently tilted left
			if (code == Code.RIGHT) {// need to tilt right
				node.balance = Code.SAME;
				return node;
			} else {
				Code temp = node.left.balance;
				if (temp == Code.LEFT || temp == Code.SAME) {// check which rotation is needed
					node.tree.increaseRotationCount(1);
					return rotateRight(node, node.left);
				} else {
					node.tree.increaseRotationCount(2);
					return doubleRotateRight(node);
				}
			}

		}
	}

	/**
	 * double rotate left of a given node
	 * 
	 * @param node node that an imbalance occours
	 * @return the root of the new subtree
	 */
	private static Node doubleRotateLeft(Node node) {
		Node A = node;
		Node C = node.right;
		Node B = C.left;
		A.right = B.left;
		C.left = B.right;
		B.parent = A.parent;
		A.parent = B;
		C.parent = B;
		if (B.balance == Code.LEFT) {
			A.balance = Code.SAME;
			C.balance = Code.RIGHT;
		} else if (B.balance == Code.RIGHT) {
			A.balance = Code.LEFT;
			C.balance = Code.SAME;
		} else {
			A.balance = Code.SAME;
			C.balance = Code.SAME;
		}
		B.balance = Code.SAME;
		B.left = A;
		B.right = C;
		C.rank = C.rank - B.rank - 1;
		B.rank += A.rank + 1;
		return B;
	}

	/**
	 * double rotate right of a given node
	 * 
	 * @param node node that an imbalance occours
	 * @return the root of the new subtree
	 */
	private static Node doubleRotateRight(Node node) {
		Node A = node;
		Node C = node.left;
		Node B = C.right;
		A.left = B.right;
		C.right = B.left;
		B.parent = A.parent;
		A.parent = B;
		C.parent = B;
		if (B.balance == Code.LEFT) {
			C.balance = Code.SAME;
			A.balance = Code.RIGHT;
		} else if (B.balance == Code.RIGHT) {
			C.balance = Code.LEFT;
			A.balance = Code.SAME;
		} else {
			A.balance = Code.SAME;
			C.balance = Code.SAME;
		}
		B.balance = Code.SAME;
		B.left = C;
		B.right = A;
		A.rank = A.rank - C.rank - B.rank - 2;
		B.rank = C.rank + B.rank + 1;
		return B;
	}

	@Override
	public String toString() {
		if (this == NULL_NODE)
			return "";
		return this.left.toString() + this.element + this.right.toString();
	}

	public String toDebugString() {
		if (this == NULL_NODE)
			return "";
		return ", " + this.element + this.rank + this.balance.toString() + this.left.toDebugString()
				+ this.right.toDebugString();
	}

	/**
	 * Get the element of the node at the requested position.
	 *
	 * @param pos
	 * @return
	 */
	public char get(int pos) {
		if (this.rank == pos)
			return this.element;
		if (this.rank > pos)
			return this.left.get(pos);

		return this.right.get(pos - this.rank - 1);
	}

	/**
	 * Delete the node at the requested position.
	 *
	 * @param pos
	 * @return the element deleted
	 */
	public Node delete(int pos) {
		if (this.rank > pos) {
			this.rank--;
			Code temp = this.left.balance;
			this.left = this.left.delete(pos);
			if ((temp != Code.SAME && temp != this.left.balance) || this.left == NULL_NODE) {
				if (temp != Code.SAME && this.left.balance != Code.SAME) return this;
				return rotateHandler(this, Code.RIGHT);
			}
		} else if (this.rank < pos) {
			Code temp = this.right.balance;
			this.right = this.right.delete(pos - this.rank - 1);
			if ((temp != Code.SAME && temp != this.right.balance) || this.right == NULL_NODE) {
				if (temp != Code.SAME && this.right.balance != Code.SAME) return this;
				return rotateHandler(this, Code.LEFT);
			}
		} else {
			if (this.left == NULL_NODE && this.right == NULL_NODE)
				return NULL_NODE;
			else if (this.left == NULL_NODE)
				return this.right;
			else if (this.right == NULL_NODE)
				return this.left;
			else {
				Node temp = this.right;
				while (temp.left != NULL_NODE)
					temp = temp.left;
				char t = this.element;
				this.element = temp.element;
				Code tempCode = this.right.balance;
				this.right = this.right.delete(0);
				if ((tempCode != Code.SAME && tempCode != this.right.balance) || this.right == NULL_NODE) {
					if (tempCode != Code.SAME && this.right.balance != Code.SAME) return this;
					return rotateHandler(this, Code.LEFT);
				}
			}
		}
		return this;
	}

}