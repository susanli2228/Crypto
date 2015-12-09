import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Object;
import java.util.HashMap;
import java.util.HashSet;

public class LetterExchange {

	public String text;
	
	
	
	/**creates a string that you work upon*/
	public LetterExchange(String s) {
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
	
	
	
	public static String findFreq(String inputText) {
		int [] count = new int[26];
		
		char[] letArray = LetterExchange.letter();
		HashMap<String, Integer> threeLet = new HashMap<String, Integer>();
		HashMap<String, Integer> oneLet = new HashMap<String, Integer>();
		int countTo3 = 0;
		String let1 = ""; String let2 = ""; String let3 = "";
		String word = "";
		
		//FINDS 3-LETTER WORDS
		for (int k=0; k<inputText.length(); k++) {
			for (int m=0; m<26; m++) {
				if (inputText.charAt(k) == letArray[m]) {
					count[m] = count[m] + 1;
					break;
				}
			}
			
			if (countTo3==3 && !Character.isLetter(inputText.charAt(k))) {
				//three letters follow by space = word
				word = let3 + let2 + let1;
				if (!threeLet.containsKey(word)){
					threeLet.put(word, 1);
				}else {
					threeLet.put(word, threeLet.get(word)+1);
				}
				countTo3 = 0;
			}
			
			else if (Character.isLetter(inputText.charAt(k))){
				countTo3++;
				if (countTo3 <= 3) {
					let3 = let2; let2 = let1; let1 = Character.toString(inputText.charAt(k));
				}
				
			}else if (countTo3 == 1) {//reaching a space after one character (1-let word)
				if (!oneLet.containsKey(let1)){
					oneLet.put(let1, 1);
				}else {
					oneLet.put(let1, oneLet.get(let1)+1);
				}
				countTo3 = 0;
				
			}else {
				countTo3 = 0;
			}
			
			
		}
		
		//System.out.print(threeLet.entrySet());
		System.out.println("");
	
		
		//DISPLAYS COMMON LETTERS
		char thisE = ' ';
		for (int i=0; i<26; i++) {
			if (count[i] > 90) {
				System.out.print(count[i]);
				System.out.print('-');
				System.out.print(letArray[i]);
				System.out.print("  ");
				if (count[i] > 180) {
					thisE = letArray[i];
					inputText = inputText.replace(thisE, 'e');//e!!!!
					System.out.print(letArray[i] + "is e");
				}
			}
			
		}
		System.out.println("");
		System.out.print("[");
		
		for (String s: oneLet.keySet()) {
				
			System.out.print(s);
			System.out.print("=");
			System.out.print(oneLet.get(s));
			System.out.print(", ");
			
		}
		System.out.print("]");
		
		
		//DISPLAYS COMMON 3-LETTER WORDS
		//FINDS TWO MOST-COMMON
		int highestNum = 0;
		int secondNum = 0;
		System.out.print("[");
		for (String s: threeLet.keySet()) {
			if (threeLet.get(s)>=3){
				if (threeLet.get(s) > secondNum){
					secondNum = threeLet.get(s);
					if (threeLet.get(s) > highestNum) {
						secondNum = highestNum; highestNum = threeLet.get(s);
					}
				}
				
				System.out.print(s);
				System.out.print("=");
				System.out.print(threeLet.get(s));
				System.out.print(", ");
			}
		}
		System.out.print("]");
		System.out.println("");
		
		for (String ss: threeLet.keySet()) {
			if (threeLet.get(ss) == highestNum || threeLet.get(ss) == secondNum) {
				if (ss.charAt(2) == thisE) {//the or she
					inputText = inputText.replace(ss.charAt(0), 't');//t
					System.out.print("  " + ss.charAt(0) + " is t;;");
					inputText = inputText.replace(ss.charAt(1), 'h');//h
					System.out.print("  " + ss.charAt(1) + " is h;;");
				}else {//word is "and"
					inputText = inputText.replace(ss.charAt(0), 'a');//a
					System.out.print("  " + ss.charAt(0) + " is a;;");
					inputText = inputText.replace(ss.charAt(1), 'n');//n
					System.out.print("  " + ss.charAt(1) + " is n;;");
					inputText = inputText.replace(ss.charAt(2), 'd');//d
					System.out.print("  " + ss.charAt(2) + " is d;;");
				}
			}
		}
		
		System.out.println("");
		
		System.out.print(count[0]); System.out.print(" ");
		System.out.print(count[1]); System.out.print(" ");
		System.out.print(count[2]); System.out.print(" ");
		System.out.print(count[3]); System.out.print(" ");
		System.out.print(count[4]); System.out.print(" ");
		System.out.print(count[5]); System.out.print(" ");
		System.out.print(count[6]); System.out.print(" ");
		System.out.print(count[7]); System.out.print(" ");
		System.out.print(count[8]); System.out.print(" ");
		System.out.print(count[9]); System.out.print(" ");
		System.out.print(count[10]); System.out.print(" ");
		System.out.print(count[11]); System.out.print(" ");
		System.out.print(count[12]); System.out.print(" ");
		System.out.print(count[13]); System.out.print(" ");
		System.out.print(count[14]); System.out.print(" ");
		System.out.print(count[15]); System.out.print(" ");
		System.out.print(count[16]); System.out.print(" ");
		System.out.print(count[17]); System.out.print(" ");
		System.out.print(count[18]); System.out.print(" ");
		System.out.print(count[19]); System.out.print(" ");
		System.out.print(count[20]); System.out.print(" ");
		System.out.print(count[21]); System.out.print(" ");
		System.out.print(count[22]); System.out.print(" ");
		System.out.print(count[23]); System.out.print(" ");
		System.out.print(count[24]); System.out.print(" ");
		System.out.print(count[25]); System.out.print(" ");
		
		
		return inputText;
	}
	
	
	public static void Replacer(String inputText) throws IOException {
		
		//System.out.println(inputText);
//		inputText = inputText.replace('D', 'a');//good
//		inputText = inputText.replace('Y', 'n');//good
//		inputText = inputText.replace('H', 'd');//good
//		inputText = inputText.replace('K', 't');//nogood
//		inputText = inputText.replace('G', 'h');//good
//		inputText = inputText.replace('M', 'e');//good
		inputText = inputText.replace('P', 'i');//7
		inputText = inputText.replace('S', 's');//8
		inputText = inputText.replace('H', 'r');//9
		inputText = inputText.replace('A', 'w');//10
		inputText = inputText.replace('J', 'o');//11
		inputText = inputText.replace('U', 'y');//12
		inputText = inputText.replace('M', 'k');//13
		inputText = inputText.replace('X', 'm');//14
		inputText = inputText.replace('Z', 'l');//15
		inputText = inputText.replace('O', 'f');//16
		inputText = inputText.replace('N', 'p');//17
		inputText = inputText.replace('Y', 'u');//18
		inputText = inputText.replace('V', 'c');//19
		inputText = inputText.replace('G', 'g');//20
		inputText = inputText.replace('D', 'v');//21
		inputText = inputText.replace('C', 'j');//22
		inputText = inputText.replace('E', 'b');//23
		inputText = inputText.replace('K', 'q');//24
		inputText = inputText.replace('W', 'x');//25
		inputText = inputText.replace('F', 'z');//26
		
		System.out.println("");
		System.out.println(inputText);
		
		//System.out.println("hi");
		
		inputText = inputText.replace('a', 'A');//1
		inputText = inputText.replace('b', 'B');//2
		inputText = inputText.replace('c', 'C');//3
		inputText = inputText.replace('d', 'D');//4
		inputText = inputText.replace('e', 'E');//5
		inputText = inputText.replace('f', 'F');//6
		inputText = inputText.replace('g', 'G');//7
		inputText = inputText.replace('h', 'H');//8
		inputText = inputText.replace('i', 'I');//9
		inputText = inputText.replace('j', 'J');//10
		inputText = inputText.replace('k', 'K');//11
		inputText = inputText.replace('l', 'L');//12
		inputText = inputText.replace('m', 'M');//13
		inputText = inputText.replace('n', 'N');//14
		inputText = inputText.replace('o', 'O');//15
		inputText = inputText.replace('p', 'P');//16
		inputText = inputText.replace('q', 'Q');//17
		inputText = inputText.replace('r', 'R');//18
		inputText = inputText.replace('s', 'S');//19
		inputText = inputText.replace('t', 'T');//20
		inputText = inputText.replace('u', 'U');//21
		inputText = inputText.replace('v', 'V');//22
		inputText = inputText.replace('w', 'W');//23
		inputText = inputText.replace('x', 'X');//24
		inputText = inputText.replace('y', 'Y');//25
		inputText = inputText.replace('z', 'Z');//26
		
		System.out.println(inputText);
		
		boolean allesGut = false;
		if (allesGut == true) {
			FileWriter fw = new FileWriter("C:\\Users\\Susan\\OneDrive\\Documents\\CS 4830 - Cryptography\\substitution_50.answer", false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(inputText);
			bw.close();
		}
		
		return;
	}
	

}
