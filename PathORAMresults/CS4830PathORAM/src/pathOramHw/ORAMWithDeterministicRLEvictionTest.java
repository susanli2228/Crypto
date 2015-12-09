package pathOramHw;

import static org.junit.Assert.*;

import org.junit.Test;

public class ORAMWithDeterministicRLEvictionTest {

	@Test
	public void test() {
		int bucket_size = 4;
		int num_blocks = (int) Math.pow(2, 5);
		
		//Set the Bucket size for all the buckets.
		Bucket.setMaxSize(bucket_size);
				
		//Initialize new UntrustedStorage
		UntrustedStorageInterface storage = new ServerStorageForHW();

		//Initialize a randomness generator
		RandForORAMInterface rand_gen = new RandomForORAMHW();
		
		//Initialize a new Oram
		ORAMWithDeterministicRLEviction oram = new ORAMWithDeterministicRLEviction(storage, rand_gen, bucket_size, num_blocks);
		System.out.println(oram.ReverseBits(110,8));
		
	}

}
