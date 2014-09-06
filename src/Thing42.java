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
	/**
	 * Generic mutable attribute data
	 */
	private D data;
	/**
	 * Generic immutable generic key
	 */
	private final K key;
	/**
	 * ordered collection of Thing42 objects pool.
	 */
	private final long level;
	/**
	 * ordered collection of Thing42 objects pool.
	 */
	private ArrayList<Thing42orNull<K, D>> pool;
	/**
	 * unordered collection of Thing42 objects peers
	 */
	private Map<K, LinkedList<Thing42orNull<K, D>>> peers;

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
		this.pool = new ArrayList<Thing42orNull<K, D>>();
		this.peers = new HashMap<K, LinkedList<Thing42orNull<K, D>>>();
	}

	/**
	 * Access the data of this Thing42.
	 * 
	 * @return The data of this object
	 */
	@Override
	public D getData() {
		return this.data;
	}

	/**
	 * Access the key of this Thing42.
	 * 
	 * @return the key of this object
	 */
	@Override
	public K getKey() {
		return this.key;
	}

	/**
	 * Modify the data of this Thing42.
	 * 
	 * @param newData
	 *            the updated data for this object
	 */
	@Override
	public void setData(D newData) {
		this.data = newData;
	}

	/**
	 * Add a peer to this object.
	 * 
	 * @param newPeer
	 *            Thing42orNull object to be added to peers set
	 * @throws NullPointerException
	 *             - if the specified peer is null
	 */
	@Override
	public void addPeer(Thing42orNull<K, D> newPeer) {
		nullCheck(newPeer);
		K key = newPeer.getKey();
		if (this.peers.get(key) == null) {
			this.peers.put(key, new LinkedList<Thing42orNull<K, D>>());
		}
		this.peers.get(key).addFirst(newPeer);
	}

	/**
	 * Append a member to the pool of this object.
	 * 
	 * @param newMember
	 *            Thing42 object to be appended to pool list
	 * @throws NullPointerException
	 *             - if the specified item is null
	 */
	@Override
	public void appendToPool(Thing42orNull<K, D> newMember) {
		nullCheck(newMember);
		this.pool.add(newMember);
	}

	/**
	 * Access the level of this Thing42.
	 * 
	 * @return the level of this object
	 */
	@Override
	public long getLevel() {
		return this.level;
	}

	/**
	 * Access a peer matching the specified key.
	 * 
	 * @param key
	 *            the search key
	 * @return any peer known by this object that matches the given key; null if
	 *         no match
	 */
	@Override
	public Thing42orNull<K, D> getOnePeer(K key) {
		Thing42orNull<K, D> one = null;
		List<Thing42orNull<K, D>> matchingKeys = this.peers.get(key);
		if (matchingKeys != null && matchingKeys.size() > 0) {
			one = matchingKeys.get(0);
		}
		return one;
	}

	/**
	 * Access all peers.
	 * 
	 * @return all peers known by this object; if no peers then returns a
	 *         collection with size() == 0.
	 */
	@Override
	public Collection<Thing42orNull<K, D>> getPeersAsCollection() {
		List<Thing42orNull<K, D>> retValue = new LinkedList<Thing42orNull<K, D>>();
		Collection<LinkedList<Thing42orNull<K, D>>> lists = this.peers.values();
		for (Collection<Thing42orNull<K, D>> list : lists) {
			retValue.addAll(list);
		}

		return retValue;
	}

	/**
	 * Access all peers matching the specified key.
	 * 
	 * @param key
	 *            the search key
	 * @return all peers known by this object that match the given key; if no
	 *         peer matches then returns a collection with size() == 0.
	 */
	@Override
	public Collection<Thing42orNull<K, D>> getPeersAsCollection(Object key) {
		Collection<Thing42orNull<K, D>> mpeers = this.peers.get(key);
		return mpeers == null ? new LinkedList<Thing42orNull<K, D>>() : mpeers;
	}

	/**
	 * Access all members of the pool.
	 * 
	 * @return all members of the pool known by this object; if no members then
	 *         returns a list with size() == 0.
	 */
	@Override
	public List<Thing42orNull<K, D>> getPoolAsList() {
		return this.pool;
	}

	/**
	 * Remove a single instance of the specified object from this object's pool.
	 * 
	 * @param member
	 *            the member to be removed from the pool
	 * @return true if a pool member was removed as a result of this call
	 * @throws NullPointerException
	 *             - if the specified parameter is null
	 */
	@Override
	public boolean removeFromPool(Thing42orNull<K, D> member) {
		nullCheck(member);
		return this.pool.remove(member);
	}

	/**
	 * Remove a single instance of the specified peer from this object.
	 * 
	 * @param peer
	 *            - the peer to be removed
	 * @return true if a peer was removed as a result of this call
	 * @throws NullPointerException
	 *             - if the specified peer is null
	 */

	@Override
	public boolean removePeer(Thing42orNull<K, D> peer) {
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
