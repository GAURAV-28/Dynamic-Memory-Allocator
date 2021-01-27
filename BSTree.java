// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    //private functions begin
    private BSTree add(BSTree root, BSTree toInsert){
        if(root==null){
            return toInsert;
        }
        if(root.parent==null){
            BSTree temp=add(root.right, toInsert);
            root.right=temp;
            temp.parent=root;
        }
        
        else{
            if(root.key>toInsert.key){
                BSTree temp=add(root.left, toInsert);
                root.left=temp;
                temp.parent=root;
            }
            else if(root.key==toInsert.key){
                if(root.address>toInsert.address){
                    BSTree temp=add(root.left, toInsert);
                    root.left=temp;
                    temp.parent=root;
                }
                else{
                    BSTree temp=add(root.right, toInsert);
                    root.right=temp;
                    temp.parent=root;
                }
            }
            else{
                BSTree temp=add(root.right, toInsert);
                root.right=temp;
                temp.parent=root;
            }
            
        }
        return root;
        
    }

    private BSTree search(BSTree root,int key, boolean exact){
        
        if(root==null){
            return null;
        }
        if(exact){
            if(root.key<key){
                return search(root.right, key, true);
            }
            else if(root.key>key){
                return search(root.left, key, true);
            }
            else{
                if(search(root.left, key, true)!=null) return search(root.left, key, true);
                return root;
            }
        }
        else{
            if(root.key>key && root.left==null){
                return root;
            }
            else if(root.key<key && root.right==null){
                return root.getNext();
            }

            else if(root.key<key){
                return search(root.right, key, false);
            }
            else if(root.key>key){
                return search(root.left, key, false);
            }
            else{
                if(search(root.left, key, true)!=null) return search(root.left, key, true);
                return root;
            }
        }

    }

    private BSTree getSuccessor(BSTree root) { 
        if (root.right != null) { 
            return getMin(root.right); 
        }
        BSTree par = root.parent; 
        while (par != null && root == par.right) { 
            root = par; 
            par = par.parent; 
        } 
        return par; 
    } 

    private BSTree getMin(BSTree root){
        if(root==null){
            return null;
        }
        while(root.left!=null){
            root=root.left;
        }
        return root;
    }

    private BSTree getMax(BSTree root){
        if(root==null){
            return null;
        }
        while(root.right!=null){
            root=root.right;
        }
        return root;
    }

    private BSTree recDel(BSTree root, Dictionary e){
        
        if(root==null){
            return null;
        }
        if(root.parent==null && e.key==-1 && e.address==-1){
            return null;
        }
        if(root.key<e.key){
            root.right = recDel(root.right, e);
        }
        else if(root.key==e.key){
            if(root.address>e.address){
                root.left = recDel(root.left, e);
            }
            else if(root.address==e.address){
                if(root.left==null && root.right==null){
                    if(root.parent.left==root){
                        root.parent.left=null;
                    }
                    else{
                        root.parent.right=null;
                    }
                    return null;
                    
                }
                else if (root.left == null) 
                {
                    root.right.parent=root.parent;
                    if(root.parent.left==root){
                        root.parent.left=root.right;
                    }
                    else{
                        root.parent.right=root.right;
                    }
                    return root.right;
                }
                else if (root.right == null) {
                    root.left.parent=root.parent;
                    if(root.parent.left==root){
                        root.parent.left=root.left;
                    }
                    else{
                        root.parent.right=root.left;
                    }
                    return root.left;
                }
                else{
                    Dictionary min = getMin(root.right);
                    BSTree temp = new BSTree(min.address, min.size, min.key);

                    temp.parent = root.parent;
                    temp.left=root.left;
                    temp.right=root.right;

                    temp.right.parent=temp;
                    temp.left.parent=temp;

                    if(root.parent.left==root){
                        root.parent.left=temp;
                    }
                    else{
                        root.parent.right=temp;
                    }

                    root.right=null;
                    root.left=null;
                    //root.parent=null;

                    temp.right = recDel(temp.right, min);
                    return temp;
                }
            }
            else{
                root.right = recDel(root.right, e);
            }
        }
        else{
            root.left = recDel(root.left, e);
        }

        return root;

    }

    private boolean checkDel(BSTree root, Dictionary x){
        if(root==null){
            return false;
        }

        if(root.key<x.key){
            return checkDel(root.right, x);
        }
        else if(root.key>x.key){
            return checkDel(root.left, x);
        }
        else{
            if(root.address>x.address){
                return checkDel(root.left, x);
            }
            else if(root.address==x.address){
                return true;
            }
            else{
                return checkDel(root.right, x);
            }

        }
        
        
    }

    private BSTree getRoot(BSTree x){
        BSTree tree = x;
        while(tree.parent!=null){
            tree=tree.parent;
        }
        return tree.right;
    }

    //private functions end

    public BSTree Insert(int address, int size, int key) 
    {
        BSTree toInsert = new BSTree(address, size, key);
        BSTree tree = this;
        while(tree.parent!=null){
            tree=tree.parent;
        }
        tree = add(tree, toInsert);
        return toInsert;
    }

    public boolean Delete(Dictionary e)
    {
        if(e==null){
            return false;
        }
        BSTree tree = this;
        while(tree.parent!=null){
            tree=tree.parent;
        } 
        
        boolean t = checkDel(getRoot(this), e);
        //System.out.println(t);
        recDel(tree.right, e);


        return t;
    }
        
    public BSTree Find(int key, boolean exact)
    {
        return search(getRoot(this), key, exact);
    }

    public BSTree getFirst()
    { 
        return getMin(getRoot(this));
    }

    public BSTree getNext(){
        if(this.parent==null) return null;
        return getSuccessor(this);
    }

    public boolean sanity()

    {
        if(loopDetect(this) || (this.right!=null && loopDetect(this.right))) {
            return false;
        }

        BSTree tree = this;
        while(tree.parent!=null){
            tree=tree.parent;
        }
        if(tree.key!=-1 || tree.address!=-1) return false;
        if(tree.left!=null) return false;


        if(!checkKeyInv(getRoot(this))){
            return false;
        }

        if(!checkPointerInv(getRoot(this))){
            return false;
        }


        return true;
    }

    //private sanity functions
    private boolean checkKeyInv(BSTree root){
        if(root==null || (root.right==null && root.left==null)){
            return true;
        }
        
        if(root.left==null){
            BSTree x = getMin(root.right);
            if(x.key<root.key) return false;
            if(root.key==x.key && x.address<root.address) return false;
            return checkKeyInv(root.right);
        }
        else if(root.right==null){
            BSTree x = getMax(root.left);
            if(x.key>root.key) return false;
            if(root.key==x.key && x.address>root.address) return false;
            return checkKeyInv(root.left);
        }
        else{
            BSTree x = getMin(root.right);
            BSTree y = getMax(root.left);
            if(x.key<root.key) return false;
            if(root.key==x.key && x.address<root.address) return false;
            if(y.key>root.key) return false;
            if(root.key==y.key && y.address>root.address) return false;
            return checkKeyInv(root.left) && checkKeyInv(root.right);
        }
        
    }

    private boolean checkPointerInv(BSTree root){
        if(root==null || (root.right==null && root.left==null)){
            return true;
        }

        if(root.left==null){
            if(root.right.parent!=root) return false;
            return checkPointerInv(root.right);
        }
        else if(root.right==null){
            if(root.left.parent!=root) return false;
            return checkPointerInv(root.left);
        }
        else{
            if(root.right.parent!=root || root.left.parent!=root) return false;
            return checkPointerInv(root.left) && checkPointerInv(root.right);
        }

    }

    private boolean loopDetect(BSTree root){
        if(root==null){
            return true;
        }
        BSTree slow = root;
        BSTree fast = root;
        while(slow!=null && fast!=null && fast.parent!=null){
            slow=slow.parent;
            fast=fast.parent.parent;
            if(slow==fast){
                return true;
            }
        }
        return false; 
    }

}


 


