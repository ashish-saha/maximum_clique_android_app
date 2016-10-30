package com.example.deep.graphs;

/**
 * Created by deep on 10/30/16.
 */

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

public class UndirectedGraph {
    int size;
    SLLSparseM M;
    String result = "";
    Vector <String> friendSuggestion;
    ArrayList <Integer> myList;
    ArrayList <ArrayList<Integer>> powerSet = new ArrayList <ArrayList<Integer>> ();

    ArrayList <ArrayList<Integer>> softClique = new ArrayList <ArrayList<Integer>> ();
    ArrayList <ArrayList<Integer>> advanceSoftClique = new ArrayList <ArrayList<Integer>> ();

    // constructor initialize an undirected graph, n is the number of nodes
    public UndirectedGraph(int n){
        size =n;
        myList = new ArrayList<Integer> ();

        for (int i=0; i<n; i++)
            myList.add(i);
        M = new SLLSparseM ();

        ArrayList<Integer> tempList = new ArrayList <Integer> ();
        getAllSets (myList, size, tempList, 0);
        selectionSort (powerSet, powerSet.size());

    }

    // check if the given node id is out of bounds
    private boolean outOfBounds(int nidx){
        return (nidx < 0 || nidx >= size);
    }

    // set an edge (n1,n2).
    // Since this is an undirected graph, (n2,n1) is also set to one
    public void setEdge(int n1, int n2){

        if (outOfBounds (n1) || outOfBounds (n2))			return;
        M.setElement(n1, n2);
        M.setElement(n2, n1);

    }

    // print an output soft clique in one line
    public void printClique(ArrayList<Integer> nlist){
        for(int i = 0; i < nlist.size(); ++i) {
            System.out.print(MainActivity.myVector.get(nlist.get(i)) + " ");
            result+= MainActivity.myVector.get(nlist.get(i)) + " ";
        }
        System.out.println("");
        result += "\n";
        System.out.println(friendSuggestion);
        result += ("Friend-Suggestion ");
        result += friendSuggestion;
        System.out.println("");
        result += "\n \n";

    }

    public String return_result () {
        return result;
    }

    // compute maximal soft clique
    // cliquesize_lower_bd: k
    // num_missing_edges: l
    public void findMaxSoftClique(int cliquesize_lower_bd, int num_missing_edges){

        for (ArrayList <Integer> iter : powerSet)	{

            if (iter.size() < cliquesize_lower_bd)	{					// cliquesize_lower_bd: k
                continue;
            }

            if(isClique(iter, num_missing_edges))		{

                if (softClique.size() == 0)	{							// if the initial list is empty insert the first element
                    softClique.add(iter);
                    printClique(iter);
                    continue;
                }

                else	{												// check if "iter" is a subset of some bigger clique
                    for (int k=0; k<softClique.size(); k++){
                        if (softClique.get(k).containsAll(iter))	{	// if iter is a subset of some bigger clique continue with the next "iter"
                            break;
                        }
                        else if (k==softClique.size()-1){				// if the loop finishes, it means it not a subset of any bigger clique
                            softClique.add(iter);
                            printClique(iter);
                        }
                    }
                }
            }
        }
    }

    int index =0 ;
    public void getAllSets (ArrayList<Integer> myList,int length,ArrayList<Integer> tempList, int allowedAfter)	{

        if (allowedAfter == length)					return;
        else	{
            for (int i=allowedAfter; i<length; i++)	{
                tempList.add(myList.get(i));
                powerSet.add(new ArrayList <Integer> ());
                powerSet.get(index).addAll(tempList);
                index++;

                if (allowedAfter != length)
                    getAllSets(myList, length, tempList, i+1);
                tempList.remove(tempList.size()-1);
            }
        }
    }


    public void selectionSort (ArrayList <ArrayList<Integer>> arrayR, int length) {
        for ( int i = 0; i < length - 1; i++ ) {
            int indexLowest = i;
            for ( int j = i + 1; j < length; j++ )
                if ( arrayR.get(j).size() > arrayR.get(indexLowest).size() )
                    indexLowest = j;
            if ( arrayR.get(indexLowest) != arrayR.get(i) ) {
                ArrayList<Integer> temp = new ArrayList <Integer> ();
                temp.addAll(arrayR.get(indexLowest));
                arrayR.get(indexLowest).clear();
                arrayR.get(indexLowest).addAll (arrayR.get(i));
                arrayR.get(i).clear();
                arrayR.get(i).addAll(temp);

            }
        }
    }


    public  boolean isClique(ArrayList <Integer> list, int num_missing_edges)	{

        friendSuggestion = new Vector <String> (size, 10);
        int missing_edge = 0;
        for (int x: list)	{
            for (int y: list)	{
                if (x==y) continue;
                else if (M.belongsTo(y, x))					// if row y has indegree from x
                    continue;
                else	{
                    missing_edge++;						// increment the missing edge
                    if (!friendSuggestion.contains(MainActivity.myVector.get(x)))
                        friendSuggestion.add(MainActivity.myVector.get(x));
                    if (!friendSuggestion.contains(MainActivity.myVector.get(y)))
                        friendSuggestion.add(MainActivity.myVector.get(y));
                }
            }
        }

        if ((missing_edge/2) <= num_missing_edges)	{
            return true;
        }
        else
            return false;
    }


}
