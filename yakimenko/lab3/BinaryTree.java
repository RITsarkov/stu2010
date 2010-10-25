package yakimenko.collections;

/**
 * Коллекция хранит данные в структуре B tree и гарантирует логарифмическое время вставки, удаления и поиска.
 * 
 */
public interface BinaryTree<T extends Comparable<T>> {
	boolean add(T key);
	NodeTree<T> remove(T key);
	boolean contains(T key);
}
