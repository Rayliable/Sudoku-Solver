package codes;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Grid {
		
	private Cell first;
	private int dimension;
	//private int totalChangesMade=0;
	
	public File infile = new File("SSolverInput.txt");
	
	public static Grid FGrid = null;


	public Grid(){
		first=null;
		//solved=false;
	}
	
	public Grid(int dimension)
	{
		//solved=false;
		this.dimension = dimension;
		if(dimension == 1)
			first = new Cell();
		else if(dimension <= 0)
			first = null;
		else
		{
			first = new Cell();
			Cell temp = null;
			Cell rowMarker = first;
			Cell columnMarker = first;
			for(int x = 0; x<dimension-1; x++)
			{
				temp = new Cell();
				columnMarker.setRight(temp);
				temp.setLeft(columnMarker);
				columnMarker = temp;
			}
			for(int y = 0; y < dimension - 1; y++)
			{
				//making the first node in the row
				temp = new Cell();
				rowMarker.setDown(temp);
				temp.setUp(rowMarker);
				rowMarker = temp;
				//making the rest of the row
				columnMarker = rowMarker;
				for(int x = 0; x < dimension-1; x++)
				{
					temp = new Cell();
					temp.setLeft(columnMarker);
					columnMarker.setRight(temp);
					temp.setUp( temp.getLeft().getUp().getRight() );
					temp.getUp().setDown(temp);
					columnMarker = temp;
				}
			}
		}
		
		//Code for setting Box values here
		Cell temp = first;
		Cell rowMarker = first;
		int rowNum=1;
		int columnNum=1;
		while(temp != null)
		{
			while(temp != null)
			{
				temp.setColValue(columnNum);
				temp.setRowValue(rowNum);
				
				if(rowNum>0&&rowNum<4){
					if(columnNum>0&&columnNum<4)
						temp.setBoxValue(1);
					else if(columnNum>3&&columnNum<7)
						temp.setBoxValue(2);
					else if(columnNum>6&&columnNum<10)
						temp.setBoxValue(3);
				}
				
				else if(rowNum>3&&rowNum<7){
					if(columnNum>0&&columnNum<4)
						temp.setBoxValue(4);
					else if(columnNum>3&&columnNum<7)
						temp.setBoxValue(5);
					else if(columnNum>6&&columnNum<10)
						temp.setBoxValue(6);
				}
				
				else if(rowNum>6&&rowNum<10){
					if(columnNum>0&&columnNum<4)
						temp.setBoxValue(7);
					else if(columnNum>3&&columnNum<7)
						temp.setBoxValue(8);
					else if(columnNum>6&&columnNum<10)
						temp.setBoxValue(9);
				}
						
				temp = temp.getRight();
				columnNum++;
			}
			//System.out.println();
			rowMarker = rowMarker.getDown();
			rowNum++;
			columnNum=1;
			temp = rowMarker;
		}
		//End of BV code
		
		
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public Cell getFirst() {
		return first;
	}

	public boolean checkIfFilled(){
		
		Cell temp = first;
		Cell rowMarker = first;
		while(temp != null)
		{
			while(temp != null)
			{
				if(temp.getData()<=0)
					return false;
				
				
				temp = temp.getRight();
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		
		return true;
	}
	
	public boolean checkIfSolved(){
		
		boolean solved = true;
		
		if(!checkIfFilled())
			solved=false;
		
		verify: for(int x=1;x<10;x++){	
			int[] rowA = {0,0,0,0,0,0,0,0,0,0};
			int[] colA = {0,0,0,0,0,0,0,0,0,0};
			int[] boxA = {0,0,0,0,0,0,0,0,0,0};
			Cell temp = first;
			Cell marker = first;
			
			while(temp != null)
			{
				while(temp != null)
				{
					if(temp.getRowValue()==x)
						rowA[temp.getData()]++;
					
					if(temp.getColValue()==x)
						colA[temp.getData()]++;
					
					if(temp.getBoxValue()==x)
						boxA[temp.getData()]++;
					
					temp = temp.getRight();
				}
				marker = marker.getDown();
				temp = marker;
			}
			
			for(int y=1;y<10;y++){
				if(rowA[y]!=1){
					solved=false;
					break verify;
				}
				if(colA[y]!=1){
					solved=false;
					break verify;
				}
				if(boxA[y]!=1){
					solved=false;
					break verify;
				}
			}
			
		}
		
		return solved;
		
	}
	
	public void display()
	{
		Cell temp = first;
		Cell rowMarker = first;
		while(temp != null)
		{
			while(temp != null)
			{
				if(temp.getData() > 99)
					System.out.print(temp.getData() + " ");
				else if(temp.getData() > 9)
					System.out.print(temp.getData() + "  ");
				else 
					System.out.print(temp.getData() + "   ");
				temp = temp.getRight();
			}
			System.out.println();
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		
		
	}
	
	public void displayBV()
	{
		Cell temp = first;
		Cell rowMarker = first;
		while(temp != null)
		{
			while(temp != null)
			{
				if(temp.getData() > 99)
					System.out.print(temp.getBoxValue() + " ");
				else if(temp.getData() > 9)
					System.out.print(temp.getBoxValue() + "  ");
				else 
					System.out.print(temp.getBoxValue() + "   ");
				temp = temp.getRight();
			}
			System.out.println();
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
	}
	
	public void displayPoten(int num)
	{
		Cell temp = first;
		Cell rowMarker = first;
		while(temp != null)
		{
			while(temp != null)
			{
				if(temp.getData() > 99)
					System.out.print(temp.getPotentialNumber(num) + " ");
				else if(temp.getData() > 9)
					System.out.print(temp.getPotentialNumber(num) + "  ");
				else 
					System.out.print(temp.getPotentialNumber(num) + "   ");
				temp = temp.getRight();
			}
			System.out.println();
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		
		
	}
	
	public void populate()throws IOException{
		Scanner input = new Scanner(infile);
		Cell temp = first;
		Cell rowMarker = first;
		//int cellNum=1;
		
		while(temp != null)
		{
			while(temp != null)
			{
				if(input.hasNext())
					temp.setData(input.nextInt());
				if(temp.getData()!=0){
					temp.setPotentialNumber(0, true);
					for(int x=1;x<=9;x++)
						temp.setPotentialNumber(x, false);
				}
				temp = temp.getRight();
			}
			//System.out.println();
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		input.close();
	}
	
	public void elimBox(Cell cell){
		
		if(cell.getData()>9||cell.getData()<=0)
			return;
		
		Cell temp = first;
		Cell rowMarker = first;
		
		while(temp != null)
		{
			while(temp != null)
			{
				if(temp.getBoxValue()==cell.getBoxValue()){
					temp.setPotentialNumber(cell.getData(), false);
				}
				temp = temp.getRight();
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		
	}
	
	public void elimRow(Cell cell){
		if(cell.getData()>9||cell.getData()<=0)
			return;
		
		Cell temp = first;
		Cell rowMarker = first;
		
		while(temp != null)
		{
			while(temp != null)
			{
				if(temp.getRowValue()==cell.getRowValue()){
					temp.setPotentialNumber(cell.getData(), false);
				}
				temp = temp.getRight();
			}
			//System.out.println();
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		
	}
		
	public void elimCol(Cell cell){
		if(cell.getData()>9||cell.getData()<=0)
			return;
		
		Cell temp = first;
		Cell rowMarker = first;
		
		while(temp != null)
		{
			while(temp != null)
			{
				if(temp.getColValue()==cell.getColValue()){
					temp.setPotentialNumber(cell.getData(), false);
				}
				temp = temp.getRight();
			}
			//System.out.println();
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		
	}
	
	public void elimPoss(){
		
		Cell temp = first;
		Cell rowMarker = first;
		
		while(temp != null)
		{
			while(temp != null)
			{
				if(temp.getData()!=0){
					elimBox(temp);
					elimRow(temp);
					elimCol(temp);
				}
				temp = temp.getRight();
			}
			//System.out.println();
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		
	}

	public int solveMethod1(){
		
			int changesMade=0;
			Cell temp = first;
			Cell rowMarker = first;
			
			while(temp != null)
			{
				while(temp != null)
				{
					//System.out.println("numposs: " +temp.getData());
					int numsPossible=0;
					int possPosition=-1;
					for(int x=1;x<10;x++)
						if(temp.getPotentialNumber(x)){
							numsPossible++;
							possPosition=x;
						}
					
					if(numsPossible==1){
						temp.setData(possPosition);
						temp.setPotentialNumber(0, true);
						temp.setPotentialNumber(possPosition, false);
						changesMade++;
					}
						
					temp = temp.getRight();
				}
				//System.out.println();
				rowMarker = rowMarker.getDown();
				temp = rowMarker;
			}
			
			return changesMade;
	}
	
	public int solveMethod2(){
		
		int changesMade=0;
		for(int x=1;x<10;x++){
			Cell temp = first;
			Cell rowMarker = first;
			int boxNumber=x;
			int rowNumber=x;
			int colNumber=x;
			
			int boxNumAmt[]={0,0,0,0,0,0,0,0,0,0};
			int rowNumAmt[]={0,0,0,0,0,0,0,0,0,0};
			int colNumAmt[]={0,0,0,0,0,0,0,0,0,0};
			
			while(temp != null)
			{
				while(temp != null)
				{
					if(temp.getBoxValue()==boxNumber){
						for(int y=1;y<10;y++)
							if(!temp.getPotentialNumber(0)&&temp.getPotentialNumber(y))
								boxNumAmt[y]++;
					}
					
					if(temp.getRowValue()==rowNumber){
						for(int y=1;y<10;y++)
							if(!temp.getPotentialNumber(0)&&temp.getPotentialNumber(y))
								rowNumAmt[y]++;
					}
					
					if(temp.getColValue()==colNumber){
						for(int y=1;y<10;y++)
							if(!temp.getPotentialNumber(0)&&temp.getPotentialNumber(y))
								colNumAmt[y]++;
					}
					
					temp = temp.getRight();
				}
				rowMarker = rowMarker.getDown();
				temp = rowMarker;
			}
			
			
			for(int y=1;y<10;y++)
				if(boxNumAmt[y]==1){//If there is one possibility in the box
					changesMade++;
					Cell temp2 = first;
					Cell rowMarker2 = first;
					while(temp2 != null)
					{
						while(temp2 != null)
						{
							if(temp2.getBoxValue()==boxNumber&&!temp2.getPotentialNumber(0)&&temp2.getPotentialNumber(y)){
								temp2.setData(y);
								temp2.setPotentialNumber(0, true);
								for(int z=1;z<10;z++)
									temp2.setPotentialNumber(z, false);
							}
							temp2 = temp2.getRight();
						}
						rowMarker2 = rowMarker2.getDown();
						temp2 = rowMarker2;
					}
					
				}
			
			
			for(int y=1;y<10;y++)
				if(rowNumAmt[y]==1){//If there is one possibility in the row
					changesMade++;
					Cell temp2 = first;
					Cell rowMarker2 = first;
					while(temp2 != null)
					{
						while(temp2 != null)
						{
							if(temp2.getRowValue()==rowNumber&&!temp2.getPotentialNumber(0)&&temp2.getPotentialNumber(y)){
								temp2.setData(y);
								temp2.setPotentialNumber(0, true);
								for(int z=1;z<10;z++)
									temp2.setPotentialNumber(z, false);
							}
							temp2 = temp2.getRight();
						}
						rowMarker2 = rowMarker2.getDown();
						temp2 = rowMarker2;
					}
					
				}
			
			
			for(int y=1;y<10;y++)
				if(colNumAmt[y]==1){//If there is one possibility in the column
					changesMade++;
					Cell temp2 = first;
					Cell rowMarker2 = first;
					while(temp2 != null)
					{
						while(temp2 != null)
						{
							if(temp2.getColValue()==colNumber&&!temp2.getPotentialNumber(0)&&temp2.getPotentialNumber(y)){
								temp2.setData(y);
								temp2.setPotentialNumber(0, true);
								for(int z=1;z<10;z++)
									temp2.setPotentialNumber(z, false);
							}
							temp2 = temp2.getRight();
						}
						rowMarker2 = rowMarker2.getDown();
						temp2 = rowMarker2;
					}
					
				}
			
		}
		return changesMade;
	}
	
	public int solveMethod3(){
		return solveMethod3Box()+solveMethod3Row()+solveMethod3Col();
	}
	
	public int solveMethod3Box(){ //Only done for Boxes
		int changesMade=0;
		
		for(int z=1;z<10;z++){
			//~~~~~~~~~~~~~~~~~~~~~~~~
			
			//Loop 1
			Cell temp = first;
			Cell rowMarker = first;
			//System.out.println("L1");
			while(temp != null)
			{
				while(temp != null)
				{
					if(temp.getBoxValue()==z){
						int num1=0;
						int num2=0;
	
						int numsPossible=0;
						for(int x=1;x<10;x++)
							if(temp.getPotentialNumber(x)){
								if(numsPossible==0)
									num1=x;
								else
									num2=x;
								numsPossible++;
							}
						if(numsPossible==2){
							//Loop 2
							//System.out.println("L2");
							Cell temp2=temp;
							Cell rowMarker2= rowMarker;
							
							while(temp2 != null)
							{
								while(temp2 != null)
								{
									if(temp2.getBoxValue()==z){
										int numsPossible2=0;
										for(int x=1;x<10;x++)
											if(temp2.getPotentialNumber(x))
												numsPossible2++;
										
										if(numsPossible2==2){
											changesMade++;
											for(int x=1;x<10;x++)
												if(temp2.getPotentialNumber(x)&&temp.getPotentialNumber(x)){
													//Loop 3
													//System.out.println("L3");
													Cell temp3=first;
													Cell rowMarker3= first;
														while(temp3 != null)
														{
															while(temp3 != null)
															{
																if(temp3.getBoxValue()==z){
																	if(temp3!=temp2&&temp3!=temp){
																		temp3.setPotentialNumber(num1, false);
																		temp3.setPotentialNumber(num2, false);
																	}
																}
																temp3 = temp3.getRight();
															}
															rowMarker3 = rowMarker3.getDown();
															temp3 = rowMarker3;
														}
													
												}
										}	
									}	
									temp2 = temp2.getRight();
								}
								rowMarker2 = rowMarker2.getDown();
								temp2 = rowMarker2;
							}
						
								
						
						
						temp = temp.getRight();
						}
					}
					rowMarker = rowMarker.getDown();
					temp = rowMarker;
				}
			}
			
			
			//~~~~~~~~~~~~~~~~~~~~~~~~
		}
		return changesMade;
	}

	public int solveMethod3BoxV2(){ //Only done for Boxes //Changed
		
		int changesMade = 0;
		Cell temp = first;
		Cell rowMarker = first;
		
		//Change cols here
		for(int BV=1;BV<10;BV++){
			
			//int BV=2;
			int unsolvedCells = 0;
			while(temp!=null)
			{
				while(temp!=null){
					if(temp.getData() == 0&&temp.getBoxValue()==BV){
						unsolvedCells++;
					}
					temp = temp.getRight();
				}
				rowMarker=rowMarker.getDown();
				temp=rowMarker;
			}
			
			
			//int maxN = unsolvedCells-1;
			//int cellGroupSize = 3; // This is the group size we are looking at currently
			/*This is hard coded at 3 right now but will be looped to go from 3 to maxN later*/
			
			int cellGroupSize = 2;
			//System.out.println("help");
			if(unsolvedCells >=4 && unsolvedCells < 9)//don't need to do this method if this is not the case
			{
				//we are at 3 right now so we are looking for a cell with three unknowns
				int[] unknowns = new int[cellGroupSize];  // list of unknowns for a given cell
				
				//find the first unsolved cell
				temp=first;
				rowMarker=first;
				
				while(temp!=null){
					
					while(temp!=null)
					{
						//System.out.println("Shakaoofka");
						//temp = marker;
						while(temp!=null && (temp.numberOfPossibilities() != cellGroupSize || temp.getData() != 0 || temp.getBoxValue()!=BV))
							temp = temp.getRight();
					
						if(temp!=null&&temp.getBoxValue()==BV)//this means we stopped at an unsolved cell with 3 potentials
						{
							//System.out.println(cellGroupSize +" potentials found!");
							//record those potentials
							int index = 0;
							for(int x = 1; x<10; x++)
								if(temp.getPotentialNumber(x))
									unknowns[index++] = x; //Executes setting the array int to x, then adds to index
							//marker = temp.getRight();
							int numberOfCellsWithSamePotential = 0;
							Cell temp3=first;
							Cell rowMarker3=first;
							while(temp3!=null){
								while(temp3!=null)
								{//counting the other cells in the box with the same potentials
									while(temp3!=null && (temp3.numberOfPossibilities() != cellGroupSize || temp3.getData() != 0||temp3.getBoxValue()!=BV))
										temp3 =temp3.getRight();
									if(temp3!=null&&temp3.getBoxValue()==BV)
									{// NOCWSP isn't increasing -- fixed
										boolean matchFound = true;
										//System.out.println(cellGroupSize);
										for(int x = 0; x<cellGroupSize; x++){
											//System.out.println(!temp3.getPotentialNumber(unknowns[x]));
											if(!temp3.getPotentialNumber(unknowns[x]))
												matchFound = false;
										}
										
										//System.out.println("--Moving On");
										
										if(matchFound){
											numberOfCellsWithSamePotential++;
											//System.out.println("Adding!");
										}
										
										//System.out.println("--");
									}
									if(temp3!=null)
										temp3 =temp3.getRight();
								}
								rowMarker3=rowMarker3.getDown();
								temp3=rowMarker3;
							}
							/*At this point, we have counted all the cells in the row with the same 3 potentials */
							
							//System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
							if(numberOfCellsWithSamePotential == cellGroupSize)//we have 3 cells with the same 3 potentials
							{
								//System.out.println("Grouplet Found");
								Cell temp2 = first;
								Cell rowMarker2=first;
								
								while(temp2!=null){
									while(temp2!= null)//searching for the cells that are not these 3 cells
									{
										while(temp2!=null && temp2.getData() != 0 && temp2.getBoxValue()!=BV)
										{
											//System.out.println("skipping " + temp.getData());
											temp2 =temp2.getRight();
											
										}
										if(temp2!=null&&temp2.getBoxValue()==BV)
										{
											//System.out.println("checking..." + temp.getData());
											
											boolean matchFound = true;
											for(int x = 0; x<cellGroupSize; x++)
												if(!temp2.getPotentialNumber(unknowns[x]))
													matchFound = false;
											
											if((!matchFound) || temp2.numberOfPossibilities()!=cellGroupSize)//and eliminate the potentials for those cells
											{
												for(int x = 0; x<cellGroupSize; x++)
													temp2.eliminatePotential(unknowns[x]);
												changesMade++;
												
											}
										}
										if(temp2!=null)
											temp2 =temp2.getRight();
									}
									rowMarker2=rowMarker2.getDown();
									temp2=rowMarker2;
								}
							}
									
						}
						if(temp!=null)
							temp=temp.getRight();
					}
					//System.out.println("uWu");
					rowMarker=rowMarker.getDown();
					temp=rowMarker;
				}
				//while()
			}
				
				
		
			
		
		}//End of changing BVs
		
		return changesMade;
		
	}
	
	public int solveMethod3Row(){ //Only done for Rows
		
		int changesMade=0;
		
		for(int z=1;z<10;z++){
			//~~~~~~~~~~~~~~~~~~~~~~~~
			
			//Loop 1
			Cell temp = first;
			Cell rowMarker = first;
			//System.out.println("L1");
			while(temp != null)
			{
				while(temp != null)
				{
					if(temp.getRowValue()==z){
						int num1=0;
						int num2=0;
	
						int numsPossible=0;
						for(int x=1;x<10;x++)
							if(temp.getPotentialNumber(x)){
								if(numsPossible==0)
									num1=x;
								else
									num2=x;
								numsPossible++;
							}
						if(numsPossible==2){
							//Loop 2
							//System.out.println("L2");
							Cell temp2=temp;
							Cell rowMarker2= rowMarker;
							
							while(temp2 != null)
							{
								while(temp2 != null)
								{
									if(temp2.getRowValue()==z){
										int numsPossible2=0;
										for(int x=1;x<10;x++)
											if(temp2.getPotentialNumber(x))
												numsPossible2++;
										
										if(numsPossible2==2){
											changesMade++;
											for(int x=1;x<10;x++)
												if(temp2.getPotentialNumber(x)&&temp.getPotentialNumber(x)){
													//Loop 3
													//System.out.println("L3");
													Cell temp3=first;
													Cell rowMarker3= first;
														while(temp3 != null)
														{
															while(temp3 != null)
															{
																if(temp3.getRowValue()==z){
																	if(temp3!=temp2&&temp3!=temp){
																		temp3.setPotentialNumber(num1, false);
																		temp3.setPotentialNumber(num2, false);
																	}
																}
																temp3 = temp3.getRight();
															}
															rowMarker3 = rowMarker3.getDown();
															temp3 = rowMarker3;
														}
													
												}
										}	
									}	
									temp2 = temp2.getRight();
								}
								rowMarker2 = rowMarker2.getDown();
								temp2 = rowMarker2;
							}
						
								
						
						
						temp = temp.getRight();
						}
					}
					rowMarker = rowMarker.getDown();
					temp = rowMarker;
				}
			}
			
			
			//~~~~~~~~~~~~~~~~~~~~~~~~
		}
		return changesMade;
	}
	
	public int solveMethod3RowV2(){ //Only done for Rows //Changed
		int changesMade = 0;
		Cell temp = first;
		Cell rowMarker = first;
		
		//Change rows here
		while(rowMarker!=null){
			temp=rowMarker;
			
			int unsolvedCells = 0;
			while(temp!=null)
			{
				if(temp.getData() == 0)
					unsolvedCells++;
				temp = temp.getRight();
			}
			
			//int maxN = unsolvedCells-1;
			//int cellGroupSize = 3; // This is the group size we are looking at currently
			/*This is hard coded at 3 right now but will be looped to go from 3 to maxN later*/
			
			int cellGroupSize = 2;
				//System.out.println("row:" +row +" run: " +cellGroupSize);
				
				if(unsolvedCells >=4 && unsolvedCells < 9)//don't need to do this method if this is not the case
				{
					//we are at 3 right now so we are looking for a cell with three unknowns
					int[] unknowns = new int[cellGroupSize];  // list of unknowns for a given cell
					
					//find the first unsolved cell
					temp = rowMarker;
					Cell marker = temp;
					
					while(temp!=null)
					{
						temp = marker;
						while(temp!=null && (temp.numberOfPossibilities() != cellGroupSize || temp.getData() != 0))
							temp = temp.getRight();
					
						if(temp!=null)//this means we stopped at an unsolved cell with 3 potentials
						{
							//System.out.println(cellGroupSize +" potentials found!");
							//record those potentials
							int index = 0;
							for(int x = 1; x<10; x++)
								if(temp.getPotentialNumber(x))
									unknowns[index++] = x; //Executes setting the array int to x, then adds to index
							marker = temp.getRight();
							int numberOfCellsWithSamePotential = 0;
							while(temp!=null)
							{//counting the other cells in the row with the same potentials
								while(temp!=null && (temp.numberOfPossibilities() != cellGroupSize || temp.getData() != 0))
									temp =temp.getRight();
								if(temp!=null)
								{
									boolean matchFound = true;
									for(int x = 0; x<cellGroupSize; x++)
										if(!temp.getPotentialNumber(unknowns[x]))
											matchFound = false;
									
									if(matchFound)
										numberOfCellsWithSamePotential++;
								}
								if(temp!=null)
									temp =temp.getRight();
							}
							/*At this point, we have counted all the cells in the row with the same 3 potentials */
							//System.out.println("NOCWSP:" + numberOfCellsWithSamePotential);
							
							if(numberOfCellsWithSamePotential == cellGroupSize)//we have 3 cells with the same 3 potentials
							{
								//System.out.println("Grouplet Found");
								temp = rowMarker;
								
								while(temp!= null)//searching for the cells that are not these 3 cells
								{
									while(temp!=null && temp.getData() != 0)
									{
										//System.out.println("skipping " + temp.getData());
										temp =temp.getRight();
										
									}
									if(temp!=null)
									{
										//System.out.println("checking..." + temp.getData());
										
										boolean matchFound = true;
										for(int x = 0; x<cellGroupSize; x++)
											if(!temp.getPotentialNumber(unknowns[x]))
												matchFound = false;
										
										if((!matchFound) || temp.numberOfPossibilities()!=cellGroupSize)//and eliminate the potentials for those cells
										{
											for(int x = 0; x<cellGroupSize; x++)
												temp.eliminatePotential(unknowns[x]);
											changesMade++;
											
										}
									}
									if(temp!=null)
										temp =temp.getRight();
								}
								
							}
									
						}
					}
					
					
					//while()
				
				
				
			}
			
			
			rowMarker=rowMarker.getDown();
		
		}//End of changing rows
		
		return changesMade;
		
	}
	
	public int solveMethod3Col(){ //Only done for Columns
				
		int changesMade=0;
		
		for(int z=1;z<10;z++){
			//~~~~~~~~~~~~~~~~~~~~~~~~
			
			//Loop 1
			Cell temp = first;
			Cell rowMarker = first;
			//System.out.println("L1");
			while(temp != null)
			{
				while(temp != null)
				{
					if(temp.getColValue()==z){
						int num1=0;
						int num2=0;
	
						int numsPossible=0;
						for(int x=1;x<10;x++)
							if(temp.getPotentialNumber(x)){
								if(numsPossible==0)
									num1=x;
								else
									num2=x;
								numsPossible++;
							}
						if(numsPossible==2){
							//Loop 2
							//System.out.println("L2");
							Cell temp2=temp;
							Cell rowMarker2= rowMarker;
							
							while(temp2 != null)
							{
								while(temp2 != null)
								{
									if(temp2.getColValue()==z){
										int numsPossible2=0;
										for(int x=1;x<10;x++)
											if(temp2.getPotentialNumber(x))
												numsPossible2++;
										
										if(numsPossible2==2){
											changesMade++;
											for(int x=1;x<10;x++)
												if(temp2.getPotentialNumber(x)&&temp.getPotentialNumber(x)){
													//Loop 3
													//System.out.println("L3");
													Cell temp3=first;
													Cell rowMarker3= first;
														while(temp3 != null)
														{
															while(temp3 != null)
															{
																if(temp3.getColValue()==z){
																	if(temp3!=temp2&&temp3!=temp){
																		temp3.setPotentialNumber(num1, false);
																		temp3.setPotentialNumber(num2, false);
																	}
																}
																temp3 = temp3.getRight();
															}
															rowMarker3 = rowMarker3.getDown();
															temp3 = rowMarker3;
														}
													
												}
										}	
									}	
									temp2 = temp2.getRight();
								}
								rowMarker2 = rowMarker2.getDown();
								temp2 = rowMarker2;
							}
						
								
						
						
						temp = temp.getRight();
						}
					}
					rowMarker = rowMarker.getDown();
					temp = rowMarker;
				}
			}
			
			
			//~~~~~~~~~~~~~~~~~~~~~~~~
		}
		return changesMade;
		
	}

	public int solveMethod3ColV2(){ //Only done for Columns //Changed
		int changesMade = 0;
		Cell temp = first;
		Cell colMarker = first;
		
		//Change cols here
		while(colMarker!=null){
			temp=colMarker;
			
			int unsolvedCells = 0;
			while(temp!=null)
			{
				if(temp.getData() == 0)
					unsolvedCells++;
				temp = temp.getDown();
			}
			
			//int maxN = unsolvedCells-1;
			//int cellGroupSize = 3; // This is the group size we are looking at currently
			/*This is hard coded at 3 right now but will be looped to go from 3 to maxN later*/
			
			int cellGroupSize = 2;
				//System.out.println("row:" +row +" run: " +cellGroupSize);
				
				if(unsolvedCells >=4 && unsolvedCells < 9)//don't need to do this method if this is not the case
				{
					//we are at 3 right now so we are looking for a cell with three unknowns
					int[] unknowns = new int[cellGroupSize];  // list of unknowns for a given cell
					
					//find the first unsolved cell
					temp = colMarker;
					Cell marker = temp;
					
					while(temp!=null)
					{
						temp = marker;
						while(temp!=null && (temp.numberOfPossibilities() != cellGroupSize || temp.getData() != 0))
							temp = temp.getDown();
					
						if(temp!=null)//this means we stopped at an unsolved cell with 3 potentials
						{
							//System.out.println(cellGroupSize +" potentials found!");
							//record those potentials
							int index = 0;
							for(int x = 1; x<10; x++)
								if(temp.getPotentialNumber(x))
									unknowns[index++] = x; //Executes setting the array int to x, then adds to index
							marker = temp.getDown();
							int numberOfCellsWithSamePotential = 0;
							while(temp!=null)
							{//counting the other cells in the row with the same potentials
								while(temp!=null && (temp.numberOfPossibilities() != cellGroupSize || temp.getData() != 0))
									temp =temp.getDown();
								if(temp!=null)
								{
									boolean matchFound = true;
									for(int x = 0; x<cellGroupSize; x++)
										if(!temp.getPotentialNumber(unknowns[x]))
											matchFound = false;
									
									if(matchFound)
										numberOfCellsWithSamePotential++;
								}
								if(temp!=null)
									temp =temp.getDown();
							}
							/*At this point, we have counted all the cells in the row with the same 3 potentials */
							//System.out.println("NOCWSP:" + numberOfCellsWithSamePotential);
							
							if(numberOfCellsWithSamePotential == cellGroupSize)//we have 3 cells with the same 3 potentials
							{
								System.out.println("Grouplet Found");
								temp = colMarker;
								
								while(temp!= null)//searching for the cells that are not these 3 cells
								{
									while(temp!=null && temp.getData() != 0)
									{
										//System.out.println("skipping " + temp.getData());
										temp =temp.getDown();
										
									}
									if(temp!=null)
									{
										//System.out.println("checking..." + temp.getData());
										
										boolean matchFound = true;
										for(int x = 0; x<cellGroupSize; x++)
											if(!temp.getPotentialNumber(unknowns[x]))
												matchFound = false;
										
										if((!matchFound) || temp.numberOfPossibilities()!=cellGroupSize)//and eliminate the potentials for those cells
										{
											for(int x = 0; x<cellGroupSize; x++)
												temp.eliminatePotential(unknowns[x]);
											changesMade++;
											
										}
									}
									if(temp!=null)
										temp =temp.getDown();
								}
								
							}
									
						}
					}
					
					
					//while()
				}
				
				
			
			
			
			colMarker=colMarker.getRight();
		
		}//End of changing cols
		
		return changesMade;
	}
		
	public int solveMethod4(){
		return solveMethod4Row()/*+solveMethod4Col()+solveMethod4Box()*/;
	}
	
	public int solveMethod4Box()
	{
		int changesMade = 0;
		Cell temp = first;
		Cell rowMarker = first;
		
		//Change cols here
		for(int BV=1;BV<10;BV++){
			
			//int BV=2;
			int unsolvedCells = 0;
			while(temp!=null)
			{
				while(temp!=null){
					if(temp.getData() == 0&&temp.getBoxValue()==BV){
						unsolvedCells++;
					}
					temp = temp.getRight();
				}
				rowMarker=rowMarker.getDown();
				temp=rowMarker;
			}
			
			
			int maxN = unsolvedCells-1;
			//int cellGroupSize = 3; // This is the group size we are looking at currently
			/*This is hard coded at 3 right now but will be looped to go from 3 to maxN later*/
			
			for(int cellGroupSize = 3; cellGroupSize<=maxN; cellGroupSize++){
				//System.out.println("help");
				if(unsolvedCells >=4 && unsolvedCells < 9)//don't need to do this method if this is not the case
				{
					//we are at 3 right now so we are looking for a cell with three unknowns
					int[] unknowns = new int[cellGroupSize];  // list of unknowns for a given cell
					
					//find the first unsolved cell
					temp=first;
					rowMarker=first;
					
					while(temp!=null){
						
						while(temp!=null)
						{
							//System.out.println("Shakaoofka");
							//temp = marker;
							while(temp!=null && (temp.numberOfPossibilities() != cellGroupSize || temp.getData() != 0 || temp.getBoxValue()!=BV))
								temp = temp.getRight();
						
							if(temp!=null&&temp.getBoxValue()==BV)//this means we stopped at an unsolved cell with 3 potentials
							{
								//System.out.println(cellGroupSize +" potentials found!");
								//record those potentials
								int index = 0;
								for(int x = 1; x<10; x++)
									if(temp.getPotentialNumber(x))
										unknowns[index++] = x; //Executes setting the array int to x, then adds to index
								//marker = temp.getRight();
								int numberOfCellsWithSamePotential = 0;
								Cell temp3=first;
								Cell rowMarker3=first;
								while(temp3!=null){
									while(temp3!=null)
									{//counting the other cells in the box with the same potentials
										while(temp3!=null && (temp3.numberOfPossibilities() != cellGroupSize || temp3.getData() != 0||temp3.getBoxValue()!=BV))
											temp3 =temp3.getRight();
										if(temp3!=null&&temp3.getBoxValue()==BV)
										{// NOCWSP isn't increasing -- fixed
											boolean matchFound = true;
											//System.out.println(cellGroupSize);
											for(int x = 0; x<cellGroupSize; x++){
												//System.out.println(!temp3.getPotentialNumber(unknowns[x]));
												if(!temp3.getPotentialNumber(unknowns[x]))
													matchFound = false;
											}
											
											//System.out.println("--Moving On");
											
											if(matchFound){
												numberOfCellsWithSamePotential++;
												//System.out.println("Adding!");
											}
											
											//System.out.println("--");
										}
										if(temp3!=null)
											temp3 =temp3.getRight();
									}
									rowMarker3=rowMarker3.getDown();
									temp3=rowMarker3;
								}
								/*At this point, we have counted all the cells in the row with the same 3 potentials */
								
								//System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
								if(numberOfCellsWithSamePotential == cellGroupSize)//we have 3 cells with the same 3 potentials
								{
									//System.out.println("Grouplet Found");
									Cell temp2 = first;
									Cell rowMarker2=first;
									
									while(temp2!=null){
										while(temp2!= null)//searching for the cells that are not these 3 cells
										{
											while(temp2!=null && temp2.getData() != 0 && temp2.getBoxValue()!=BV)
											{
												//System.out.println("skipping " + temp.getData());
												temp2 =temp2.getRight();
												
											}
											if(temp2!=null&&temp2.getBoxValue()==BV)
											{
												//System.out.println("checking..." + temp.getData());
												
												boolean matchFound = true;
												for(int x = 0; x<cellGroupSize; x++)
													if(!temp2.getPotentialNumber(unknowns[x]))
														matchFound = false;
												
												if((!matchFound) || temp2.numberOfPossibilities()!=cellGroupSize)//and eliminate the potentials for those cells
												{
													for(int x = 0; x<cellGroupSize; x++)
														temp2.eliminatePotential(unknowns[x]);
													changesMade++;
													
												}
											}
											if(temp2!=null)
												temp2 =temp2.getRight();
										}
										rowMarker2=rowMarker2.getDown();
										temp2=rowMarker2;
									}
								}
										
							}
							if(temp!=null)
								temp=temp.getRight();
						}
						//System.out.println("uWu");
						rowMarker=rowMarker.getDown();
						temp=rowMarker;
					}
					//while()
				}
				
				
			}
			
		
		}//End of changing BVs
		
		return changesMade;
	}
	
	public int solveMethod4Col()
	{
		int changesMade = 0;
		Cell temp = first;
		Cell colMarker = first;
		
		//Change cols here
		while(colMarker!=null){
			temp=colMarker;
			
			int unsolvedCells = 0;
			while(temp!=null)
			{
				if(temp.getData() == 0)
					unsolvedCells++;
				temp = temp.getDown();
			}
			
			int maxN = unsolvedCells-1;
			//int cellGroupSize = 3; // This is the group size we are looking at currently
			/*This is hard coded at 3 right now but will be looped to go from 3 to maxN later*/
			
			for(int cellGroupSize = 3; cellGroupSize<=maxN; cellGroupSize++){
				//System.out.println("row:" +row +" run: " +cellGroupSize);
				
				if(unsolvedCells >=4 && unsolvedCells < 9)//don't need to do this method if this is not the case
				{
					//we are at 3 right now so we are looking for a cell with three unknowns
					int[] unknowns = new int[cellGroupSize];  // list of unknowns for a given cell
					
					//find the first unsolved cell
					temp = colMarker;
					Cell marker = temp;
					
					while(temp!=null)
					{
						temp = marker;
						while(temp!=null && (temp.numberOfPossibilities() != cellGroupSize || temp.getData() != 0))
							temp = temp.getDown();
					
						if(temp!=null)//this means we stopped at an unsolved cell with 3 potentials
						{
							//System.out.println(cellGroupSize +" potentials found!");
							//record those potentials
							int index = 0;
							for(int x = 1; x<10; x++)
								if(temp.getPotentialNumber(x))
									unknowns[index++] = x; //Executes setting the array int to x, then adds to index
							marker = temp.getDown();
							int numberOfCellsWithSamePotential = 0;
							while(temp!=null)
							{//counting the other cells in the row with the same potentials
								while(temp!=null && (temp.numberOfPossibilities() != cellGroupSize || temp.getData() != 0))
									temp =temp.getDown();
								if(temp!=null)
								{
									boolean matchFound = true;
									for(int x = 0; x<cellGroupSize; x++)
										if(!temp.getPotentialNumber(unknowns[x]))
											matchFound = false;
									
									if(matchFound)
										numberOfCellsWithSamePotential++;
								}
								if(temp!=null)
									temp =temp.getDown();
							}
							/*At this point, we have counted all the cells in the row with the same 3 potentials */
							//System.out.println("NOCWSP:" + numberOfCellsWithSamePotential);
							
							if(numberOfCellsWithSamePotential == cellGroupSize)//we have 3 cells with the same 3 potentials
							{
								System.out.println("Grouplet Found");
								temp = colMarker;
								
								while(temp!= null)//searching for the cells that are not these 3 cells
								{
									while(temp!=null && temp.getData() != 0)
									{
										//System.out.println("skipping " + temp.getData());
										temp =temp.getDown();
										
									}
									if(temp!=null)
									{
										//System.out.println("checking..." + temp.getData());
										
										boolean matchFound = true;
										for(int x = 0; x<cellGroupSize; x++)
											if(!temp.getPotentialNumber(unknowns[x]))
												matchFound = false;
										
										if((!matchFound) || temp.numberOfPossibilities()!=cellGroupSize)//and eliminate the potentials for those cells
										{
											for(int x = 0; x<cellGroupSize; x++)
												temp.eliminatePotential(unknowns[x]);
											changesMade++;
											
										}
									}
									if(temp!=null)
										temp =temp.getDown();
								}
								
							}
									
						}
					}
					
					
					//while()
				}
				
				
			}
			
			
			colMarker=colMarker.getRight();
		
		}//End of changing cols
		
		return changesMade;
	}
	
	public int solveMethod4Row()
	{
		System.out.println("Start SMR");
		int changesMade = 0;
		Cell temp = first;
		Cell rowMarker = first;
		
		//Change rows here
		while(rowMarker!=null){
			temp=rowMarker;
			
			int unsolvedCells = 0;
			while(temp!=null)
			{
				if(temp.getData() == 0)
					unsolvedCells++;
				temp = temp.getRight();
			}
			
			int maxN = unsolvedCells-1;
			//int cellGroupSize = 3; // This is the group size we are looking at currently
			/*This is hard coded at 3 right now but will be looped to go from 3 to maxN later*/
			
			for(int cellGroupSize = 3; cellGroupSize<=maxN; cellGroupSize++){
				//System.out.println("row:" +row +" run: " +cellGroupSize);
				
				if(unsolvedCells >=4 && unsolvedCells < 9)//don't need to do this method if this is not the case
				{
					//we are at 3 right now so we are looking for a cell with three unknowns
					int[] unknowns = new int[cellGroupSize];  // list of unknowns for a given cell
					
					//find the first unsolved cell
					temp = rowMarker;
					Cell marker = temp;
					
					while(temp!=null)
					{
						temp = marker;
						while(temp!=null && (temp.numberOfPossibilities() != cellGroupSize || temp.getData() != 0))
							temp = temp.getRight();
					
						if(temp!=null)//this means we stopped at an unsolved cell with 3 potentials
						{
							//System.out.println(cellGroupSize +" potentials found!");
							//record those potentials
							int index = 0;
							for(int x = 1; x<10; x++)
								if(temp.getPotentialNumber(x))
									unknowns[index++] = x; //Executes setting the array int to x, then adds to index
							marker = temp.getRight();
							int numberOfCellsWithSamePotential = 0;
							while(temp!=null)
							{//counting the other cells in the row with the same potentials
								while(temp!=null && (temp.numberOfPossibilities() != cellGroupSize || temp.getData() != 0))
									temp =temp.getRight();
								if(temp!=null)
								{
									boolean matchFound = true;
									for(int x = 0; x<cellGroupSize; x++)
										if(!temp.getPotentialNumber(unknowns[x]))
											matchFound = false;
									
									if(matchFound)
										numberOfCellsWithSamePotential++;
								}
								if(temp!=null)
									temp =temp.getRight();
							}
							/*At this point, we have counted all the cells in the row with the same 3 potentials */
							//System.out.println("NOCWSP:" + numberOfCellsWithSamePotential);
							
							if(numberOfCellsWithSamePotential == cellGroupSize)//we have 3 cells with the same 3 potentials
							{
								System.out.println("NOCWSP==");//TODO
								//System.out.println("Grouplet Found");
								temp = rowMarker;
								
								while(temp!= null)//searching for the cells that are not these 3 cells
								{
									while(temp!=null && temp.getData() != 0)
									{
										//System.out.println("skipping " + temp.getData());
										temp =temp.getRight();
										
									}
									if(temp!=null)
									{
										//System.out.println("checking..." + temp.getData());
										
										boolean matchFound = true;
										for(int x = 0; x<cellGroupSize; x++)
											if(!temp.getPotentialNumber(unknowns[x]))
												matchFound = false;
										
										if((!matchFound) || temp.numberOfPossibilities()!=cellGroupSize)//and eliminate the potentials for those cells
										{
											for(int x = 0; x<cellGroupSize; x++)
												temp.eliminatePotential(unknowns[x]);
											changesMade++;
											
										}
									}
									if(temp!=null)
										temp =temp.getRight();
								}
								
							}
									
						}
					}
					
					
					//while()
				}
				
				
			}
			
			
			rowMarker=rowMarker.getDown();
		
		}//End of changing rows
		
		return changesMade;
	}
	
	public void firstRowPotentials()
	{
		Cell temp = first;
		while(temp!=null)
		{
			System.out.println("\n===================");
			for(int x = 1; x<10; x++)
				if(temp.getPotentialNumber(x))
					System.out.print(x + " ");
			temp = temp.getRight();
		}
	}
	
	public void lastRowPotentials()
	{
		Cell temp = first.getDown().getDown().getDown().getDown().getDown().getDown().getDown().getDown();
		while(temp!=null)
		{
			System.out.println("\n===================");
			for(int x = 1; x<10; x++)
				if(temp.getPotentialNumber(x))
					System.out.print(x + " ");
			temp = temp.getRight();
		}
	}
	
	public Cell findFirstUnsolved(){
		
		Cell temp=first;
		Cell marker=first;
		
		while(temp != null)
		{
			while(temp != null)
			{
				if(temp.getPotentialNumber(0)==false&&temp.getData()==0)
					return temp;
				
				temp = temp.getRight();
			}
			marker = marker.getDown();
			temp = marker;
		}
		
		return null;
	}
	
	public Grid duplicateGrid(Grid CGrid){
		//Create a new grid and copy the original values to the grid
		Cell temp=first;
		Cell marker=first;
		
		Cell temp2=CGrid.getFirst();
		Cell marker2=CGrid.getFirst();
		
		while(temp != null)
		{
			while(temp != null)
			{
				temp2.setData(temp.getData());
				
				for(int y=1;y<10;y++)
					temp2.setPotentialNumber(y, temp.getPotentialNumber(y));
				
				temp = temp.getRight();
				temp2=temp2.getRight();
			}
			marker = marker.getDown();
			marker2=marker2.getDown();
			temp = marker;
			temp2=marker2;
		}
		//finished grid
		
		return CGrid;
	}
	
	/*public boolean guessing(){
		boolean solved=false;
		
		Grid CGrid = duplicateGrid(new Grid(9));
		
		
	}*/
	
	
	public boolean guessing(Grid TGrid){
		boolean solved=false;
		
		if(TGrid==null){
			TGrid=this;
		}
		
		if(!checkIfSolved()){
			
			Cell temp=TGrid.getFirst();
			/*Cell marker=temp;
			while(marker.getLeft()!=null)
				marker=marker.getLeft();
			*/
			Cell marker=TGrid.getFirst();
			
			//Search for an unsolved cell
			loop: while(temp != null)
			{
				while(temp != null)
				{
					if(temp.getPotentialNumber(0)==false){
						for(int x=1;x<10;x++){
							if(temp.getPotentialNumber(x)){ //if unsolved and has potential number
								 //GUESSNUM with x num
								//System.out.println(temp.getData());
								Grid CGrid= new Grid(9);
								CGrid=duplicateGrid(CGrid);
								
								Cell tempC=CGrid.first;
								Cell markerC = CGrid.first;
								loop2:while(temp != null)
								{
									while(temp != null)
									{
										if(tempC.getRowValue()==temp.getRowValue()&&tempC.getColValue()==temp.getColValue())
											break loop2;
										tempC=tempC.getRight();
									}
									markerC=markerC.getDown();
									tempC=markerC;
								}
								
								if(guessNum(CGrid, tempC, x)){ //If GUESSNUM solved the sudoku, break
									solved = true;
									break loop;
								}
							}
						}
					}
					
					temp = temp.getRight();
				}
				marker = marker.getDown();
				temp = marker;
			}
			
		}
		return solved;
	}

	public boolean guessNum(Grid CGrid, Cell cell, int num){
		
		cell.setData(num);
		boolean solved = false;
		
		solveMethods();
		if(CGrid.checkIfSolved()){
			solved=true;
			FGrid = CGrid;
		}
		else if(!CGrid.checkIfFilled())
			if(guessing(CGrid))
				solved=true;
		
		return solved;
		
	}
	
	public void solveMethods(){
		//TODO fix 2 and 3 then work on guessing then work on 5
		int totalChangesMade = 0;
		
		do{
			totalChangesMade = 0;
			elimPoss();
			int c1=0;//solveMethod1();//Works fine
			int c2=0;//solveMethod2();
			int c3=0;//solveMethod3();
			int c4=solveMethod4();//Works fine
			
			//TODO methods 2 and 3 are screwing everything up
			/*
			System.out.println("C1: " +c1);
			System.out.println("C2: " +c2);
			System.out.println("C3: " +c3);
			System.out.println("C4: " +c4);
			System.out.println("=================");
			display();
			System.out.println("/////////////////");*/
			
			//totalChangesMade=solveMethod1()+solveMethod2()+solveMethod3()+solveMethod4();
			totalChangesMade=c1+c2+c3+c4;
			
		}while(totalChangesMade>0);
		
		
	}
	
	public void solveSudoku(){
		
		solveMethods();
		
		if(!checkIfSolved()&&!checkIfFilled()){
			System.out.println("Guess here");
			//guessing(null);
			first.tour(this);
			if(checkIfSolved())
				display();//TODO
			//Do something with FGrid?
			
		}
		if(checkIfSolved())
			System.out.println("Solved");
		
	}
	
	
}