package mx.loal.pharmacy_admin_api.utils.reports;

import lombok.RequiredArgsConstructor;
import mx.loal.pharmacy_admin_api.props.JasperProperties;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSwapFile;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class PdfUtils {

    private final JasperProperties jasperProperties;

    public File generarPdf(String archivoReporte, Map<String, Object> params, Map<String, String> imagenes, Map<String, String> subReportes,
                           String archivoDestino, Collection<?> listaBeans) throws Exception {

        if (Objects.nonNull(imagenes) && !imagenes.isEmpty()) {
            for (var nombreImagen : imagenes.keySet()) {
                var image = ImageIO.read(new FileInputStream(jasperProperties.getTemplates() + "/images/" + nombreImagen));
                params.put(imagenes.get(nombreImagen), image);
            }
        }

        var tempFile = File.createTempFile(archivoDestino, ".pdf");
        var tempPath = tempFile.getAbsolutePath();

        var virtualizer = new JRSwapFileVirtualizer(1, new JRSwapFile(tempPath.replace(tempFile.getName(), ""), 2048, 1024), false);
        params.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        var origenDatos = new JRBeanCollectionDataSource(listaBeans);
        var reporte = getJasper(archivoReporte, jasperProperties.getTemplates());
        adicionaSubReportes(subReportes, params);

        var print = JasperFillManager.fillReport(reporte, params, origenDatos);
        JasperExportManager.exportReportToPdfFile(print, tempPath);

        virtualizer.cleanup();

        tempPath = tempPath.replace("\\", "//");

        return new File(tempPath);
    }

    private void adicionaSubReportes(Map<String, String> subReportes, Map<String, Object> params) throws Exception {
        if (Objects.nonNull(subReportes) && !subReportes.isEmpty()) {
            for (var nombreSubReporte : subReportes.keySet()) {
                var reporte = getJasper(nombreSubReporte, jasperProperties.getTemplates() + "/subreports");
                params.put(subReportes.get(nombreSubReporte), reporte);
            }
        }
    }

    public JasperReport getJasper(String nombreReporte, String dir) throws Exception {

        var archivoCompilado = dir + "/" + nombreReporte.replace(".jrxml", ".jasper");
        nombreReporte = dir + "/" + nombreReporte;

        var report = new File(nombreReporte);

        if (report.isFile())
            JasperCompileManager.compileReportToFile(nombreReporte, archivoCompilado);

        return (JasperReport) JRLoader.loadObject(new File(archivoCompilado));
    }

}
