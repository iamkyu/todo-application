package todoapp.web.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.view.AbstractView;
import todoapp.commons.domain.Spreadsheet;
import todoapp.commons.web.support.SpreadsheetSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvView extends AbstractView implements SpreadsheetSupport {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public CsvView() {
        setContentType("text/csv");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        log.debug("CsvView: CSV 컨텐츠를 출력합니다.");

        Spreadsheet sheet = obtainSpreadsheet(model);

        String fileName = URLEncoder.encode(sheet.getName() + ".csv", "UTF-8");
        String contentDisposition = String.format("attachment; filename=\"%s\"", fileName);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);

        if (sheet.hasHeader()) {
            String header = sheet.getHeader()
                    .map(row -> joining(row.getCells().stream()
                            .map(Spreadsheet.Cell::getValue)
                            .collect(Collectors.toList()), ","))
                    .orElse("");
            response.getWriter().println(header);
        }

        if (sheet.hasRows()) {
            for (Spreadsheet.Row row : sheet.getRows()) {
                String line = joining(row.getCells().stream()
                        .map(Spreadsheet.Cell::getValue)
                        .collect(Collectors.toList()), ",");
                response.getWriter().println(line);
            }
        }

        response.flushBuffer();
    }

}
