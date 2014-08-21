import java.util.List;
import java.util.Collection;

public interface Thing42orNull<K, D> { 
	
	public void addPeer(Thing42orNull<K, ?> newPeer);

	public void appendToPool(Thing42orNull<?,?> newMember);

	public D getData();

	public K getKey();

	public long getLevel();

	public Thing42orNull<K, ?> getOnePeer(K key);

	public Collection<Thing42orNull<K, ?>> getPeersAsCollection();

	public Collection<Thing42orNull<K, ?>> getPeersAsCollection(K key);

	public List<Thing42orNull<?, ?>> getPoolAsList();

	public boolean removeFromPool(Thing42orNull<?, ?> member);

	public boolean removePeer(Thing42orNull<K, ?> peer);

	public void setData(D newData);
}
