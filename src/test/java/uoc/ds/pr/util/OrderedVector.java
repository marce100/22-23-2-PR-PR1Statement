package uoc.ds.pr.util;

import edu.uoc.ds.adt.sequential.FiniteContainer;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;
import edu.uoc.ds.traversal.IteratorTraversalImpl;

import java.util.Comparator;

public class OrderedVector<Integer> implements FiniteContainer<Integer> {

    private Comparator<Integer> comparator;
    private Integer[] data;
    private int len;

    public OrderedVector(int max, Comparator<Integer> comparator) {
        this.comparator = comparator;
        this.data= (Integer[])new Object[max];
        this.len=0;
    }

    public Integer elementAt(int i) {
        return this.data[i];
    }

    public void rshift(int i) {
        int p=this.len-1;
        while (p>=i) {
            this.data[p+1]=this.data[p];
            p--;
        }
    }

    public void lshift(int i) {
        int p=i;
        while (p<this.len) {
            this.data[p]=this.data[p+1];
            p++;
        }
    }

    public void update(Integer vIn) {
        int i = 0;
        boolean end=false;
        Integer v = null;

        this.delete(vIn);

        if (this.isFull()) {
            Integer pOut = this.last();
            if (comparator.compare(pOut, vIn)>0) {
                this.delete(pOut);
                this.update(vIn);
            }
        }

        while (i<this.len && this.data[i]!=null && this.comparator.compare(this.data[i], vIn)>=0)
            i++;

        rshift(i);

        this.data[i]=vIn;
        this.len++;

    }

    private Integer last() {
        return this.last();
    }


    @Override
    public boolean isEmpty() {
        return this.len==0;
    }

    public int size() {
        return 0;
    }

    @Override
    public Iterator<Integer> values() {
        return (Iterator<Integer>)new IteratorArrayImpl<Integer>(this.data, this.len,0);
    }

    public boolean isFull() {
        return this.len==this.data.length;
    }

    public int numElements(){
        return this.len;
    }

    public void delete (Integer elem) {
        int i=0;
        boolean found=false;

        while (!found && i<this.len)
            found= (comparator.compare(elem, this.data[i++])>=0);

        if (found) {
            lshift(i-1);
            this.len--;
        }

    }


}

