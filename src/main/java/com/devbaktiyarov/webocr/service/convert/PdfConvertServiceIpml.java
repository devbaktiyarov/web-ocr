package com.devbaktiyarov.webocr.service.convert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devbaktiyarov.webocr.entity.ImageFile;
import com.devbaktiyarov.webocr.entity.UserProfile;
import com.devbaktiyarov.webocr.repository.UserRepository;
import com.devbaktiyarov.webocr.util.TessUtil;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class PdfConvertServiceIpml implements PdfConvertService {


    private static final String FONT = "src/main/resources/static/fonts/Helvetica.ttf";
    private static final String DATEFORMAT = "yyyy-MM-dd:hh:mm:ss";
    private static final String HEADERKEY = "Content-Disposition";

    @Value("${path.pdf}")
    private String path;

    private Tesseract tesseract;
    private UserRepository userRepository;
    

    public PdfConvertServiceIpml(Tesseract tesseract, UserRepository userRepository) {
        this.tesseract = tesseract;
        this.userRepository = userRepository;
    }

    @Override
    public void converImageToPdf(HttpServletResponse response,
            MultipartFile[] files,
            String language, Principal principal) {
        
        tesseract.setLanguage(language);
        try (Document document = new Document(PageSize.A4)) {
            response.setContentType("application/pdf");
            DateFormat dateFormatter = new SimpleDateFormat(DATEFORMAT);
            String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=" + currentDateTime + ".pdf";
            response.setHeader(HEADERKEY, headerValue);
            
            PdfWriter.getInstance(document,  response.getOutputStream());
            if(principal != null) { 
                Optional<UserProfile> user = userRepository.findByEmail(principal.getName());
                if(user.isPresent()) {
                    String fileName = user.get().getUserId() + "_" + currentDateTime + ".pdf";
                    ImageFile imageFile = new ImageFile();
                    imageFile.setName(fileName);
                    imageFile.setType("pdf");
                    user.get().getImageFileList().add(imageFile);
                    userRepository.save(user.get());
    
                    PdfWriter.getInstance(document, new FileOutputStream(path + fileName));
                }
            }
            document.open();
            Font fontParagraph = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true);
            fontParagraph.setSize(10);
            for (MultipartFile file : files) {
                try {
                    String result = tesseract.doOCR(TessUtil.createImageFromBytes(file.getBytes()));
                    Paragraph paragraph = new Paragraph(result, fontParagraph);
                    paragraph.setAlignment(Paragraph.ALIGN_CENTER);
                    com.lowagie.text.Image image = com.lowagie.text.Image.getInstance(file.getBytes());
                    image.scaleAbsolute(250, 100);
                    image.setAlignment(com.lowagie.text.Image.ALIGN_CENTER);
                    document.add(image);
                    document.add(paragraph);
                } catch (TesseractException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (DocumentException | IOException e1) {
            e1.printStackTrace();
        }
        
        
    }
    
}
