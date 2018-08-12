/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.radd.models;

import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Piotr
 */
public interface EditModel {
    
    public File[] getImages();
    
    public void loadImages(File[] files);

    public String getFileChooserPath();

    public FileNameExtensionFilter getFilter();

    public String getSourceFolderPath();

    public String getDestinationFolderPath();

    public void setDestinationFolderPath(File dir);
}
