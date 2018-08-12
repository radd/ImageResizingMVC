/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.radd.models;

import java.io.File;
import java.util.Observable;
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
    
    public EditModelImpl() {
        initModel();
    }
    
    private void initModel() {
        setFileChooserPath();
        setFilter();
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
    
    
    
    public void setSourceFolderPath() {
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
 
}
