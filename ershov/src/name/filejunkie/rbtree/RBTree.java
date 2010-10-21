package name.filejunkie.rbtree;

import name.filejunkie.rbtree.Node.Color;

/**
 * Red-Black tree class. Allows any comparable objects to be added into.
 * 
 * @author Ershov Ilya <filejunkie@gmail.com>
 * @version 2010.09.26
 * @since 1.6
 */
public class RBTree<T extends Comparable<T>> implements RedBlackTree<T>{
	
	Node<T> root;
	
	/**
	 * Default constructor
	 */
	public RBTree() {
		this.root = null;
	}

	/**
	 * Adds a comparable element to the tree
	 * 
	 *  @param e value to be added
	 */
	@Override
	public void add(T e) {
		
		Node<T> cur = this.root;		
		if(this.root == null){
			cur = this.root = new Node<T>(e, null);
		}
		else{
			while(cur != null){
				if(cur.getValue().compareTo(e) > 0){
					if(cur.getLeft() == null){
						cur.setLeft(new Node<T>(e, cur));
						cur = cur.getLeft();
						break;
					}
					else{
						cur = cur.getLeft();
					}
				}
				else{
					if(cur.getRight() == null){
						cur.setRight(new Node<T>(e, cur));
						cur = cur.getRight();
						break;
					}
					else{
						cur = cur.getRight();
					}
				}
			}
		}
		
		addFix1(cur);
	}
	

	/**
	 * Checks if tree has no red children of red parents
	 * 
	 * @return true if tree is correct
	 */
	public boolean check(){
		return check(root);
	}

	/**
	 * Checks if tree contains an element
	 * 
	 * @param e value to find
	 * @return true if value exists in the tree at least once
	 */

	@Override
	public boolean contains(T e) {
		Node<T> cur = root;
		
		while(cur != null){
			if(cur.getValue().equals(e)){
				return true;
			}
			if(cur.getValue().compareTo(e) > 0){
				cur = cur.getLeft();
			}
			else{
				cur = cur.getRight();
			}
		}
		
		return false;
	}
	
	/**
	 * Deletes element from the tree
	 * 
	 * @param e value to delete
	 * @return false if there is no such value
	 */

	@Override
	public boolean delete(T e) {
		if(!this.contains(e)){
			return false;
		}
		
		Node<T> cur = this.root;
		while(!cur.getValue().equals(e)){
			if(cur.getValue().compareTo(e) > 0){
				cur = cur.getLeft();
			}
			else{
				cur = cur.getRight();
			}
		}
		
		delete(cur);
		
		return true;
	}

	/**
	 * Returns black height of the tree
	 * 
	 * @return black height, or -1 if tree is not a valid red-black tree
	 */
	public int getBlackHeight(){
		if(root == null){
			return 0;
		}
		return getBlackHeight(root);
	}

	/**
	 * Generates a human-readable string presentation of the tree
	 * 
	 * @return tree as a string
	 */
	@Override
	public String toString() {
		if(root == null){
			return "<empty>";
		}
		else{
			return root.toString();
		}
	}

	
	private void addFix1(Node<T> node) {
		if(node.getParent() == null){
			node.makeBlack();
		}
		else{
			addFix2(node);
		}
	}

	private void addFix2(Node<T> node) {
		if(!node.getParent().isBlack()){
			addFix3(node);
		}
	}
	
	private void addFix3(Node<T> node) {
		Node<T> uncle, grandparent;
		uncle = node.getUncle();
		
		if(uncle != null && uncle.isRed()){
			node.getParent().makeBlack();
			uncle.makeBlack();
			grandparent = node.getGrandparent();
			grandparent.makeRed();
			addFix1(grandparent);
		}
		else{
			addFix4(node);
		}		
	}

	private void addFix4(Node<T> node) {
		Node<T> grandparent = node.getGrandparent();
		
		if(node == node.getParent().getRight() && node.getParent() == grandparent.getLeft()){
			rotateLeft(node.getParent());
		}
		else if(node == node.getParent().getLeft() && node.getParent() == grandparent.getRight()){
			rotateRight(node.getParent());
		}
		
		addFix5(node);
	}

	private void addFix5(Node<T> node) {
		Node<T> grandparent = node.getGrandparent();
		
		node.getParent().makeBlack();
		grandparent.makeRed();
		if(node == node.getParent().getLeft() && node.getParent() == grandparent.getLeft()){
			rotateRight(grandparent);
		}
		else{
			rotateLeft(grandparent);
		}
	}

	private boolean check(Node<T> node){
		if(node == null)
			return true;
		
		if(node.isRed()){
			if(node.getLeft() != null && node.getLeft().isRed()){
				return false;
			}
			if(node.getRight() != null && node.getRight().isRed()){
				return false;
			}
		}
		
		return check(node.getLeft()) && check(node.getRight());
	}
	

	private void delete(Node<T> node) {
		if(node.getLeft() != null && node.getRight() != null){
			Node<T> next = node.getRight();
			while(next.getLeft() != null){
				next = next.getLeft();
			}
			node.setValue(next.getValue());
			node = next;
		}
		
		//it is assumed that node has no more than one child
		Node<T> child;
		if(node.getLeft() != null)
			child = node.getLeft();
		else
			child = node.getRight();
		
		if(child != null){
			node.setValue(child.getValue());
			node.setRight(child.getRight());
			node.setLeft(child.getLeft());
			if(node.getLeft() != null)
				node.getLeft().setParent(node);
			if(node.getRight() != null)
				node.getRight().setParent(node);
			child.setParent(null);
			if(node.isBlack()){
				if(child.isRed())
					node.makeBlack();
				else
					deleteFix1(node);
			}
		}
		else{
			if(node.isBlack())
				deleteFix1(node);
			if(node.getParent() != null){
				if(node.getParent().getLeft() == node)
					node.getParent().setLeft(null);
				if(node.getParent().getRight() == node)
					node.getParent().setRight(null);
				node.setParent(null);
			}
			else{
				root = null;
			}
		}
		
	}

