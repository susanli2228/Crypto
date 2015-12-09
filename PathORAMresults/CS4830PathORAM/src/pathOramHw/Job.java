/*
 *  You can use/modify this class to test your ORAM (for correctness, not security).
 *  
 *  You can experiment modifying this class, but we will not take it into account (we will test your ORAM implementations on this as well as other Jobs)
 *  
 */

package pathOramHw;

import java.util.ArrayList;
import java.util.Arrays;

import pathOramHw.ORAMInterface.Operation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.Arrays;

import pathOramHw.ORAMInterface.Operation;

public class Job {

	public static void main(String[] args) throws IOException {
		/*int bucket_size = 4;
		int num_blocks = (int) Math.pow(2, 5);*/
		int bucket_size = 2;
		int num_blocks = (int) Math.pow(2, 20);
		
		//Set the Bucket size for all the buckets.
		Bucket.setMaxSize(bucket_size);
				
		//Initialize new UntrustedStorage
		UntrustedStorageInterface storage = new ServerStorageForHW();

		//Initialize a randomness generator
		RandForORAMInterface rand_gen = new RandomForORAMHW();
		
		//Initialize a new Oram
		ORAMInterface oram = new ORAMWithDeterministicRLEviction(storage, rand_gen, bucket_size, num_blocks);
		
		

		/*Do same sample computation: fill an array with numbers, then read it back.*/
//		for(int i = 0; i < 60; i++){
//			oram.access(Operation.WRITE, i % num_blocks, sampleData(i) );
//			System.out.println("Writing Block " + i + " to ORAM. The stash size is: " + oram.getStashSize());
//		}
//		
//		for(int i = 0; i < num_blocks; i++){
//			System.out.println("Reading Block " + i + " from ORAM. Value is :" + Arrays.toString(oram.access(Operation.READ, i, null)));
//		}
		
		int[] info = new int[100];
		
		for(int i = 0; i < 3000000; i++){
			oram.access(Operation.WRITE, i % num_blocks, sampleData(i) );
			System.out.println("Writing Block " + i + " to ORAM. The stash size is: " + oram.getStashSize());
		}
		
		for(int i = 0; i < 500000; i++){
			oram.access(Operation.WRITE, i % num_blocks, sampleData(i) );
			System.out.println("Reading Block " + i + " from ORAM. Stash size :" + oram.getStashSize());
			info[oram.getStashSize()] = info[oram.getStashSize()] + 1;
		}
		
		FileWriter fw = new FileWriter("C:\\Users\\Susan\\OneDrive\\Documents\\CS 4830 - Cryptography\\A2with220.txt", true);
		PrintWriter pw = new PrintWriter(fw);
		pw.write("-1, 500000\n\n\n");
		//pw.flush();
		pw.write("\n");
		
		int [] newinfo = new int[100];
		newinfo[99] = 0;
		for (int i=98; i>= 0; i--){	
			newinfo[i] = info[i+1] + newinfo[i+1];	
			
		}
		for (int i=0; i<100; i++){
			pw.write("      " + i + ", " + newinfo[i] + "\n");
//			
		}
		
		pw.close();	
		
		for (int i=0; i<100; i++){
			System.out.println(info[i]);
		}
	}
	
	private static byte[] sampleData(int i){
		ByteBuffer bb = ByteBuffer.allocate(24); 
		bb.putInt(i); 
		return bb.array();
	}
	
}
