/**
 * 
 */
package org.apache.phoenix.hive.objectinspector;

import java.util.List;

import org.apache.hadoop.hive.serde2.lazy.objectinspector.primitive.LazyObjectInspectorParameters;
import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.phoenix.schema.types.PhoenixArray;

import com.google.common.collect.Lists;

/**
 * @author JeongMin Ju
 *
 */
public class PhoenixListObjectInspector implements ListObjectInspector {

	private ObjectInspector listElementObjectInspector;
	private byte separator;
	private LazyObjectInspectorParameters lazyParams;

	public PhoenixListObjectInspector(ObjectInspector listElementObjectInspector, byte separator, LazyObjectInspectorParameters lazyParams) {
		this.listElementObjectInspector = listElementObjectInspector;
	    this.separator = separator;
	    this.lazyParams = lazyParams;
	}
	
	@Override
	public String getTypeName() {
		 return org.apache.hadoop.hive.serde.serdeConstants.LIST_TYPE_NAME + "<" + listElementObjectInspector.getTypeName() + ">";
	}

	@Override
	public Category getCategory() {
		return Category.LIST;
	}

	@Override
	public ObjectInspector getListElementObjectInspector() {
		return listElementObjectInspector;
	}

	@Override
	public Object getListElement(Object data, int index) {
		if (data == null) {
			return null;
		}
		
		PhoenixArray array = (PhoenixArray) data;
		
		return array.getElement(index);
	}

	@Override
	public int getListLength(Object data) {
		if (data == null) {
			return -1;
		}
		
		PhoenixArray array = (PhoenixArray) data;
		return array.getDimensions();
	}

	@Override
	public List<?> getList(Object data) {
		if (data == null) {
			return null;
		}
		
		PhoenixArray array = (PhoenixArray) data;
		int valueLength = array.getDimensions();
		List<Object> valueList = Lists.newArrayListWithExpectedSize(valueLength);
		
		for (int i = 0; i < valueLength; i++) {
			valueList.add(array.getElement(i));
		}
		
		return valueList;
	}

	public byte getSeparator() {
		return separator;
	}

	public LazyObjectInspectorParameters getLazyParams() {
		return lazyParams;
	}

}
