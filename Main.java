package codes;

import java.io.IOException;

public class Main {

	public static Grid SGrid = new Grid(9);
	
	public static void main(String[] args) throws IOException{
		
		SGrid.populate();
		//SGrid.elimPoss();
		SGrid.display();
		System.out.println();
		SGrid.solveSudoku();
		SGrid.display();
		//SGrid.lastRowPotentials();
		//System.out.println("hhh");
		/*
		int num1=6;
		Cell temp=SGrid.getFirst();
		for(int y=1;y<10;y++){
			if(y==num1)
				for(int x=1;x<10;x++)
					System.out.println(x +": " +(temp.getPotentialNumber(x)));
			temp=temp.getRight();
		}*/
		//SGrid.displayPoten(0);
		
		
	}
	
}
