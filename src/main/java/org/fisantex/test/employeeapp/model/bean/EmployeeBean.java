package org.fisantex.test.employeeapp.model.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeBean implements Serializable {

	private Long id;
	private String name;
	private String lastName;
	private String addresse;

}