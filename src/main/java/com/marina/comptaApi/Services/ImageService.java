package com.marina.comptaApi.Services;


import com.marina.comptaApi.Models.ImageData;
import com.marina.comptaApi.Repositories.ImageRepository;
import com.marina.comptaApi.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService{

    @Autowired
    private ImageRepository repository;

    public ImageData uploadImage(MultipartFile file) throws IOException {

        //        if (imageData != null) {
//            return "Image enregitré avec succes : " + file.getOriginalFilename();
//        }
        return repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    public List<ImageData> getImage(){
        return repository.findAll();
    }
}