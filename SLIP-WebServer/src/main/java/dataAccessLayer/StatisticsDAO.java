package dataAccessLayer;

import statistics.PositionPoint;

/**
 * Created by Calvin . T . Murray on 07/01/2015.
 */
public interface StatisticsDAO {

    public Long getMinTime(long sessionID);

    public Long getMaxTime(long sessionID);

    public PositionPoint getClosestPoint (long sessionID, long timestampExpected, long lowTimestamp, long highTimestamp);

}