	private void deleteFix1(Node<T> node) {
		if(node.getParent() != null)
			deleteFix2(node);
	}

	private void deleteFix2(Node<T> node) {
		Node<T> sibling = node.getSibling();
		
		if(sibling.isRed()){
			node.getParent().makeRed();
			sibling.makeBlack();
			if(node == node.getParent().getLeft())
				rotateLeft(node.getParent());
			else
				rotateRight(node.getParent());
		}
		deleteFix3(node);
	}

	private void deleteFix3(Node<T> node) {
		Node<T> sibling = node.getSibling();
		
		if(node.getParent().isBlack() && ( sibling.isBlack()) && ( sibling.getLeft() == null || sibling.getLeft().isBlack()) && ( sibling.getRight() == null || sibling.getRight().isBlack() )   ){
			sibling.makeRed();
			deleteFix1(node.getParent());
		}
		else{
			deleteFix4(node);
		}
	}

	private void deleteFix4(Node<T> node) {
		Node<T> sibling = node.getSibling();
		
		if(node.getParent().isRed() &&  sibling.isBlack() &&  (sibling.getLeft() == null || sibling.getLeft().isBlack()) &&  (sibling.getRight() == null || sibling.getRight().isBlack()) ){
			sibling.makeRed();
			node.getParent().makeBlack();
		}
		else{
			deleteFix5(node);
		}
	}
	
	private void deleteFix5(Node<T> node) {
		Node<T> sibling = node.getSibling();
		
		if(sibling.isBlack()){
			if(node == node.getParent().getLeft() && (sibling.getRight() == null || sibling.getRight().isBlack()) && (sibling.getLeft() != null && sibling.getLeft().isRed())){
				sibling.makeRed();
				sibling.getLeft().makeBlack();
				rotateRight(sibling);
			}
			else if(node == node.getParent().getRight() && (sibling.getLeft() == null || sibling.getLeft().isBlack()) && (sibling.getRight() != null && sibling.getRight().isRed())){
				sibling.makeRed();
				sibling.getRight().makeBlack();
				rotateLeft(sibling);
			} 
		}
		deleteFix6(node);
	}
	
	private void deleteFix6(Node<T> node) {
		Node<T> sibling = node.getSibling();
		
		sibling.setColor(node.getParent().getColor());
		node.getParent().makeBlack();
		
		if(node == node.getParent().getLeft()){
			sibling.getRight().makeBlack();
			rotateLeft(node.getParent());
		}
		else{
			sibling.getLeft().makeBlack();
			rotateRight(node.getParent());
		}
	}
	
	private int getBlackHeight(Node<T> node){
		if(node.getLeft() == null && node.getRight() == null){
			return node.isBlack() ? 1 : 0;
		}
		if(node.getLeft() == null){
			if(getBlackHeight(node.getRight()) != 0)
				return -1;
			return node.isBlack() ? 1 : 0;
		}
		if(node.getRight() == null){
			if(getBlackHeight(node.getLeft()) != 0)
				return -1;
			return node.isBlack() ? 1 : 0;
		}
		int leftHeight = getBlackHeight(node.getLeft());
		int rightHeight = getBlackHeight(node.getRight());
		if(leftHeight == -1 || rightHeight == -1)
			return -1;
		if(leftHeight != rightHeight)
			return -1;
		return leftHeight + ( node.isBlack() ? 1 : 0 );
	}
	

	private void rotateLeft(Node<T> p) {
		Node<T> q = p.getRight();
		Node<T> a = p == null ? null : p.getLeft();
		Node<T> b = q == null ? null : q.getLeft();
		Node<T> c = q == null ? null : q.getRight();
		
		if(q != null){
			T tmp = p.getValue();
			Color ctmp = p.getColor();
			p.setValue(q.getValue());
			p.setColor(q.getColor());
			q.setValue(tmp);
			q.setColor(ctmp);
		}
		
		p.setRight(c);
		if(c != null)
			c.setParent(p);
		
		p.setLeft(q);
		if(q != null)
			q.setParent(p);
		
		if(q != null)
			q.setLeft(a);
		if(a != null)
			a.setParent(q);
		
		if(q != null)
			q.setRight(b);
		if(b != null)
			b.setParent(q);
		
	}

	private void rotateRight(Node<T> q) {
		Node<T> p = q.getLeft();
		Node<T> a = p == null ? null : p.getLeft();
		Node<T> b = p == null ? null : p.getRight();
		Node<T> c = q == null ? null : q.getRight();
		
		if(p != null){
			T tmp = q.getValue();
			Color ctmp = q.getColor();
			q.setValue(p.getValue());
			q.setColor(p.getColor());
			p.setValue(tmp);
			p.setColor(ctmp);
		}
		
		q.setLeft(a);
		if(a != null)
			a.setParent(q);
		
		q.setRight(p);
		if(p != null) p.setParent(q);
		
		if(p != null)
			p.setLeft(b);
		if(b != null)
			b.setParent(p);
		
		if(p != null)
			p.setRight(c);
		if(c != null)
			c.setParent(p);
		
	}
}
