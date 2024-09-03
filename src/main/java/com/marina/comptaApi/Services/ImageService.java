package com.marina.comptaApi.Services;


import com.marina.comptaApi.Models.FileData;
import com.marina.comptaApi.Models.ImageData;
import com.marina.comptaApi.Repositories.FileDataRepository;

import com.marina.comptaApi.utils.ImageUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService{

    private static final String FOLDER_PATH = "C:\\Users\\AGODA Marina\\Documents\\IPNET INSTITUTE OF TECHNOLOGY\\MEMOIRE\\APP\\front\\src\\assets\\images\\";


    @Autowired
    private FileDataRepository fileDataRepository;

//    public ImageData uploadImage(MultipartFile file) throws IOException {
//
//        //        if (imageData != null) {
////            return "Image enregitré avec succes : " + file.getOriginalFilename();
////        }
//        return repository.save(ImageData.builder()
//                .name(file.getOriginalFilename())
//                .type(file.getContentType())
//                .imageData(ImageUtils.compressImage(file.getBytes())).build());
//    }
//
//    public byte[] downloadImage(String fileName){
//        Optional<ImageData> dbImageData = repository.findByName(fileName);
//        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
//        return images;
//    }
//
//    public List<ImageData> getImage(){
//        return repository.findAll();
//    }


    @Transactional
    public FileData upload(MultipartFile file) throws IOException {
        String filePath=FOLDER_PATH+file.getOriginalFilename();

        FileData fileData=fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        return fileData;
    }

    public byte[] getFile(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

}