package in.fssa.vanha.model;

public class Product extends ProductEntity {
	/**
	 * Compares this ProductEntity to another ProductEntity for ordering.
	 *
	 * This method compares two ProductEntity objects based on their ID values. If
	 * the other object is not an instance of ProductEntity, the comparison result
	 * is -1.
	 *
	 * @param o The ProductEntity to be compared.
	 * @return A negative integer, zero, or a positive integer as this ProductEntity
	 *         is less than, equal to, or greater than the specified ProductEntity.
	 */
	@Override
	public int compareTo(ProductEntity o) {
		// TODO Auto-generated method stub
		if (o instanceof Product) {
			Product otherProduct = (Product) o;
			return Integer.compare(this.getId(), otherProduct.getId());
		}
		return -1;
	}
}