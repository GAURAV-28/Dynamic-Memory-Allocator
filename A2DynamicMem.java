// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 

    public void Defragment() {

        Dictionary store;
        if(type==2){
            store = new BSTree();
        }
        else{
            store = new AVLTree();
        }
        Dictionary st = freeBlk.getFirst();
        for(;st!=null;st=st.getNext()){
            store.Insert(st.address, st.size, st.address);
        }

        
        Dictionary x = store.getFirst();
        
        
        if(x==null) return;
        Dictionary y = x.getNext();
        
        while(y!=null){
            if(x.address+x.size==y.address){
                //System.out.println("om"+x.address+" "+y.address);
                Dictionary p,q;
                if(type==2){
                    p = new BSTree(x.address, x.size, x.size);
                    q = new BSTree(y.address, y.size, y.size);
                }
                else{
                    p = new AVLTree(x.address, x.size, x.size);
                    q = new AVLTree(y.address, y.size, y.size);
                }
                store.Delete(x);
                store.Delete(y);
                freeBlk.Delete(p);
                freeBlk.Delete(q);
               
                freeBlk.Insert(p.address, p.size+q.size, p.size+q.size);
                x = store.Insert(p.address, p.size+q.size, p.address);
                y = x.getNext();
            }
            else{
                x=y;
                y=y.getNext();
            }
            
        }
        store=null;
        return;

    }

}