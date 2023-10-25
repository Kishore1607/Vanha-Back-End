package in.fssa.vanha.model;

public class Assets extends AssetsEntity {

	/**
	 * Compares this Assets object to another AssetsEntity for ordering.
	 * 
	 * @param o The AssetsEntity to compare to this Assets object.
	 * @return a negative integer, zero, or a positive integer if this object is
	 *         less than, equal to, or greater than the specified object.
	 * @throws ClassCastException if the specified object is not an instance of
	 *                            Assets.
	 */
	@Override
	public int compareTo(AssetsEntity o) {
		if (o instanceof Assets) {
			Assets otherAssets = (Assets) o;
			return Integer.compare(this.getId(), otherAssets.getId());
		}
		return -1;
	}

}
