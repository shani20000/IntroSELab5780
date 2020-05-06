package elements;

import primitives.Color;

abstract class Light {
    protected Color _intensity;

    /**
     * constructor
     * @param _intensity
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    /**
     * getter
     * @return
     */

    public Color get_intensity() {
        return _intensity;
    }
}
