package pathOramHw;

import java.util.ArrayList;

import pathOramHw.ORAMInterface.Operation;

/*
 * Name: Susan Li	
 * NetID: szl5
 */

public class ORAMWithDeterministicRLEviction implements ORAMInterface{

	/**
	 * TODO add necessary variables 
	 */
	
	private int bucket_size;
	private int num_blocks;
	private UntrustedStorageInterface stor;
	private RandForORAMInterface rando;
	private ArrayList<Block> stash = new ArrayList<Block>();
	private Integer[] positionmap = new Integer[0];
	private int G=0;
	private boolean init = false;
		
	public ORAMWithDeterministicRLEviction(UntrustedStorageInterface storage, RandForORAMInterface rand_gen, int bucket_size, int num_blocks){
		// TODO Complete the constructor
		
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
	public Integer[] getPositionMap(){
		// TODO Must complete this method for submission
		return this.positionmap;
	}


	@Override
	public byte[] access(Operation op, int blockIndex, byte[] newdata) {
		// TODO Must complete this method for submission
		if (!this.init) {
			this.init = true;
			this.G = 0;
		}
		
		int posblock = positionmap[blockIndex];
		positionmap[blockIndex] = rando.getRandomLeaf();
		
		for (int i=0; i<=getNumLevels(); i++) {
			Bucket b = stor.ReadBucket(P(posblock, i));
			
			Block a = b.getBlockByKey(blockIndex);
			if (a != null) {
				stash.add(a);
				b.removeBlock(a);
				
			}
			stor.WriteBucket(P(posblock, i), b); //in or outside if loop?
			
		}
		
		byte[] data = new byte[24];
		Block theblock = new Block();
		
		int stashsize = stash.size();
		for (int j=0; j<stashsize; j++) {
			if (stash.get(j).index == blockIndex) {
				data = stash.get(j).data;
				theblock = stash.get(j);
				break;
					
			}
		}
		if (op == Operation.WRITE) {
			stash.remove(theblock);
			stash.add(new Block(positionmap[blockIndex], blockIndex, newdata));
		}
		
		//DO TWICE
		int twice = 0;
		while (twice<2) {
			int g = ReverseBits(this.G % (int)Math.pow(2, getNumLevels()), getNumLevels());
			this.G = this.G+1;
			//ArrayList<Block> tempstash = new ArrayList<Block>(0);
			for (int k=0; k<=getNumLevels(); k++ ){
				ArrayList<Block> readblocks = stor.ReadBucket(P(g, k)).getBlocks();
				for (Block b: readblocks) {
					if (b.leaf_id > -1) {
						stash.add(b);
					}
				}
				 
			}
			
			for (int l = getNumLevels(); l>=0; l--) {
				ArrayList<Block> tempstash = new ArrayList<Block>(0);
				for (int m=0; m< stash.size(); m++) {
					if (P(g, l)==P(stash.get(m).leaf_id, l)) {
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
				stor.WriteBucket(P(g, l), newbucket);
			}
			twice++;
			
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
		return (int) Math.pow(2,  getNumLevels());
	}

	@Override
	public int getNumLevels() {
		// TODO Must complete this method for submission
		return (int) Math.ceil(Math.log(getNumBlocks())/Math.log(2));
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
	
	public int getGlobalG() {
		// TODO Must complete this method for submission
		return this.G;
	}
	
	public int ReverseBits(int G, int bits_length)
	{
		// TODO Must complete this method for submission
		if (G<2) { //G equals 1 or 0, same in base two as base ten, same reversed
			return G;
		}
		int acc = 0;
		int place = 0;
		while (G >= 1) { //let's turn this binary
			int temp = (G%2);
			acc = acc + (int) Math.pow(10, place)*temp;
			place ++;
			G = G/2;
		}
		
		//System.out.println(acc);
		int reversed = 0;
		for (int rb = 0; rb<bits_length; rb++) { // now reverse the binary number
			int newacc = acc % 10; 
			//System.out.println(newacc);
			reversed = (int) (reversed + newacc*Math.pow(10, bits_length-rb-1));
			//System.out.println(reversed);
			acc = acc/10;
		}
		//System.out.println(reversed);
		
		int finalacc = 0;
		int finalplace = 0;
		for (int c=0; c<bits_length; c++){
			int digit = reversed % 10;
			if (digit ==1 ){
				finalacc = finalacc + (int)Math.pow(2, finalplace);
			}
			finalplace ++;
			reversed = reversed/10;
		}
		 
		return finalacc;
	}

	//You may add helper methods.
	
}
