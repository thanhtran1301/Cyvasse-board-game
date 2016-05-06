import java.util.*;
import java.awt.Point;

public class Trebuchet extends Piece{
 ArrayList<Point> validMoves = new ArrayList<Point>();
 
  
 public Trebuchet(int player){
  super(8, player);
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
 		int i,j;
 		int xcor = (int)p.getX();
    	int ycor = (int)p.getY();
 		for(i = ycor - 1 ; i <= ycor + 1; i++)
 		{
 			for (j = xcor -1 ; j <= xcor +1 ;j++)
 			{
 				if(( i!= ycor || j!= xcor) && (0 <= i && i <= 7) && (0 <= j && j <= 7) )
 				{
 					if (squareArray[j][i].getModifier() != Square.MOUNTAIN) {
 						if(squareArray[j][i].getPiece() == null){
 							validMoves.add(new Point(j,i));
 						}

 					}
 				}
 			}
 		}	
 		if ( trebBegin != null && trebEnd != null){
 			int counterX, counterY;
 			Point center = trebEnd;
 			if((trebEnd.x - trebBegin.x) == -1 && (trebEnd.y - trebBegin.y) == -1 ){
 				counterX = counterY = -1;
 				getRangeAttack(3, counterX, counterY,center,squareArray);

 			}
 			else if( (trebEnd.x - trebBegin.x) == 0 && (trebEnd.y - trebBegin.y) == -1 ){
 				counterX = 0;
 				counterY = -1;
 				getRangeAttack(3, counterX, counterY,center,squareArray);
 			}		
 			else if( (trebEnd.x - trebBegin.x) == 1 && (trebEnd.y - trebBegin.y) == -1 ){
 				counterX = 1;
 				counterY = -1;
 				getRangeAttack(3, counterX, counterY,center,squareArray);
 			}
 			else if( (trebEnd.x - trebBegin.x) == -1 && (trebEnd.y - trebBegin.y) == 0 ){
 				counterX = -1;
 				counterY = 0;
 				getRangeAttack(3, counterX, counterY,center,squareArray);
 			}			
 			else if( (trebEnd.x - trebBegin.x) == 1 && (trebEnd.y - trebBegin.y) == 0 ){
 				counterX = 1;
 				counterY = 0;
 				getRangeAttack(3, counterX, counterY,center,squareArray);
 			}
 			else if( (trebEnd.x - trebBegin.x) == -1 && (trebEnd.y - trebBegin.y) == 1 ){
 				counterX = -1;
 				counterY = 1;
 				getRangeAttack(3, counterX, counterY,center,squareArray);
 			}		
 			else if( (trebEnd.x - trebBegin.x) == 0 && (trebEnd.y - trebBegin.y) == 1 ){
 				counterX = 0;
 				counterY = 1;
 				getRangeAttack(3, counterX, counterY,center,squareArray);
 			}		
 			else if( (trebEnd.x - trebBegin.x) == 1 && (trebEnd.y - trebBegin.y) == 1 ){
 				counterX = 1;
 				counterY = 1;
 				getRangeAttack(3, counterX, counterY,center,squareArray);
 			}	




 		}
 		return validMoves;
 	}
 }


public void getRangeAttack(int range, int counterX, int counterY, Point center,Square[][] squareArray){
	for(int i = 1; i <= 3; i++){
		Point tempP = new Point(center.x + counterX*i, center.y + counterY*i);
		if(Board.validPoint(tempP)){
			if(squareArray[center.x + counterX*i][center.y + counterY*i].getModifier() == Square.MOUNTAIN){
				if(squareArray[center.x + counterX*i][center.y + counterY*i].getPiece() == null){
					break;

				}
				else{
					if(squareArray[center.x + counterX*i][center.y + counterY*i].getPiece().getPieceNo() == Piece.DRAGON){
						validMoves.add(new Point(center.x + counterX*i, center.y + counterY*i));
						break;
					}
				}
			}
			else{
				if(i == 1){
				validMoves.add(new Point(center.x + counterX*i, center.y + counterY*i));
			}
				else if( i != 1 && squareArray[center.x + counterX*i][center.y + counterY*i].getPiece() != null){
					validMoves.add(new Point(center.x + counterX*i, center.y + counterY*i));
				}	

		}
		}
		else{
			break;
		}
	}
	return;

}
}