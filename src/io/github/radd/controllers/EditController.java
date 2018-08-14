/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.radd.controllers;

import java.io.File;

/**
 *
 * @author Piotr
 */
public interface EditController {
    
    public void setImages(File[] files);

    public void setSaveFolder(File folder);

    public void setShrink(int i);

    public void setQuality(int i);

    public void editImages();
}
