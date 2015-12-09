import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Arrays;

public class Caesar {

	public String text;
	
	
	//CONSTRUCTOR
	public Caesar(String s) {
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
	
	public static String solve(String inputText) throws IOException {
		int [] count = new int[26];
		
		char[] letArray = Caesar.letter();
		char[] expArray = Caesar.expandedArray();
		
		//COUNT FREQUENCY OF LETTERS
		for (int k=0; k<inputText.length(); k++) {
			for (int m=0; m<26; m++) {
				if (inputText.charAt(k) == letArray[m]) {
					count[m] = count[m] + 1;
					break;
				}
			}
					
		}
		
		//find which letter is representing e
		int isE = 0;
		for (int i=0; i<26; i++) {
			if (count[i] > 180) {
				isE = i;
				break;
			}
		}
		//System.out.println(isE);
		
		//turn the wheel
		String newText = "";
		int dif = 4-isE; //4 is the index of E (5th letter)
		for (int j=0; j<inputText.length(); j++) {
			char oldChar = inputText.charAt(j);
			if (Character.isLetter(oldChar)) {
				int indexOfOld = 0;
				
				for (int p=0; p<26; p++) {
					if (letArray[p]==oldChar) {
						indexOfOld = p;
						break;
					}
				}
				
				if (dif+indexOfOld >=0) {
					newText = newText + Character.toString(expArray[dif+indexOfOld]);
				}else {
					//System.out.println(dif+indexOfOld);
					newText = newText + Character.toString(expArray[dif+indexOfOld+26]);
				}
			
				
			}else{
				newText = newText + " ";
			}
				
			
			
			
		}
		
		FileWriter fw = new FileWriter("C:\\Users\\Susan\\OneDrive\\Documents\\CS 4830 - Cryptography\\vigenere_green_50.answer", false);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(newText);
		bw.close();
	
		
		
		System.out.println(newText);
		return newText;
	}
	
	
	
	
}
