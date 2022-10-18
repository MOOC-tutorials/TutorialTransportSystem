package model.vo;
/**
 * Clase usada para representar las actualizaciones en tiempo relacionadas con una ruta  .
 * 
 */
import java.util.ArrayList;

import data_structures.SimpleArrayLIst;


public class BusEstimVO {
	private String RouteNo;
	private String RouteName;
	private String Direction;
	private routeMap RouteMap; 
	private SimpleArrayLIst<ItemSchedule>Schedules;

	public String darRouteName()
	{
		return RouteName;
	}
	public String darRouteNo()
	{
		return RouteNo;
	}
	public BusEstimVO(String routeNo, String routeName, String direction,
			SimpleArrayLIst<ItemSchedule> schedules) {
		
		RouteNo = routeNo;
		RouteName = routeName;
		Direction = direction;
		Schedules = schedules;
	}
	public SimpleArrayLIst<ItemSchedule> darSchedules()
	{
		return Schedules;
	}
	public class routeMap
	{
		public String Href;
	}
	public class ItemSchedule{
		private  String Pattern;
		private String Destination;
		private String ExpectedLeaveTime ;
		private int ExpectedCountdown;
		public ItemSchedule(String pattern, String destination, String expectedLeaveTime, String expectedCountdown,
				String scheduleStatus, boolean cancelledTrip, boolean cancelledStop, boolean addedTrip,
				boolean addedStop, String lastUpdate) {
			
			Pattern = pattern;
			Destination = destination;
			ExpectedLeaveTime = expectedLeaveTime;
			ExpectedCountdown = Integer.parseInt(expectedCountdown);
			ScheduleStatus = scheduleStatus;
			CancelledTrip = cancelledTrip;
			CancelledStop = cancelledStop;
			AddedTrip = addedTrip;
			AddedStop = addedStop;
			LastUpdate = lastUpdate;
		}
		private String ScheduleStatus;
		private boolean CancelledTrip;
		private boolean CancelledStop ;
		private boolean AddedTrip;
		private boolean AddedStop;
		private String LastUpdate;

		public String darFecha()
		{
			return ExpectedLeaveTime;
		}
		public String darScheduleStatus()
		{
			return ScheduleStatus.trim();
		}
		public String getPattern() {
			return Pattern;
		}
		public void setPattern(String pattern) {
			Pattern = pattern;
		}
		public String getDestination() {
			return Destination;
		}
		public void setDestination(String destination) {
			Destination = destination;
		}
		public int getExpectedCountdown() {
			return ExpectedCountdown;
		}
		public void setExpectedCountdown(int expectedCountdown) {
			ExpectedCountdown = expectedCountdown;
		}
	}
public String darDirection()
{
	return Direction;
}
public SimpleArrayLIst<ItemSchedule> getSchedules() {
	return Schedules;
}
public void setSchedules(SimpleArrayLIst<ItemSchedule> schedules) {
	Schedules = schedules;
}
}