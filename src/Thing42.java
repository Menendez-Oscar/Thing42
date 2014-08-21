import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Thing42<K, D> extends Pair<K, D> implements Thing42orNull<K, D> {
	
	public static final String NPE_MESSAGE = "Argument cannot be null";

	private final long level;

	private LinkedList<Thing42orNull<?,?>> pool;
	
	private Map<K, List<Thing42orNull<K,?>>> peers;
	
	public Thing42(K keyIn, long levelIn, D dataIn) {
		super(keyIn, dataIn);
		this.level = levelIn;
		this.pool = new LinkedList<Thing42orNull<?,?>>();
		this.peers = new HashMap<K, List<Thing42orNull<K,?>>>();
	}

	@Override
	public void addPeer(Thing42orNull<K, ?> newPeer) {
		nullCheck(newPeer);
		K key = newPeer.getKey();
		if (this.peers.get(key) == null) {
			this.peers.put(key, new LinkedList<Thing42orNull<K,?>>());
		}
		this.peers.get(key).add(newPeer);
	}

	@Override
	public void appendToPool(Thing42orNull<?,?> newMember) {
		nullCheck(newMember);
		this.pool.addLast(newMember);
	}

	@Override
	public long getLevel() {
		return this.level;
	}

	@Override
	public Thing42orNull<K, ?> getOnePeer(K key) {
		Thing42orNull<K, ?> one = null;
		List<Thing42orNull<K,?>> matchingKeys = this.peers.get(key);
		if (matchingKeys != null && matchingKeys.size() > 0) {
			one = matchingKeys.get(0);
		}
		return one;
	}

	@Override
	public Collection<Thing42orNull<K, ?>> getPeersAsCollection() {
		List<Thing42orNull<K,?>> retValue = new LinkedList<Thing42orNull<K,?>>();
		Collection<List<Thing42orNull<K, ?>>> lists = this.peers.values();
		for (Collection<Thing42orNull<K,?>> list : lists) {
			retValue.addAll(list);
		}

		return retValue;
	}

	@Override
	public Collection<Thing42orNull<K, ?>> getPeersAsCollection(Object key) {
		Collection<Thing42orNull<K, ?>> mpeers = this.peers.get(key);
		return mpeers == null? new LinkedList<Thing42orNull<K,?>>() : mpeers;
	}

	@Override
	public List<Thing42orNull<?, ?>> getPoolAsList() {
		return this.pool;
	}

	@Override
	public boolean removeFromPool(Thing42orNull<?, ?> member) {
		nullCheck(member);
		return this.pool.remove(member);
	}

	@Override
	public boolean removePeer(Thing42orNull<K, ?> peer) {
		nullCheck(peer);
		return this.peers.get(peer.getKey()).remove(peer);
	}

	/**
	 * Checks for {@code null}
	 * @param obj the {@code Object} to check if {@code null}
	 * @throws NullPointerException if the parameter is {@code null}
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
		Thing42<?,?> other = (Thing42<?,?>) obj;
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
