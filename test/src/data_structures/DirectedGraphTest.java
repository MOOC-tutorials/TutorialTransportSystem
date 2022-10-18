package data_structures;

import junit.framework.TestCase;


public class DirectedGraphTest extends TestCase {
private DirectedGraph<Integer, Integer> grafoPrueba;

public void setupEscenario1()
{ grafoPrueba= new DirectedGraph<>();

	
}
public void setupEscenario2()
{
	grafoPrueba.addVertex(0, 1);
	grafoPrueba.addVertex(1, 2);
	grafoPrueba.addVertex(2, 3);
	grafoPrueba.addVertex(3, 4);
	grafoPrueba.addVertex(4, 5);
	grafoPrueba.addVertex(5, 6);
	grafoPrueba.addVertex(6, 7);
	grafoPrueba.addVertex(7, 8);
	grafoPrueba.addVertex(8, 9);
	grafoPrueba.addVertex(9, 10);
	grafoPrueba.addVertex(10, 11);
	


}
public void setupEscenario3()
{
	grafoPrueba.addEdge(0, 1, 2);
	grafoPrueba.addEdge(1, 2, 3);
	grafoPrueba.addEdge(2, 3, 1);
	grafoPrueba.addEdge(3, 4, 99);
	grafoPrueba.addEdge(6,1, 1);
	
}
public void testDirectedGrap()
{
	setupEscenario1();
	assertTrue( "No se creo el grafo", grafoPrueba!=null);
	setupEscenario2();
	assertTrue("No se añadieron los nodos", grafoPrueba.darCantidadDeVertices()==11);
	setupEscenario3();
	assertTrue("No se añadieron los ejes",grafoPrueba.darCantidadEjes()==5);
	
}
public void testDarVecinosGraph()
{
	setupEscenario1();
	setupEscenario2();
	setupEscenario3();
	assertTrue("No se encontraron vecino", grafoPrueba.darVecinosVertice(1).size()==1);
	
}
}
