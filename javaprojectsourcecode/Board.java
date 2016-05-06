import java.util.*;
import java.awt.Point;

public class Board{
 //static variable for the maximum number of each type of tile
 public static final int maxMountainTiles = 6;
 public static final int maxWaterTiles = 5;
 public static final int maxForestTiles = 6;
 public static final int maxFortressTiles = 1;
 
 //Directions of travel
 public static final int NORTH = 0;
 public static final int NORTHEAST = 1;
 public static final int EAST = 2;
 public static final int SOUTHEAST = 3;
 public static final int SOUTH = 4;
 public static final int SOUTHWEST = 5;
 public static final int WEST = 6;
 public static final int NORTHWEST = 7;
 
 //Coordinates of Travel
 public static final int[][] DIRECTIONCOORD = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
 
 //Array of Sqaures that make up the board
 Square[][] squareArray;
 /**
 ** Default contructor that makes an empty board
 **/
 public Board(){
  squareArray = new Square[8][8];
  setupBlankBoard();
 }
 /**
 ** Copy constructor
 ** @param presetSquareArray Preset board configuration
 **/
 public Board(Square[][] presetSquareArray){
  squareArray = presetSquareArray;
 }
 /**
 ** Method that sets up blank board
 **/
 public void setupBlankBoard(){
  for(int i = 0; i < squareArray.length; i++){
   for(int j = 0; j < squareArray[i].length; j++){
    squareArray[i][j] = new Square();
    //Square have no owners, and are empty
    squareArray[i][j].setPiece(null);
   }
  }
 }
 /**
 ** Getter method for the board length
 ** @return Board length
 **/
 public int getLength(){
  return squareArray[0].length;
 }
 /**
 ** Getter method for the board width
 ** @return Board width
 **/
 public int getWidth(){
  return squareArray.length;
 }
 /**
 ** Getter method for a Square
 ** @param p Coordinate of Square
 ** @return Square at a particular coordinate
 **/
 public Square getSquare(Point p){
  return squareArray[p.x][p.y];
 }
 /**
 ** Method to set a square with a given modifier
 ** @param p Coordinate of square
 ** @param modifier Modifier of terrain
 ** @param player Player number
 **/
 public void setSquare(Point p, int modifier, int player){
  //Add error handling here
  squareArray[p.x][p.y].setModifier(modifier, player);
 }
 /**
 ** Method to remove a piece from a point
 ** @return p Coordinate of piece
 **/
 public void removePiece(Point p){
  if(squareArray[p.x][p.y].getPiece() != null){
   squareArray[p.x][p.y].removePiece();
  }
 }
 /**
 ** Method to set a piece, given coordinate and piece
 ** @param p Coordinate of piece
 ** @param piece piece being set
 **/
 public void setPiece(Point p, Piece piece){
  squareArray[p.x][p.y].setPiece(piece);
 }
 /**
 ** Method to set a piece, given coordinate and piece Number and Player
 ** @param p Coordinate of piece
 ** @param pieceNo Number of piece being set
 ** @param player Player number
 **/
 public void setPiece(Point p, int pieceNo, int player){
  squareArray[p.x][p.y].setPiece(pieceNo, player);
 }
 /**
 ** Method to return all Valid Moves, given a Point
 ** @param p point of piece to calculate valid moves from
 ** @return List of points that represent valid moves
 **/
 public List<Point> getValidMoves(Point p){
  ArrayList<Point> returnList = new ArrayList<Point>();
  Piece movingPiece = getSquare(p).getPiece();
  if(movingPiece != null){
   return movingPiece.getValidMoves(p, squareArray);
  }
  return returnList;
 }
 /**
 ** Method that checks that the Board has the appropriate number of terrain tiles
 ** @param player Player number
 ** @return Whether requirements have been fulfilled
 **/
 public boolean correctTerrain(int player){
  //Initialize list of mountain tiles  
  ArrayList<Point> mountainTiles = new ArrayList<Point>();
  //Initialize list of modifiers
  int[] modifierArray = {0,0,0,0,0};
  //Check through all squares
  for(int i = 0; i < squareArray.length; i++){
   for(int j = 0; j < squareArray[i].length; j++){
    Point temp = new Point(i, j);
    Square tempSquare = getSquare(temp);
    //If a square is not empty, and belongs to the given player
    if(tempSquare.getModifier() != Square.EMPTY && tempSquare.getOwner() == player){
     //Increment appropriate modifier
     modifierArray[tempSquare.getModifier()]++;
    }
    //Add mountains to mountain list
    if(tempSquare.getModifier() == Square.MOUNTAIN && tempSquare.getOwner() == player){
     mountainTiles.add(temp);
    }
   }
  }
  
  //Go through all mountain tiles
  for(int i = 0; i < mountainTiles.size(); i++){
   //Check through list of surrounding squares
   if(numberOfContMountains(mountainTiles.get(i), player) > 2){
    System.out.println("Too many mountains in a row");
    return false;
   }
  }
  //Make sure every modifier has the correct number of tiles
  if(modifierArray[1] != maxMountainTiles || modifierArray[2] != maxWaterTiles 
   || modifierArray[3] != maxForestTiles 
   || modifierArray[4] != maxFortressTiles){
   return false;
  }
  return true;
 }
 /**
 ** Internal method to check for contiguous mountains given a point
 ** @param p point to check
 ** @return Number of contiguous mountains
 **/
 private int numberOfContMountains(Point p, int player){
  //Initialize the number of mountains
  int contMount = 1;
  //List of checked Points
  ArrayList<Point> checkedPoints = new ArrayList<Point>();
  //Queue for points to be checked
  ArrayList<Point> queue = new ArrayList<Point>();
  
  //Add initial point into queue
  queue.add(p);
  //Make sure initial point is ignored
  checkedPoints.add(p);
  
  while(!queue.isEmpty()){
   Point currP = queue.get(0);
   //Get all squares within one tile of currP
   for(int i = -1; i < 2; i++){
    for(int j = -1; j < 2; j++){
     //skip original square
     if(i == 0 && j == 0){
      continue;
     }
     Point tempP = new Point(currP.x+i, currP.y+j);
     if(Board.validPoint(tempP)){ //Make sure point is actually on the board
      Square tempSquare = squareArray[tempP.x][tempP.y];
      //Enqueue if empty square is Mountain and not already in the list
      if(!checkedPoints.contains(tempP) && tempSquare.getModifier() == Square.MOUNTAIN && tempSquare.getOwner() == player){
       checkedPoints.add(tempP);
       queue.add(tempP);
       contMount++;
      }
      
     }
    }
   }
   //Dequeue currP
   queue.remove(0);
  }
  
  return contMount;
 }
 /**
 ** Method to check for all units are present
 ** @param player Player number
 ** @return Whether all unit requirements have been met
 **/
 public boolean correctPieces(int player){
  int[] pieceArray = new int [10];
  for(int i = 0; i < squareArray.length; i++){
   for(int j = 0; j < squareArray[i].length; j++){
    Point temp = new Point(i, j);
    Square tempSquare = getSquare(temp);
    //Check number of pieces
    if(tempSquare.getPiece() != null){
     if(tempSquare.getPiece().getOwner() == player){
      //System.out.println("PieceNo: " + tempSquare.getPiece().getPieceNo() + " at " + temp);
      pieceArray[tempSquare.getPiece().getPieceNo()-1]++;
     }
    }
    //Check no pieces on mountains except dragon
    if(tempSquare.getModifier() == Square.MOUNTAIN && tempSquare.getPiece() != null){
     if(tempSquare.getPiece().getPieceNo() != Piece.DRAGON){
      return false;
     }
    }
   }
  }
  for(int i = 0; i < pieceArray.length; i++){
   if(pieceArray[i] != 1){
    System.out.println("Check pieceNo: " + i);
    return false;
   }
  }
  return true;
 }
 /**
 ** Static method to check if a particular point is a point within the board
 ** @param p point to check
 ** @return Whether point is valid.
 **/
 public static boolean validPoint(Point p){
  if(p.x < 0 || p.x >= GameBoard.NUMROWSQUARES || p.y < 0 || p.y >= GameBoard.NUMROWSQUARES){
   return false;
  }
  return true;
 }

//This is my modification for the water movement unlock
  public void waterUnlock(){
    for(int i = 0; i < squareArray.length; i++){
      for(int j = 0; j < squareArray.length; j++){
        if(squareArray[i][j].getPiece() != null && squareArray[i][j].getModifier() == Square.WATER){
          squareArray[i][j].getPiece().loseturn++;
        }
      }
    }
  }
  public void waterUnlock1(Point endPoint){
    for(int i = 0; i < squareArray.length; i++){
      for(int j = 0; j < squareArray.length; j++){
        if(squareArray[i][j].getPiece() != null && squareArray[i][j].getModifier() == Square.WATER){
          if(endPoint.x == i && endPoint.y == j) continue;
          else squareArray[i][j].getPiece().loseturn++;
        }
      }
    }
  }
}


