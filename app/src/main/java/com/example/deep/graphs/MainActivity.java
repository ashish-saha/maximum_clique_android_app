package com.example.deep.graphs;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;


public class MainActivity extends AppCompatActivity {

    static Vector<String> myVector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AssetManager assetManager = getAssets();

        Scanner sc = null;
        String tmps;
        int graph_size = 0;				// number of nodes
        boolean isUndirected = true;	// whether the graph is undirected or directed
        try {
            InputStream inputStream = assetManager.open(String.format("UndirectedGraph.txt"));
            sc = new Scanner(inputStream);
            tmps = sc.next();
            if(tmps.equals("UNDIRECTED")){
                isUndirected = true;
                tmps = sc.next();
                graph_size = sc.nextInt();
            }else if(tmps.equals("DIRECTED")){
                isUndirected = false;
                tmps = sc.next();
                graph_size = sc.nextInt();

            }else{
//                throw new Exception();

            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }


        UndirectedGraph UG;
        DirectedGraph DG;

        if( isUndirected ){
            // undirected graph
            UG = new UndirectedGraph(graph_size);
            myVector = new Vector <String> (graph_size, 10);
            try {
                while (sc.hasNext()) {
                    tmps = sc.next();
                    if(tmps.equals("Friend")){
                        // set an edge
                        String temp1 = sc.next();
                        String temp2 = sc.next();

                        if (!myVector.contains(temp1))		myVector.add(temp1);
                        if (!myVector.contains(temp2))		myVector.add(temp2);

                        int nid1 = myVector.indexOf(temp1); // row index
                        int nid2 = myVector.indexOf(temp2); // col index
                        UG.setEdge(nid1, nid2);
                    }else if(tmps.equals("END")){
                        // finished, return the matrix
                        sc.close();
                        break;
                    }else{
                        sc.close();
                        throw new Exception();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error Reading the Graph Data!");
                return;
            }

//            int k = parameters.get(0);
//            int l = parameters.get(1);

            int k = 3;
            int l = 1;
            TextView text_1 = (TextView) findViewById(R.id.output_1);
            text_1.setText("Computing (" + k + "-" + l + ") maximal soft cliques!");

//            System.out.println("Computing (" + k + "-" + l + ") maximal soft cliques!");

            UG.findMaxSoftClique(k, l);
            TextView text_2 = (TextView) findViewById(R.id.output_2);
            text_2.setText(UG.return_result());



        }else{
            // directed graph
            DG = new DirectedGraph(graph_size);

            try {
                while (sc.hasNext()) {
                    tmps = sc.next();
                    if(tmps.equals("EDGE")){
                        // set an edge

                        int nid1 = sc.nextInt(); // row index
                        int nid2 = sc.nextInt(); // col index
                        DG.setEdge(nid1, nid2);
                    }else if(tmps.equals("END")){
                        // finished, return the matrix
                        sc.close();
                        break;
                    }else{
                        sc.close();
                        throw new Exception();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error Reading the Graph Data!");
                return;
            }
//            int k = parameters.get(0);
//            System.out.println("Computing " + k + "'th PageRank!");
//            DG.computePageRank(k);
        }

        System.out.println("END");
        TextView text_3 = (TextView) findViewById(R.id.output_3);
        text_3.setText("END");


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
