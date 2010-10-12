package name.filejunkie.rbtree;

/**
 * @author Ershov Ilya <filejunkie@gmail.com>
 * @version 2010.09.26
 * @since 1.6
 */
//import java.util.InputMismatchException;
import java.util.Random;
//import java.util.Scanner;

public class LabRBTree {
	
	private static final int ITERATIONS = 1000;

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
		

		int adds[] = new int[ITERATIONS];
		int dels[] = new int[ITERATIONS];
		boolean ok = true;
		
		for(int i = 0; i < ITERATIONS; i++){
			adds[i] = rand.nextInt(1000);
			tree.add(adds[i]);
			if(tree.getBlackHeight() == -1 || !tree.check()){
				System.out.println(tree);
				for(int j = 0; j <= i; j++){
					System.out.printf("%d ",adds[j]);
				}
				System.out.println("");
				ok = false;
				break;
			}
		}
		if(ok){
			for(int i = 0; i < ITERATIONS; i++){
				dels[i] = rand.nextInt(1000);
				tree.delete(dels[i]);
				if(tree.getBlackHeight() == -1 || !tree.check()){
					System.out.println(tree);
					for(int j = 0; j < ITERATIONS; j++){
						System.out.printf("%d ",adds[j]);
					}
					System.out.println("");
					for(int j = 0; j <= i; j++){
						System.out.printf("%d ",dels[j]);
					}
					System.out.println("");
					break;					
				}
			}
		}
		System.out.println(tree);
	}
}
