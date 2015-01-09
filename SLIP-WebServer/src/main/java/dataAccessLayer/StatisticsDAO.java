package dataAccessLayer;

import statistics.PositionPoint;

import java.util.List;

/**
 * Created by Calvin . T . Murray on 07/01/2015.
 */
public interface StatisticsDAO {

    public Long getMinTime(long sessionID);

    public Long getMaxTime(long sessionID);

    public PositionPoint getClosestPoint (long sessionID, long timestampExpected, long lowTimestamp, long highTimestamp);

    public List<PositionPoint> getPoints (long sessionID);

}
