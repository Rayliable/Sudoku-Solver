package codes;

public class Cell {
	private int data;
	private Cell up, down, left, right;
	private boolean [] potentialNumber = new boolean [10];
	private int boxValue, rowValue, colValue;
	/*
	* address meanings: 
	* [0] - has the cell been solved?
	* [1-9] - does it have the potential to be this number
	* 
	*/
	public Cell(){
		up = null;
		down = null;
		left = null;
		right = null;
		data = 0;
		for(int x= 1; x< 10; x++)
		potentialNumber[x] = true;
		potentialNumber[0] = false;
		boxValue = 0;
		rowValue=0;
		colValue=0;
	}
	
	public int numberOfPossibilities()
	{
		int count = 0;
		for(int x =1; x < 10; x++ )
			if(potentialNumber[x])
				count++;
		return count;
	}
	
	public void eliminatePotential(int number)
	{
		potentialNumber[number] = false;
	}
	
	public int getData() {
		return data;
	}
	public void setData(int number) {
		this.data = number;
	}
	public Cell getUp() {
		return up;
	}
	public void setUp(Cell up) {
		this.up = up;
	}
	public Cell getDown() {
		return down;
	}
	public void setDown(Cell down) {
		this.down = down;
	}
	public Cell getLeft() {
		return left;
	}
	public void setLeft(Cell left) {
		this.left = left;
	}
	public Cell getRight() {
		return right;
	}
	public void setRight(Cell right) {
		this.right = right;
	}
	public boolean getPotentialNumber(int pos) {
		return potentialNumber[pos];
	}
	public void setPotentialNumber(int pos, boolean newValue) {
		this.potentialNumber[pos]= newValue;
	}

	public int getBoxValue() {
		return boxValue;
	}

	public void setBoxValue(int boxValue) {
		this.boxValue = boxValue;
	}

	public int getRowValue() {
		return rowValue;
	}

	public void setRowValue(int rowValue) {
		this.rowValue = rowValue;
	}

	public int getColValue() {
		return colValue;
	}

	public void setColValue(int colValue) {
		this.colValue = colValue;
	}

	public void tour(Grid PGrid){
		
		PGrid.solveMethods();
		System.out.println("started");
		Grid TGrid = PGrid.duplicateGrid(new Grid(9));
		Cell TCell = null;
		
		Cell temp=TGrid.getFirst();
		Cell marker=temp;
		
		loop: while(temp!=null){
			while(temp!=null){
				if(temp.getColValue()==colValue&&temp.getRowValue()==rowValue){
					TCell=temp;
					break loop;
				}
				temp=temp.getRight();
			}
			marker=marker.getDown();
			temp=marker;
		}
		
		
		System.out.println("TESTING!!!");
		System.out.println("CELL:" + this.data);
		PGrid.display();
		System.out.println("==========");
		TGrid.display();
		/*
		if(this==Main.SGrid.getFirst())
			TGrid=Main.SGrid;
		else
			TGrid=Main.SGrid.duplicateGrid(new Grid(9));*/
		
		if(TGrid.checkIfSolved()){
			System.out.println("Solved with tour");
			return;
		}
		
		//-------------------------
		
		if(potentialNumber[0]){
			Cell temp2=this;
			Cell marker2=temp;
			while(marker2.getLeft()!=null)
				marker2=marker2.getLeft();
			
			while(temp2!=null){
				while(temp2!=null){
					if(!temp2.getPotentialNumber(0)){
						temp2.tour(PGrid);
						return;
					}
					temp2=temp2.getRight();
				}
				marker2=marker2.getDown();
				temp2=marker2;
			}
		}
		
		//------------------------
		
		boolean[] potNum2= new boolean[10];
		for(int x=0; x<10; x++)
			potNum2[x]=TCell.getPotentialNumber(x);
		
		for(int x=1; x<10; x++){
			System.out.println("Running"+x);
			if(TCell.getPotentialNumber(x)){
				System.out.println("Found");
				TCell.setData(x);
				TCell.setPotentialNumber(0,true);
				for(int y=1; y<10; y++)
					TCell.setPotentialNumber(y,false);
				
					
				//Start
				if(TGrid.checkIfSolved()){
					System.out.println("Solved with tour");
					return;
				}
				else {
					TGrid.findFirstUnsolved().tour(TGrid);
				}
				//end
				TCell.setData(0);
				TCell.setPotentialNumber(0,false);
				for(int y=1; y<10; y++)
					TCell.setPotentialNumber(y,potNum2[y]);
				
			}
		}
		
		
	}

}