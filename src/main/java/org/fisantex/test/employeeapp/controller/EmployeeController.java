package org.fisantex.test.employeeapp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.dozer.DozerBeanMapper;
import org.fisantex.test.employeeapp.dao.EmployeeDao;
import org.fisantex.test.employeeapp.exception.ElaborateResponseException;
import org.fisantex.test.employeeapp.model.bean.EmployeeBean;
import org.fisantex.test.employeeapp.model.bean.OperationError;
import org.fisantex.test.employeeapp.model.bean.OperationResult;
import org.fisantex.test.employeeapp.model.entity.Employee;
import org.fisantex.test.employeeapp.util.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("employeeController")
public class EmployeeController {

	private static DozerBeanMapper mapper = new DozerBeanMapper(
			Arrays.asList(new String[] { "META-INF/dozer_mapping.xml" }));
	// move to a props file
	@Autowired
	private EmployeeDao<Employee> employeeDao;

	@PostConstruct
	private void init() {
		employeeDao.initDao(Employee.class);
	}

	public String getName() {
		return employeeDao.getClazz().getName();
	}

	public List<EmployeeBean> getEmployeeList(String... querypars) {
		try {
			String sort = querypars[0];
			String filter = querypars[1];
			if (sort != null) {
				return employeeDao
						._listBySorting(MethodUtils.transcodeField(mapper, EmployeeBean.class, Employee.class, sort))
						.stream().map(beanMappingFunction).collect(Collectors.<EmployeeBean>toList());
			} else if (filter != null) {
				return employeeDao
						._listByFiltering(MethodUtils.transcodeField(mapper, EmployeeBean.class, Employee.class,
								MethodUtils.extractFilter(filter)[0]), MethodUtils.extractFilter(filter)[1])
						.stream().map(beanMappingFunction).collect(Collectors.<EmployeeBean>toList());
			}
			return employeeDao._list().stream().map(beanMappingFunction).collect(Collectors.<EmployeeBean>toList());
		} catch (Exception e) {
			throw new ElaborateResponseException(OperationResult.builder().success(false)
					.operationErrors(OperationError.builder().errorCode("500").errorMessage(e.toString()).build())
					.build());
		}

	}

	public EmployeeBean getEmployee(long id) {
		try {

			Employee employee = employeeDao._getById(id);
			return beanMappingFunction.apply(employee);
		} catch (Exception e) {
			throw new ElaborateResponseException(OperationResult.builder().success(false)
					.operationErrors(OperationError.builder().errorCode("500").errorMessage(e.toString()).build())
					.build());
		}
	}

	public OperationResult addEmployee(EmployeeBean employee) {
		try {
			return (employeeDao._add(entityMappingFunction.apply(employee)))
					? OperationResult.builder().success(true).build()
					: OperationResult.builder().success(false).operationErrors(
							OperationError.builder().errorCode("404").errorMessage("Employee Not Inserted").build())
							.build();
		} catch (Exception e) {
			throw new ElaborateResponseException(OperationResult.builder().success(false)
					.operationErrors(OperationError.builder().errorCode("500").errorMessage(e.toString()).build())
					.build());
		}
	}

	public OperationResult removeEmployee(long id) {
		try {
			OperationResult.builder().success(true)
					.operationErrors(OperationError.builder().errorCode("400").errorMessage("BAD").build()).build();
			return (employeeDao._remove(id)) ? OperationResult.builder().success(true).build()
					: OperationResult.builder().success(false).operationErrors(
							OperationError.builder().errorCode("404").errorMessage("Employee Not Found").build())
							.build();
		} catch (Exception e) {
			throw new ElaborateResponseException(OperationResult.builder().success(false)
					.operationErrors(OperationError.builder().errorCode("500").errorMessage(e.toString()).build())
					.build());
		}
	}

	public OperationResult updateEmployee(EmployeeBean employee) {
		try {
			return (employeeDao._update(entityMappingFunction.apply(employee), employee.getId()))
					? OperationResult.builder().success(true).build()
					: OperationResult.builder().success(false).operationErrors(
							OperationError.builder().errorCode("404").errorMessage("Employee Not Found").build())
							.build();
		} catch (Exception e) {
			throw new ElaborateResponseException(OperationResult.builder().success(false)
					.operationErrors(OperationError.builder().errorCode("500").errorMessage(e.toString()).build())
					.build());
		}
	}

	private Function<Employee, EmployeeBean> beanMappingFunction = new Function<Employee, EmployeeBean>() {
		public EmployeeBean apply(Employee t) {
			return mapper.map(t, EmployeeBean.class);
		}
	};
	private Function<EmployeeBean, Employee> entityMappingFunction = new Function<EmployeeBean, Employee>() {
		public Employee apply(EmployeeBean t) {
			return mapper.map(t, Employee.class);
		}
	};

}