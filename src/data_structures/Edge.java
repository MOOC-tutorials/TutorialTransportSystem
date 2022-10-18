package data_structures;

public class Edge<T> 
{
	private double peso;
	private T llaveorigen;
	private T llaveDestino;
	private T deDondeViene;
	public T getDeDondeViene() {
		return deDondeViene;
	}
	public void setDeDondeViene(T deDondeViene) {
		this.deDondeViene = deDondeViene;
	}
	public Edge(double peso, T llaveorigen, T llaveDestino) {
	
		this.peso = peso;
		this.llaveorigen = llaveorigen;
		this.llaveDestino = llaveDestino;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public T getLlaveorigen() {
		return llaveorigen;
	}
	public void setLlaveorigen(T llaveorigen) {
		this.llaveorigen = llaveorigen;
	}
	public T getLlaveDestino() {
		return llaveDestino;
	}
	public void setLlaveDestino(T llaveDestino) {
		this.llaveDestino = llaveDestino;
	}
}
