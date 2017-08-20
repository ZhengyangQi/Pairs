
/* class of the Card
 * store the value of state of the cards
 */
class Card {
	//value on the card
	char value;
	//true means face up, false means face down
	boolean face;
	boolean matched = false;
	
	//constructor
	Card (char letter){
		value = letter;
		face = false;
		matched = false;
	}
	
	/*function to flip the side of the card
	 * 
	 */
	void flipSide(){
		if(face == false) {face = true; }
		if(face == true) {face = false; }
	}
	
	/*function to check whether two cards has the same value
	 *param toCompare is the Card to be compare with
	 *return whether the two are of same value 
	 */
	boolean isEqual(Card toCompare){
		if(value == toCompare.value){return true; }
		else{return false; }
	}
	
	/*function to get the value of the card
	 */
	char getValue(){
		return value;
	}
	
	/*function to get the state of the card
	 * 
	 */
	boolean getFace(){
		return face;
	}
	
}



/*class MathchCardGame
 *  construct the card game with an array 
 */

public class MatchCardGame {
	 //public field to store the Card object
	 public Card[] cards; 
	 //initiate the number of flips
	 int numberofFlip = 0;
	 //size of the game
	 public final int n;
	 //auxiliary fields
	 public static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	 public static java.util.ArrayList<Integer> inputnumber = new java.util.ArrayList<Integer>();
	 
	 
	 /*constructor
	  *param inputSize is the size of the game
	  */
	 public MatchCardGame (int inputSize ){
		 n = inputSize;
	     cards = new Card[n]; 
	     for(int i = 0; i <n; ++i){
	    	 cards[i] = new Card(alphabet[i/4]);
	     }	 
	 }
	 
	 /*function to converts the state of the board to an appropriate String representation
	  */
	 public String boardToString(){
		 // initiate string
		 String playCards = "";
		 int k = 0;
		 // fix number of rows
		for(int i = 0; i < n/4; ++i){
			//for loop to print all cards
			for(int j = 0; j < 4; ++j){
				if(cards[k].getFace() == true){
				 playCards = playCards + cards[k].getValue() +"(" +k+")" +"|";}
				if(cards[k].getFace() == false){
				playCards = playCards + "[]" +"(" +k+")" +"|";}
				 k++;
			 }
			 playCards = playCards + "\n";
		 }
		
		 return playCards;
	 }
	 
	 /*function to play card number i. If card i cannot be played because it’s face-up, 
	  * or if i is an invalid card number, then return false. If i is a card 
	  * number that can be played, play card i and return true.
	  */
	 public boolean flip(int i){
		 // check conditions 
		 if(i>=n){return false;}
		 else if(cards[i].getFace() == true){return false;}
		 else{ 
			 numberofFlip++;
			 cards[i].face = true;
			 inputnumber.add(i);
			 return true;}	 		 
	 }
	 
	 /* function to return true if the previous pair was a match and returns false 
	  * otherwise. This method should be called only after flip has been 
	  * called an even number of times and before flipMismatch is called.
	  */
	 public boolean wasMatch(){
		 // check the values of the last two cards
		 if(cards[inputnumber.get(inputnumber.size()-1)].isEqual(cards[inputnumber.get(inputnumber.size()-2)])){
			 cards[inputnumber.get(inputnumber.size()-1)].matched = true;
			 cards[inputnumber.get(inputnumber.size()-2)].matched = true;
			 return true;}
		 else{return false;}
	 }
	 
	 /*function to revert the a mismatched pair to face-down position. 
	  * This method should only be called after a 2 calls of flip results in a mismatch.
	  */
	 public void flipMismatch (){
		 cards[inputnumber.get(inputnumber.size()-1)].face = false;
		 cards[inputnumber.get(inputnumber.size()-2)].face = false;
	 }
	 
    /*function to return true if all cards have been matched and the game is 
     * over and returns false otherwise.
     */
	 public boolean gameOver(){
		 // initiate the a sentinel value
		 boolean flag = true;
		 //for loop to check each card
		 for(int i = 0; i <n; ++i){
			 if(cards[i].getFace() == false){
				 // update flag if find face-downed card
				 flag = false;
				 break;}
		 }
		 return flag;
	 }
	 
	 /* function to return the total number of card flips that have been performed so far.
	  */
	 public int getFlips (){
		 return numberofFlip;
	 }
	 
	 /* function to shuffle the cards using the Fisher-Yates shuffle.
	  */
	 public void shuffleCards (){
		 //for loop to perform Fisher-Yates shuffle
		 for(int i = n-1; i >=0; --i){
			java.util.Random generator = new java.util.Random(); 
			int j = generator.nextInt(i+1);
			 Card temp = cards[i];
			 cards[i] = cards[j];
			 cards[j] = temp;
		 } 
	 }
	 
	 /*function to return the size of the game
	  */
	 int getSize(){return n;}
	 
	 
	
}


