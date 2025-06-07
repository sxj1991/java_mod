package com.xjsun.filepdf.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfService {

    public byte[] generatePdfFromHtml(String htmlContent) throws IOException {
        String styledHtml = "<html><head><style>body { font-family: 'Source Han Sans SC'; }</style></head><body>" + htmlContent + "</body></html>";

        W3CDom w3cDom = new W3CDom();
        Document w3cDoc = w3cDom.fromJsoup(Jsoup.parse(styledHtml));

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();

            // Load font from classpath using the expected supplier interface
            try (InputStream fontStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("fonts/SourceHanSansSC-Regular.ttf")) {
                if (fontStream != null) {
                    // The library expects a supplier. We need to read the stream into a byte array first,
                    // as the stream will be closed before the renderer can use it.
                    byte[] fontBytes = fontStream.readAllBytes();
                    builder.useFont(() -> new ByteArrayInputStream(fontBytes), "Source Han Sans SC");
                } else {
                    // Font not found, log a severe error.
                    System.err.println("FATAL: Font 'SourceHanSansSC-Regular.ttf' not found in resources/fonts/. PDF generation will fail for CJK chars.");
                }
            }

            builder.withW3cDocument(w3cDoc, "/");
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        } catch (Exception e) {
            // Adding more detailed exception logging
            System.err.println("Error during PDF generation: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Failed to generate PDF", e);
        }
    }
} 