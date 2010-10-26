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

	public boolean hasNext() {
		if(this.getRight() != null){
			return true;
		}
		if(this.getParent() == null){
			return false;
		}
		
		return this.getParent().hasNext(this);
	}

	private boolean hasNext(Node<T> node) {
		if(this.getLeft() == node){
			return true;
		}
		if(this.getParent() == null){
			return false;
		}
		
		return this.getParent().hasNext(this);
	}

	public Node<T> getNext() {
		if(this.getRight() != null){
			return this.getRight().getLeast();
		}
		if(this.getParent() == null){
			return null;
		}
		
		return this.getParent().getNext(this);
	}

	private Node<T> getNext(Node<T> node) {
		if(this.getLeft() == node){
			return this;
		}
		if(this.getParent() == null){
			return null;
		}
		
		return this.getParent().getNext(this);
	}

	public Node<T> getLeast() {
		if(this.getLeft() == null){
			return this;
		}
		else{
			return this.getLeft().getLeast();
		}
		
	}

	public Node<T> getPrev() {
		if(this.getLeft() != null){
			return this.getLeft().getMost();
		}
		if(this.getParent() == null){
			return null;
		}
		
		return this.getParent().getPrev(this);
	}

	private Node<T> getPrev(Node<T> node) {
		if(this.getRight() == node){
			return this;
		}
		if(this.getParent() == null){
			return null;
		}
		
		return this.getParent().getPrev(this);
	}
	
	public Node<T> getMost() {
		if(this.getRight() == null){
			return this;
		}
		else{
			return this.getRight().getMost();
		}
	}
}
