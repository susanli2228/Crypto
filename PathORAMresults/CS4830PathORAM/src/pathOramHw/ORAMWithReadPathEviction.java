package pathOramHw;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Name: Susan LI
 * NetID: szl5
 */

public class ORAMWithReadPathEviction implements ORAMInterface{
	
	/**
	 * TODO add necessary variables 
	 */
	private int bucket_size;
	private int num_blocks;
	private UntrustedStorageInterface stor;
	private RandForORAMInterface rando;
	private ArrayList<Block> stash = new ArrayList<Block>();
	private Integer[] positionmap = new Integer[0];
	
	public ORAMWithReadPathEviction(UntrustedStorageInterface storage, RandForORAMInterface rand_gen, int bucket_size, int num_blocks){
		// TODO complete the constructor
		this.bucket_size = bucket_size;
		this.num_blocks = num_blocks;
		this.stor = storage;
		this.rando = rand_gen;
		this.positionmap = new Integer[num_blocks];
		rando.setBound(getNumLeaves());
		for (int i=0; i<num_blocks; i++) {
			positionmap[i] = rando.getRandomLeaf();
		}
		stor.setCapacity(getNumBuckets());
		for (int x=0; x< getNumBuckets(); x++) {
			stor.WriteBucket(x, new Bucket());
		}
	}


	@Override
	public byte[] access(Operation op, int blockIndex, byte[] newdata) {
		// TODO Must complete this method for submission
		int posblock = positionmap[blockIndex]; //1
		positionmap[blockIndex] = rando.getRandomLeaf();     //2
		
		for (int i=0; i<=getNumLevels(); i++) {
			ArrayList<Block> allblocks = stor.ReadBucket(P(posblock,i)).getBlocks();
			for (Block b :allblocks) {
				if (b.leaf_id > -1) {
					//System.out.println("hi");
					stash.add(b);
					//System.out.println(((Integer)stash.size()).toString());
				}
			}
		}
		
		byte[] data = new byte[24];
		
		int stashsize = stash.size();
		//System.out.format("stashsize is %d", stashsize);
		Block theblock = new Block();
		for (int j=0; j<stashsize; j++) {
			if (stash.get(j).index == blockIndex) {
				data = stash.get(j).data;
				theblock = stash.get(j);
//				System.out.println(Arrays.toString(data));	
//				System.out.print("data");
			}
		}
		
		if (op == Operation.WRITE){
			
			stash.remove(theblock);
			stash.add(new Block(positionmap[blockIndex], blockIndex, newdata));
//			System.out.println(Arrays.toString(newdata));
//			System.out.print("newdata");
		}
		
		for (int k = getNumLevels(); k>=0; k--) {
			ArrayList<Block> tempstash = new ArrayList<Block>(0);
			for (int m=0; m< stash.size(); m++) {
				if (P(posblock, k)==P(stash.get(m).leaf_id, k)) {
					tempstash.add(stash.get(m));
				}
			}
			int selnum = Math.min(tempstash.size(),bucket_size);
			//ArrayList<Block> newtempstash = new ArrayList<Block>(0);
			Bucket newbucket = new Bucket();
			for (int n=0; n< selnum; n++) {
				//newtempstash.add(tempstash.get(n));
				stash.remove(tempstash.get(n));
				newbucket.addBlock(tempstash.get(n));
			}	
			stor.WriteBucket(P(posblock, k), newbucket);
		}
		return data;
	}


	@Override
	public int P(int leaf, int level) {
		// TODO Must complete this method for submission
		int curlevel = this.getNumLevels();
		int curindex = leaf + (this.getNumLeaves() - 1); //leaf + 2^L -1
		while (level < curlevel) {
			curindex = (curindex-1)/2;
			curlevel--;
		}
		return curindex;
		
		
	}


	@Override
	public Integer[] getPositionMap() {
		// TODO Must complete this method for submission
		return positionmap;
	}


	@Override
	public ArrayList<Block> getStash() {
		// TODO Must complete this method for submission
		return stash;
	}


	@Override
	public int getStashSize() {
		// TODO Must complete this method for submission
		return stash.size();
	}

	@Override
	public int getNumLeaves() {
		// TODO Must complete this method for submission
		return (int) Math.pow(2, getNumLevels());
	}


	@Override
	public int getNumLevels() {
		// TODO Must complete this method for submission
		return (int) Math.ceil((Math.log(getNumBlocks())/Math.log(2)));
	}


	@Override
	public int getNumBlocks() {
		// TODO Must complete this method for submission
		return this.num_blocks;
	}


	@Override
	public int getNumBuckets() {
		// TODO Must complete this method for submission
		return (int) Math.pow(2, getNumLevels()+1)-1;
	}

	//You may add helper methods.
	
}
