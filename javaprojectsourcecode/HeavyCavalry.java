import java.util.*;
import java.awt.Point;

public class HeavyCavalry extends Piece{
 ArrayList<Point> validMoves = new ArrayList<Point>();
  List<Integer> captureList = new ArrayList<Integer>() {{
            add(Piece.RABBLE);
            
            add(Piece.CROSSBOWMAN);
            add(Piece.LIGHTCAVALRY);
            add(Piece.HEAVYCAVALRY);
            add(Piece.ELEPHANT);
            
            add(Piece.KING);
           }};
 public HeavyCavalry(int player){
  super(5, player);
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
  int range = 0;
  Point origin = new Point(p.x, p.y);
  getMoveHelper1(origin, p, squareArray,range);
  return validMoves;
}
}
public void getMoveHelper1( Point origin, Point p, Square[][] squareArray, int range){
  if (range >= 2) return;
  else{


  for(int i = -1; i < 2; i++){
    for( int j = -1; j < 2; j++){
      
      if( p.x + i == origin.x && p.y +j == origin.y) continue;
      if(Board.validPoint(new Point(p.x + i,p.y + j))){
          if(squareArray[p.x + i][p.y + j].getModifier() == Square.FOREST && squareArray[p.x + i][p.y + j].getPiece() == null){
            if(!(validMoves.contains(new Point(p.x +i, p.y +j)))){
            validMoves.add(new Point(p.x + i,p.y +j));
            range = range + 2;
            
            getMoveHelper1(origin,new Point(p.x +i, p.y + j), squareArray, range);
            range = range - 2;
            
          }
          else continue;
        }
          else if(squareArray[p.x + i][p.y + j].getModifier() == Square.MOUNTAIN){
        
            continue;
          }
          else if( squareArray[p.x + i][p.y + j].getPiece() != null){

            if(!(validMoves.contains(new Point(p.x +i,p.y +j))) && captureList.contains(squareArray[p.x +i][p.y +j].getPiece().getPieceNo())){
              validMoves.add(new Point(p.x + i, p.y +j));
            
            continue;
            }
            else continue;                       
          }

          else if (squareArray[p.x + i][p.y + j].getPiece() == null){
            if(!(validMoves.contains(new Point(p.x +i,p.y +j)))){
            validMoves.add(new Point(p.x +i,p.y +j));
            range++;
            
            getMoveHelper1(origin,new Point(p.x + i, p.y + j), squareArray, range);
            range--;
            
          }
          else {
            range++;
            
            getMoveHelper1(origin,new Point(p.x + i, p.y + j), squareArray, range);
            range--;            
           
        }
      }
      }
      else{
        continue;
      }
    }
  }
  return;


}

}
public boolean canTake(Piece p){
  return captureList.contains(p.getPieceNo());
 }
}