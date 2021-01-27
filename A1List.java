// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
        A1List toInsert = new A1List(address, size, key);

        if(this.next==null) return null;

        toInsert.next = this.next;
        this.next = toInsert;
        toInsert.prev = this;
        toInsert.next.prev = toInsert;
        

        return toInsert;
    }

    public boolean Delete(Dictionary d) 
    {
        boolean t = false;
        if(d==null){
            return t;
        }
        A1List toDelete;
        
        if(this.prev==null){
            toDelete=this.next;
        }
        else{
            if(this.key==d.key && this.address==d.address && this.size==d.size){
                
                if(this.prev.prev==null){
                    this.key=this.prev.key;
                    this.address=this.prev.address;
                    this.size=this.prev.size;
                    this.prev=null;
                    return true;
                }
                else{
                    this.key=this.prev.key;
                    this.address=this.prev.address;
                    this.size=this.prev.size;
                    A1List temp = this.prev;
                    this.prev=temp.prev;
                    temp.prev.next=this;
                    return true;
                }
            }
            else{
                toDelete=this;
            }
        }
        
        
        while(toDelete.next!=null){
            if(toDelete.key==d.key && toDelete.address==d.address && toDelete.size==d.size){
                t=true;
                if(toDelete.next==null){
                    return false;
                }
                toDelete.next.prev=toDelete.prev;
                toDelete.prev.next=toDelete.next;
                return true; //
            }
            toDelete=toDelete.next;
        }

        A1List toDelete1 = this;
        if(toDelete1.next==null){
            toDelete1=this.prev;
        }

        else{
            if(this.key==d.key && this.address==d.address && this.size==d.size){
                
                if(this.prev.prev==null){
                    this.key=this.prev.key;
                    this.address=this.prev.address;
                    this.size=this.prev.size;
                    this.prev=null;
                    return true;
                }
                else{
                    this.key=this.prev.key;
                    this.address=this.prev.address;
                    this.size=this.prev.size;
                    A1List temp = this.prev;
                    this.prev=temp.prev;
                    temp.prev.next=this;
                    return true;
                }
            }
            else{
                toDelete=this;
            }
        }

        while(toDelete1.prev!= null){
            if(toDelete1.key==d.key && toDelete1.address==d.address && toDelete1.size==d.size){
                t=true;
                if(toDelete1.prev==null){
                    return false;
                }
                toDelete1.prev.next=toDelete1.next;
                toDelete1.next.prev=toDelete1.prev;
                
                return true; //
            }
            toDelete1=toDelete1.prev;
        }

        return t;
    }

    public A1List Find(int k, boolean exact)
    { 
        A1List toFind1 = this.getFirst();
        if(toFind1==null) return null;
        
        while(toFind1.next != null){
            //System.out.println(toFind1.key);
            if(exact==false && toFind1.key>=k){
                //System.out.println(" match forward");
                if(toFind1.prev==null || toFind1.next==null) return null;
                return toFind1;
            }
            else if(exact==true && toFind1.key==k){
                if(toFind1.prev==null || toFind1.next==null) return null;
                return toFind1;
            }
            else{
                toFind1=toFind1.next;
            }
        }
        
        //System.out.println("No match");
        return null;
    }

    public A1List getFirst()
    {
        A1List toReturn = this;
        while(toReturn.prev != null){
            toReturn = toReturn.prev;
        }
        if(toReturn.next.next == null){
            return null;
        }
        else{
            return toReturn.next;
        }
    }
    
    public A1List getNext() 
    {
        if(this.next.next==null){
            return null;
        }
        
        return this.next;
    }

    public boolean sanity()
    {
        A1List slowF = this;
        A1List fastF = this;
        while(slowF!=null && fastF!=null && fastF.next!=null){
            slowF=slowF.next;
            fastF=fastF.next.next;
            if(slowF==fastF){
                return false;
            }
        }

        A1List slowB = this;
        A1List fastB = this;
        while(slowB!=null && fastB!=null && fastB.prev!=null){
            slowB=slowB.prev;
            fastB=fastB.prev.prev;
            if(slowB==fastB){
                return false;
            }
        } 
        
        A1List forw = this;
        if(forw.next==null && forw.key!=-1) return false;
        
        while(forw.next != null){
            if(forw.prev==null){
                if(forw.key!=-1){
                    return false;
                }
            }
            else{
                if(forw.prev.next!=forw || forw.next.prev!=forw){
                    return false;
                }
            }
            forw=forw.next;
        }

        A1List back = this;
        if(back.prev==null && back.key!=-1) return false;
        while(back.prev!=null){
            if(back.next==null){
                if(back.key!=-1) return false;
            }
            else{
                if(back.prev.next!=back || back.next.prev!=back){
                    return false;
                }
            }
            back=back.prev;
        }

               
        
        return true;
    }


    
}


