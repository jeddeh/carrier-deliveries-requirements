package info.jedda.carrierdeliveries.entity;

import java.util.ArrayList;

...having an application singleton or class with static methods avoids the need to serialize
 and deserialize objects between activities in Android.

/**
 * Holds the current Carrier Run and exposes the Delivery instances for the Carrier Run to
 * the entire project.
 */
public final class CarrierDeliveries {
	private static String carrierRun;
	private static String distributorId;
	private static ArrayList<Delivery> deliveries;

	private CarrierDeliveries() {
	}

	public static String getCarrierRun() {
		return carrierRun;
	}

	public static void setCarrierRun(String carrierRun) {
		CarrierDeliveries.carrierRun = carrierRun;
	}

	public static String getDistributorId() {
		return distributorId;
	}

	public static void setDistributorId(String distributorId) {
		CarrierDeliveries.distributorId = distributorId;
	}

	public static ArrayList<Delivery> getDeliveries() {
		return deliveries;
	}

	public static void setDeliveries(ArrayList<Delivery> deliveries) {
		CarrierDeliveries.deliveries = deliveries;
	}

	public static Delivery getDelivery(long deliveryId) {
		for (Delivery delivery : deliveries) {
			if (delivery.getDeliveryId() == deliveryId) {
				return delivery;
			}
		}
		throw new IllegalArgumentException();
	}
}

