/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageprocessing;

/**
 *
 * @author aleksandar
 */
public class FilterPickList {

    private ImageFilter filterObject;
    private String filterName;

    public FilterPickList(ImageFilter filterObject, String filterName) {
        this.filterObject = filterObject;
        this.filterName = filterName;
    }

    public ImageFilter getFilterObject() {
        return filterObject;
    }

    public void setFilterObject(ImageFilter filterObject) {
        this.filterObject = filterObject;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    @Override
    public String toString() {
        return this.getFilterName();
    }
    
    

}
