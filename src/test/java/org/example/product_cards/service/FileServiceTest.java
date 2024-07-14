package org.example.product_cards.service;

import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

  @Mock
  private ApiService apiService;
  @InjectMocks
  private FileService fileService;
  private static final String TEST_RESOURCES_DIR = "src/test/resources/static/photo_without_background";

  @BeforeAll
  static void setUpClass() {
// Create the test resources directory if it doesn't exist
    Path testResourcesPath = Paths.get(TEST_RESOURCES_DIR);
    if (!Files.exists(testResourcesPath)) {
      try {
        Files.createDirectories(testResourcesPath);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @BeforeEach
  void setUp() throws IOException {
    ReflectionTestUtils.setField(fileService, "resourceDir", TEST_RESOURCES_DIR);
    // Create some test files in the test resources directory
    List<String> fileNames = List.of("image1.jpg", "image2.jpg", "image3.jpg");
    for (String fileName : fileNames) {
      Path filePath = Paths.get(TEST_RESOURCES_DIR, fileName);
      Files.createFile(filePath);
    }
  }

  @AfterEach
  void tearDown() throws IOException {
    // Clean up the test resources directory after each test
    Path testResourcesPath = Paths.get(TEST_RESOURCES_DIR);
    Files.walk(testResourcesPath)
        .map(Path::toFile)
        .forEach(File::delete);
  }

  @Test
  void givenImageUrlsAndProductName_whenProcessImages_thenReturnImageNames() {
    List<URL> imageUrls = List.of(
        Instancio.create(URL.class),
        Instancio.create(URL.class)
    );
    String productName = Instancio.create(String.class);

    byte[] mockImageData1 = Instancio.create(byte[].class);
    byte[] mockImageData2 = Instancio.create(byte[].class);

    when(apiService.sendRequestToRemoveBackground(eq(imageUrls.get(0)))).thenReturn(mockImageData1);
    when(apiService.sendRequestToRemoveBackground(eq(imageUrls.get(1)))).thenReturn(mockImageData2);

    List<String> photoNames = fileService.processImages(imageUrls, productName);

    assertThat(photoNames).hasSize(2);

    verify(apiService, times(1)).sendRequestToRemoveBackground(eq(imageUrls.get(0)));
    verify(apiService, times(1)).sendRequestToRemoveBackground(eq(imageUrls.get(1)));

  }


  @Test
  void givenFileName_whenGetImage_thenReturnImageBytes() {
    // Arrange
    byte[] expectedImageData = Instancio.create(byte[].class);
    String fileName = "test_image.jpg";
    Path filePath = Paths.get(TEST_RESOURCES_DIR, fileName);

    try {
      Files.write(filePath, expectedImageData);
    } catch (IOException e) {
      fail("Failed to create the test image file");
    }

    byte[] actualImageData = fileService.getImage(fileName);

    assertArrayEquals(expectedImageData, actualImageData);

    // Clean up
    try {
      Files.delete(filePath);
    } catch (IOException e) {
      fail("Failed to delete the test image file");
    }
  }

  @Test
  void givenImageBytesAndFileName_whenSaveImageToResources_thenPerformExpectedOperations() {
    byte[] imageData = Instancio.create(byte[].class);
    String fileName = "test_image.jpg";
    Path filePath = Paths.get(TEST_RESOURCES_DIR, fileName);

    fileService.saveImageToResources(imageData, fileName);

    assertTrue(Files.exists(filePath), "The image file should have been created");

    // Check the file content
    byte[] savedImageData;
    try {
      savedImageData = Files.readAllBytes(filePath);
      assertArrayEquals(imageData, savedImageData);
    } catch (IOException e) {
      fail("Failed to read the saved image file");
    }

    // Clean up
    try {
      Files.delete(filePath);
    } catch (IOException e) {
      fail("Failed to delete the test image file");
    }
  }

  @Test
  void givenImagesNames_whenDeleteImagesFromResources_thenPerformExpectedOperations() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    List<String> fileNames = Arrays.asList("image1.jpg", "image2.jpg");

    Method method = FileService.class.getDeclaredMethod("deleteImagesFromResources", List.class);
    method.setAccessible(true);
    method.invoke(fileService, fileNames);

    for (String fileName : fileNames) {
      Path filePath = Paths.get(TEST_RESOURCES_DIR, fileName);
      assertFalse(Files.exists(filePath), "File " + fileName + " should have been deleted");
    }

    // Check that the remaining file still exists
    Path remainingFilePath = Paths.get(TEST_RESOURCES_DIR, "image3.jpg");
    assertTrue(Files.exists(remainingFilePath), "File image3.jpg should still exist");
  }

}