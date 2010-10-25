package yakimenko.collections;

public class NodeTree<T extends Comparable<T>> implements BinaryTree<T>{
	
	private T key;
	private NodeTree<T> right;
	private NodeTree<T> left;
	
	public NodeTree(T key) {
		this.key = key;
	}
	
	public boolean contains (T key) {
		NodeTree<T> current = this;
		while (current != null) {
			if (current.getKey().compareTo(key) == 0)
				return true;
			if (current.getKey().compareTo(key) > 0)
				current = current.getLeft();
			else 
				current = current.getRigth();
		}
		return false;
	}
	
	public boolean add(T key) {
		NodeTree<T> root = this;
		if (root.getKey().compareTo(key) > 0) {
			if (root.getLeft() != null) root.getLeft().add(key);
			else root.setLeft(new NodeTree<T> (key));
		}
		else {
			if (root.getRigth() != null) root.getRigth().add(key);
			else root.setRight(new NodeTree<T> (key));
		}
		return true;
	}

	public NodeTree<T> remove(T key) {
		NodeTree<T> root = this;
		NodeTree<T> new_root;
		if (root.getKey().compareTo(key) == 0) {
			if (root.getLeft() == null) {
				return root.getRigth();
			}
			NodeTree<T> tmp = root.getLeft();
			while (tmp.getRigth() != null)
				tmp = tmp.getRigth();
			tmp.setRight(root.getRigth());
			return root.getLeft();
		}
		if (root.getKey().compareTo(key) > 0) {
			new_root = root.getLeft().remove(key);
			root.setLeft(new_root);
		} else {
			new_root = root.getRigth().remove(key);
			root.setRight(new_root);
		}
		return root;
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

	public T getKey() {
		return key;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public NodeTree<T> getRigth() {
		return right;
	}

	public void setRight(NodeTree<T> right) {
		this.right = right;
	}

	public NodeTree<T> getLeft() {
		return left;
	}

	public void setLeft(NodeTree<T> left) {
		this.left = left;
	}
}
