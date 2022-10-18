package model.vo;
/**
 * 
 * Clase para representar el recorrido particular que hace un viaje
 *
 */
public class SiluetaVO 
{
	private String ShapeId;
	private double ShapeLat;
	private double ShapeLon;
	private int pointSequence;
	private double distanceRecorrida;
	public String getShapeId() {
		return ShapeId;
	}
	public void setShapeId(String shapeId) {
		ShapeId = shapeId;
	}
	public double getShapeLat() {
		return ShapeLat;
	}
	public void setShapeLat(double shapeLat) {
		ShapeLat = shapeLat;
	}
	public double getShapeLon() {
		return ShapeLon;
	}
	public void setShapeLon(double shapeLon) {
		ShapeLon = shapeLon;
	}
	public int getPointSequence() {
		return pointSequence;
	}
	public void setPointSequence(int pointSequence) {
		this.pointSequence = pointSequence;
	}
	public double getDistanceRecorrida() {
		return distanceRecorrida;
	}
	public void setDistanceRecorrida(double distanceRecorrida) {
		this.distanceRecorrida = distanceRecorrida;
	}


}
