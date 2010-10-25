package yakimenko.collections;

public class NodeTree implements Comparable <NodeTree>, BinaryTree{
	
	private int key;
	private NodeTree right;
	private NodeTree left;
	
	public NodeTree(int key) {
		this.key = key;
	}

	public int compareTo(NodeTree node) {
		if (this.key < node.key) return -1;
		else if (this.key > node.key) return 1;
		return 0;
	}
	
	public NodeTree getNode (int key) {
		NodeTree current = this;
		while (current != null) {
			if (current.getKey() == key)
				return current;
			if (current.getKey() > key)
				current = current.getLeft();
			else 
				current = current.getRigth();
		}
		return null;
	}
	
	public boolean add(Comparable<NodeTree> node) {
		NodeTree root = this;
		if (root.compareTo((NodeTree)node) > 0) {
			if (root.getLeft() != null) root.getLeft().add(node);
			else root.setLeft((NodeTree) node);
		}
		else {
			if (root.getRigth() != null) root.getRigth().add(node);
			else root.setRight((NodeTree) node);
		}
		return true;
	}

	public NodeTree remove(Comparable<NodeTree> node) {
		NodeTree root = this;
		NodeTree new_root;
		if (root.compareTo((NodeTree)node) == 0) {
			if (root.getLeft() == null) {
				return root.getRigth();
			}
			NodeTree tmp = root.getLeft();
			while (tmp.getRigth() != null)
				tmp = tmp.getRigth();
			tmp.setRight(root.getRigth());
			return root.getLeft();
		}
		if (root.compareTo((NodeTree)node) > 0) {
			new_root = root.getLeft().remove(node);
			root.setLeft(new_root);
		} else {
			new_root = root.getRigth().remove(node);
			root.setRight(new_root);
		}
		return root;
	}

	public boolean contains(Comparable<NodeTree> node) {
		if ((NodeTree) node != null) 
			return true;
		return false;
	}
	
	public void traverse() {
		if ( this.left != null) {
			if (this.right != null)
				System.out.print(" (");
			this.left.traverse();
		}
		if ((this.right != null)&&( this.left != null))
			System.out.print(" )");
		System.out.print( " " + this.key );
	    if ( this.right != null ) {
	    	if ( this.left != null)
	    		System.out.print(" (");
	        this.right.traverse();
	        System.out.print(" )");
	    }
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public NodeTree getRigth() {
		return right;
	}

	public void setRight(NodeTree right) {
		this.right = right;
	}

	public NodeTree getLeft() {
		return left;
	}

	public void setLeft(NodeTree left) {
		this.left = left;
	}
}
