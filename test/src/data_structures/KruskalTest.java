package data_structures;

import junit.framework.TestCase;

public class KruskalTest extends TestCase
{private DirectedGraph<Integer, String> grafoPrueba;
private Kruskal<Integer, String> kruskal;
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
		grafoPrueba.addEdge(0, 1, 1);
		grafoPrueba.addEdge(1, 2, 1);
		grafoPrueba.addEdge(1, 3, 3);
		grafoPrueba.addEdge(1, 4, 2);
		grafoPrueba.addEdge(2, 5, 1);
		kruskal= new Kruskal<>(grafoPrueba);
	}
	public void testnosoycapo()
	{
		setupEscenario1();
		setupEscenario2();
		setupEscenario3();


		kruskal.ConstruirUnionFind();
	}
}
