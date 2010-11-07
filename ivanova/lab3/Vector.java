//package ru.spbstu.telematics.stu.collections;
/**
 * Саморасширяющаяся коллекция. Объекты хранятся в массиве в порядке добавления.
 *
 */
public interface Vector<T extends Comparable<T>> {
	
	/**
	 * Добавляет объект в коллекцию
	 * @param o
	 */
	void add(T o);
	
	/**
	 * Добавляет объект в коллекцию на указанную позицию
	 * @param o
	 * @param pos
	 */
	void add1(T o, int pos);
	
	/**
	 * Удаляет объект из коллекции, находящийся на указанной позиции
	 * @param index
	 */
	void remove(int index);
	
	/**
	 * Возвращает объект, находящийся на определенной позиции
	 * @param index
	 * @return
	 */
	T get(int index);
	
	/**
	 * Возвращает индекс объекта, если такой есть в векторе. Если такого нет, то -1.
	 * @param o
	 * @return
	 */
	int indexOf(T o);
}
