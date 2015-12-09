import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Vten {

	public String text;
	
	public Vten(String s) {
		// TODO Auto-generated constructor stub
	
		this.text = s;
	}
	
	public static char[] letter(){
		char[] alphabet = new char[26];
		alphabet[0] = 'A'; alphabet[1] = 'B'; 
		alphabet[2] = 'C'; alphabet[3] = 'D';
		alphabet[4] = 'E'; alphabet[5] = 'F';
		alphabet[6] = 'G'; alphabet[7] = 'H';
		alphabet[8] = 'I'; alphabet[9] = 'J';
		alphabet[10] = 'K'; alphabet[11] = 'L';
		alphabet[12] = 'M'; alphabet[13] = 'N';
		alphabet[14] = 'O'; alphabet[15] = 'P';
		alphabet[16] = 'Q'; alphabet[17] = 'R';
		alphabet[18] = 'S'; alphabet[19] = 'T';
		alphabet[20] = 'U'; alphabet[21] = 'V';
		alphabet[22] = 'W'; alphabet[23] = 'X';
		alphabet[24] = 'Y'; alphabet[25] = 'Z';
		
		
		return alphabet;
	}
	
	
	public static char[] expandedArray(){
		char[] alphabet = new char[52];
		alphabet[0] = 'A'; alphabet[1] = 'B'; 
		alphabet[2] = 'C'; alphabet[3] = 'D';
		alphabet[4] = 'E'; alphabet[5] = 'F';
		alphabet[6] = 'G'; alphabet[7] = 'H';
		alphabet[8] = 'I'; alphabet[9] = 'J';
		alphabet[10] = 'K'; alphabet[11] = 'L';
		alphabet[12] = 'M'; alphabet[13] = 'N';
		alphabet[14] = 'O'; alphabet[15] = 'P';
		alphabet[16] = 'Q'; alphabet[17] = 'R';
		alphabet[18] = 'S'; alphabet[19] = 'T';
		alphabet[20] = 'U'; alphabet[21] = 'V';
		alphabet[22] = 'W'; alphabet[23] = 'X';
		alphabet[24] = 'Y'; alphabet[25] = 'Z';
		
		alphabet[26] = 'A'; alphabet[27] = 'B'; 
		alphabet[28] = 'C'; alphabet[29] = 'D';
		alphabet[30] = 'E'; alphabet[31] = 'F';
		alphabet[32] = 'G'; alphabet[33] = 'H';
		alphabet[34] = 'I'; alphabet[35] = 'J';
		alphabet[36] = 'K'; alphabet[37] = 'L';
		alphabet[38] = 'M'; alphabet[39] = 'N';
		alphabet[40] = 'O'; alphabet[41] = 'P';
		alphabet[42] = 'Q'; alphabet[43] = 'R';
		alphabet[44] = 'S'; alphabet[45] = 'T';
		alphabet[46] = 'U'; alphabet[47] = 'V';
		alphabet[48] = 'W'; alphabet[49] = 'X';
		alphabet[50] = 'Y'; alphabet[51] = 'Z';
		
		return alphabet;
	}
	
	
	
	
	//Separate String s into 10 buckets
	public static void buckets(String inputText) throws IOException {
		
		char[] letArray = Vten.letter();
		char[] expArray = Vten.expandedArray();
		
		int [] [] letters = new int [10] [26]; 
		
		int countLet = 0;
		int countSpaces = 0;
		int [] spaces = new int[inputText.length()/2];//contains indices of spaces in original text
		
		for (int i = 0; i<inputText.length(); i++) {
			if (Character.isLetter(inputText.charAt(i))) {
				
				int bucketInd = countLet%10;
				for (int j = 0; j<26; j++){
					if (inputText.charAt(i) == letArray[j]){
					
						letters[bucketInd][j] = letters[bucketInd][j]+1; break;	
					}	
				} 
				countLet++;
				
			}else {
				spaces[countSpaces] = i;
				
				countSpaces++;
			}
			
			
			
				
		}
		
		int[] maxArray = new int[10];//index of "e" for each row
		
		//find "e" for each bucket/row
		for (int k=0; k<10; k++) {//row by row
			//int maxInd = k;
			int highest = 0; //highest occurrence letter
			int indE = -1;   //index of highest occurrence letter
			for (int l=0; l<26; l++) {
				if (letters[k][l] > highest) {
					highest = letters[k][l];
					indE = l;
				}
				
			}
			maxArray[k] = indE;
		}
		
		System.out.println("");
		for (int aa=0; aa<10; aa++){
			System.out.print(maxArray[aa]);
			System.out.print(" ");
		}
		System.out.println("");
		
//		maxArray[0] = 11;
//		maxArray[1] = 20;
//		maxArray[2] = 20;
//		maxArray[3] = 19;
//		maxArray[4] = 24;
		maxArray[5] = 13;
//		maxArray[6] = 17;
//		maxArray[7] = 12;
		maxArray[8] = 21;
//		maxArray[9] = 4;
		
		
		int[] turnHowMuch = new int [10];
		//turn the 10 wheels
		for (int m=0; m<10; m++){
			int dif = 4-maxArray[m];//this is how much you turn the wheel
			turnHowMuch[m] = dif;
		}
		
		String newText = "";
		String numberText = "";
		int letIndex = 0;
		for (int p=0; p<inputText.length(); p++) {
			char oldChar = inputText.charAt(p);
			if (Character.isLetter(oldChar)) {
				
				int indexOfOld = 0;
				for (int q=0; q<26; q++) {
					if (letArray[q]==oldChar) {
						indexOfOld = q;
						break;
					}
				}//okay, now you found the old letter, how much to turn by? next
				int turnBy = turnHowMuch[letIndex%10];
				if (turnBy+indexOfOld >=0) {
					//System.out.println(turnBy+indexOfOld);
					newText = newText + Character.toString(expArray[turnBy+indexOfOld]);
				}else {
					newText = newText + Character.toString(expArray[turnBy+indexOfOld+26]);
				}
				numberText = numberText + letIndex%10;
				letIndex++;
				
			}else {
				
				newText = newText + " ";
				numberText = numberText + " ";
			}
			
			
		}
		
		boolean allesGut = false;
		if (allesGut == true) {
			FileWriter fw = new FileWriter("C:\\Users\\Susan\\OneDrive\\Documents\\CS 4830 - Cryptography\\vigenere_blue_50.answer", false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(newText);
			bw.close();
		}
		

		
		
		
	System.out.println(newText);
	System.out.println(numberText);
	System.out.println("");
	for (int y=0; y<10; y++) {
		System.out.print("Row" + y + ": ");
		for (int z=0; z<26; z++) {
			System.out.print(letters[y][z]);
			System.out.print("  ");
		}
		System.out.println("");
	}
	
		
		
	}//end of function buckets
	
	

}
