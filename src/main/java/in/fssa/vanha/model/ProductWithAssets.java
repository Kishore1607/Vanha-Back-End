package in.fssa.vanha.model;

import java.util.Set;

public class ProductWithAssets {
	private Product product;
	private Set<Assets> assets;
//	private User user;

	public ProductWithAssets(Product product, Set<Assets> assets) {
		this.product = product;
		this.assets = assets;
//		this.user = user;
	}


	public Product getProduct() {
		return product;
	}

	public Set<Assets> getAssets() {
		return assets;
	}
	
//	public User getUser() {
//		return user;
//	}
}