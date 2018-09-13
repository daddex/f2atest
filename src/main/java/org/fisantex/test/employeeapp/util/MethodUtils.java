package org.fisantex.test.employeeapp.util;

import org.dozer.DozerBeanMapper;
import org.dozer.metadata.ClassMappingMetadata;
import org.dozer.metadata.FieldMappingMetadata;
import org.dozer.metadata.MappingMetadata;

public class MethodUtils {
	public static String[] extractFilter(String queryPar) {
		return queryPar.split(":");
	}

	public static <T,Y> String transcodeField(DozerBeanMapper mapper,Class<T> source,Class<Y> destination,String fieldToTranscode) {
		MappingMetadata mt = mapper.getMappingMetadata();
		ClassMappingMetadata cm = mt.getClassMapping(source, destination);
		FieldMappingMetadata fmm = cm.getFieldMappingBySource(fieldToTranscode);
		return fmm.getDestinationName();
	}
}
