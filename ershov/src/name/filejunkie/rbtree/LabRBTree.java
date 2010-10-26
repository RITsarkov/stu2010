package name.filejunkie.rbtree;

/**
 * @author Ershov Ilya <filejunkie@gmail.com>
 * @version 2010.09.26
 * @since 1.6
 */
//import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Random;
//import java.util.Scanner;

public class LabRBTree {
	
	private static final int ITERATIONS = 10;

	public static void main(String[] args) {
		RBTree<Integer> tree = new RBTree<Integer>();
		Random rand = new Random();
//		Scanner in = new Scanner(System.in);
		
//		System.out.println("Hello! Enter some value");		
//		try{
//			while(true){
//				tree.add(in.nextInt());
//				System.out.println(tree);
//			}
//		}
//		catch(InputMismatchException e){
//			System.out.println("OK. Now let's delete something.");
//			in.nextLine();
//		}
//		
//		try{
//			while(true){
//				if(tree.delete(in.nextInt()) == false){
//					System.out.println("Fail");
//				}
//				else{
//					System.out.println(tree);
//					if(!tree.check() || tree.getBlackHeight() == -1)
//						System.out.println("Fail");	
//				}
//			}
//		}
//		catch(InputMismatchException e){
//			System.out.println("Goodbye!");
//		}
		
			
		for(int i = 0; i < ITERATIONS; i++){
			tree.add(rand.nextInt(1000));
		}
		System.out.println(tree);
		
		for(int i: tree){
			System.out.printf("%d ", i);
		}
		System.out.println("");
		
		int j = 0;
		Iterator<Integer> it = tree.iterator();
		while(it.hasNext()){
			it.next();
			if(++j % 2 == 1){
				it.remove();
			}
		}
		for(int i: tree){
			System.out.printf("%d ", i);
		}
		System.out.println("");
		System.out.println(tree);
		
	}
}
