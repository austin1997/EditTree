package editortrees;

import java.util.ArrayList;

// A node in a height-balanced binary tree with rank.
// Except for the NULL_NODE (if you choose to use one), one node cannot
// belong to two different trees.

public class Node {

	public static final Node NULL_NODE = new Node(null, -1);

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
	private int rank; // inorder position of this node within its own subtree.
	private Code balance;
	private Node parent; // You may want this field.
	// Feel free to add other fields that you find useful

	// You will probably want to add several other methods

	// For the following methods, you should fill in the details so that they
	// work correctly

	public int height() {
		if (this == NULL_NODE) {
			return -1;
		}
		return 1 + Math.max(this.left.height(), this.right.height());
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
		this.tree = this.parent.tree;
	}

	/**
	 * TODO Put here a description of what this constructor does.
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

	private static Node rotateLeft(Node parent, Node child) {
		child.left.parent = parent;
		child.parent = parent.parent;
		parent.parent = child;
		///////////////////////////
		parent.right = child.left;
		child.left = parent;
		child.balance = Code.SAME;
		parent.balance = Code.SAME;
		child.rank += parent.rank + 1;
		return child;
	}

	private static Node rotateRight(Node parent, Node child) {
		child.right.parent = parent;
		child.parent = parent.parent;
		parent.parent = child;
		///////////////////////
		parent.left = child.right;
		child.right = parent;
		child.balance = Code.SAME;
		parent.balance = Code.SAME;
		parent.rank = parent.rank - child.rank - 1;

		return child;
	}
	//
	// public int size() {
	// if (this == NULL_NODE) return 0;
	// return this.left.size() + this.right.size() + 1;
	// }

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param ch
	 * @return
	 */
	public Node add(char ch) {
		// TODO Auto-generated method stub.
		if (this.right == NULL_NODE) {
			Node temp = new Node(ch, this);
			// temp.rank = this.rank + 1;
			this.right = temp;
			if (this.balance == Code.SAME)
				this.balance = Code.RIGHT;
			else
				this.balance = Code.SAME;
			return this;
		}
		Code temp = this.right.balance;
		this.right = this.right.add(ch);
		if (this.right.balance != temp && this.right.balance != Code.SAME) {
			return rotateHandler(this, Code.RIGHT);
		}

		return this;
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param ch
	 * @param pos
	 * @return
	 */
	public Node add(char ch, int pos) {
		// TODO Auto-generated method stub.
		// if (this.rank == pos){
		// this.rank ++;
		//// if (this.right != NULL_NODE) {
		//// this.right.increaseBy++;
		//// }
		// if (this.left != NULL_NODE) {
		// int tempPos = pos - this.rank - 1;
		// Code temp = this.left.balance;
		// this.left = this.left.add(ch, pos);
		// if (this.left.balance != temp && this.left.balance != Code.SAME) {
		// return rotateHandler(this, Code.LEFT);
		// }
		// }else {
		// this.left = new Node(ch, this);
		// this.balance = Code.LEFT;
		// this.left.rank = pos;
		// }
		// }else if (this.rank < pos){
		// if(this.right == NULL_NODE) {
		// this.right = new Node(ch, this);
		// this.right.rank = pos;
		// this.balance = Code.RIGHT;
		// }else{
		// Code temp = this.right.balance;
		// this.right = this.right.add(ch, pos);
		// if (this.right.balance != temp && this.right.balance != Code.SAME) {
		// return rotateHandler(this, Code.RIGHT);
		// }
		// }
		// }else{
		// if(this.left == NULL_NODE){
		// this.left = new Node(ch, this);
		// this.left.rank = pos;
		// this.balance = Code.LEFT;
		// }else {
		// Code temp = this.left.balance;
		// this.left = this.left.add(ch, pos);
		// if (this.left.balance != temp && this.left.balance != Code.SAME) {
		// return rotateHandler(this, Code.LEFT);
		// }
		// }
		//
		// }
		// return this;
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
				this.left = this.left.add(ch, pos);
				if (this.left.balance != temp && this.left.balance != Code.SAME) {
					return rotateHandler(this, Code.LEFT);
				}
			}
		} else if (this.rank > pos) {
			this.rank++;
			Code temp = this.left.balance;
			this.left = this.left.add(ch, pos);
			if (this.left.balance != temp && this.left.balance != Code.SAME) {
				return rotateHandler(this, Code.LEFT);
			}
		} else {
			if (this.right == NULL_NODE) {
				return this.add(ch);
			}
			Code temp = this.right.balance;
			this.right = this.right.add(ch, pos - this.rank - 1);
			if (this.right.balance != temp && this.right.balance != Code.SAME) {
				return rotateHandler(this, Code.RIGHT);
			}
		}
		return this;
	}

	private static Node rotateHandler(Node node, Code code) {
		if (code == Code.SAME) {
			return node;
		}
		Code currentCode = node.balance;

		if (currentCode == Code.SAME) {
			node.balance = code;
			return node;
		} else if (currentCode == Code.RIGHT) {
			if (code == Code.LEFT) {
				node.balance = Code.SAME;
				return node;
			} else {
				Code temp = node.right.balance;
				if (temp == Code.RIGHT) {
					node.tree.increaseRotationCount(1);
					return rotateLeft(node, node.right);
				} else {
					node.tree.increaseRotationCount(2);
					// node.right = rotateRight(node.right, node.right.left);
					return doubleRotateLeft(node);
				}
			}
		} else {
			if (code == Code.RIGHT) {
				node.balance = Code.SAME;
				return node;
			} else {
				Code temp = node.left.balance;
				if (temp == Code.LEFT) {
					node.tree.increaseRotationCount(1);
					return rotateRight(node, node.left);
				} else {
					node.tree.increaseRotationCount(2);
					return doubleRotateRight(node);
				}
			}

		}
	}

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

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	public String toDebugString() {
		if (this == NULL_NODE)
			return "";
		return ", " + this.element + this.rank + this.balance.toString() + this.left.toDebugString()
				+ this.right.toDebugString();
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param pos
	 * @return
	 */
	public char get(int pos) {
		// TODO Auto-generated method stub.
		if (this.rank == pos)
			return this.element;
		if (this.rank > pos)
			return this.left.get(pos);

		return this.right.get(pos - this.rank - 1);
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param pos
	 * @return
	 */
	public Node delete(int pos) {
		// TODO Auto-generated method stub.
		if (this.rank > pos) {
			this.rank--;
			Code temp = this.left.balance;
			this.left = this.left.delete(pos);
			if ((temp != Code.SAME && temp != this.left.balance) || this.left == NULL_NODE) {

				return rotateHandler(this, Code.RIGHT);
			}
		} else if (this.rank < pos) {
			Code temp = this.right.balance;
			this.right = this.right.delete(pos - this.rank - 1);
			if ((temp != Code.SAME && temp != this.right.balance) || this.right == NULL_NODE) {
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
				// temp.element = t;
				// this.rank = this.rank - 1;
				// temp.parent.right = temp.delete();
				// Node out = this.delete(this.rank - 1);
				Code tempCode = this.right.balance;
				this.right = this.right.delete(0);
				if ((tempCode != Code.SAME && tempCode != this.right.balance) || this.right == NULL_NODE) {
					return rotateHandler(this, Code.LEFT);
				}

				// this.rank --;
				// return this.delete(this.rank - 1);
			}
		}
		return this;
	}

}