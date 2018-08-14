/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.radd.models;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observable;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Piotr
 */
public class EditModelImpl extends Observable implements EditModel {
    
    private String fileChooserPath;
    private FileNameExtensionFilter filter;
    private File[] images;
    private String sourceFolderPath;
    private String destinationFolderPath;
    private String errorMessage;

    private int shrink;
    private int quality;
    
    public EditModelImpl() {
        initModel();
    }
    
    private void initModel() {
        setFileChooserPath();
        setFilter();
        setEditOptions();
    }
    
    private void changeStateAndNotify(Action a) {
        setChanged();
        notifyObservers(a);
    }
     
    @Override
    public File[] getImages() {
        return images;
    }
    
    @Override
    public void loadImages(File[] files) {     
        if(files != null && files.length > 0) {
            setImages(files);
            setSourceFolderPath();
        }
    }
    
    private void setImages(File[] files) {
        images = files;
        changeStateAndNotify(Action.SET_IMAGES);   
    }
 
    @Override
    public String getFileChooserPath() {
        return fileChooserPath;
    }
    
    private void setFileChooserPath() {
        String path = System.getProperty("user.home") + "/Pictures";
        if(path == null || path.isEmpty())
            path = "C:";  
        fileChooserPath = path;
    }
    
    @Override
    public FileNameExtensionFilter getFilter() {
        return filter;
    }
    
    private void setFilter() {
        filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png");
    }
    
    private void setSourceFolderPath() {
        if(images != null && images.length > 0) {
            File file = images[0];
            sourceFolderPath = file.getParent();
            changeStateAndNotify(Action.SET_SOURCE_PATH);
            autoFillDestinationFolderPath();
        }
    }

    @Override
    public String getSourceFolderPath() {
        return sourceFolderPath;
    }

    private void autoFillDestinationFolderPath() {
        if(destinationFolderPath == null || destinationFolderPath.isEmpty())
            setDestinationFolderPath(sourceFolderPath);
    }
    
    @Override
    public String getDestinationFolderPath() {
        return destinationFolderPath;
    }
    
    private void setDestinationFolderPath(String path) {
        destinationFolderPath = path;    
        changeStateAndNotify(Action.SET_DEST_PATH);
    }
    
    @Override
    public void setDestinationFolderPath(File dir) {
        if (dir != null && !dir.getAbsolutePath().isEmpty())
            setDestinationFolderPath(dir.getAbsolutePath());
    }

    private void setEditOptions() {
        shrink = SHRINK_DEFAULT;
        quality = QUALITY_DEFAULT;
    }

    @Override
    public void setShrink(int i) {
        if(i >= SHRINK_MIN && i <= SHRINK_MAX)
            shrink = i;
    }
    
    @Override
    public void setQuality(int i) {
        if(i >= QUALITY_MIN && i <= QUALITY_MAX)
            quality = i;
    }

    @Override
    public void editImages() {
        if(images != null && images.length > 0) {
            editAllImages();
        }
        else {
            changeStateAndNotify(Action.EDIT_ERROR_1);
        }
    }

    private void editAllImages() {
        try {
            for (File inputImg : images) {
                editImage(inputImg);
            }
            changeStateAndNotify(Action.EDIT_OK);
        } catch (IOException e) {
            System.out.println(e);
            errorMessage = e.getMessage();
            changeStateAndNotify(Action.EDIT_ERROR_2);
        }
    }

    private void editImage(File img) throws IOException {
        BufferedImage inputImage = ImageIO.read(img);
        double percent = (double) shrink / 100;
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        
        //prepare output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        //draw image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 
        //create output image info
        OutputImageInfo outputInfo = new OutputImageInfo(img, destinationFolderPath, scaledWidth, scaledHeight);

        //save image
        ImageIO.write(outputImage, outputInfo.getFormatName(), new File(outputInfo.getOutputPath()));
        
        inputImage.flush();
        outputImage.flush();
    }

    @Override
    public String getErrorMsg() {
        return errorMessage;
    }
    
    //TODO move to new file
    //TODO change to FileInfo
    class OutputImageInfo {

        private final File inputImg;
        private final String destPath;
        private final int width;
        private final int height;
        
        private String outputPath;
        private String formatName;
        private String imageName;
        
        
        private OutputImageInfo(File inputImg, String destPath, int width, int height) throws IOException {
            this.inputImg = inputImg;
            this.destPath = destPath;
            this.width = width;
            this.height = height;
            
            createInfo();
        }

        private void createInfo() throws IOException {
            String inputPath = inputImg.getAbsolutePath();
            formatName = getFormatName(inputPath); 
            
            String fullFileName = getFullFileName(inputPath);
            imageName = getImageName(fullFileName);
            
            outputPath = getOutputPath(imageName, formatName);
        }

        private String getFormatName(String path) throws IOException {
            if (path.indexOf(".") > 0) {
                return path.substring(path.lastIndexOf(".") + 1);
            } else {
                throw new IOException("Invalid image format");
            }       
        }
      
        private String getImageName(String fullFileName) throws IOException {
            if (fullFileName.indexOf(".") > 0) {
                return fullFileName.substring(0, fullFileName.lastIndexOf("."));
            } else {
                throw new IOException("Invalid image format");
            }
        }
          
        private String getFullFileName(String path) {
            Path p = Paths.get(path);
            return p.getFileName().toString();
        }
         
          
        private String getOutputPath(String name, String format) throws IOException {
            return destPath + File.separator + name + "_" + width + "x" + height + "." + format;
        }

        public String getOutputPath() {
            return outputPath;
        }

        public String getFormatName() {
            return formatName;
        }

        public String getImageName() {
            return imageName;
        }
    }
    
 
}
