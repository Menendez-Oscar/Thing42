import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Thing42 does stuff.
 * 
 * @author Guy Grigsby et al
 * @see Thing42orNull
 * @param <K>
 *            thing k
 * @param <D>
 *            thing d
 */
public class Thing42<K, D> implements Thing42orNull<K, D> {

	public static final String NPE_MESSAGE = "Argument cannot be null";
	private D data;
	private final K key;
	private final long level;
	private ArrayList<Thing42orNull<?, ?>> pool;
	private Map<K, LinkedList<Thing42orNull<K, ?>>> peers;

	/**
	 * Default constructor
	 * 
	 * @param keyIn
	 * @param levelIn
	 * @param dataIn
	 */
	public Thing42(K keyIn, long levelIn, D dataIn) {
		this.key = keyIn;
		this.data = dataIn;
		this.level = levelIn;
		this.pool = new ArrayList<Thing42orNull<?, ?>>();
		this.peers = new HashMap<K, LinkedList<Thing42orNull<K, ?>>>();
	}

	/**
	 * Gets the data
	 * 
	 * @return the data
	 */
	public D getData() {
		return this.data;
	}

	/**
	 * Gets the key
	 * 
	 * @return the key
	 */
	public K getKey() {
		return this.key;
	}

	/**
	 * Sets the data
	 * 
	 * @param newData
	 *            the new data
	 */
	public void setData(D newData) {
		this.data = newData;
	}

	/**
	 * Adds peer to {@link Thing42orNull} list. If
	 * 
	 * @param newPeer
	 */
	public void addPeer(Thing42orNull<K, ?> newPeer) {
		nullCheck(newPeer);
		K key = newPeer.getKey();
		if (this.peers.get(key) == null) {
			this.peers.put(key, new LinkedList<Thing42orNull<K, ?>>());
		}
		this.peers.get(key).addFirst(newPeer);
	}

	/**
	 * Appends new {@link Thing42orNull} to member pool.
	 * 
	 * @param newMember
	 */
	public void appendToPool(Thing42orNull<?, ?> newMember) {
		nullCheck(newMember);
		this.pool.add(newMember);
	}

	/**
	 * Gets level
	 * 
	 * @return level
	 */
	public long getLevel() {
		return this.level;
	}

	/**
	 * Gets {@link Thing42orNull} peer by key
	 * 
	 * @see Thing42orNull
	 * @param key
	 * @return {@link Thing42orNull} or null if not found
	 */
	public Thing42orNull<K, ?> getOnePeer(K key) {
		Thing42orNull<K, ?> one = null;
		List<Thing42orNull<K, ?>> matchingKeys = this.peers.get(key);
		if (matchingKeys != null && matchingKeys.size() > 0) {
			one = matchingKeys.get(0);
		}
		return one;
	}

	/**
	 * Gets peer or peers as {@link java.util.Collection}
	 * 
	 * @return {@link java.util.Collection} of {@link Open Declaration
	 *         java.util.LinkedList.LinkedList} containing all peer
	 *         {@link Thing42orNull}
	 */
	public Collection<Thing42orNull<K, ?>> getPeersAsCollection() {
		List<Thing42orNull<K, ?>> retValue = new LinkedList<Thing42orNull<K, ?>>();
		Collection<LinkedList<Thing42orNull<K, ?>>> lists = this.peers.values();
		for (Collection<Thing42orNull<K, ?>> list : lists) {
			retValue.addAll(list);
		}

		return retValue;
	}

	/**
	 * Gets peer or peers as {@link java.util.Collection} that matches the key.
	 * 
	 * @param key
	 * @return {@link java.util.Collection} of {@link Open Declaration
	 *         java.util.LinkedList.LinkedList} containing all peer
	 *         {@link Thing42orNull} that match the key.
	 */
	public Collection<Thing42orNull<K, ?>> getPeersAsCollection(Object key) {
		Collection<Thing42orNull<K, ?>> mpeers = this.peers.get(key);
		return mpeers == null ? new LinkedList<Thing42orNull<K, ?>>() : mpeers;
	}

	/**
	 * 
	 * @return
	 */
	public List<Thing42orNull<?, ?>> getPoolAsList() {
		return this.pool;
	}

	/**
	 * Removes {@link Thing42orNull} from pool
	 * 
	 * @param member
	 *            {@link Thing42orNull} to be removed
	 * @return {@code true} if found and removed, otherwise {@code false}
	 */
	public boolean removeFromPool(Thing42orNull<?, ?> member) {
		nullCheck(member);
		return this.pool.remove(member);
	}

	/**
	 * Removes {@link Thing42orNull} peer
	 * 
	 * @param peer
	 *            {@link Thing42orNull} to be removed.
	 * @return {@code true} if found and removed, otherwise {@code false}
	 */
	public boolean removePeer(Thing42orNull<K, ?> peer) {
		nullCheck(peer);
		return this.peers.get(peer.getKey()).remove(peer);
	}

	/**
	 * Checks for {@code null}
	 * 
	 * @param obj
	 *            the {@code Object} to check if {@code null}
	 * @throws NullPointerException
	 *             if the parameter is {@code null}
	 */
	private void nullCheck(Object obj) throws NullPointerException {
		if (obj == null) {
			throw new NullPointerException(NPE_MESSAGE);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (level ^ (level >>> 32));
		result = prime * result + ((peers == null) ? 0 : peers.hashCode());
		result = prime * result + ((pool == null) ? 0 : pool.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Thing42<?, ?> other = (Thing42<?, ?>) obj;
		if (level != other.level)
			return false;
		if (peers == null) {
			if (other.peers != null)
				return false;
		} else if (!peers.equals(other.peers))
			return false;
		if (pool == null) {
			if (other.pool != null)
				return false;
		} else if (!pool.equals(other.pool))
			return false;
		return true;
	}

}
