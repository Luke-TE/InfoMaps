import React, { Component } from 'react';
import './App.css';
import * as am4core from "@amcharts/amcharts4/core";
import * as am4maps from "@amcharts/amcharts4/maps";
import am4geodata_worldLow from "@amcharts/amcharts4-geodata/worldLow";

export class Map extends Component {

    componentDidMount() {


        // make a container
        let container = am4core.create("container", am4core.Container);
        container.width = am4core.percent(100);
        container.height = am4core.percent(100);
        container.layout = "vertical";

        // define a map
        let map = container.createChild(am4maps.MapChart);

        // set the map
        map.geodata = am4geodata_worldLow;

        // set the projection
        map.projection = new am4maps.projections.Miller();


        let polygonSeries = map.series.push(new am4maps.MapPolygonSeries());


        polygonSeries.useGeodata = true;

        let polygonTemplate = polygonSeries.mapPolygons.template;
        polygonTemplate.tooltipText = "{name}\n {value}";
        polygonTemplate.fill = am4core.color("#bea3b3");


        // hover
        let hs = polygonTemplate.states.create("hover");
        hs.properties.fill = am4core.color("#8f6faa");



        polygonSeries.exclude = ["AQ","GF", "PR"];


        polygonSeries.dataSource.url = "http://localhost:3001/init.json";



        console.log(polygonSeries.data);
        // color rule
        polygonSeries.heatRules.push({
            "property": "fill",
            "target": polygonSeries.mapPolygons.template,
            "min" : am4core.color("#a2bdc5"),
            "max" : am4core.color("#3ba7e3")
        });
        let heatLegend = container.createChild(am4maps.HeatLegend);
        heatLegend.series = polygonSeries;

        heatLegend.width = am4core.percent(100);
        polygonSeries.mapPolygons.template.events.on("over", function(ev) {
            if (!isNaN(ev.target.dataItem.value)) {
                heatLegend.valueAxis.showTooltipAt(ev.target.dataItem.value);
            }
            else {
                heatLegend.valueAxis.hideTooltip();
            }
        });

        polygonSeries.mapPolygons.template.events.on("out", function() {
            heatLegend.valueAxis.hideTooltip();
        });
        this.chart = map;
    }



    componentWillUnmount() {
        if (this.chart) {
            this.chart.dispose();
        }
    }

    render() {
        return (
            <div>
                <div id="container" style={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    height: '930px'}}/>
            </div>
        );
    }
}