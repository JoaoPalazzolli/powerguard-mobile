package br.project.powerguard;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PDFGenerator {
    private Context context;
    private String economia, consumoTotal, ligados, desligados;

    // Fontes personalizadas
    private static Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private static Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

    public PDFGenerator(Context context, String economia, String consumoTotal, String ligados, String desligados) {
        this.context = context;
        this.economia = economia;
        this.consumoTotal = consumoTotal;
        this.ligados = ligados;
        this.desligados = desligados;
    }

    public void generatePDF() {
        Document document = new Document();

        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String fileName = "Relatorio_Energia_" + timeStamp + ".pdf";
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(downloadsDir, fileName);

            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            addContent(document);
            document.close();

            // Mostrar Toast de sucesso
            Toast.makeText(context, "Relatório baixado com sucesso!", Toast.LENGTH_LONG).show();

            // Abrir o PDF após o download
            openPDF(file);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro ao gerar PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void openPDF(File file) {
        // Criar URI usando FileProvider (para Android 7.0+)
        Uri uri = FileProvider.getUriForFile(
                context,
                context.getApplicationContext().getPackageName() + ".provider",
                file
        );

        // Criar Intent para visualizar o PDF
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Se não houver visualizador de PDF instalado
            Toast.makeText(context, "Nenhum aplicativo para visualizar PDF encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void addMetaData(Document document) {
        document.addTitle("Relatório de Consumo de Energia");
        document.addSubject("Relatório gerado pelo aplicativo");
        document.addKeywords("energia, consumo, relatório");
        document.addAuthor("Aplicativo Aula");
        document.addCreator("Aplicativo Aula");
    }

    private void addTitlePage(Document document) throws DocumentException, IOException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_powerguard_sem_fundo);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image logo = Image.getInstance(stream.toByteArray());
        logo.scaleToFit(200f, 100f);
        logo.setAlignment(Element.ALIGN_CENTER);
        document.add(logo);


        addEmptyLine(preface, 1); // Espaço após a imagem

        // Título
        Paragraph title = new Paragraph("Relatório de Consumo de Energia", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        addEmptyLine(preface, 1);

        // Data
        Paragraph date = new Paragraph(
                "Gerado em: " + new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date()),
                normalFont);
        date.setAlignment(Element.ALIGN_CENTER);
        document.add(date);

        addEmptyLine(preface, 3);
        document.add(preface);
    }

    private void addContent(Document document) throws DocumentException {
        // Tabela de resumo
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        // Células da tabela
        addTableHeader(table);
        addRows(table);

        document.add(table);

        // Espaçamento
        Paragraph space = new Paragraph(" ");
        document.add(space);
    }

    private void addTableHeader(PdfPTable table) {
        PdfPCell cell;

        cell = new PdfPCell(new Phrase("Item", subtitleFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Valor", subtitleFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private void addRows(PdfPTable table) {
        table.addCell(new Phrase("Economia Estimada", normalFont));
        table.addCell(new Phrase(economia, normalFont));

        table.addCell(new Phrase("Consumo Total", normalFont));
        table.addCell(new Phrase(consumoTotal, normalFont));

        table.addCell(new Phrase("Dispositivos Ligados", normalFont));
        table.addCell(new Phrase(ligados, normalFont));

        table.addCell(new Phrase("Dispositivos Desligados", normalFont));
        table.addCell(new Phrase(desligados, normalFont));
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}