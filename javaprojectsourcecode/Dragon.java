import java.util.*;
import java.awt.Point;

public class Dragon extends Piece{
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
  
 public Dragon(int player){
  super(9, player);
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
 		getVertical(p,squareArray);
 		getHorizontal(p,squareArray);
 		getDiagonal1(p,squareArray);
   		getDiagonal2(p,squareArray);
  		return validMoves;
 	}
}
public void rewind(int n, int pos){
	for(int i = 0; i < n; i++){
		validMoves.remove(pos);
	}

}
public void getVertical(Point p, Square[][] squareArray)
{
	int addedNo = 0;
	int pos = validMoves.size();
	int xcor = p.x;
	int ycor = p.y;
	for(int i = 0; i < squareArray.length; i++){
		if( i == ycor) continue;
		else if(squareArray[xcor][i].getPiece() != null && squareArray[xcor][i].getPiece().getPieceNo() == Piece.DRAGON &&
			squareArray[xcor][i].getModifier() != Square.FOREST){
			if(i < ycor){
				validMoves.add(new Point(xcor,i));
				rewind(addedNo,pos);
				continue;
			}
			else{
				validMoves.add(new Point(xcor,i));
				break;
			}
		}
		else{
				Piece tempP = squareArray[xcor][i].getPiece();
				if(!(squareArray[xcor][i].getModifier() == Square.FOREST && tempP!= null)){
					validMoves.add(new Point(xcor,i));
					addedNo++;
				}
		}
	}
}

public void getHorizontal(Point p,Square[][]squareArray)
{	
	int addedNo = 0;
	int pos = validMoves.size();
	int xcor = p.x;
	int ycor = p.y;
	for (int i = 0; i < squareArray.length; i++)
	{
		if ( i == xcor) continue;
		else if(squareArray[i][ycor].getPiece() != null && squareArray[i][ycor].getPiece().getPieceNo() == Piece.DRAGON &&
			squareArray[i][ycor].getModifier() != Square.FOREST){
			if(i < xcor){
				validMoves.add(new Point(i,ycor));
				rewind(addedNo,pos);
				continue;
			}
			else{
				validMoves.add(new Point(i,ycor));
				break;
			}
		}		
		else{
				Piece tempP = squareArray[i][ycor].getPiece();
				if(!(squareArray[i][ycor].getModifier() == Square.FOREST && tempP!= null)){
					validMoves.add(new Point(i,ycor));
					addedNo++;
				}
		}
	}
}
// this method gets the diagonal path from topleft to bottomright 
public void getDiagonal1(Point p, Square[][] squareArray){
	int xFirst, yFirst;
	int xOri = p.x;
	int yOri = p.y;
	int pos = validMoves.size();
	int addedNo = 0;
	if(xOri == yOri){
		xFirst = yFirst =0;
	}
	else if (xOri > yOri){
		yFirst = 0;
		xFirst = xOri - yOri;
	}
	else{
		xFirst = 0;
		yFirst = yOri - xOri;
	}
	while(xFirst < squareArray.length && yFirst < squareArray.length){
		if (xFirst == xOri && yFirst == yOri){
			xFirst++;
			yFirst++;
			continue;
		}
		else if(squareArray[xFirst][yFirst].getPiece() != null && squareArray[xFirst][yFirst].getPiece().getPieceNo() == Piece.DRAGON &&
			squareArray[xFirst][yFirst].getModifier() != Square.FOREST){
			if(xFirst < xOri){
				validMoves.add(new Point(xFirst,yFirst));
				rewind(addedNo,pos);
				xFirst++;
				yFirst++;
				continue;
			}
			else{
				validMoves.add(new Point(xFirst,yFirst));
				break;
			}
		}				
		else{
			Piece tempP = squareArray[xFirst][yFirst].getPiece();
			if(!(squareArray[xFirst][yFirst].getModifier() == Square.FOREST && tempP != null)){
				validMoves.add(new Point(xFirst,yFirst));
				addedNo++;
			}
			xFirst++;
			yFirst++;
		}

	}

}
//this method gets the diagonal path from the topright to bottome left
public void getDiagonal2(Point p, Square[][] squareArray){
	int xFirst, yFirst;
	int xOri = p.x;
	int yOri = p.y;
	int pos = validMoves.size();
	int addedNo = 0;
	xFirst = xOri;
	yFirst = yOri;
	while((0 < xFirst) && (yFirst < squareArray.length -1 )) {
			xFirst--;
			yFirst++;
	}
	
	
	while(xFirst < squareArray.length  && 0 <= yFirst  ){
		if (xFirst == xOri && yFirst == yOri){
			xFirst++;
			yFirst--;
			continue;
		}
		else if(squareArray[xFirst][yFirst].getPiece() != null && squareArray[xFirst][yFirst].getPiece().getPieceNo() == Piece.DRAGON &&
			squareArray[xFirst][yFirst].getModifier() != Square.FOREST){
			if(xFirst < xOri){
				validMoves.add(new Point(xFirst,yFirst));
				rewind(addedNo,pos);
				xFirst++;
				yFirst--;
				continue;
			}
			else{
				validMoves.add(new Point(xFirst,yFirst));
				break;
			}
		}						
		else{
		Piece tempP = squareArray[xFirst][yFirst].getPiece();
				if(!(squareArray[xFirst][yFirst].getModifier() == Square.FOREST && tempP != null)){
					validMoves.add(new Point(xFirst,yFirst));
					addedNo++;
				}
				xFirst++;
				yFirst--;
	}

}

}
}