package charts;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractChart<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private ChartType type;
	
	public ChartType getType() {
		return type;
	}
	
	protected void setType(ChartType type ) {
		this.type = type;
	}
	
	public abstract List<T> getData();
	
	protected abstract void createChart(long sessionID);

}
