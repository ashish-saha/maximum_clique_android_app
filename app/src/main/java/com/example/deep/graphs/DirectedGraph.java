package com.example.deep.graphs;

/**
 * Created by deep on 10/30/16.
 */


public class DirectedGraph {

    SLLSparseM Mout;
    SLLSparseM Min;
    static int size;

    // constructor initialize an undirected graph, n is the number of nodes
    public DirectedGraph(int n){
        Mout = new SLLSparseM ();
        Min = new SLLSparseM ();
        size = n;
    }

    // check if the given node id is out of bounds
    private boolean outOfBounds(int nidx){
        return (nidx < 0 || nidx >= size);
    }

    // set an edge (n1,n2)
    // beware of repeatingly setting a same edge and out-of-bound node ids
    public void setEdge(int n1, int n2){

        if (outOfBounds (n1) || outOfBounds (n2))			return;
        Mout.setElement (n1,n2);
        Min.setElement  (n2,n1);

    }

    //counts the number of outdegrees
    public double edge (int k){
        SLLSparseM.rowHeadNode curr = Mout.header;
        while (curr != null){
            if (curr.rowHeadidx == k)	return curr.rowElements;
            curr = curr.nextRow;
        }
        return 0;
    }



    // compute page rank after num_iters iterations
    // then print them in a monotonically decreasing order
    void computePageRank(int num_iters){

        int [] nodes = new int [size];
        for (int i=0;i<size; i++)
            nodes[i] =i;

        int [] edeges = new int [size];
        double [] rank = new double [size];
        double [] newRank = new double [size];

        for (int a=0; a<size; a++)	{											//intially rank is 1 and newRak is 0
            rank [a] = 1;
            newRank [a] = 0;
        }
        Mout.getDegree (nodes, edeges);


        for (int k=1; k<=num_iters; k++){										// pange rank for k iterations
            int j = 0;
            for (j=0; j<size; j++){											// computing rank for each node
                double i = 0;
                SLLSparseM.rowHeadNode curr = Min.header;
                while (curr != null){
                    if (curr.rowHeadidx == j)	{
                        SLLSparseM.elementNode ele = curr.next;
                        while (ele != null)	{
                            i += rank [ele.element]/(edge(ele.element)); 		// rank of indegree edges
                            ele = ele.next;
                            if (ele == null){
                                newRank[j] = i;
                            }
                        }
                    }
                    curr = curr.nextRow;
                }
            }
            for (int a=0; a<size; a++)	{										// copy the newRank Array to rank and clear newRank
                rank [a] = newRank [a];
                newRank [a] = 0;
            }
        }
        selectionSort (rank, nodes, size);

        for (int i=0; i<size; i++)	{
            System.out.println(nodes [i] + " " + rank[i]);
        }
    }


    private static void selectionSort (double[] arrayR, int [] arrayN, int length) {
        for ( int i = 0; i < length - 1; i++ ) {
            int indexLowest = i;
            for ( int j = i + 1; j < length; j++ )
                if ( arrayR[j] > arrayR[indexLowest] )
                    indexLowest = j;
            if ( arrayR[indexLowest] != arrayR[i] ) {
                double temp = arrayR[indexLowest];
                arrayR[indexLowest] = arrayR[i];
                arrayR[i] = temp;

                int tempN = arrayN[indexLowest];
                arrayN[indexLowest] = arrayN[i];
                arrayN[i] = tempN;
            }
        }
    }


}
