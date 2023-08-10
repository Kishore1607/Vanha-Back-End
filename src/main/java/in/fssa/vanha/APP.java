package in.fssa.vanha;

import in.fssa.vanha.model.*;
import in.fssa.vanha.service.*;

public class APP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
//			User newUser = new User();
//            newUser.setName("John Doe");
//            newUser.setEmail("john@example.com");
//            newUser.setPassword("J1o2h3n#");
//            newUser.setNumber(8974657380l);
//            newUser.setLocation("America");
//
//            UserService userService = new UserService();
//
//            userService.create(newUser);

//            System.out.println("New user created successfully!");
			
			BidHistory bid = new BidHistory();
			bid.setBidAmount(10000000);
			bid.setBuyerUnique("Nataliya Dyer");
			bid.setProductUnique("P12345");			

            BidHistoryService bidService = new BidHistoryService();
            
            bidService.create(bid);
			
            
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error creating user: " + e.getMessage());
		}
	}

}
