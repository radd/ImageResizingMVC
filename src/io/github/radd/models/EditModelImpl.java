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
    public void setImages(File[] files) {     
        if(files != null && files.length > 0) {
            images = files;
            changeStateAndNotify(Action.SET_IMAGES);
        }
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

   
}
