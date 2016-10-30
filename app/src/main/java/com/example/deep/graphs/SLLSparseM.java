package com.example.deep.graphs;

/**
 * Created by deep on 10/30/16.
 */

public class SLLSparseM  {

    public  class elementNode {
        protected int element;
        protected elementNode next;
        public elementNode (int e, elementNode n) {
            element = e;
            next = n;
        }
        public elementNode (int e) {
            element = e;
            next = null;
        }
    }

    public  class rowHeadNode {
        protected int rowHeadidx;
        protected int rowElements;
        protected elementNode next;
        protected rowHeadNode nextRow;
        public rowHeadNode (int r, elementNode e, rowHeadNode h) {
            rowHeadidx = r;
            rowElements++;// = n;
            next = e;
            nextRow = h;
        }
        public rowHeadNode (int r, elementNode e) {
            rowHeadidx = r;
            rowElements++;// = n;
            next = e;
            nextRow = null;
        }
    }

    protected rowHeadNode header;
    private int nrows, ncols;
    private int nelements;

    public SLLSparseM(){
        header = null;
        nelements = 0;
    }


    public int nrows() {
        return nrows;
    }
    public int ncols() {
        return ncols;
    }
    public int numElements() {
        return nelements;
    }




    public void setElement(int ridx, int col) {
        nelements++;
        rowHeadNode currHead = header;
        elementNode e;


        // if the list is empty create an element then create a rowHeadNode
        if (currHead== null)	{
            e = new elementNode (col);
            header = new rowHeadNode (ridx, e);
            return;
        }

        while ( currHead!= null)	{
            //add rowHeadNode in fort of the list
            if(ridx < currHead.rowHeadidx)		{
                e = new elementNode (col);
                header = new rowHeadNode (ridx, e, header);
                return;
            }

            //add rowHeadNode at last
            if ((ridx > currHead.rowHeadidx) && (currHead.nextRow == null))	{
                e = new elementNode (col);
                currHead.nextRow = new rowHeadNode (ridx, e);
                return;
            }

            //add rowHeadNode in between or end of the list
            if ((ridx > currHead.rowHeadidx) && (ridx <currHead.nextRow.rowHeadidx))	{
                e = new elementNode (col);
                currHead.nextRow = new rowHeadNode (ridx, e, currHead.nextRow );
                return;
            }

            //add an element
            if (ridx == currHead.rowHeadidx)	{
                elementNode currEle = currHead.next;
                if (currEle.element == col)				return;						// Duplicate EDGE
                while (currEle.next != null)	{
                    currEle = currEle.next;
                    if (currEle.element == col)			return;						// Duplicate EDGE
                }
                currEle.next = new elementNode (col);
                currHead.rowElements++;
                return;
            }
            currHead = currHead.nextRow;
        }
    }

    public void getDegree(int [] nodes, int [] edeges){
        rowHeadNode curr = header;
        int counter = 0;
        while (curr != null) {
            nodes [counter] = curr.rowHeadidx;
            edeges [counter] = curr.rowElements;
            counter++;
            curr = curr.nextRow;
        }
    }

    public boolean belongsTo (int y, int x){
        rowHeadNode curr = header;
        while (curr != null)	{
            if (curr.rowHeadidx == x)	{
                elementNode ele = curr.next;
                while (ele != null)	{
                    if (ele.element == y)		return true;
                    ele = ele.next;
                }
            }
            curr = curr.nextRow;
        }
        return false;
    }
    public void print (){
        rowHeadNode h = header;
        elementNode e;
        while (h != null)	{
            e =h.next;
            System.out.print(h.rowHeadidx +"|" + h.rowElements + "----------");;
            while ( e!= null )	{
                System.out.print( "|" + e.element + "|" + "--");
                e = e.next;
                if (e == null) 		System.out.println ("@");
            }
            h = h.nextRow;
        }
        return;
    }

}
