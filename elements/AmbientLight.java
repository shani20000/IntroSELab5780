package elements;

import primitives.Color;

public class AmbientLight extends Light{

    /**
     * constructor
     * @param _intensity
     * @param ka
     */
    public AmbientLight(Color _intensity, double ka) {
       super(_intensity.scale(ka));
    }


}
