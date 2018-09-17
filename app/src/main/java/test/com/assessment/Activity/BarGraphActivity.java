package test.com.assessment.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

import test.com.assessment.Class.DataModel;

public class BarGraphActivity extends AppCompatActivity {

    private ArrayList<DataModel> arrData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        arrData=new ArrayList<>();
        arrData=(ArrayList<DataModel>) getIntent().getSerializableExtra("arrData");

        DataPoint[] dataPoints=new DataPoint[10];

        for(int i=0;i<10;i++){
          dataPoints[i]=new DataPoint(i,Integer.parseInt(arrData.get(i).getSalary().replace("$","").replace(",","")));

        }

        GraphView graph = (GraphView) findViewById(R.id.graph);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
        graph.addSeries(series);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(20);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
    }
}
