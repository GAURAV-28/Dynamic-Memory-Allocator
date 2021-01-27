// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    
    public AVLTree Insert(int address, int size, int key) 
    { 
        AVLTree toInsert = new AVLTree(address, size, key);
        AVLTree tree = this;
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
        AVLTree tree = this;
        while(tree.parent!=null){
            tree=tree.parent;
        } 
        boolean t = checkDel(getRoot(this), e);
        //System.out.println(t);
        recDel(tree.right, e);
        return t;
    }
        
    public AVLTree Find(int key, boolean exact)
    {
        return search(getRoot(this), key, exact);
    }

    public AVLTree getFirst()
    { 
        return getMin(getRoot(this));
    }

    public AVLTree getNext()
    {
        if(this.parent==null) return null;
        return getSuccessor(this);
    }

    public boolean sanity()
    {
        if(loopDetect(this) || (this.right!=null && loopDetect(this.right))) {
            return false;
        }

        AVLTree tree = this;
        while(tree.parent!=null){
            tree=tree.parent;
        }
        if(tree.key!=-1 || tree.address!=-1) return false;
        if(tree.left!=null) return false;


        if(!checkKeyInv(tree.right)){
            return false;
        }

        if(!checkPointerInv(tree.right)){
            return false;
        }

        if(!checkBal(tree.right)){
            return false;
        }

        return true;
    }

    //private functions:
    private AVLTree add(AVLTree root, AVLTree toInsert){
        if(root==null){
            return toInsert;
        }
        if(root.parent==null){
            AVLTree temp=add(root.right, toInsert);
            root.right=temp;
            temp.parent=root;
            return root;
        }
        
        else{
            if(root.key>toInsert.key){
                AVLTree temp=add(root.left, toInsert);
                root.left=temp;
                temp.parent=root;
            }
            else if(root.key==toInsert.key){
                if(root.address>toInsert.address){
                    AVLTree temp=add(root.left, toInsert);
                    root.left=temp;
                    temp.parent=root;
                }
                else{
                    AVLTree temp=add(root.right, toInsert);
                    root.right=temp;
                    temp.parent=root;
                }
            }
            else{
                AVLTree temp=add(root.right, toInsert);
                root.right=temp;
                temp.parent=root;
            }
           
        }
        root = reBalance(root);
        return root;
        
    }

    private AVLTree reBalance(AVLTree node){
        updateHeight(node);
        int diff = getDiff(node);
        //System.out.println("---------------");
        //System.out.println(diff+" "+node.key);
        if(diff>1){
            if(height(node.left.left) >= height(node.left.right)){
                node = rotateRight(node);
            }
            else{
            AVLTree temp = rotateLeft(node.left);
            node.left = temp;
            temp.parent = node;
            node = rotateRight(node);
            }
        }

        if(diff<-1){
            if(height(node.right.right) >= height(node.right.left)){
                node = rotateLeft(node);
            } 
            else{
                //System.out.println("rlr");
                AVLTree temp = rotateRight(node.right);
                node.right = temp;
                temp.parent = node;
                node = rotateLeft(node);
            }
        }

        return node;
    }

    private void updateHeight(AVLTree node){
        node.height = 1+ max(height(node.left), height(node.right));
    }

    private int height(AVLTree node){
        if(node==null) return -1;
        return node.height;
    }

    private int getDiff(AVLTree node){
        if(node==null) return 0;
        return height(node.left)-height(node.right);
    }

    private int max(int a, int b){
        if(a>b) return a;
        return b;
    }

    private AVLTree rotateLeft(AVLTree x){
        AVLTree y = x.right;
        AVLTree t = y.left;
        if(x.parent.left==x){
            x.parent.left=y;
        }
        else{
            x.parent.right=y;
        }
        y.parent = x.parent;
        y.left = x;
        x.parent = y;
        if(t!=null) t.parent = x;
        x.right = t;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private AVLTree rotateRight(AVLTree x){
        AVLTree y = x.left;
        AVLTree t = y.right;
        if(x.parent.left==x){
            x.parent.left=y;
        }
        else{
            x.parent.right=y;
        }
        y.parent = x.parent;
        y.right = x;
        x.parent = y;
        if(t!=null) t.parent = x;
        x.left = t;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private AVLTree getRoot(AVLTree x){
        AVLTree tree = x;
        while(tree.parent!=null){
            tree=tree.parent;
        }
        return tree.right;
    }

    private AVLTree search(AVLTree root,int key, boolean exact){
        
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

    private AVLTree getSuccessor(AVLTree root) { 
        if (root.right != null) { 
            return getMin(root.right); 
        }
        AVLTree par = root.parent; 
        while (par != null && root == par.right) { 
            root = par; 
            par = par.parent; 
        } 
        return par; 
    } 

    private AVLTree getMin(AVLTree root){
        if(root==null){
            return null;
        }
        while(root.left!=null){
            root=root.left;
        }
        return root;
    }

    private AVLTree getMax(AVLTree root){
        if(root==null){
            return null;
        }
        while(root.right!=null){
            root=root.right;
        }
        return root;
    }

    private AVLTree recDel(AVLTree root, Dictionary e){
        
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
                    //reBalance(root.parent);
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
                    AVLTree temp = new AVLTree(min.address, min.size, min.key);

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
                    return reBalance(temp);
                }
            }
            else{
                root.right = recDel(root.right, e);
            }
        }
        else{
            root.left = recDel(root.left, e);
        }

        return reBalance(root);

    }

    private boolean checkDel(AVLTree root, Dictionary x){
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

    private boolean checkKeyInv(AVLTree root){
        if(root==null || (root.right==null && root.left==null)){
            return true;
        }
        
        if(root.left==null){
            AVLTree x = getMin(root.right);
            if(x.key<root.key) return false;
            if(root.key==x.key && x.address<root.address) return false;
            return checkKeyInv(root.right);
        }
        else if(root.right==null){
            AVLTree x = getMax(root.left);
            if(x.key>root.key) return false;
            if(root.key==x.key && x.address>root.address) return false;
            return checkKeyInv(root.left);
        }
        else{
            AVLTree x = getMin(root.right);
            AVLTree y = getMax(root.left);
            if(x.key<root.key) return false;
            if(root.key==x.key && x.address<root.address) return false;
            if(y.key>root.key) return false;
            if(root.key==y.key && y.address>root.address) return false;
            return checkKeyInv(root.left) && checkKeyInv(root.right);
        }
        
    }

    private boolean checkPointerInv(AVLTree root){
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

    private boolean loopDetect(AVLTree root){
        if(root==null){
            return true;
        }
        AVLTree slow = root;
        AVLTree fast = root;
        while(slow!=null && fast!=null && fast.parent!=null){
            slow=slow.parent;
            fast=fast.parent.parent;
            if(slow==fast){
                return true;
            }
        }
        return false; 
    }

    private boolean checkBal(AVLTree node){
        if(node==null) return true;
        if(height(node.left)-height(node.right)>1 || height(node.left)-height(node.right)<-1) return false;
        return checkBal(node.left) && checkBal(node.right);
    }

    
}


