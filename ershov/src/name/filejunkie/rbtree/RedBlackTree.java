package name.filejunkie.rbtree;

public interface RedBlackTree<T extends Comparable<T>> {
	void add(T e);
	boolean delete(T e);
	boolean contains(T e);
}
