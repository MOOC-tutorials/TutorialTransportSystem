package data_structures;


import junit.framework.TestCase;

public class KosarajuTest extends TestCase
{
	private DirectedGraph<Integer, String> grafoPrueba;
	private Kosaraju<Integer,String> ciclo;
	public void setupEscenario1()
	{ 
		grafoPrueba= new DirectedGraph<>();


	}
	public void setupEscenario2()
	{
		setupEscenario1();
		grafoPrueba.addVertex(0, "a");
		grafoPrueba.addVertex(1, "b");
		grafoPrueba.addVertex(2, "c");
		grafoPrueba.addVertex(3, "d");
		grafoPrueba.addVertex(4, "e");
		grafoPrueba.addVertex(5, "f");
		grafoPrueba.addVertex(6, "g");
		grafoPrueba.addVertex(7, "h");
		grafoPrueba.addVertex(8, "i");
		grafoPrueba.addVertex(9, "j");
		grafoPrueba.addVertex(10, "k");



	}
	public void setupEscenario3()
	{
		setupEscenario2();
		grafoPrueba.addEdge(0, 1, 2);
		grafoPrueba.addEdge(1, 2, 3);
		grafoPrueba.addEdge(2, 3, 1);
		grafoPrueba.addEdge(3, 4, 99);
		grafoPrueba.addEdge(4, 6, 99);
		grafoPrueba.addEdge(6,1, 1);
		ciclo= new Kosaraju<Integer,String> (grafoPrueba,0);
	}
	public void setupEscenario4()
	{
		setupEscenario2();
		grafoPrueba.addEdge(0, 1, 2);
		grafoPrueba.addEdge(1, 2, 3);
		grafoPrueba.addEdge(2, 3, 1);
		grafoPrueba.addEdge(3, 4, 99);
		grafoPrueba.addEdge(4, 7, 99);
		ciclo= new Kosaraju<Integer,String> (grafoPrueba,0);
	}
	public void setupEscenario5()
	{
		setupEscenario2();
		grafoPrueba.addEdge(0, 1, 2);
		grafoPrueba.addEdge(1, 2, 3);
		grafoPrueba.addEdge(2, 3, 1);
		grafoPrueba.addEdge(3, 4, 91);
		grafoPrueba.addEdge(4, 7, 55);
		grafoPrueba.addEdge(7, 1, 77);
		grafoPrueba.addEdge(7, 0, 88);
		grafoPrueba.addEdge(8, 9, 11);
		ciclo= new Kosaraju<Integer,String> (grafoPrueba,0);
	}
	public void testTransponerGrafo()
	{
		setupEscenario5();
		int cant1=grafoPrueba.darCantidadDeVertices();
		int cant3=grafoPrueba.darCantidadEjes();
		DirectedGraph<Integer,String> transpuesto=ciclo.transponerGrafo();
		int cant2=transpuesto.darCantidadDeVertices();
		int cant4=transpuesto.darCantidadEjes();
		assertEquals("No estan todos los vertices", cant1,cant2);
		assertEquals("No estan todos los ejes", cant3, cant4);
	}
	public void testTieneCiclo()
	{
		setupEscenario3();
		assertTrue("Deberia tener ciclos", ciclo.tieneCiclo());
		setupEscenario4();
		assertFalse("No deberia tener ciclos", ciclo.tieneCiclo());
	}
	public void testDarCiclos()
	{
		setupEscenario5();			
		int usar=ciclo.darCiclos().getSize();
		assertEquals("Deberia tener otros ciclos", 6,usar);
		setupEscenario4();
		usar=ciclo.darCiclos().getSize();
		assertEquals("Deberia tener otro ciclos", 0,usar);
		setupEscenario3();
		usar=ciclo.darCiclos().getSize();
		assertEquals("Deberia tener otro ciclos", 6,usar);

	}
}
