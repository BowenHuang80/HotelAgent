/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.jsfbean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author brian
 */
//<f:converter converterId="defaultDateConverter" />
@FacesConverter("customDateConverter")
public class CustomeDateConverter extends DateTimeConverter {
  
    public CustomeDateConverter() {
        super();
        this.setTimeZone(TimeZone.getDefault());
        // handle the format here set in the attribute
        this.setPattern("MM/dd/yyyy");
    }
  
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Date date = null;
        // formats to handle
        return date;
    }
  
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        //handle the html output        
        return "";
    }
}
