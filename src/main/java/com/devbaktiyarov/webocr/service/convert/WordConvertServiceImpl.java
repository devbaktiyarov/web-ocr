package com.devbaktiyarov.webocr.service.convert;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devbaktiyarov.webocr.entity.UserProfile;
import com.devbaktiyarov.webocr.entity.ImageFile;
import com.devbaktiyarov.webocr.repository.UserRepository;
import com.devbaktiyarov.webocr.util.TessUtil;

import jakarta.servlet.http.HttpServletResponse;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class WordConvertServiceImpl implements WordConverService {

    private static final String WORDCONTENTTYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    private static final String DATEFORMAT = "yyyy-MM-dd:hh:mm:ss";
    private static final String HEADERKEY = "Content-Disposition";

    @Value("${path.word}")
    private String wordPath;

    private Tesseract tesseract;

    private UserRepository userRepository;

    public WordConvertServiceImpl(Tesseract tesseract, UserRepository userRepository) {
        this.tesseract = tesseract;
        this.userRepository = userRepository;
    }

    @Override
    public void converImageToWord(HttpServletResponse response, MultipartFile[] files, 
                    String language, Principal principal) {
        tesseract.setLanguage(language);
        XWPFDocument document = new XWPFDocument();
        for (MultipartFile file : files) {

            XWPFParagraph fileName = document.createParagraph();
            fileName.setAlignment(ParagraphAlignment.START);

            XWPFRun fileNameRun = fileName.createRun();
            XWPFParagraph fileText = document.createParagraph();

            fileText.setAlignment(ParagraphAlignment.START);
            XWPFRun fileTextRun = fileName.createRun();

            XWPFParagraph image = document.createParagraph();
            XWPFRun imageRun = image.createRun();
            try {
                fileNameRun.setText(file.getOriginalFilename());
                fileNameRun.setBold(true);
                fileNameRun.setFontSize(16);
                fileNameRun.setFontFamily("Times New Roman");

                fileTextRun.setText(tesseract.doOCR(TessUtil.createImageFromBytes(file.getBytes())));
                fileTextRun.setFontFamily("Times New Roman");
                InputStream is = new ByteArrayInputStream(file.getBytes());
                imageRun.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, "my pic", Units.toEMU(300), Units.toEMU(200));
            } catch (TesseractException | IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
        }
        try {
            response.setContentType(WORDCONTENTTYPE);
            DateFormat dateFormatter = new SimpleDateFormat(DATEFORMAT);
            String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=" + currentDateTime + ".docx";
            response.setHeader(HEADERKEY, headerValue);
            if(principal != null) { 
                Optional<UserProfile> user = userRepository.findByEmail(principal.getName());
                if(user.isPresent()) {
                    String fileName = user.get().getUserId() + "_" + currentDateTime + ".docx";
                    ImageFile imageFile = new ImageFile();
                    imageFile.setName(fileName);
                    imageFile.setType("word");
                    user.get().getImageFileList().add(imageFile);
                    userRepository.save(user.get());
                    document.write(new FileOutputStream(wordPath + fileName));
                }
            }
            document.write(response.getOutputStream());
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
