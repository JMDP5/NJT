/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package imageprocessing;

import java.awt.image.BufferedImage;

/**
 * Interface for image filter s
 * @author Aleksandar
 */
public interface ImageFilter {
    public BufferedImage processImage(BufferedImage image);
}
