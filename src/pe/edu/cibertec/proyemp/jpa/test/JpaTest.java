package pe.edu.cibertec.proyemp.jpa.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import pe.edu.cibertec.proyemp.jpa.domain.Departamento;
import pe.edu.cibertec.proyemp.jpa.domain.Empleado;

public class JpaTest {

	private EntityManager manager;
	
	public JpaTest(EntityManager manager) {
		super();
		this.manager = manager;
	}

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
		
		EntityManager manager = factory.createEntityManager();
		
		JpaTest test = new JpaTest(manager);
		
		EntityTransaction tx = manager.getTransaction();
		
		tx.begin();
		
		//test.crearEmpleados();
		test.crearEmpleados();
		test.modificarNombreDepartamento();
		
		tx.commit();
		
		test.listarEmpleados();
		
		System.out.println("... done");

	}
	
	private void crearEmpleados() {
		int nroDeEmpleados = manager
				.createQuery("Select a From Empleado a", Empleado.class)
				.getResultList().size();

		if (nroDeEmpleados == 0) {
			System.out.println("creando empleados");
			Departamento departamento = new Departamento("Java");
			manager.persist(departamento);
			manager.persist(new Empleado("Bob", departamento));
			manager.persist(new Empleado("Mike", departamento));
		}
		
	}

	private void modificarNombreDepartamento() {
		//		Departamento dep = manager.
		//		createQuery("from Departamento where id=1", 
		//		Departamento.class).getSingleResult();
	
		Departamento dep = manager.find(Departamento.class, 
		new Long(1));

		dep.setNombre(".NET");
		//manager.persist(dep);
		
	}
	
	private void listarEmpleados() {
		List<Empleado> resultList = manager.createQuery(
				"Select a From Empleado a", Empleado.class).getResultList();

		System.out.println("nro de empleados:" + resultList.size());
		for (Empleado next : resultList) {
			System.out.println("siguiente empleado: " + next);
		}
		
	}
}
