function createHeatmapChart() {
    $('#heatmapGraphContainer').highcharts({
        chart: {
            type: 'heatmap',
            width : 500,
            height : 500
        },
        credits: {
		    enabled: false
		},
        title: {
            text:'Heatmap'
        },
        yAxis: {
            categories: ['0-9', '10-19', '20-29', '30-39', '40-49' , '50-59' , '60-69'],
            title: {
                text:'Y Coordinate Range'
            },
            min:0
        },
        xAxis: {
            categories: ['0-9', '10-19', '20-29', '30-39', '40-49' , '50-59' , '60-69'],
            title:{
                text:'X Coordinate Range'
            },
            min:0
        },
        tooltip: {
            formatter: function () {
                return 'X Coordinate Range: <b>'+this.series.xAxis.categories[this.point.x]+
                '</b><br>'+'Y Coordinate Range: <b>'+this.series.xAxis.categories[this.point.y]+
                '</b><br>'+'Number of points:  <b>'+this.point.value+'</b><br>';
            }
        },
        colorAxis: {
            stops: [
                [0, '#ffffff'], //white
                [0.25,'#FFEB3B'], // yellow
                [0.5,'#FF9800'], // orange
                [1,'#ff0000'] // red
            ],
            min: 0 // Start the heatmap range at zero
        },
        series: [{
            name: 'X & Y Coordinate Range',
            borderWidth: 1,
            data: heatmapPoints ,
            dataLabels: {
                enabled: true,
                color: 'black',
                style: {
                    textShadow: 'none',
                    HcTextStroke: null
                }
            }
        }]

    });
}