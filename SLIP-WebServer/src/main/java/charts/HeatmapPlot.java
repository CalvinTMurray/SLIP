package charts;

import statistics.PositionPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Calvin . T . Murray on 03/01/2015.
 */
public class HeatmapPlot extends AbstractChart<int[]> {

    private static final long serialVersionUID = 8589628046314201215L;
    private List<int[]> data;
    private List<PositionPoint> points;

    public HeatmapPlot(long sessionID, List<PositionPoint> points) {
        this.data = new ArrayList<>();
        this.points = points;
        super.setType(ChartType.HEATMAP);

        createChart(sessionID);
    }

    @Override
    public List<int[]> getData() {
        return data;
    }

    @Override
    protected void createChart(long sessionID) {
        System.out.println("\nCREATING HEATMAP PLOT");

        int numberOfxGrids = 14;
        int numberOfyGrids = 14;
        double width_and_height = 5; // Each grid square is 10 x 10;

        int[][] grid = new int[numberOfxGrids][numberOfyGrids];

        for (PositionPoint p : points) {
            if (p.xPosition == null || p.yPosition == null) {
                continue;
            }

            int gridX = (int) ((p.xPosition/(numberOfxGrids*width_and_height)) * numberOfxGrids);
            int gridY = (int) ((p.yPosition/(numberOfyGrids*width_and_height)) * numberOfyGrids);

            System.out.println("Coordinate: " + p + " gridX: " + gridX + " gridY: " + gridY);
            grid[gridX][gridY] += 1;
        }

        for (int i = 0; i < numberOfxGrids; i++) {
            for (int j = 0; j < numberOfyGrids; j++) {
                data.add(new int[]{i, j, grid[i][j]});
            }
        }

//        System.out.println(Arrays.deepToString(grid));
    }

}
