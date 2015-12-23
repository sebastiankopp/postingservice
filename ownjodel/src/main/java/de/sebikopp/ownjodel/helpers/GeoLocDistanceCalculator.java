package de.sebikopp.ownjodel.helpers;

import de.sebikopp.ownjodel.model.GeoPosition;

public class GeoLocDistanceCalculator {
	private static final double R_ERDE = 6370; // in km
	private static final double MAX_DIST_FOR_NEAR_LOC = 15; // km
	public static boolean isNearLoc(GeoPosition pos, GeoPosition spot){
		return calcDistance(pos, spot) <= MAX_DIST_FOR_NEAR_LOC;
	}
	
	private static double calcDistance(GeoPosition pos1, GeoPosition pos2){
		// phi: Geographische Breite
		// lambda: Geographische Länge
		// In dieser Erläuterung Winkel im Bogenmaß
		// r_Erde*arccos (sin_phi_1*sin_phi_2 + cos_phi_1*cos_phi_2 + cos (lambda_b - lambda_a))
		double arccos = Math.acos(
					(Math.sin(degToRad(pos1.getLatitude()))*Math.sin(degToRad(pos2.getLatitude()))) +
					(Math.cos(degToRad(pos1.getLatitude()))*Math.cos(degToRad(pos2.getLatitude()))) +
					Math.cos(degToRad(pos2.getLongitude()) - degToRad(pos1.getLongitude()))
				);
		return arccos*R_ERDE; // DUMMY
	}
	private static double degToRad(double deg){
		return deg*(Math.PI/180);
	}
}
