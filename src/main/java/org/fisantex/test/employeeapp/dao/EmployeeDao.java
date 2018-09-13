package org.fisantex.test.employeeapp.dao;

 
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.springframework.stereotype.Service;

@Service("employeeDao")
public class EmployeeDao<T>  {

		private static final String PERSISTENCE_UNIT_NAME = "employee";
		private static EntityManagerFactory factory;
		private static EntityManager manager;
		protected Class<T> clazz; 	
		
		public Class<T> getClazz() {
			return clazz;
		}
		public void initDao(Class<T> clazz) {
			this.clazz = clazz;
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			
		}
	 
		public boolean _add(T bean) {
			EntityTransaction trx = null;
			try {
				manager = factory.createEntityManager();
				trx = manager.getTransaction();
				trx.begin();
				manager.persist(bean);
			} finally {
				trx.commit();
				manager.close();
			}
			return true;
		}

		public boolean _update(T bean,Object id) {
			EntityTransaction trx = null;
			try {
				
				manager = factory.createEntityManager();
				trx = manager.getTransaction();
				trx.begin();
				if(manager.find(clazz, id)==null) {
					return false;
				}			
				bean = manager.merge(bean);			  	
			} finally {
				trx.commit();
				manager.close();
			}
			return true;
		}

		public T _getById(Object id) {			
			manager = factory.createEntityManager();
			EntityTransaction trx = manager.getTransaction();
			trx.begin();
			T obj =  manager.find(clazz, id);
			trx.commit();
			manager.close();
			return obj;
		}

		public boolean _remove(Object id) {
			T ret = _getById(id);
			EntityTransaction trx = null;
			if (ret != null) {
				try {
					manager = factory.createEntityManager();
					trx = manager.getTransaction();
					trx.begin();
					if (!manager.contains(ret)) {
						ret = manager.merge(ret);
					}
					manager.remove(ret);					 
				} finally {
					trx.commit();
					manager.close();

				}
			}
			return true;
		}
		public List<T> _listByFiltering(String field,String value) {
			EntityTransaction trx = null;
			manager = factory.createEntityManager();
			trx = manager.getTransaction();
			trx.begin();
			Query q = manager.createNativeQuery("SELECT e.ID, e.NAME, e.LASTNAME,e.ADDRESSE FROM Employee e WHERE ".concat(field).concat(" LIKE '").concat(value).concat("%'"),this.clazz); 
			List<T> _list = q.getResultList();
			trx.commit();
			manager.close();
			return _list;
		}
		public List<T> _listBySorting(String field) {
			EntityTransaction trx = null;
			manager = factory.createEntityManager();
			trx = manager.getTransaction();
			trx.begin();
			Query q = manager.createNativeQuery("SELECT e.ID, e.NAME, e.LASTNAME,e.ADDRESSE FROM Employee e ORDER BY ".concat(field),this.clazz);  
			List<T> _list = q.getResultList();
			trx.commit();
			manager.close();
			return _list;
		}
		public List<T> _list() {
			EntityTransaction trx = null;
			manager = factory.createEntityManager();
			trx = manager.getTransaction();
			trx.begin();
			String query = "SELECT T FROM " + clazz.getName() + " T ";
			Query q = manager.createQuery(query);

			List<T> _list = q.getResultList();
			trx.commit();
			manager.close();
			return _list;
		}
		
	 
}
