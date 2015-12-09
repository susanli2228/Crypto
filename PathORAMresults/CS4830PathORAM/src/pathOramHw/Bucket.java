package pathOramHw;

import java.util.ArrayList;

import javax.management.RuntimeErrorException;

/*
 * Name: Susan Li	
 * NetID: szl5
 */

public class Bucket{
	private static boolean is_init = false;
	private static int max_size_Z = -1;
	public ArrayList<Block> realblocks = new ArrayList<Block>(0);
	public ArrayList<Block> dummyblocks = new ArrayList<Block>(0);
	
	
	//TODO Add necessary variables
	
	Bucket(){
		if(is_init == false)
		{
			throw new RuntimeException("Please set bucket size before creating a bucket");
		}
		else {
			//Block newdummy = new Block(-1, -1, new byte[24]);
			for (int i=0; i<max_size_Z; i++) {
				
				dummyblocks.add(new Block());
			}
			
		}
		//TODO Must complete this method for submission
	}
	
	// Copy constructor
	Bucket(Bucket other)
	{
		if(other == null)
		{
			throw new RuntimeException("the other bucket is not malloced.");
		}
		
		this.is_init = other.is_init;
		this.max_size_Z = other.max_size_Z;
		this.realblocks = other.realblocks;
		this.dummyblocks = other.dummyblocks;
		//TODO Must complete this method for submission
	}
	
	//Implement and add your own methods.
	Block getBlockByKey(int key){
		// TODO Must complete this method for submission
		realblocks.trimToSize();
		for (int i = 0; i< realblocks.size(); i++) {
			if (realblocks.get(i).index == key) {
				return realblocks.get(i);
			}
		} return null;
	}
	
	boolean addBlock(Block new_blk){ //CHECK TO SEE IF YOU CAN MAKE SIG BOOLEAN from void (MUST STASH NO ROOM)
		// TODO Must complete this method for submission
		if (realblocks.size() < max_size_Z) {
			realblocks.add(new_blk);
			
			Block newdummy = new Block(-1, -1, new byte[24]);
			dummyblocks.remove(newdummy);
			return true;
		}
		return false;
		
	}
	
	boolean removeBlock(Block rm_blk)
	{
		// TODO Must complete this method for submission
		if (realblocks.contains(rm_blk)) {
			realblocks.remove(rm_blk);
			
			Block newdummy = new Block(-1, -1, new byte[24]);
			dummyblocks.add(newdummy);
			return true;
		}
		
		
		return false;
	}
	
	
	ArrayList<Block> getBlocks(){
		// TODO Must complete this method for submission
		// This should return an ArrayList of all blocks that have been added to the Bucket, both dummy and non dummy.
		ArrayList<Block> allblocks = realblocks;
		allblocks.addAll(dummyblocks);
		
		return allblocks;
	}
	
	

	static void setMaxSize(int maximumSize)
	{
		if(is_init == true)
		{
			throw new RuntimeException("Max Bucket Size was already set");
		}
		max_size_Z = maximumSize;
		is_init = true;
	}
	
	//Your implementation SHOULD NOT call this method
	static void resetState()
	{
		is_init = false;
	}

}