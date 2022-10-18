package data_structures;

import junit.framework.TestCase;

public class CycleTest extends TestCase
{
	private DirectedGraph<Integer, String> grafoPrueba;
	private Cycle<Integer,String> ciclo;
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
		ciclo= new Cycle<Integer,String> (grafoPrueba,0,null);
	}
	public void setupEscenario4()
	{
		setupEscenario2();
		grafoPrueba.addEdge(0, 1, 2);
		grafoPrueba.addEdge(1, 2, 3);
		grafoPrueba.addEdge(2, 3, 1);
		grafoPrueba.addEdge(3, 4, 99);
		grafoPrueba.addEdge(4, 7, 99);
		ciclo= new Cycle<Integer,String> (grafoPrueba,0,null);
	}
	public void setupEscenario5()
	{
		setupEscenario2();
		grafoPrueba.addEdge(0, 1, 2);
		grafoPrueba.addEdge(1, 2, 3);
		grafoPrueba.addEdge(1, 7, 99);
		grafoPrueba.addEdge(2, 3, 1);
		grafoPrueba.addEdge(3, 4, 91);
		grafoPrueba.addEdge(4, 7, 55);
		grafoPrueba.addEdge(7, 1, 77);
		grafoPrueba.addEdge(7, 0, 88);
		ciclo= new Cycle<Integer,String> (grafoPrueba,0,null);
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
		assertEquals("Deberia tener otros ciclos", 2,usar);
		setupEscenario4();
		usar=ciclo.darCiclos().getSize();
		assertEquals("Deberia tener otro ciclos", 0,usar);
		setupEscenario3();
		usar=ciclo.darCiclos().getSize();
		assertEquals("Deberia tener otro ciclos", 1,usar);

	}

}
