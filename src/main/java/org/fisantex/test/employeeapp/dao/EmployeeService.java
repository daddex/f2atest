package org.fisantex.test.employeeapp.dao;

import java.io.Serializable;
import java.util.List;

public interface EmployeeService<T> {
	public boolean _add(T bean);
	public boolean _update(T bean,Object id);
	public T _getById(Object id);
	public boolean _remove(Serializable id);
	public List<T> _listByFiltering(String field,String value);
	public List<T> _listBySorting(String field);
	public List<T> _list();
}
