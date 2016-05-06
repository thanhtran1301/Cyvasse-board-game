import java.util.*;
import java.awt.Point;

public class Catapult extends Piece{
 ArrayList<Point> validMoves = new ArrayList<Point>();
 List<Integer> captureList = new ArrayList<Integer>() {{
            add(Piece.RABBLE);
            add(Piece.SPEARMAN);
            add(Piece.CROSSBOWMAN);
            add(Piece.LIGHTCAVALRY);
            add(Piece.HEAVYCAVALRY);
            add(Piece.ELEPHANT);
            add(Piece.CATAPULT);
            add(Piece.TREBUCHET);
            add(Piece.DRAGON);
            add(Piece.KING);
           }};
 
 public Catapult(int player){
  super(7, player);
 } 

 //This method assumes that the board is read from top left, row by row
 //ending at bottom right.
 @Override
  public List<Point> getValidMoves(Point p, Square[][] squareArray){
 	validMoves = new ArrayList<Point>();
 	if (inwater && loseturn < 2){
   		return validMoves;
  	}else{
    	inwater = false;
    	loseturn = 0;
 		getDiagonal1(p, squareArray);
 		getDiagonal2(p,squareArray);
 		getHorizontal(p,squareArray);
 		getVertical(p, squareArray);
 		return validMoves;
 	
	}
}


public void getDiagonal1(Point p, Square[][] squareArray){
	Point tempPoint = new Point(p.x - 1, p.y - 1);
	int counter;
	counter = -1;
	int range = 1;
	diagonal1Helper( counter, range, tempPoint,squareArray);
	counter = 1;
	range = 1;
	tempPoint = new Point(p.x + 1, p.y + 1);
	diagonal1Helper( counter, range, tempPoint,squareArray);
}
public void getDiagonal2(Point p, Square[][] squareArray){
	Point tempPoint = new Point(p.x - 1, p.y + 1);
	int counter;
	counter = 1;
	int range = 1;
	diagonal2Helper( counter, range, tempPoint,squareArray);
	counter = -1;
	range = 1;
	tempPoint = new Point(p.x + 1, p.y - 1);
	diagonal2Helper( counter, range, tempPoint,squareArray);
}
public void getHorizontal(Point p, Square[][] squareArray){
	Point tempPoint = new Point(p.x - 1, p.y);
	int counter;
	counter = 1;
	int range = 1;
	getHorizontalHelper( counter, range, tempPoint,squareArray);
	counter = -1;
	range = 1;
	tempPoint = new Point(p.x + 1, p.y);
	getHorizontalHelper( counter, range, tempPoint,squareArray);
}
public void getVertical(Point p, Square[][] squareArray){
	Point tempPoint = new Point(p.x, p.y - 1);
	int counter;
	counter = 1;
	int range = 1;
	getVerticalHelper( counter, range, tempPoint,squareArray);
	counter = -1;
	range = 1;
	tempPoint = new Point(p.x, p.y + 1);
	getVerticalHelper( counter, range, tempPoint,squareArray);
}
public void getVerticalHelper(int counter, int range, Point tempPoint, Square[][] squareArray){
	if (range >= 3) return;

	if (Board.validPoint(tempPoint)){
		
		Square tempSquare = squareArray[tempPoint.x][tempPoint.y];
		if(tempSquare.getModifier() != Square.MOUNTAIN){
			if((range == 1 ) && !(validMoves.contains(tempPoint)) && 
			(tempSquare.getPiece() == null || captureList.contains(tempSquare.getPiece().getPieceNo()))){
				validMoves.add(tempPoint);
				range++;
				getVerticalHelper(counter, range, new Point(tempPoint.x, tempPoint.y - counter), squareArray);
			}
			else if( range != 1 && tempSquare.getPiece() != null){
				validMoves.add(tempPoint);
				range++;
				getVerticalHelper(counter, range, new Point(tempPoint.x, tempPoint.y - counter), squareArray);
			}
			else {
				range++;
				getVerticalHelper(counter, range, new Point(tempPoint.x , tempPoint.y - counter), squareArray);

			}
		}
		else if(tempSquare.getModifier() == Square.MOUNTAIN){
				if(tempSquare.getPiece() == null){
					
					return;
				}
				
				else{
					validMoves.add(tempPoint);
					return;
				}
	}
	else{
		return;
	}
		}
		else return;
}

public void getHorizontalHelper(int counter, int range, Point tempPoint, Square[][] squareArray){
	if (range >= 3) return;

	if (Board.validPoint(tempPoint)){
		
		Square tempSquare = squareArray[tempPoint.x][tempPoint.y];
		if(tempSquare.getModifier() != Square.MOUNTAIN){
			if((range == 1 ) && !(validMoves.contains(tempPoint)) && 
			(tempSquare.getPiece() == null || captureList.contains(tempSquare.getPiece().getPieceNo()))){
				validMoves.add(tempPoint);
				range++;
				getHorizontalHelper(counter, range, new Point(tempPoint.x - counter, tempPoint.y ), squareArray);
			}
			else if( range != 1 && tempSquare.getPiece() != null){
				validMoves.add(tempPoint);
				range++;
				getHorizontalHelper(counter, range, new Point(tempPoint.x - counter, tempPoint.y ), squareArray);
			}
			else {
				range++;
				getHorizontalHelper(counter, range, new Point(tempPoint.x - counter, tempPoint.y ), squareArray);

			}
		}
		else if(tempSquare.getModifier() == Square.MOUNTAIN){
				if(tempSquare.getPiece() == null){
					
					return;
				}
				
				else{
					validMoves.add(tempPoint);
					return;
				}
	}
	else{
		return;
	}
		}
		else return;
}
public void diagonal2Helper(int counter, int range, Point tempPoint, Square[][] squareArray){
	if (range >= 3) return;

	if (Board.validPoint(tempPoint)){
		
		Square tempSquare = squareArray[tempPoint.x][tempPoint.y];
		if(tempSquare.getModifier() != Square.MOUNTAIN){
			if((range == 1 ) && !(validMoves.contains(tempPoint)) && 
			(tempSquare.getPiece() == null || captureList.contains(tempSquare.getPiece().getPieceNo()))){
				validMoves.add(tempPoint);
				range++;
				diagonal2Helper(counter, range, new Point(tempPoint.x - counter, tempPoint.y + counter), squareArray);
			}
			else if( range != 1 && tempSquare.getPiece() != null){
				validMoves.add(tempPoint);
				range++;
				diagonal2Helper(counter, range, new Point(tempPoint.x - counter, tempPoint.y + counter), squareArray);
			}
			else {
				range++;
				diagonal2Helper(counter, range, new Point(tempPoint.x - counter, tempPoint.y + counter), squareArray);

			}
		}
		else if(tempSquare.getModifier() == Square.MOUNTAIN){
				if(tempSquare.getPiece() == null){
					
					return;
				}
				
				else{
					validMoves.add(tempPoint);
					return;
				}
	}
	else{
		return;
	}
		}
		else return;
}

public void diagonal1Helper(int counter, int range, Point tempPoint, Square[][] squareArray){
	if (range >= 3) return;

	if (Board.validPoint(tempPoint)){
		
		Square tempSquare = squareArray[tempPoint.x][tempPoint.y];
		if(tempSquare.getModifier() != Square.MOUNTAIN){
			if((range == 1 ) && !(validMoves.contains(tempPoint)) && 
			(tempSquare.getPiece() == null || captureList.contains(tempSquare.getPiece().getPieceNo()))){
				validMoves.add(tempPoint);
				range++;
				diagonal1Helper(counter, range, new Point(tempPoint.x + counter, tempPoint.y + counter), squareArray);
			}
			else if( range != 1 && tempSquare.getPiece() != null){
				validMoves.add(tempPoint);
				range++;
				diagonal1Helper(counter, range, new Point(tempPoint.x + counter, tempPoint.y + counter), squareArray);
			}
			else {
				range++;
				diagonal1Helper(counter, range, new Point(tempPoint.x + counter, tempPoint.y + counter), squareArray);

			}
		}
		else if(tempSquare.getModifier() == Square.MOUNTAIN){
				if(tempSquare.getPiece() == null){
					
					return;
				}
				else{
					validMoves.add(tempPoint);
					return;
				}
	}
	else{
		return;
	}
		}
		else return;
}


public boolean canTake(Piece p){
	return captureList.contains(p.getPieceNo());
 }
}