/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.radd.controllers;

import io.github.radd.models.EditModel;
import io.github.radd.views.MainFrame;
import java.io.File;

/**
 *
 * @author Piotr
 */
public class EditControllerImpl implements EditController {

    private EditModel model;
    private MainFrame view;
    
    public EditControllerImpl(EditModel model) {
        this.model = model;
        view = new MainFrame(this, model);
        
    }
    
    @Override
    public void setImages(File[] files) {
        model.setImages(files);
    }
    
    
}
