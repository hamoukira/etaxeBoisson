
//import controller.util.DateUtil;
import java.time.LocalDateTime;
//import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("dateTimeAttributeConverter")
public class DateTimeAttributeConverter implements Converter {

    @Override
    public LocalDateTime getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("*****************getAsObject*******************");
        System.out.println("value :: "+value);
//        System.out.println("LocalDateTime value :: "+DateUtil.convrtString(value,"dd/MM/yyyy HH:mm:ss").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//        return DateUtil.convrtString(value,"dd/MM/yyyy HH:mm:ss").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return LocalDateTime.parse(value, dtf);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        System.out.println("***************** DateAttributeConverter getAsString*******************");
        return String.valueOf(value);
    }

   

}