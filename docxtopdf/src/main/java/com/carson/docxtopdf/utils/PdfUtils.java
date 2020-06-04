package com.carson.docxtopdf.utils;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import fr.opensagres.xdocreport.itext.extension.font.ITextFontRegistry;
import fr.opensagres.xdocreport.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JiShaochen
 * @date 2018/4/14 16:03
 * @desc .
 */
@Slf4j
public class PdfUtils {

    public static void main(String[] args) throws Exception {

        String path =  "/Users/carson/Desktop/preview.pdf";
        File file = new File(path);
        OutputStream outputStream = new FileOutputStream(file);
        String url = "程序开发规范V1.1.docx";
        File docx = new File("/Users/carson/Desktop/"+url);
        InputStream inputStream = new FileInputStream(docx);

        if(!file.exists()){
            file.createNewFile();
        }
        Map<String,String> params = new HashMap<>();
        wordConverterToPdf(inputStream,outputStream,params);

    }
    /**
     * @Author: ShaoChen
     * @Description: PDF转为多张图片 sourcePath是源文件， targetSource是
     * @Date: 16:07 2018/4/14
     */
    public static Integer pdfConvertToImage(String sourcePath, String targetSource) {
        File file = new File(sourcePath);
        try {
            file.mkdir();
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for(int i=0; i<pageCount; i++){
                BufferedImage image = renderer.renderImage(i, 2.5f);
                ImageIO.write(image,"JPG",new File(targetSource+ "/" + i + ".jpg"));
            }
            return pageCount;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @Author: ShaoChen
     * @Description: 将docx文件转换为Pdf文件, 调用这个方法，后面的配合使用的
     * @Date: 10:30 2018/4/16
     */
    public static void docConvertToPdf(String docxPath, String targetUrl) throws Exception {
        InputStream source = new FileInputStream(docxPath);
        OutputStream target = new FileOutputStream(targetUrl);
        Map<String, String> params = new HashMap<String, String>();
        PdfOptions options = PdfOptions.create();
        wordConverterToPdf(source, target, options, params);
    }

    /**
     * 将word文档， 转换成pdf, 中间替换掉变量
     *
     * @param source 源为word文档， 必须为docx文档
     * @param target 目标输出
     * @param params 需要替换的变量
     * @throws Exception
     */
    public static void wordConverterToPdf(InputStream source,
                                          OutputStream target, Map<String, String> params) throws Exception {
        PdfOptions options = PdfOptions.create();
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
                    log.error("设置中文字体异常:"+e.getMessage());
                    return ITextFontRegistry.getRegistry().getFont(familyName, encoding, size, style, color);
                }
            }


        });
        wordConverterToPdf(source, target, options, params);
    }

    /**
     * 将word文档， 转换成pdf, 中间替换掉变量
     *
     * @param source  源为word文档， 必须为docx文档
     * @param target  目标输出
     * @param params  需要替换的变量
     * @param options PdfOptions.create().fontEncoding( "windows-1250" ) 或者其他
     * @throws Exception
     */
    public static void wordConverterToPdf(InputStream source, OutputStream target,
                                          PdfOptions options,
                                          Map<String, String> params) throws Exception {
        XWPFDocument doc = new XWPFDocument(source);
        paragraphReplace(doc.getParagraphs(), params);
        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    paragraphReplace(cell.getParagraphs(), params);
                }
            }
        }
        PdfConverter.getInstance().convert(doc, target, options);
    }

    /**
     * 替换段落中内容
     */
    private static void paragraphReplace(List<XWPFParagraph> paragraphs, Map<String, String> params) {
        if (!params.isEmpty()) {
            for (XWPFParagraph p : paragraphs) {
                for (XWPFRun r : p.getRuns()) {
                    String content = r.getText(r.getTextPosition());
                    if (StringUtils.isNotEmpty(content) && params.containsKey(content)) {
                        r.setText(params.get(content), 0);
                    }
                }
            }
        }
    }

    /**
     * @Author: ShaoChen
     * @Description:   pdf添加水印
     * @Date: 10:36 2018/4/16
     */
    public static void pdfToWatermarkPdf(String sourceUrl, String targetUrl, String message) throws IOException, DocumentException {
        // 要输出的pdf文件
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(targetUrl)));
        // 将pdf文件先加水印然后输出
        setWatermark(bos, sourceUrl, 16, message);
    }

    public static void setWatermark(BufferedOutputStream bos, String input,
                                    int permission, String message) throws DocumentException,
            IOException {

        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, bos);
        int total = reader.getNumberOfPages() + 1;
        BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
        PdfGState gs = new PdfGState();
        for (int i = 1; i < total; i++) {
            /*
               首先介绍一下： 前两个参数是pdf的横纵坐标，可以想象成一个平面直角坐标系，原地在pdf每页的左下角；
               line为x轴；
               height为y轴；
               angle为水印的角度；
               这三个参数自己调整就好
            */
            // 设置第一行水印
            setLineWaterMark(stamper, base, gs, i, 140,1000, 40, message);
            setLineWaterMark(stamper, base, gs, i, 320,1000, 40, message);
            setLineWaterMark(stamper, base, gs, i, 500,1000, 40, message);
            setLineWaterMark(stamper, base, gs, i, 680,1000, 40, message);
            setLineWaterMark(stamper, base, gs, i, 860,1000, 40, message);
            // 设置第二行水印
            setLineWaterMark(stamper, base, gs, i, 140,700, 40, message);
            setLineWaterMark(stamper, base, gs, i, 320,700, 40, message);
            setLineWaterMark(stamper, base, gs, i, 500,700, 40, message);
            setLineWaterMark(stamper, base, gs, i, 680,700, 40, message);
            setLineWaterMark(stamper, base, gs, i, 860,700, 40, message);
            // 设置第三行水印
            setLineWaterMark(stamper, base, gs, i, 140,400, 40, message);
            setLineWaterMark(stamper, base, gs, i, 320,400, 40, message);
            setLineWaterMark(stamper, base, gs, i, 500,400, 40, message);
            setLineWaterMark(stamper, base, gs, i, 680,400, 40, message);
            setLineWaterMark(stamper, base, gs, i, 860,400, 40, message);
            // 设置第四行水印
            setLineWaterMark(stamper, base, gs, i, 140,100, 40, message);
            setLineWaterMark(stamper, base, gs, i, 320,100, 40, message);
            setLineWaterMark(stamper, base, gs, i, 500,100, 40, message);
            setLineWaterMark(stamper, base, gs, i, 680,100, 40, message);
            setLineWaterMark(stamper, base, gs, i, 860,100, 40, message);

        }
        stamper.close();
    }

    private static void setLineWaterMark(PdfStamper stamper, BaseFont base, PdfGState gs, int page, Integer line,
                                         Integer height, Integer angle, String message) {
        PdfContentByte content;
        content = stamper.getOverContent(page);// 在内容上方加水印
//                content = stamper.getUnderContent(i);//在内容下方加水印
        gs.setFillOpacity(0.2f);
        content.setGState(gs);
        content.beginText();
        content.setColorFill(Color.LIGHT_GRAY);
        content.setFontAndSize(base, 16);
        content.setTextMatrix(20, 70);
        content.showTextAligned(Element.ALIGN_CENTER, message, line, height, angle);
        content.setColorFill(Color.BLACK);
        content.setFontAndSize(base, 8);
        content.endText();
    }
}