import java.util.List;
import java.util.Collection;

public interface Thing42orNull<K, D> {

	public void addPeer(Thing42orNull<K, D> newPeer)
			throws NullPointerException;

	public void appendToPool(Thing42orNull<K, D> newMember)
			throws NullPointerException;

	boolean equals(Object obj);

	public D getData();

	public K getKey();

	public long getLevel();

	public Thing42orNull<K, D> getOnePeer(K key);

	public Collection<Thing42orNull<K, D>> getPeersAsCollection();

	public Collection<Thing42orNull<K, D>> getPeersAsCollection(K key);

	public List<Thing42orNull<K, D>> getPoolAsList();

	public int hashCode();

	public boolean removeFromPool(Thing42orNull<K, D> member)
			throws NullPointerException;

	public boolean removePeer(Thing42orNull<K, D> peer)
			throws NullPointerException;

	public void setData(D newData);
}
