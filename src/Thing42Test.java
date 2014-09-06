import static org.junit.Assert.*;

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
		Thing42orNull<String, String> peer = stringThing
				.getOnePeer(stringThing2.getKey());
		assertTrue(peer == stringThing2); // same object
		try {
			stringThing.addPeer(null);
			fail(Thing42.NPE_MESSAGE);
		} catch (NullPointerException e) {

		}
	}

	@Test
	public void testAppendToPool() {
		Thing42orNull<String, String> stringThing = newStringThing();
		Thing42orNull<String, String> stringThing2 = newStringThing();

		stringThing.appendToPool(stringThing2);
		stringThing2.appendToPool(stringThing);

		assertTrue(stringThing.equals(stringThing2.getPoolAsList().get(0)));
		assertTrue(stringThing2.equals(stringThing.getPoolAsList().get(0)));
		try {
			stringThing.addPeer(null);
			fail(Thing42.NPE_MESSAGE);
		} catch (NullPointerException e) {

		}

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
		Thing42orNull<String, String> peer = stringThing
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
		Collection<Thing42orNull<String,String>> coll = stringThing.getPeersAsCollection(savedKey); 
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
		List<Thing42orNull<String, String>> pool = stringThing.getPoolAsList();
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
		try {
			stringThing.addPeer(null);
			fail(Thing42.NPE_MESSAGE);
		} catch (NullPointerException e) {

		}
	}

	@Test
	public void testRemovePeer() {
		Thing42orNull<String, String> stringThing = newStringThing();
		Thing42orNull<String, String> stringThing2 = newStringThing();
		stringThing.addPeer(stringThing2);
		Thing42orNull<String, String> peer = stringThing
				.getOnePeer(stringThing2.getKey());
		stringThing.removePeer(peer);
		assertTrue(stringThing.getPeersAsCollection().size() == 0);
		try {
			stringThing.addPeer(null);
			fail(Thing42.NPE_MESSAGE);
		} catch (NullPointerException e) {

		}
	}

	@Test
	public void testSetData() {
		Thing42orNull<String, String> stringThing = newStringThing();
		assertTrue(STRING_DATA.equals(stringThing.getData()));
		String newData = "newData";
		stringThing.setData(newData);
		assertTrue(newData.equals(stringThing.getData()));

	}

    @Test
    public void testHashCodeAndEquals() {
        Thing42orNull<String, String> stringThing = newStringThing();
        Thing42orNull<String, String> stringThing2 = newStringThing();
        assertEquals("Hashcodes match", stringThing.hashCode(), stringThing.hashCode());
        assertTrue("Object equals self", stringThing.equals(stringThing));
        assertFalse("Object does not equal another object", stringThing.equals(stringThing2));
    }

	/**
	* returns a new {@code Thing42<String, String>} with 
	* the following attributes
	* <ul>
	*	<li>{@code STRING_KEY}</li>
	* 	<li>{@code STRING_LEVEL}</li>
	*	<li>{@code STRING_DATA}</li>
	* </ul>
	* @return a new {@code Thing42<String, String>}
	*/
	private Thing42<String, String> newStringThing() {
		return new Thing42<String, String>(STRING_KEY, STRING_LEVEL,
				STRING_DATA);
	}
	/**
	* returns a new {@code Thing42<Integer, Integer>} with 
	* the following attributes
	* <ul>
	*	<li>{@code INTEGER_KEY}</li>
	* 	<li>{@code INTEGER_LEVEL}</li>
	*	<li>{@code INTEGER_DATA}</li>
	* </ul>
	* @return a new {@code Thing42<Integer, Integer>}
	*/
	private Thing42<Integer, Integer> newIntThing() {
		return new Thing42<Integer, Integer>(INTEGER_KEY, INTEGER_LEVEL,
				INTEGER_DATA);
	}
	/**
	* returns a new {@code Thing42<Boolean, Boolean>} with 
	* the following attributes
	* <ul>
	*	<li>{@code BOOL_KEY}</li>
	* 	<li>{@code BOOL_LEVEL}</li>
	*	<li>{@code BOOL_DATA}</li>
	* </ul>
	* @return a new {@code Thing42<Boolean, Boolean>}
	*/
	private Thing42<Boolean, Boolean> newBoolThing() {
		return new Thing42<Boolean, Boolean>(BOOL_KEY, BOOL_LEVEL, BOOL_DATA);
	}
	/**
	* Adds {@code count} number of peers with arbitrary attributes
	* of type {@code Thing42<String, String>}
	* @param stringThing the {@code Thing42} to add the peers to
	* @param count the number of arbitrary peers to add
	*/
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
