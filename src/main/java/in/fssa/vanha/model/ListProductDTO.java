package in.fssa.vanha.model;

public class ListProductDTO {

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerLocation() {
		return sellerLocation;
	}

	public void setSellerLocation(String sellerLocation) {
		this.sellerLocation = sellerLocation;
	}

	public String getSellerImage() {
		return SellerImage;
	}

	public void setSellerImage(String SellerImage) {
		this.SellerImage = SellerImage;
	}

	@Override
	public String toString() {
		return "ListProductDTO [productId=" + productId + ", ProductName=" + ProductName + ", price=" + price
				+ ", asset=" + asset + ", sellerName=" + sellerName + ", sellerLocation=" + sellerLocation
				+ ", SellerImage=" + SellerImage + "]";
	}

	// Product
	String productId;
	String ProductImage;
	String ProductName;
	int price;

	// Asset
	String asset;

	// Seller
	String sellerName;
	String sellerLocation;
	String SellerImage;

}