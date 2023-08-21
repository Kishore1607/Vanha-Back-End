package in.fssa.vanha.model;

/**
 * 
 * @author KishoreSugumar
 *
 */
public class Assets extends AssetsEntity {

	@Override
	public int compareTo(AssetsEntity o) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (o instanceof Assets) {
			Assets otherAssets = (Assets) o;
			return Integer.compare(this.getId(), otherAssets.getId());
		}
		return -1;
	}

}
