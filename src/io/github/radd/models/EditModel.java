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
    
    public final int SHRINK_DEFAULT = 100;
    public final int SHRINK_MAX = 100;
    public final int SHRINK_MIN = 1;
    public final int SHRINK_STEP = 2;
    
    public final int QUALITY_DEFAULT = 100;
    public final int QUALITY_MAX = 100;
    public final int QUALITY_MIN = 1;
    public final int QUALITY_STEP = 1;
     
    public File[] getImages();
    
    public void loadImages(File[] files);

    public String getFileChooserPath();

    public FileNameExtensionFilter getFilter();

    public String getSourceFolderPath();

    public String getDestinationFolderPath();
    public void setDestinationFolderPath(File dir);

    public void setShrink(int i);
    public void setQuality(int i);
    
}
