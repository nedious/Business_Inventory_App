package imhoff.wgubikerecreated.model;

/**
 * Class: Outsourced allows values for outsourced to be entered.
 */
public class Outsourced extends Part {
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Company name getter.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Company name setter.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}