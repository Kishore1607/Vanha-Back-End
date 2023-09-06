package in.fssa.vanha.model;

import java.util.Set;

public class ProductDetailDTO {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getUsedPeriod() {
		return usedPeriod;
	}

	public void setUsedPeriod(int usedPeriod) {
		this.usedPeriod = usedPeriod;
	}

	public String getUsedDuration() {
		return usedDuration;
	}

	public void setUsedDuration(String usedDuration) {
		this.usedDuration = usedDuration;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerImage() {
		return sellerImage;
	}

	public void setSellerImage(String sellerImage) {
		this.sellerImage = sellerImage;
	}

	public String getSellerLocation() {
		return SellerLocation;
	}

	public void setSellerLocation(String sellerLocation) {
		SellerLocation = sellerLocation;
	}

	public Set<Assets> getAssets() {
		return assets;
	}

	public void setAssets(Set<Assets> assets) {
		this.assets = assets;
	}

	public Set<BidDTO> getBids() {
		return bids;
	}

	public void setBids(Set<BidDTO> bids) {
		this.bids = bids;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ProductDetailDTO {\n");
		sb.append("  id=").append(id).append(",\n");
		sb.append("  productId='").append(productId).append("',\n");
		sb.append("  ProductName='").append(productName).append("',\n");
		sb.append("  description='").append(description).append("',\n");
		sb.append("  price=").append(price).append(",\n");
		sb.append("  usedPeriod=").append(usedPeriod).append(",\n");
		sb.append("  usedDuration='").append(usedDuration).append("',\n");
		sb.append("  category='").append(category).append("',\n");
		sb.append("  minPrice=").append(minPrice).append(",\n");
		sb.append("  sellerName='").append(sellerName).append("',\n");
		sb.append("  sellerImage='").append(sellerImage).append("',\n");
		sb.append("  SellerLocation='").append(SellerLocation).append("',\n");

		// Loop through assets
		sb.append("  assets=[\n");
		for (AssetsEntity asset : assets) {
			sb.append("    AssetsEntity {\n");
			sb.append("      id=").append(asset.getId()).append(",\n");
			sb.append("      value='").append(asset.getValue()).append("'\n");
			sb.append("    },\n");
		}
		sb.append("  ],\n");

		// Loop through bids
		sb.append("  bids=[\n");
		for (BidDTO bid : bids) {
			sb.append("    BidDTO {\n");
			sb.append("      id=").append(bid.getId()).append(",\n");
			sb.append("      amount=").append(bid.getAmount()).append(",\n");
			sb.append("      buyerName='").append(bid.getBuyerName()).append("',\n");
			sb.append("      buyerImage='").append(bid.getBuyerImage()).append("'\n");
			sb.append("    },\n");
		}
		sb.append("  ]\n");

		sb.append("}");
		return sb.toString();
	}

	// Product
	int id;
	String productId;
	String productName;
	String description;
	int price;
	int usedPeriod;
	String usedDuration;
	String category;
	int minPrice;
       
	// Seller
	String sellerName;
	String sellerImage;
	String SellerLocation;

	// Asset
	Set<Assets> assets;

	// Bid
	Set<BidDTO> bids;
}
