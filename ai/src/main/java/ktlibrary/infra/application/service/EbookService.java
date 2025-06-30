package ktlibrary.infra.application.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EbookService {
    public byte[] generateEbook(String title, String authorName, String content) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            document.add(new Paragraph("제목: " + title).setBold().setFontSize(16));
            document.add(new Paragraph("작가: " + authorName).setItalic().setFontSize(12));
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph(content));

            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("전자책 생성 실패", e);
        }
    }
}
