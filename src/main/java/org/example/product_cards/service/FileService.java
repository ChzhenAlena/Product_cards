package org.example.product_cards.service;

import lombok.RequiredArgsConstructor;
import org.example.product_cards.exception.ReadingImageException;
import org.example.product_cards.exception.WritingImageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class FileService {

  @Value("${file.resource-dir}")
  private String resourceDir;
  private final ApiService apiService;

  public List<String> updateImages(List<URL> imageUrls, String productName, List<String> oldFileNames){
    deleteImagesFromResources(oldFileNames);
    return processImages(imageUrls, productName);
  }

  public List<String> processImages(List<URL> imageUrls, String productName){
    List <String> photoNames = new ArrayList<>();
    for (URL imageUrl : imageUrls) {
      String fileName = generateName(productName);
      byte[] imageData = apiService.sendRequestToRemoveBackground(imageUrl);
      saveImageToResources(imageData, fileName);
      photoNames.add(fileName);
    }
    return photoNames;
  }

  public byte[] getImage(String fileName) {
    try {
      File file = new File(Paths.get(resourceDir, fileName).toString());
      try (FileInputStream in = new FileInputStream(file)) {
        return in.readAllBytes();
      }
    } catch (IOException e) {
      throw new ReadingImageException();
    }
  }

  protected void saveImageToResources(byte[] imageData, String fileName) {
    try {
      File file = new File(Paths.get(resourceDir, fileName).toString());
      try (FileOutputStream fos = new FileOutputStream(file)) {
        fos.write(imageData);
      }
    } catch (IOException e) {
      throw new WritingImageException();
    }
  }

  protected void deleteImagesFromResources(List<String> fileNames) {
    for(String fileName : fileNames) {
      Path path = Paths.get(resourceDir, fileName);
      File file = new File(path.toString());
      if (file.exists()) {
        try {
          Files.delete(path);
        } catch (IOException e) {
          // ignored
        }
      }
    }
  }

  protected String generateName(String productName) {
    Random random = new Random();
    int randomNumber = random.nextInt(1000000);
    return productName.replace(" ", "_") + "_" + String.valueOf(randomNumber) + ".png";
  }

}
