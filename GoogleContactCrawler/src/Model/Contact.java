package Model;

public class Contact extends Ancestor {

    private Ancestor ancestor;

    public Contact(Ancestor ancestor) {
        super();
        this.ancestor = ancestor;
    }

    public Ancestor getAncestor() {
        return ancestor;
    }

    public void setAncestor(Ancestor ancestor) {
        this.ancestor = ancestor;
    }

}
