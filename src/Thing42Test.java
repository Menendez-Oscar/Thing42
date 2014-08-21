import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class Thing42Test {

	private static String STRING_KEY = "key";
	private static String STRING_DATA = "value";
	private static long STRING_LEVEL = 42L;

	private static Integer INTEGER_KEY = new Integer(128);
	private static Integer INTEGER_DATA = new Integer(512);
	private static long INTEGER_LEVEL = 625799L;

	private static Boolean BOOL_KEY = new Boolean(false);
	private static Boolean BOOL_DATA = new Boolean(true);
	private static long BOOL_LEVEL = 2L;

	@Test
	public void testAddPeer() {
		Thing42orNull<String, String> stringThing = newStringThing();
		Thing42orNull<String, String> stringThing2 = newStringThing();
		stringThing.addPeer(stringThing2);
		Thing42orNull<String, ?> peer = (Thing42orNull<String, ?>) stringThing
				.getOnePeer(stringThing2.getKey());
		assertTrue(peer == stringThing2); // same object
	}

	@Test
	public void testAppendToPool() {
		Thing42orNull<String, String> stringThing = newStringThing();
		Thing42orNull<String, String> stringThing2 = newStringThing();

		stringThing.appendToPool(stringThing2);
		stringThing2.appendToPool(stringThing);

		assertTrue(stringThing.equals(stringThing2.getPoolAsList().get(0)));
		assertTrue(stringThing2.equals(stringThing.getPoolAsList().get(0)));

	}

	@Test
	public void testGetData() {
		Thing42orNull<String, String> stringThing = newStringThing();
		assertTrue(STRING_DATA.equals(stringThing.getData()));

		Thing42orNull<Integer, Integer> intThing = newIntThing();
		assertTrue(INTEGER_DATA.equals(intThing.getData()));

		assertFalse(INTEGER_DATA.equals(stringThing.getData()));

		Thing42orNull<Boolean, Boolean> boolThing = newBoolThing();
		assertTrue(BOOL_DATA.equals(boolThing.getData()));

	}

	@Test
	public void testGetKey() {
		Thing42orNull<String, String> stringThing = newStringThing();
		assertTrue(STRING_KEY.equals(stringThing.getKey()));

		Thing42orNull<Integer, Integer> intThing = newIntThing();
		assertTrue(INTEGER_KEY.equals(intThing.getKey()));

		assertFalse(INTEGER_KEY.equals(stringThing.getKey()));

		Thing42orNull<Boolean, Boolean> boolThing = newBoolThing();
		assertTrue(BOOL_KEY.equals(boolThing.getKey()));
	}

	@Test
	public void testGetLevel() {
		Thing42orNull<String, String> stringThing = newStringThing();
		assertTrue(STRING_LEVEL == stringThing.getLevel());

		Thing42orNull<Integer, Integer> intThing = newIntThing();
		assertTrue(INTEGER_LEVEL == intThing.getLevel());
	}

	@Test
	public void testGetOnePeer() {
		Thing42orNull<String, String> stringThing = newStringThing();
		Thing42orNull<String, String> stringThing2 = newStringThing();
		stringThing.addPeer(stringThing2);
		stringThing.addPeer(new Thing42<String, String>("", 0, ""));
		addArbitraryPeers(stringThing, 25);
		Thing42orNull<String, ?> peer = stringThing
				.getOnePeer(stringThing2.getKey());
		assertTrue(peer == stringThing2); // same object
	}

	@Test
	public void testGetPeersAsCollection() {
		Thing42orNull<String, String> stringThing = newStringThing();
		int count = 50;
		this.addArbitraryPeers(stringThing, count);

		assertTrue(stringThing.getPeersAsCollection().size() == count);
	}

	@Test
	public void testGetPeersAsCollectionGivenKey() {
		Thing42orNull<String, String> stringThing = newStringThing();
		int count = 50;
		int modForMatch = 5;
		String savedKey = "key " + modForMatch;
		for (int k = 0; k < count; k++) {
			if (k % modForMatch == 0) {
				stringThing.addPeer(
						new Thing42<String,String>(savedKey, modForMatch, "data "+modForMatch));
			} else {
				stringThing.addPeer(new Thing42<String,String>(""+k, k, "data "+k));
			}
		}
		Collection<Thing42orNull<String,?>> coll = stringThing.getPeersAsCollection(savedKey); 
		assertTrue(coll.size() == (count/modForMatch));
	}

	@Test
	public void testGetPoolAsList() {
		Thing42orNull<String, String> stringThing = newStringThing();
		int count = 20;
		for (int k = 0; k < count; k++) {
			stringThing.appendToPool(new Thing42<String, String>("key " + k,
					(long) k, "data " + k));
		}
		List<Thing42orNull<?, ?>> pool = stringThing.getPoolAsList();
		assertTrue(pool.size() == count);
		for (int k = 0; k < count; k++) {
			assertTrue(pool.get(k).getKey().equals("key " + k));
			assertTrue(pool.get(k).getData().equals("data " + k));
			assertTrue(pool.get(k).getLevel() == k);
		}

	}

	@Test
	public void testRemoveFromPool() {
		Thing42<String, String> stringThing = newStringThing();
		Thing42<String, String> stringThing2 = newStringThing();

		stringThing.appendToPool(stringThing2);

		assertTrue(stringThing2.equals(stringThing.getPoolAsList().get(0)));

		stringThing.removeFromPool(stringThing2);

		assertTrue(stringThing.getPoolAsList().size() == 0);
	}

	@Test
	public void testRemovePeer() {
		Thing42orNull<String, String> stringThing = newStringThing();
		Thing42orNull<String, String> stringThing2 = newStringThing();
		stringThing.addPeer(stringThing2);
		Thing42orNull<String, ?> peer = stringThing
				.getOnePeer(stringThing2.getKey());
		stringThing.removePeer(peer);
		assertTrue(stringThing.getPeersAsCollection().size() == 0);
	}

	@Test
	public void testSetData() {
		Thing42orNull<String, String> stringThing = newStringThing();
		assertTrue(STRING_DATA.equals(stringThing.getData()));
		String newData = "newData";
		stringThing.setData(newData);
		assertTrue(newData.equals(stringThing.getData()));

	}

	private Thing42<String, String> newStringThing() {
		return new Thing42<String, String>(STRING_KEY, STRING_LEVEL,
				STRING_DATA);
	}

	private Thing42<Integer, Integer> newIntThing() {
		return new Thing42<Integer, Integer>(INTEGER_KEY, INTEGER_LEVEL,
				INTEGER_DATA);
	}

	private Thing42<Boolean, Boolean> newBoolThing() {
		return new Thing42<Boolean, Boolean>(BOOL_KEY, BOOL_LEVEL, BOOL_DATA);
	}

	private void addArbitraryPeers(Thing42orNull<String, String> stringThing,
			int count) {
		for (int k = 0; k < count; k++) {
			Random rand = new Random();
			stringThing
					.addPeer(new Thing42<String, String>("key "
							+ rand.nextInt(), rand.nextLong(), "data "
							+ rand.nextInt()));
		}

	}

}
