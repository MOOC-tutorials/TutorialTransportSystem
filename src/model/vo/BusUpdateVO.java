package model.vo;
/**
 * Clase usada para precisar con mas detalle la ubicaion de un bus en tiempo real.
 * 
 */
public class BusUpdateVO {
	private String VehicleNo;
	private String TripId;
	private String RouteNo;
	private String Direction;
	private String Destination;
	private String Pattern;
	private String Latitude;
	private String Longitude;
	private String RecordedTime;
	private String RouteMap;
	public BusUpdateVO(String vehicleNo, String tripId, String routeNo,
			String direction, String destination, String pattern,
			String latitude, String longitude, String recordedTime,
			String routeMap) {
		
		VehicleNo = vehicleNo;
		TripId = tripId;
		RouteNo = routeNo;
		Direction = direction;
		Destination = destination;
		Pattern = pattern;
		Latitude = latitude;
		Longitude = longitude;
		RecordedTime = recordedTime;
		RouteMap = routeMap;
	}
	public String getVehicleNo() {
		return VehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		VehicleNo = vehicleNo;
	}
	public String getTripId() {
		return TripId;
	}
	public void setTripId(String tripId) {
		TripId = tripId;
	}
	public String getRouteNo() {
		return RouteNo;
	}
	public void setRouteNo(String routeNo) {
		RouteNo = routeNo;
	}
	public String getDirection() {
		return Direction;
	}
	public void setDirection(String direction) {
		Direction = direction;
	}
	public String getDestination() {
		return Destination;
	}
	public void setDestination(String destination) {
		Destination = destination;
	}
	public String getPattern() {
		return Pattern;
	}
	public void setPattern(String pattern) {
		Pattern = pattern;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getRecordedTime() {
		return RecordedTime;
	}
	public void setRecordedTime(String recordedTime) {
		RecordedTime = recordedTime;
	}
	public String getRouteMap() {
		return RouteMap;
	}
	public void setRouteMap(String routeMap) {
		RouteMap = routeMap;
	} 


	
}
