package com.shopping.common.pojo;

import java.io.Serializable;
import java.util.List;

//为了符合easyUI的datagrid要求返回的数据的格式而封装的实体类
public class EasyUIDataGridResult implements Serializable {
	
	private long total;
	private List<?> rows;
	
	public long getTotal() {
		return total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
	

}
