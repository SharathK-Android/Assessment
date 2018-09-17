package test.com.assessment.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import test.com.assessment.Class.DataModel;
import test.com.assessment.Class.ItemDecoration;
import test.com.assessment.Interface.OnItemClickListener;

public class MainActivity extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<DataModel> arrData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrData=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        arrData=(ArrayList<DataModel>) getIntent().getSerializableExtra("arrData");


        OnItemClickListener onItemClickListener=new OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View itemView) {

                onItemClick(position,itemView);


            }
            @Override
            public void OnLocationClick(int position) {

                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("location",arrData.get(position).getCity());
                startActivity(intent);

            }

        };


        ItemDecoration itemDecoration = new ItemDecoration(this, R.dimen.viewitem_space);

        recyclerViewAdapter = new RecyclerViewAdapter(this,arrData,onItemClickListener );

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void onItemClick(int position, View itemView) {

        Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra("model",arrData.get(position));

        String transitionName = getString(R.string.transition_name);

        ActivityOptions transitionActivityOptions = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, itemView, transitionName);
        }
        startActivity(intent, transitionActivityOptions.toBundle());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_graph:

                Intent intent=new Intent(MainActivity.this,BarGraphActivity.class);
                intent.putExtra("arrData",arrData);
                startActivity(intent);

                return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
