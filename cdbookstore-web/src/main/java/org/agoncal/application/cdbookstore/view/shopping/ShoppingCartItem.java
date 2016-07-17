package org.agoncal.application.cdbookstore.view.shopping;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

public class ShoppingCartItem {

    // ======================================
    // =             Attributes             =
    // ======================================

    @NotNull
    @Size(min = 1, max = 200)
    protected String title;

    @Size(min = 1, max = 10000)
    protected String description;

    @Min(1)
    protected Float unitCost;

    @NotNull
    @Min(1)
    private Integer quantity;

    // ======================================
    // =            Constructors            =
    // ======================================

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(String title, String description, Float unitCost, Integer quantity) {

        this.title = title;
        this.description = description;
        this.unitCost = unitCost;
        this.quantity = quantity;
    }

    // ======================================
    // =          Business methods          =
    // ======================================

    public Float getSubTotal() {
        return unitCost * quantity;
    }

    // ======================================
    // =         Getters & setters          =
    // ======================================

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Float unitCost) {
        this.unitCost = unitCost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // ======================================
    // =   Methods hash, equals, toString   =
    // ======================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartItem that = (ShoppingCartItem) o;
        return Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(unitCost, that.unitCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, unitCost);
    }

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
            "title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", unitCost=" + unitCost +
            ", quantity=" + quantity +
            '}';
    }
}
