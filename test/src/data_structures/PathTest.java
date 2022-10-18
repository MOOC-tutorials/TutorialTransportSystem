package data_structures;

import junit.framework.TestCase;

public class PathTest extends TestCase 
{
	private DirectedGraph<Integer, String> grafoprueba;
	private Path<Integer, String> camino;
	public void setupEscenario1()
{
	grafoprueba= new DirectedGraph<>();
	
}
{ 


}
public void setupEscenario2()
{
	setupEscenario1();
	grafoprueba.addVertex(0, "a");
	grafoprueba.addVertex(1, "b");
	grafoprueba.addVertex(2, "c");
	grafoprueba.addVertex(3, "d");
	grafoprueba.addVertex(4, "e");
	grafoprueba.addVertex(5, "f");
	grafoprueba.addVertex(6, "g");
	grafoprueba.addVertex(7, "h");
	grafoprueba.addVertex(8, "i");
	grafoprueba.addVertex(9, "j");
	grafoprueba.addVertex(10, "k");



}
public void setupEscenario3()
{
	setupEscenario2();
	grafoprueba.addEdge(0, 1, 2);
	grafoprueba.addEdge(1, 2, 3);
	grafoprueba.addEdge(2, 3, 1);
	grafoprueba.addEdge(3, 4, 99);
	grafoprueba.addEdge(4, 6, 99);
	grafoprueba.addEdge(1,6, 1);
	camino= new Path<>(grafoprueba);
}
public void testencontrarcamino()
{
	setupEscenario1();
	setupEscenario2();
	setupEscenario3();
	assertTrue("No se encontaron todos los componentes",camino.bfs(0, grafoprueba).getSize()==6);
	
	
}
}
