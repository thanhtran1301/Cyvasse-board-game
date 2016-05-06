import java.awt.*;
import java.awt.Graphics;
import javax.swing.*;
import java.util.*;
import java.awt.Point;
import java.awt.event.*;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class GameBoard extends JFrame{
 public static final int NUMROWSQUARES = 8;
 //Define size of GUI board
 private int boardWidth = 504;
 private int boardHeight = 504;
 private int boardStartingX = 50;
 private int boardStartingY = 50;
 private int squareWidth = boardWidth/8;
 //Colors for possible terrain
 private Color[] terrainModColor = {Color.WHITE, new Color(222,184,135), new Color(135,206,250), new Color(154,205,50), Color.GRAY};
 
 //Jpanel array for piece images
 private JLabel[][] labelArray = new JLabel[8][8];
 
 //ImageIcons for each piece
 private Icon[] pieceImageArray = new ImageIcon[10];
 
 //Phase
 int phase;
 //Turn
 int playerTurn;
 
 //GUI elements
 private JLabel topLabel;
 //Button to end Turn
 private JButton endTurn;
 //If unit has been selected
 private boolean unitSelected;
 //If a player has already moved
 private boolean alreadyMoved;
 
 private boolean coverBottom;
 
 //Last point clicked by player
 private Point lastClickedPoint;
 //Data structure for the board
 private Board board;
 //List of valid moves by a unit
 private ArrayList<Point> validMoveArray = new ArrayList<Point>();
 //GUI panel for Board
 private JPanel boardPanel;
 /**
 ** General constructor for the GameBoard class.
 ** This generates a new Board, as well as initializes all variables
 ** and components
 **/
 public GameBoard(){
  board = new Board();
  
  //This is for preset boards (no error checking on pieces)
  //setupCustomBoard();
  
  initVariables();
  initComponents();
  setupCustomBoard2();

  }
 
 //Example of filling board with all mountain tiles, and one King
 // use this to skip the setup phase for testing
 private void setupCustomBoard(){
  //Setup board to custom configuration
  for(int i = 0; i < board.getLength(); i++){
   for(int j = 0; j < board.getWidth(); j++){
    Point temp = new Point(i, j);
    Square tempSquare = board.getSquare(temp);
    tempSquare.setModifier(Square.MOUNTAIN, 1);
   } 
  }
  board.getSquare(new Point(3,3)).setPiece(10, 1);
  //Set to move phase
  phase = 4;
 }

 private void setupCustomBoard2(){
  //setup terrains for the first player
  board.getSquare(new Point(0,6)).setModifier(Square.MOUNTAIN, 1);
  board.getSquare(new Point(1,7)).setModifier(Square.MOUNTAIN, 1);
  board.getSquare(new Point(3,6)).setModifier(Square.MOUNTAIN, 1);
  board.getSquare(new Point(4,7)).setModifier(Square.MOUNTAIN, 1);
  board.getSquare(new Point(6,6)).setModifier(Square.MOUNTAIN, 1);
  board.getSquare(new Point(7,7)).setModifier(Square.MOUNTAIN, 1);
  int i;
  for(i = 0; i < 5; i++)
  {
    board.getSquare(new Point(i,4)).setModifier(Square.WATER, 1);
  }
  for(i = 0; i < 6; i++)
  {
    board.getSquare(new Point(i,5)).setModifier(Square.FOREST, 1);
  }
  board.getSquare(new Point(7,4)).setModifier(Square.FORTRESS, 1);
  phase = 1;

  //setup pieces for the first player
  for( i = 0; i <= 7; i++)
  {
    board.setPiece(new Point(i,5), i + 1, 1);
  }
  i--;
  board.setPiece(new Point(i,6), 9, 1);
  board.setPiece(new Point(6,7), 10, 1);
  phase = 2;
  //setup terrains for the second player
  board.getSquare(new Point(0,0)).setModifier(Square.MOUNTAIN, 2);
  board.getSquare(new Point(1,1)).setModifier(Square.MOUNTAIN, 2);
  board.getSquare(new Point(3,0)).setModifier(Square.MOUNTAIN, 2);
  board.getSquare(new Point(4,1)).setModifier(Square.MOUNTAIN, 2);
  board.getSquare(new Point(6,0)).setModifier(Square.MOUNTAIN, 2);
  board.getSquare(new Point(7,1)).setModifier(Square.MOUNTAIN, 2);
  
  for(i = 0; i < 5; i++)
  {
    board.getSquare(new Point(i,3)).setModifier(Square.WATER, 2);
  }
  for(i = 0; i < 6; i++)
  {
    board.getSquare(new Point(i,2)).setModifier(Square.FOREST, 2);
  }
  board.getSquare(new Point(7,0)).setModifier(Square.FORTRESS, 2);
  phase = 3;
  //setup pieces for the second player
  for( i = 0; i <= 7; i++)
  {
    board.setPiece(new Point(i,2), i + 1, 2);
  }
  
  board.setPiece(new Point(6,1), 9, 2);
  board.setPiece(new Point(5,0), 10, 2);

  phase = 4;

 }
 
 /**
 ** Method to give game variables their initial values
 **/
 private void initVariables(){
  lastClickedPoint = new Point();
  playerTurn = 1;
  phase = 0;
  unitSelected= false;
  alreadyMoved = false;
  coverBottom = false;
 }
 /**
 ** Method that switches the playerTurn variable, changing the turn.
 **/
 private void playerTurnSwap(){
  if(playerTurn == 1){
   playerTurn = 2;
  }
  else if(playerTurn == 2){
   playerTurn =1;
  }
  else{
   //Error handling, should not happen
   System.out.println("Error in playerTurnSwap, invalid playerTurn Value");
  }
 }
 /**
 ** Method that initializes GUI components
 **/
 private void initComponents(){
  boardPanel = new GamePanel();
  //Create Label
  topLabel = new JLabel("Hello!");
  topLabel.setHorizontalAlignment(SwingConstants.CENTER);
  //boardPanel.add(topLabel, BorderLayout.NORTH);
  
  //Create Button
  endTurn = new JButton("End Turn");
  
  //Initialize Array of JLabels.
  for(int i = 0; i < labelArray.length; i++){
   for(int j = 0; j< labelArray[i].length; j++){
    int tempX = i*squareWidth+boardStartingX;
    int tempY = j*squareWidth+boardStartingY;
    //Give labels blank values
    labelArray[i][j] = new JLabel("");
    //Set size of label to be the size of the square
    labelArray[i][j].setBounds(tempX, tempY, squareWidth, squareWidth);
    //Center the text both horizontally and vertically
    labelArray[i][j].setHorizontalAlignment(SwingConstants.CENTER);
    labelArray[i][j].setVerticalAlignment(SwingConstants.CENTER);
    //Add label to boardPanel
    boardPanel.add(labelArray[i][j]);
   }
  }
  //Add actionlistener to endTurn button
  endTurn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e)
            {
    //4 Total phases: 
     //0: Player 1 places terrain
     //1: Player 1 places units
     //2: Player 2 places terrain
     //3: Player 2 places units
                if(phase == 0){
     //Error check, make sure all correct number of terrain
     if(board.correctTerrain(1)){
      System.out.println("All terrain pieces placed!");
      //initiate next phase
      phase++;
      
     }
     else{
      //Error Message
      System.out.println("Invalid board state");
     }
    }
    else if(phase ==1){
     //Error check, make sure there is one of each piece
     if(board.correctPieces(1)){
      System.out.println("All units accounted for!");
      phase++;
      //cover bottom half of screen
      coverBottom = true;
      boardPanel.repaint();
     }
     else{
      System.out.println("Check the units, sir!");
     }
     
    }
    else if(phase == 2){
     //Error check, make sure all correct number of terrain
     if(board.correctTerrain(2)){
      System.out.println("All terrain pieces placed!");
      //initiate next phase
      phase++;
     }
     else{
      //Error Message
      System.out.println("Invalid board state");
     }
     
    }
    else if(phase == 3){
     //Error check, make sure there is one of each piece
     if(board.correctPieces(2)){
      System.out.println("All units accounted for!");
      phase++;
      
      //uncover screen
      coverBottom = false;
      //Uncover player 1 labels
      coverPlayer1Label(false);
     }
     else{
      System.out.println("Check the units, sir!");
     }
    }
    //Game Phase
    else{
     //Cannot end turn without moving a piece
     if(alreadyMoved){
      //Switch player's turn
      playerTurnSwap();
      alreadyMoved = false;
     } 
    }
            }
        });
  //Set size of button
  endTurn.setBounds(0, 570, 600 ,30);
  //Add end turn button to Panel
  boardPanel.add(endTurn);
  //Put boardPanel on JFrame
  this.setContentPane(boardPanel);
  //Set size of JFrame
  this.setSize(750,750);
  //Exit GUI
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  //make sure everything is at preferred size
  pack();
 }
 /**
 ** Method that manages Piece movement on GUI
 ** @param endPoint the ending point of the moving unit
 ** @param p the piece that is moving
 ** @param rangedAttack whether move is a ranged attack
 **/
 private void takePiece(Point endPoint, Piece p, boolean rangedAttack){
  //Check if there is a piece there first
  if(board.getSquare(endPoint).getPiece() != null){
   //Check if endPoint is a fortress and there is a unit there
   if(board.getSquare(endPoint).getModifier() == Square.FORTRESS){
    //Destroy fortress instead of unit
    board.getSquare(endPoint).setModifier(Square.EMPTY, 0);
    return;
   }
  
   if(rangedAttack){
    //Remove unit without moving
    board.removePiece(endPoint);
    board.waterUnlock();
   }
   else // this is what I modified 
   {
    board.setPiece(endPoint, p);
    if(board.getSquare(endPoint).getModifier() == Square.WATER){
      board.getSquare(endPoint).getPiece().inwater = true;
      board.getSquare(endPoint).getPiece().loseturn = 0;
    }
    if(board.getSquare(endPoint).getPiece().getPieceNo() == Piece.TREBUCHET){
      board.getSquare(endPoint).getPiece().trebuMove(lastClickedPoint,endPoint);
    }
    board.removePiece(lastClickedPoint);
    board.waterUnlock1(endPoint);
   }
    ////
 }
   else{
    //Move unit and change labels
    board.setPiece(endPoint, p);
    if(board.getSquare(endPoint).getModifier() == Square.WATER){
      board.getSquare(endPoint).getPiece().inwater = true;
      board.getSquare(endPoint).getPiece().loseturn = 0;
    }
    if(board.getSquare(endPoint).getPiece().getPieceNo() == Piece.TREBUCHET){
      board.getSquare(endPoint).getPiece().trebuMove(lastClickedPoint,endPoint);
    }    
    board.removePiece(lastClickedPoint);
    board.waterUnlock1(endPoint);
   }
  
 }

 /**
 ** Private class to handle the main GUI
 **/
 private class GamePanel extends JPanel{
  GamePanel(){
   //Set size of GUI
   setPreferredSize(new Dimension(700,700));
   //Use absolute positioning (Not scalable)
   setLayout(null);
   //Ad mouse listeners
   addMouseListener(new MouseAdapter() {
    //Handle Mouse clicks
    @Override
    public void mouseClicked(MouseEvent e) {
     //Only care about clicks within the board
     if(e.getX() >= boardStartingX && e.getX() <= ( boardStartingX + boardWidth)
      && e.getY() >= boardStartingY && e.getY() <= (boardStartingY + boardHeight)
      ){
      int xSquare = (e.getX()-boardStartingX)/squareWidth;
      int ySquare = (e.getY()-boardStartingY)/squareWidth;
      
      boolean rightClick = SwingUtilities.isRightMouseButton(e);
      boolean leftClick = SwingUtilities.isLeftMouseButton(e);
      
      
      //Check which phase
      if(phase == 0){
       //Make sure clicks are in bottom half
       if(ySquare >=4){
        //Iterate through terrain tiles with lcick and rclick
        if(rightClick){
         decrementTerrain(new Point(xSquare, ySquare), 1);
        }
        if(leftClick){
         incrementTerrain(new Point(xSquare, ySquare), 1);
        }
        
       }
      }
      else if(phase ==1){
       //Make surer clicks are in bottom half
       if(ySquare >=4){
        //Iterate through terrain Pieces with lcick and rclick
        if(rightClick){
         decrementPiece(new Point(xSquare, ySquare),1);
        }
        if(leftClick){
         incrementPiece(new Point(xSquare, ySquare),1);
        }
       }
      }
      else if(phase == 2){
       //Make sure squares are in top half
       if(ySquare < 4){
        //Iterate through terrain tiles with lcick and rclick
        if(rightClick){
         decrementTerrain(new Point(xSquare, ySquare), 2);
        }
        if(leftClick){
         incrementTerrain(new Point(xSquare, ySquare), 2);
        }
       }
      }
      else if(phase == 3){
       //Make sure clicks are in top half
       if(ySquare < 4){
        if(rightClick){
         decrementPiece(new Point(xSquare, ySquare),2);
        }
        if(leftClick){
         incrementPiece(new Point(xSquare, ySquare),2);
        }
       }
      }
      else{//Actual game phase
       //Check if piece is clicked on, and piece belongs to the player
        // I modifed this so they players can attack their own pieces
       Piece clickedPiece = board.getSquare(new Point(xSquare, ySquare)).getPiece();
       if(unitSelected && validMoveArray.contains(new Point(xSquare, ySquare))){
        Piece lastClickedPiece = board.getSquare(lastClickedPoint).getPiece();
        Point endPoint = new Point(xSquare, ySquare);
        //If Ranged Attack
        if(Piece.isRanged[lastClickedPiece.getPieceNo()] &&
         board.getSquare(endPoint).getPiece() != null){
         takePiece(new Point(xSquare, ySquare), lastClickedPiece, true);
        }
        else{ //Movement
         takePiece(new Point(xSquare, ySquare), lastClickedPiece, false);
        }
        
   
        //Reset moves
        validMoveArray = new ArrayList<Point>();
        lastClickedPoint = new Point();
        unitSelected=false;
        alreadyMoved = true;
        //TODO
       }
       else if(clickedPiece != null && clickedPiece.getOwner() == playerTurn && !alreadyMoved){
        validMoveArray = new ArrayList<Point>(board.getValidMoves(new Point(xSquare, ySquare)));
        lastClickedPoint = new Point(xSquare, ySquare);
        unitSelected = true;
       }
       //Check if player is making a move
       
       else{
        validMoveArray = new ArrayList<Point>();
        lastClickedPoint = new Point();
        unitSelected=false;
       }
       
       //Check if King has been taken
       if(!enemyKingAlive(playerTurn)){
        endGame(playerTurn);
       }
      }
      /*else{//Actual game phase
       //Check if piece is clicked on, and piece belongs to the player
       Piece clickedPiece = board.getSquare(new Point(xSquare, ySquare)).getPiece();
       if(clickedPiece != null && clickedPiece.getOwner() == playerTurn && !alreadyMoved){
        validMoveArray = new ArrayList<Point>(board.getValidMoves(new Point(xSquare, ySquare)));
        lastClickedPoint = new Point(xSquare, ySquare);
        unitSelected = true;
       }
       //Check if player is making a move
       else if(unitSelected && validMoveArray.contains(new Point(xSquare, ySquare))){
        Piece lastClickedPiece = board.getSquare(lastClickedPoint).getPiece();
        Point endPoint = new Point(xSquare, ySquare);
        //If Ranged Attack
        if(Piece.isRanged[lastClickedPiece.getPieceNo()] &&
         board.getSquare(endPoint).getPiece() != null){
         takePiece(new Point(xSquare, ySquare), lastClickedPiece, true);
        }
        else{ //Movement
         takePiece(new Point(xSquare, ySquare), lastClickedPiece, false);
        }
        
   
        //Reset moves
        validMoveArray = new ArrayList<Point>();
        lastClickedPoint = new Point();
        unitSelected=false;
        alreadyMoved = true;
        //TODO
       }
       else{
        validMoveArray = new ArrayList<Point>();
        lastClickedPoint = new Point();
        unitSelected=false;
       }
       
       //Check if King has been taken
       if(!enemyKingAlive(playerTurn)){
        endGame(playerTurn);
       }
      }*/
      repaint();
     }
    }
   });
 
  }
  /**
  ** Overrode method that handles GUI updates
  **/
  @Override
  public void paintComponent(Graphics g) {
   super.paintComponent(g);
   //Draw white background
   g.setColor(Color.WHITE);
   g.fillRect(boardStartingX, boardStartingY, boardWidth, boardHeight);

   //Draw Terrain
   for(int i = 0; i < board.getLength(); i++){
    for(int j = 0; j < board.getWidth(); j++){
     Point temp = new Point(i, j);
     Square tempSquare = board.getSquare(temp);
     if(tempSquare.getModifier() != Square.EMPTY){
      g.setColor(terrainModColor[tempSquare.getModifier()]);
      g.fillRect(boardStartingX+(i*squareWidth), boardStartingY + (j*squareWidth), squareWidth, squareWidth);
     }
    } 
   }
   g.setColor(Color.BLACK);
   //Draw vertical columns
   for(int i = boardStartingX; i <= (boardStartingX+boardWidth); i+=(boardWidth/8)){
     g.drawLine(i,boardStartingY,i, (boardStartingY+boardHeight));
   }
   //Draw horizontal Rows
   for(int i = boardStartingY; i <= (boardStartingY+boardHeight); i+=(boardHeight/8)){
    if(i==(boardStartingY+(boardHeight/2))){
     g.setColor(Color.RED);
    }
    else{
     g.setColor(Color.BLACK);
    }
    g.drawLine(boardStartingX,i, (boardStartingX+boardWidth),i);
   }
   
   //Draw Available moves
   for(int i = 0; i < validMoveArray.size(); i++){
    Point tempP = validMoveArray.get(i);
    int radius = 3*squareWidth/4;
    g.setColor(Color.RED);
    g.drawOval(boardStartingX+tempP.x*squareWidth+(squareWidth/8), boardStartingY+tempP.y*squareWidth+(squareWidth/8), radius, radius);
   }
   
   //Check labels
   for(int i = 0; i < board.getLength(); i++){
    for(int j = 0; j < board.getWidth(); j++){
     Point temp = new Point(i, j);
     Square tempSquare = board.getSquare(temp);
     if(tempSquare.getPiece() != null){
      labelArray[temp.x][temp.y].setText(Piece.pieceName[tempSquare.getPiece().getPieceNo()] +""+tempSquare.getPiece().getOwner());
     }
     else{
      labelArray[temp.x][temp.y].setText("");
     }
    } 
   }
   
   //Cover bottom screen
   if(coverBottom){
    g.setColor(Color.BLACK);
    g.fillRect(boardStartingX, boardStartingY + (NUMROWSQUARES/2*squareWidth), 
     squareWidth*NUMROWSQUARES, squareWidth* NUMROWSQUARES/2);
    coverPlayer1Label(true);
   }
   
  }
 }
 /**
 ** Method to make Player 1's piece invisible
 ** @param covered, set visibility on or off
 **/
 private void coverPlayer1Label(boolean covered){
  for(int i = 0; i < board.getLength(); i++){
   for(int j = 4; j < board.getWidth(); j++){
    Point temp = new Point(i, j);
    labelArray[temp.x][temp.y].setVisible(!covered);
   } 
  }
 }
 /**
 ** Method that ends the game and gives the players an option to play again
 ** @param player Player that won.
 **/
 private void endGame(int player){
  //Show Message
  //Custom button text
  Object[] options = {"Play Again?", "Exit Game"};
  int n = JOptionPane.showOptionDialog(null,
   "Player " + player + " Wins!",
   "Game Over", //the name of the window
   JOptionPane.YES_NO_OPTION, //Type of options, those two buttons at bottom of box
   JOptionPane.PLAIN_MESSAGE, //The JOptionPane type
   null, //Icon to be applied to dialog
   options, //implements the values from the top
   options[0]); //The default selected component when dialog opens
  if(n == JOptionPane.YES_OPTION){ //Reset Board
   board = new Board();
   initVariables();
   phase = 0;  
  }
  else{//Exit Program
   System.exit(0);
  }
 }
 /**
 ** Method that checks if the enemy king is still alive
 ** @param player player checking for enemy king.
 ** @return Whether enemy king is alive 
 **/
 private boolean enemyKingAlive(int player){
  int enemyPlayer;
  if(player == 1){
   enemyPlayer = 2;
  }
  else{
   enemyPlayer = 1;
  }
  for(int i = 0; i < board.getLength(); i++){
   for(int j = 0; j < board.getWidth(); j++){
    Point temp = new Point(i, j);
    Square tempSquare = board.getSquare(temp);
    if(tempSquare.getPiece() != null){
     if(tempSquare.getPiece().getPieceNo() == Piece.KING && tempSquare.getPiece().getOwner() == enemyPlayer){
      return true;
     }
    }
   } 
  }
  return false;
 }
 /**
 ** Method that increments the Terrain modifier
 ** @param p point at which terrain is incremented
 ** @param player Owner of terrain
 **/
 private void incrementTerrain(Point p, int player){
  int modifier = board.getSquare(new Point(p.x,p.y)).getModifier();
  if(modifier == 4){
   modifier = 0;
  }
  else{
   modifier++;
  }
  board.setSquare(new Point(p.x,p.y), modifier, player);
 }
 /**
 ** Method that decrements the Terrain modifier
 ** @param p point at which terrain is incremented
 ** @param player Owner of terrain
 **/
 private void decrementTerrain(Point p, int player){
  int modifier = board.getSquare(p).getModifier();
  if(modifier == 0){
   modifier = 4;
  }
  else{
   modifier--;
  }
  board.setSquare(p, modifier, player);
 }
 /**
 ** Method that increments to
 ** @param p point at which piece is incremented
 ** @param player Owner of piece
 **/
 private void incrementPiece(Point p, int player){
  int pieceNo;
  if(board.getSquare(new Point(p.x,p.y)).getPiece() != null){
   pieceNo = board.getSquare(new Point(p.x,p.y)).getPiece().getPieceNo();
  }
  else{
   pieceNo = 0;
  }
  
  if(pieceNo==10){
   pieceNo = 0;
  }
  else{
   pieceNo++;
  }
  board.setPiece(p, pieceNo, player);
 }
 /**
 ** Method that increments to
 ** @param p point at which piece is incremented
 ** @param player Owner of piece
 **/
 private void decrementPiece(Point p, int player){
  int pieceNo;
  if(board.getSquare(new Point(p.x,p.y)).getPiece() != null){
   pieceNo = board.getSquare(new Point(p.x,p.y)).getPiece().getPieceNo();
  }
  else{
   pieceNo = 0;
  }
  
  if(pieceNo==0){
   pieceNo = 10;
  }
  else{
   pieceNo--;
  }
  board.setPiece(p, pieceNo, player);
 }
 //Main method that runs GUI
 public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameBoard().setVisible(true);
            }
        });
 }
 
 
}