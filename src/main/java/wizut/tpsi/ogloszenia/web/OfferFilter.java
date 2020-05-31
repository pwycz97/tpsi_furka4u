/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizut.tpsi.ogloszenia.web;

/**
 *
 * @author piotr
 */
public class OfferFilter {
    
    Integer manufacturerId;
    Integer modelId;
    Integer yearFrom;
    Integer yearTo;
    Integer priceFrom;
    Integer priceTo;
    Integer fuelTypeId;
    public String opis;
    public String sort;

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
    
    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }
    
    public Integer getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(Integer yearFrom) {
        this.yearFrom = yearFrom;
    }

    public Integer getYearTo() {
        return yearTo;
    }

    public void setYearTo(Integer yearTo) {
        this.yearTo = yearTo;
    }

    public Integer getPriceFrom(){
        return priceFrom;
    }
    
    public void setPriceFrom(Integer priceFrom){
        this.priceFrom = priceFrom;
    }
    
    public Integer getPriceTo(){
        return priceTo;
    }
    
    public void setPriceTo(Integer priceTo){
        this.priceTo = priceTo;
    }
    
    public Integer getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(Integer fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }
}
