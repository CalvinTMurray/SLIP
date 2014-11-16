package charts;

import java.util.List;



public abstract class AbstractChart<T> {

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
