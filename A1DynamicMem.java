
// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    public int Allocate(int blockSize) {
        
        Dictionary block = freeBlk.Find(blockSize, false);
        
        if(block==null || blockSize<=0){
            //System.out.println("om");
            return -1;
        }
        else{
            int add = block.address;
            int size = block.size-blockSize;
            freeBlk.Delete(block);
            allocBlk.Insert(add,blockSize,add);
            //System.out.println(freeBlk.getFirst().key);

            if(size!=0){
                freeBlk.Insert(add+blockSize,size,size);
                
            }
        
            return add;
        }
    } 
    
    public int Free(int startAddr) {
        Dictionary blk = allocBlk.Find(startAddr, true);
        if(blk == null || startAddr<0){
            return -1;
        }
        else{
            //int size = blk.size;
            allocBlk.Delete(blk);
            freeBlk.Insert(startAddr, blk.size, blk.size);
            
            return 0;
        }
        
    }

    public static void main(String[] args){
       A1DynamicMem obj = new A1DynamicMem(20,2);
       obj.Allocate(6);
       obj.Free(1);
       obj.Allocate(2);
       obj.Allocate(1);
       obj.Allocate(1);
        obj.Free(9);
        obj.Allocate(1);

       System.out.println(obj.freeBlk.getFirst().address);
       System.out.println(obj.allocBlk.getFirst().key);
    }
}