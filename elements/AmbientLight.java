package elements;

import primitives.Color;

public class AmbientLight extends Light{

    /**
     * constructor
     * @param _ia
     * @param ka
     */
    public AmbientLight(Color _ia, double ka) {
       super(_ia.scale(ka));
    }


}
