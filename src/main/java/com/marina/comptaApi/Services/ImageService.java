package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.Image;
import com.marina.comptaApi.Repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(MultipartFile file){
        imageRepository.save(
                Image.builder()
                        .url(file.getOriginalFilename())
                        .build()
        );
    }

    public void deleteImage(Long id){
        imageRepository.deleteById(id);
    }

    public Image getImage(Long id){
        return imageRepository.findById(id).orElse(null);
    }

    public List<Image> getAllImages(){
        return imageRepository.findAll();
    }

}
