package com.carson.springtestdemo.utils;

/**
 * @author carson
 */

import java.awt.Color;
import java.io.*;


import com.carson.springtestdemo.compoment.Converter;
import com.carson.springtestdemo.compoment.Errors;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;


import fr.opensagres.xdocreport.itext.extension.font.ITextFontRegistry;

@Slf4j
public class DocxToPdfConverter extends Converter {


    public DocxToPdfConverter(InputStream inStream, OutputStream outStream, boolean showMessages,
                              boolean closeStreamsWhenComplete) {
        super(inStream, outStream, showMessages, closeStreamsWhenComplete);
    }


    @Override
    public void convert() throws Exception {
        loading();
        PdfOptions options = PdfOptions.create();
        XWPFDocument document = new XWPFDocument(inStream);
//支持中文字体
        options.fontProvider(new ITextFontRegistry() {
            @Override
            public Font getFont(String familyName, String encoding, float size, int style, Color color) {
                try {
                    String path = "/Users/carson/Desktop/simsun.ttc,0";
                    BaseFont bfChinese = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    Font fontChinese = new Font(bfChinese, size, style, color);
                    if (familyName != null) {
                        fontChinese.setFamily(familyName);
                    }
                    return fontChinese;
                } catch (Throwable e) {
                    log.error("设置中文字体异常:" + e.getMessage());
                    return ITextFontRegistry.getRegistry().getFont(familyName, encoding, size, style, color);
                }
            }
        });
        processing();
        PdfConverter.getInstance().convert(document, outStream, options);
        finished();
    }

    public static void main(String[] args) throws Exception {

        String path = "/Users/carson/Desktop/preview.pdf";
        File file = new File(path);
        OutputStream outputStream = new FileOutputStream(file);
        String url = "1.docx";
        File docx = new File("/Users/carson/Desktop/" + url);
        InputStream inputStream = new FileInputStream(docx);

        if (!file.exists()) {
            file.createNewFile();
        }
        if (url.endsWith(Errors.WORD_DOCX)) {
            Converter converter = new DocxToPdfConverter(inputStream, outputStream, false, true);
            converter.convert();
        }
    }

}
