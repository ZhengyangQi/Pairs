/* hw2 
 Name: Zhengyang Qi
 SID: 204296544
 Date: 01/28/2017
 */

import java.util.*;
public class PlayCard {
	
	/*function to mimic a AI randomly flipping the card
	 *param g is the MatchCardGame that involves
	 */
	public static int playRandom (MatchCardGame g){	
		 //get the game size
	      int size = g.getSize();    
		while(!g.gameOver()) {
			  //the first random generator
		      java.util.Random generator1 = new java.util.Random(); 
              //flip the first valid card
		      while(!g.flip(generator1.nextInt(size))) {}
			  //the second random generator
		      java.util.Random generator2 = new java.util.Random(); 
              //flip the second valid card
		      while(!g.flip(generator2.nextInt(size))) {}
		      //check match or not, if not flip back
		      if(!g.wasMatch()) {g.flipMismatch();} 
		      
		    }
		//return the number of flips
		return g.getFlips();
	}
	
	/*function to generate one random number in give interval
	 *param a is the ArrayList to be compares
	 *size is the rane of the random number
	 *return a random number that satisfies the requirements
	 */
   static int generateNew(ArrayList<Integer> a, int size){
	   //initiate a sentinel value
		boolean flag = true;
		//initiate random number
		int randomNumber = 0;
		//while loop to go generate the number
		while(flag){
			int k = 0;
			//random number
			Random generator = new java.util.Random();
			randomNumber =  generator.nextInt(size);
			//go through the ArrayList
			for(int i = 0; i < a.size(); ++i){
				if(a.get(i) == randomNumber){break;}
				++k;
			}
			if(k==a.size()){flag = false;}
		}
		return randomNumber;
	}
    
   /*function to mimic a perfect memory AI
    * param g is the MatchCardGame that involves
    */
    public static int playGood(MatchCardGame g){
    	int size = g.getSize();
    	//ArrayList to record all the input values
    	ArrayList<Integer> record = new ArrayList<Integer>();
		while(!g.gameOver()) {
			//condition 1, the record is empty
			if(record.size()==0){
				//generate to different random number and flip corresponding card
				Random generator1 = new java.util.Random();
				int randomNumber1 =  generator1.nextInt(size);
				record.add(randomNumber1);
				int randomNumber2 = generateNew(record,size);
				g.flip(randomNumber1);
				g.flip(randomNumber2);
				record.add(randomNumber2);
				//if same, keep them, if not, flip back
				if(g.cards[randomNumber1].isEqual(g.cards[randomNumber2])){
					g.cards[randomNumber1].matched = true;
					g.cards[randomNumber2].matched = true;
					}
				else{
					g.flipMismatch();
				}	
				

			}
			//condition 2 if the record is not empty
			else{
				//generate a new random number and flip the card
				int n1 = generateNew(record,size);
				g.flip(n1);
				record.add(n1);
				boolean flag = false;
				//check value with the record
				for(int i = 0; i< record.size()-1;++i){
					//if find correspondence, flip the card
					if((g.cards[record.get(i)].isEqual(g.cards[n1])) && (g.cards[record.get(i)].matched == false) ){
						g.flip(record.get(i));
						g.cards[record.get(i)].matched = true;
						g.cards[n1].matched = true;
						flag = true;
						break;
					}
				}
				// if not, flip a new random card
				if(flag == false){
					int n2 = generateNew(record,size);
					g.flip(n2);
					record.add(n2);
					//check the new card with the previous card
					if(g.cards[n1].isEqual(g.cards[n2])){
						g.cards[n1].matched =true;
						g.cards[n2].matched =true;
					}
					//check the new card with record
					else{
						g.flipMismatch();
						for(int i = 0; i < record.size()-1; ++i){
							if(g.cards[record.get(i)].isEqual(g.cards[n2]) && g.cards[record.get(i)].matched == false){
								g.flip(record.get(i));
								g.flip(n2);
								g.cards[record.get(i)].matched = true;
								g.cards[n2].matched = true;
								break;
							}
						}
											
					}
	
				}

			}

		}
		//return number of flips
		return g.getFlips();
    }
    
 
	 /*plays shuffled MatchCardGames of size 32 a total of N times using playRandom method. The method
	  *returns the average number of flips to complete the games.
      */
    public static double randomMC(int N){
    	//initial values
    	double  totalflip = 0;
    	int timeofplay = 32;
    	//generate the game
    	MatchCardGame g = new MatchCardGame(timeofplay);
    	//play game N times
    	for(int i = 0; i < N; ++i){
    		g.shuffleCards();
    		totalflip += playRandom(g);
    	}	
    		double averageflip = totalflip/(double)N;
    		//return average flips
    		return averageflip;	
    }
	
    /*plays shuffled MatchCardGames of size 32 a total of N times using playGood method. The method
	 *returns the average number of flips to complete the games.
     */
    public static double goodMC(int N){
    	//initial values
    	double  totalflip = 0;
    	int timeofplay = 32;
    	//generate the game
    	MatchCardGame g = new MatchCardGame(timeofplay);
    	//play game N times
    	for(int i = 0; i < N; ++i){
    		g.shuffleCards();
    		totalflip += playGood(g);
    	}
    		double averageflip = totalflip/(double)N;
    		//return average flips
    		return averageflip;
    }
    
    
		
  public static void main(String[] args) {
      
    //set up reader to take inputs
    java.util.Scanner reader = new java.util.Scanner (System.in);
    
    int n = 16; //game size
  
  
   MatchCardGame g1 = new MatchCardGame(n);
    g1.shuffleCards();
  /*
    while(!g1.gameOver()) {
      //print board status
      System.out.println(g1.boardToString());
      
      //ask for a card to flip until we get a valid one
      System.out.println("Which card to play?");
      while(!g1.flip(reader.nextInt())) {}
      
      //print board status
      System.out.println(g1.boardToString());
      
      //ask for a card to flip until we get a valid one
      while(!g1.flip(reader.nextInt())) {}
      
      //say whether the 2 cards were a match
      if(g1.wasMatch()) {
        System.out.println("Was a match!");
      } else {
        //print board to show mismatched cards
        System.out.println(g1.boardToString());		
        System.out.println("Was not a match.");
        //flip back the mismatched cards
        g1.flipMismatch();
      }
    }
 
    //Report the score
      System.out.println("The game took " + g1.getFlips() + " flips.");
 */
   
    //Using the AIs
     int count;
     MatchCardGame g2 = new MatchCardGame(n);
     g2.shuffleCards();
     count = playRandom(g2);
     System.out.println("The bad AI took " + count + " flips.");
      
     MatchCardGame g3 = new MatchCardGame(n);
     g3.shuffleCards();
     count = playGood(g3);
     System.out.println("The good AI took " + count + " flips.");
    
    //Using MCs
    int N = 1000;
    System.out.println("The bad AI took " + randomMC(N) + " flips on average.");
    System.out.println("The good AI took " + goodMC(N) + " flips on average.");
  }
}
