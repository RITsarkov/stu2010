package yakimenko.collections;

/**
 * Коллекция хранит данные в структуре B tree и гарантирует логарифмическое время вставки, удаления и поиска.
 * 
 */
public interface BinaryTree {
	boolean add(Comparable<NodeTree> node);
	NodeTree remove(Comparable<NodeTree> node);
	boolean contains(Comparable<NodeTree> node);
}
