package com.devbaktiyarov.webocr.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devbaktiyarov.webocr.entity.ImageFile;
import com.devbaktiyarov.webocr.entity.UserProfile;
import com.devbaktiyarov.webocr.repository.FileRepository;
import com.devbaktiyarov.webocr.repository.UserRepository;

@Controller
public class FileController {

    @Value("${path.word}")
    private String wordPath;

    private UserRepository userRepository;
    private FileRepository fileRepository;

    public FileController(UserRepository userRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    @GetMapping("/files")
    public String getFiles(Principal principal, Model model) {
        Optional<UserProfile> userProfile = userRepository.findByEmail(principal.getName());

        List<ImageFile> files = fileRepository.findAllByUserId(userProfile.get().getUserId());

        model.addAttribute("files", files);
        
        return "files";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam(name = "file") String filename) throws IOException {
        File directory = new File("");
        File file = new File(directory.getAbsolutePath() + "/" + wordPath + filename);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
        FileSystemResource resource = new FileSystemResource(file);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam(name = "file_id") Integer id) {
        File directory = new File("");
        String filename = fileRepository.findById(id).get().getName();
        File file = new File(directory.getAbsolutePath() + wordPath + filename);    
        
        if(file.delete()) {
            fileRepository.deleteById(id);
        }
        
        return "redirect:/files";
    }


    
}
