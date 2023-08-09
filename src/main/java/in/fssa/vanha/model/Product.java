package in.fssa.vanha.model;

public class Product extends ProductEntity{
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