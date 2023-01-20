package com.compressionPOC.compressPDF;

import com.spire.pdf.PdfCompressionLevel;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.exporting.PdfImageInfo;
import com.spire.pdf.graphics.PdfBitmap;


import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfImageObject;


import com.aspose.pdf.*;
import com.aspose.pdf.optimization.OptimizationOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CompressPdfApplication {

//################################################
//################################################ CASE : 1
//################################################

//	public static void main(String[] args) {
//		//SpringApplication.run(CompressPdfApplication.class, args);

//		// Scenario : 1 (not working)
//		Document pdfDocument = new Document("/Users/yashwinder.kumar/Downloads/merged-report.pdf");
//		pdfDocument.optimize();
//		pdfDocument.save("/Users/yashwinder.kumar/Documents/POC/compressTempPdf/Optimized_output.pdf");


//		//Scenario : 2
//		Document doc = new Document("/Users/yashwinder.kumar/Downloads/merged-report.pdf");
//		// Initialize OptimizationOptions object
//		OptimizationOptions opt = new OptimizationOptions();
//		// Enable image compression
//		// Set the quality and resolution of images in PDF file
//		opt.getImageCompressionOptions().setCompressImages(true);
//		opt.getImageCompressionOptions().setImageQuality(10);
//		opt.getImageCompressionOptions().setMaxResolution(150);
//		opt.getImageCompressionOptions().setResizeImages(true);
//		doc.optimizeResources(opt);
//		// Save the updated file
//		doc.save("/Users/yashwinder.kumar/Documents/POC/compressTempPdf/scenario2.pdf");


//		//scenario : 3
//		Document doc = new Document("/Users/yashwinder.kumar/Downloads/merged-report.pdf");
//		OptimizationOptions opt = new OptimizationOptions();
//		// Either
//		// Unembed all fonts in PDF
//		//opt.setUnembedFonts(true);
//		// only keep embedded fonts for used characters
//		opt.setSubsetFonts(true);
//		// link duplicate streams and objects
//		//opt.setLinkDuplcateStreams(false);
//		//opt.setRemoveUnusedStreams(false);
//		//opt.setRemoveUnusedObjects(false);
//		doc.optimizeResources(opt);
//		doc.save("/Users/yashwinder.kumar/Documents/POC/compressTempPdf/scenario3_2.pdf");


//		//scenario : 4
//		Document pdfDocument = new Document("/Users/yashwinder.kumar/Downloads/merged-report.pdf");
//		// Iterate through each page and annotation
//		for (Page page : pdfDocument.getPages())
//		{
//			for (Annotation annotation : page.getAnnotations())
//			{
//				// Either flatten the annotation
//				annotation.flatten();
//				// OR delete the annotation
//				//page.getAnnotations().delete(annotation);
//			}
//		}
//		// Save optimized PDF document
//		pdfDocument.save("/Users/yashwinder.kumar/Documents/POC/compressTempPdf/scenario4_2.pdf");


//		//scenario : 5
//		Document doc = new Document("/Users/yashwinder.kumar/Downloads/merged-report.pdf");
//		// Flatten Form fields
//		if (doc.getForm().getFields().length > 0)
//		{
//			for (Field item : doc.getForm().getFields())
//			{
//				item.flatten();
//			}
//		}
//		doc.save("/Users/yashwinder.kumar/Documents/POC/compressTempPdf/scenario5.pdf");

//		//Scenario : 6 (taking above scenarios all at once)
//		Document doc = new Document("/Users/yashwinder.kumar/Downloads/merged-report.pdf");
//		OptimizationOptions opt = new OptimizationOptions();
//		opt.getImageCompressionOptions().setCompressImages(true);
//		opt.getImageCompressionOptions().setImageQuality(10);
//		opt.getImageCompressionOptions().setMaxResolution(150);
//		opt.getImageCompressionOptions().setResizeImages(true);
//		opt.setUnembedFonts(true);
//		doc.optimizeResources(opt);

//		for (Page page : doc.getPages()) {
//			for (Annotation annotation : page.getAnnotations()) {
//				//annotation.flatten(); // OR
//				page.getAnnotations().delete(annotation);
//			}
//		}
//
//		if (doc.getForm().getFields().length > 0) {
//			for (Field item : doc.getForm().getFields()) {
//				item.flatten();
//			}
//		}
//		//scenario : 7 Black-white
//		RgbToDeviceGrayConversionStrategy strategy = new RgbToDeviceGrayConversionStrategy();
//		for (int idxPage = 1; idxPage <= doc.getPages().size(); idxPage++) {
//			Page page = doc.getPages().get_Item(idxPage);
//
//			// Convert color space of each page to Greyscale
//			strategy.convert(page);
//		}
//		doc.save("/Users/yashwinder.kumar/Documents/POC/compressTempPdf/scenario7.pdf");

//	}

//################################################
//################################################ CASE : 2
//################################################
//	public static float FACTOR = 0.5f;
//
//	public static void manipulatePdf(String src, String dest) throws IOException, DocumentException {
//		PdfName key = new PdfName("ITXT_SpecialId");
//		PdfName value = new PdfName("123456789");
//		// Read the file
//		PdfReader reader = new PdfReader(src);
//		int n = reader.getXrefSize();
//		PdfObject object;
//		PRStream stream;
//		// Look for image and manipulate image stream
//		for (int i = 0; i < n; i++) {
//			object = reader.getPdfObject(i);
//			if (object == null || !object.isStream())
//				continue;
//			stream = (PRStream)object;
//			// if (value.equals(stream.get(key))) {
//			PdfObject pdfsubtype = stream.get(PdfName.SUBTYPE);
//			System.out.println(stream.type());
//			if (pdfsubtype != null && pdfsubtype.toString().equals(PdfName.IMAGE.toString())) {
//				PdfImageObject image = new PdfImageObject(stream);
//				BufferedImage bi = image.getBufferedImage();
//				if (bi == null) continue;
//				int width = (int)(bi.getWidth() * FACTOR);
//				int height = (int)(bi.getHeight() * FACTOR);
//				BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//				AffineTransform at = AffineTransform.getScaleInstance(FACTOR, FACTOR);
//				Graphics2D g = img.createGraphics();
//				g.drawRenderedImage(bi, at);
//				ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
//				ImageIO.write(img, "JPG", imgBytes);
//				stream.clear();
//				stream.setData(imgBytes.toByteArray(), false, PRStream.BEST_COMPRESSION);
//				stream.put(PdfName.TYPE, PdfName.XOBJECT);
//				stream.put(PdfName.SUBTYPE, PdfName.IMAGE);
//				stream.put(key, value);
//				stream.put(PdfName.FILTER, PdfName.DCTDECODE);
//				stream.put(PdfName.WIDTH, new PdfNumber(width));
//				stream.put(PdfName.HEIGHT, new PdfNumber(height));
//				stream.put(PdfName.BITSPERCOMPONENT, new PdfNumber(8));
//				stream.put(PdfName.COLORSPACE, PdfName.DEVICERGB);
//			}
//		}
//		// Save altered PDF
//		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
//		stamper.close();
//		reader.close();
//	}
//
//	public static void main(String[] args) throws IOException, DocumentException {
//		//createPdf(RESULT);
//		manipulatePdf("/Users/yashwinder.kumar/Downloads/merged-report.pdf", "/Users/yashwinder.kumar/Documents/POC/compressTempPdf/case2.pdf");
//	}


//################################################
//################################################ CASE : 3
//################################################
public static void main(String[] args) {

    //Create a PdfDocument object
    PdfDocument doc = new PdfDocument();

    //Load a PDF file
    doc.loadFromFile("/Users/yashwinder.kumar/Downloads/merged-report.pdf");

    //Disable incremental update
    doc.getFileInfo().setIncrementalUpdate(false);

    //Set the compression level to best
    doc.setCompressionLevel(PdfCompressionLevel.Best);

    //Loop through the pages in the document
    for (int i = 0; i < doc.getPages().getCount(); i++) {

        //Get a specific page
        PdfPageBase page = doc.getPages().get(i);

        //Get image information collection from the page
        PdfImageInfo[] images = page.getImagesInfo();

        //Traverse the items in the collection
        if (images != null && images.length > 0)
            for (int j = 0; j < images.length; j++) {

                //Get a specific image
                PdfImageInfo image = images[j];
                PdfBitmap bp = new PdfBitmap(image.getImage());

                //Set the compression quality
                bp.setQuality(20);

                //Replace the original image with the compressed one
                page.replaceImage(j, bp);
            }

        //Save the document to a PDF file
        doc.saveToFile("/Users/yashwinder.kumar/Documents/POC/compressTempPdf/case3.pdf");
        doc.close();
    }
}

}
