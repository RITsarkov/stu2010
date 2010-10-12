package name.filejunkie.rbtree;

/**
 * @author Ershov Ilya <filejunkie@gmail.com>
 * @version 2010.09.26
 * @since 1.6
 */
class Node<T extends Comparable<T>>{
	enum Color {Black, Red}
	
	Color color;
	private Node<T> left, right, parent;
	private T value;
	
	public Node(T e, Node<T> parent) {
		this.value = e;
		this.parent = parent;
		this.color = Color.Red;
	}

	public Color getColor() {
		return color;
	}
	
	public Node<T> getGrandparent(){
		if(this.parent == null){
			return null;
		}
		else{
			return this.parent.parent;
		}
	}

	public Node<T> getLeft() {
		return left;
	}

	public Node<T> getParent() {
		return parent;
	}

	public Node<T> getRight() {
		return right;
	}

	public Node<T> getSibling(){
		if(this.parent == null)
			return null;
		if(this.parent.getLeft() == this)
			return this.parent.getRight();
		else 
			return this.parent.getLeft();
	}

	public Node<T> getUncle(){
		Node<T> grandparent = this.getGrandparent();
		if(grandparent == null){
			return null;
		}
		if(this.parent == grandparent.right){
			return grandparent.left;
		}
		else{
			return grandparent.right;
		}
	}

	public T getValue() {
		return value;
	}

	public boolean isBlack(){
		return this.color == Color.Black;
	}

	public boolean isRed(){
		return this.color == Color.Red;
	}

	public void makeBlack(){
		this.color = Color.Black;
	}
	
	public void makeRed(){
		this.color = Color.Red;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setLeft(Node<T> left) {
		this.left = left;
	}
	
	public void setParent(Node<T> parent) {
		this.parent = parent;
	}
	
	public void setRight(Node<T> right) {
		this.right = right;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public String toString() {
		return value.toString() + "." + (this.isRed()?"R":"B") + "(" + (left == null?"":left.toString()) + ")(" + (right == null?"":right.toString()) + ")";
	}
}
