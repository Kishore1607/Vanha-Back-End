import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import in.fssa.vanha.order.TestAsset.TestCreateAsset;
import in.fssa.vanha.order.TestAsset.TestFindAssets;
import in.fssa.vanha.order.TestAsset.TestUpdateAsset;
import in.fssa.vanha.order.TestBidHistory.TestCreateBid;
import in.fssa.vanha.order.TestBidHistory.TestFindAllBid;
import in.fssa.vanha.order.TestProduct.TestCreateProduct;
import in.fssa.vanha.order.TestProduct.TestDeleteProduct;
import in.fssa.vanha.order.TestProduct.TestFindAllProductByCategory;
import in.fssa.vanha.order.TestProduct.TestFindBySellerId;
import in.fssa.vanha.order.TestProduct.TestUpdateProduct;
import in.fssa.vanha.order.TestUser.TestCreateUser;
import in.fssa.vanha.order.TestUser.TestUpdateUser;

@RunWith(JUnitPlatform.class)
@SelectClasses({
    TestCreateUser.class,
    TestUpdateUser.class,
    TestCreateProduct.class,
    TestUpdateProduct.class,
    TestUpdateAsset.class,
    TestFindAssets.class,
    TestDeleteProduct.class,
    TestCreateAsset.class,
    TestCreateBid.class,
    TestFindAllBid.class,
    TestFindAllProductByCategory.class,
    TestFindBySellerId.class,
})
public class TestSuite {
}