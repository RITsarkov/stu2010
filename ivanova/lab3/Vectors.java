import java.util.*;

public class Vectors<T extends Comparable<T>> implements Vector<T> {
	int rasm;
	Object vect[];

	public Vectors() {
		rasm = 5;
		vect = new Object[rasm];
	}

	private void doublebig() {
		Object vect1[] = new Object[vect.length];
		System.arraycopy(vect, 0, vect1, 0, vect.length);
		int doplong = 2 * vect.length;
		vect = new Object[doplong];
		System.arraycopy(vect1, 0, vect, 0, doplong / 2);

	}

	public void add(T o) {
		int i = 0;
		while ((i < vect.length) && (vect[i] != null)) {
			i++;
			while (i == vect.length) {
				doublebig();
			}
		}
		vect[i] = o;
		System.out.println("Added to the position " + i);
	}

	public void add1(T o, int pos) {
		if (pos < 0) {
			System.out.println("Error: Negative index!");
			// выход из метода
		}
		while ((pos >= vect.length) || (vect[vect.length - 1] != null)) {
			// System.out.println("Error: Going beyond the array ");
			doublebig();
		}
		if (vect[pos] != null) {
			for (int i = vect.length - 1; i > pos; i--) {
				vect[i - 1] = vect[i];
			}
		}
		vect[pos] = o;
		System.out.println("Added to the position " + pos);
	}

	public void remove(int index) {
		if (index < 0) {
			System.out.println("Error: Negative index!");
			// выход из метода
		}
		if (index >= vect.length) {
			System.out.println("Error: No object with index " + index);
		} else
			vect[index] = null;
		System.out.println("Removed from the position " + index);
	}

	public T get(int index) {
		if ((index >= vect.length) || (index < 0)) {
			throw new NoSuchElementException("There are no more elements");
		} else
			System.out.println("Object=" + vect[index]);
		return (T) vect[index];
	}

	public int indexOf(T o) {
		int i = 0;
		for (i = 0; i < vect.length; i++) {
			if (vect[i] != null){
				if (vect[i].equals(o)) {
					System.out.println("Index=" + i);
					return i;
				}
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int vvod;
		Vectors<Integer> vector = new Vectors<Integer>();
		Scanner in = new Scanner(System.in);
		do {
			System.out.println("Vector:");
			System.out.println("1 - Add object");
			System.out.println("2 - Add object to position");
			System.out.println("3 - Remove object from position");
			System.out.println("4 - Show object on position ");
			System.out.println("5 - Show index of object");
			System.out.println("6 - Exit");
			vvod = in.nextInt();
			if (vvod == 1) {
				vector.add(in.nextInt());
			}
			if (vvod == 2) {
				vector.add1(in.nextInt(), in.nextInt());
			}
			if (vvod == 3) {
				vector.remove(in.nextInt());
			}
			if (vvod == 4) {
				/*
				 * if (vector.get(in.nextInt()) == "nonExist") {
				 * System.out.println("Error: Negative or nonexistent index ");
				 * }
				 */

				vector.get(in.nextInt());
			}
			if (vvod == 5) {
				if (vector.indexOf(in.nextInt()) == -1) {
					System.out.println("Error: No such objects");
				}
			}

		} while (vvod != 6);
	}
}
