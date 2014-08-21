/**
 * A generic pair data structure
 * @author guy
 *
 * @param <K> the key
 * @param <D> the data
 */
public abstract class Pair<K, D> {

	private D data;
	private final K key;

	protected Pair(K keyIn, D dataIn) {
		key = keyIn;
		data = dataIn;
	}
	/**
	 * Gets the data
	 * @return the data
	 */
	public D getData() {
		return this.data;
	}
	/**
	 * Gets the key
	 * @return the key
	 */
	public K getKey() {
		return this.key;
	}
	/**
	 * Sets the data
	 * @param newData the new data
	 */
	public void setData(D newData) {
		this.data = newData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair<?,?> other = (Pair<?,?>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

}