package co.com.homologacionesu.util;

import java.util.Calendar;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * Objetivo: Tener métodos para validar en la aplicación
 * @author Daniel Serna
 */
public class JSFUtil {

    /**
     * Descripciòn: Mètodo que obtiene la fuente para la generaciònd el excel
     * @param fontColor
     * @param fontHeight
     * @param fontBold
     * @param workbook
     * @return 
     */
    public static HSSFFont createFont(short fontColor, short fontHeight,
            boolean fontBold, HSSFWorkbook workbook) {

        HSSFFont font = workbook.createFont();
        font.setBold(fontBold);
        font.setColor(fontColor);
        font.setFontName("Arial");
        font.setFontHeightInPoints(fontHeight);

        return font;
    }

    /**
     * Descripciòn: Mètodo que proporciona el estilo al excel generado
     * @param font
     * @param cellAlign
     * @param cellColor
     * @param cellBorder
     * @param cellBorderColor
     * @param workbook
     * @return 
     */
    public static HSSFCellStyle createStyle(HSSFFont font, short cellAlign,
            short cellColor, boolean cellBorder, short cellBorderColor,
            HSSFWorkbook workbook) {

        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(cellAlign);
        style.setFillForegroundColor(cellColor);
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        if (cellBorder) {
            style.setBorderTop(XSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            style.setBorderRight(XSSFCellStyle.BORDER_THIN);
            style.setBorderBottom(XSSFCellStyle.BORDER_THIN);

            style.setTopBorderColor(cellBorderColor);
            style.setLeftBorderColor(cellBorderColor);
            style.setRightBorderColor(cellBorderColor);
            style.setBottomBorderColor(cellBorderColor);
        }

        return style;
    }

    /**
     * Descripciòn: Mètodo que suma y resta fechas
     * @param fecha
     * @param dias
     * @return 
     */
    public static Date sumarRestarDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos

    }
}
