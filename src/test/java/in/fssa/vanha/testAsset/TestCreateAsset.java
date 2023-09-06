package in.fssa.vanha.testAsset;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.service.AssetsService;

public class TestCreateAsset {
	@Test
	public void testCreateValidateWithNullInput() {
		Set<Assets> assetsArray = new HashSet<>();

		for (int i = 0; i < 4; i++) {
			Assets asset = new Assets();
			asset.setValue(null);
			assetsArray.add(asset);
		}
		assertThrows(ValidationException.class, () -> {
			AssetsService assetsService = new AssetsService();
			assetsService.create((Set<Assets>) assetsArray);
		});
	}

	@Test
	public void testCreateValidateWithEmptyInput() {
		Set<Assets> assetsArray = new HashSet<>();

		for (int i = 0; i < 4; i++) {
			Assets asset = new Assets();
			asset.setValue("");
			assetsArray.add(asset);
		}
		assertThrows(ValidationException.class, () -> {
			AssetsService assetsService = new AssetsService();
			assetsService.create((Set<Assets>) assetsArray);
		});
	}

	@Test
	public void testCreateValidateWithValidInput() {
		Set<Assets> assetsArray = new HashSet<>();

		for (int i = 0; i < 4; i++) {
			Assets asset = new Assets();
			asset.setValue("https://source.unsplash.com/featured/?motorcycle");
			assetsArray.add(asset);
		}
		assertDoesNotThrow(() -> {
			AssetsService assetsService = new AssetsService();
			assetsService.create((Set<Assets>) assetsArray);
		});
	}

	@Test
	public void testCreateValidateWithInvalidLength() {
		Set<Assets> assetsArray = new HashSet<>();

		for (int i = 0; i < 4; i++) {
			Assets asset = new Assets();
			asset.setValue(
					"https://source.unspljncuanlhcnailnvadknvkmnvkadvamnvadkvkankvnoiamvidmioash.com/featured/?motorcycle");
			assetsArray.add(asset);
		}

		assertThrows(ValidationException.class, () -> {
			AssetsService assetsService = new AssetsService();
			assetsService.create((Set<Assets>) assetsArray);
		});
	}

	@Test
	public void testCreateValidateWithInvalidInput() {
		Set<Assets> assetsArray = new HashSet<>();

		for (int i = 0; i < 4; i++) {
			Assets asset = new Assets();
			asset.setValue("Invalid.URL");
			assetsArray.add(asset);
		}

		assertThrows(ValidationException.class, () -> {
			AssetsService assetsService = new AssetsService();
			assetsService.create((Set<Assets>) assetsArray);
		});
	}
}
