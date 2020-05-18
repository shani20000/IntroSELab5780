package primitives;

public class Material {
   private double _kD;
   private double _kS;
   private double _kT;
   private double _kR;
   private double _nShininess;

    /**
     * constructor
     * @param _kD
     * @param _kS
     * @param _kT
     * @param _kR
     * @param _nShininess
     */
    public Material(double _kD, double _kS, double _nShininess, double _kT, double _kR) {
        this._kD = _kD;
        this._kS = _kS;
        this._kT = _kT;
        this._kR = _kR;
        this._nShininess = _nShininess;
    }

    /**
     * constructor
     * @param _kD
     * @param _kS
     * @param _nShininess
     */
    public Material(double _kD, double _kS, double _nShininess) {
        this(_kD, _kS,_nShininess, 0, 0);
    }

    /**
     * getter
     * @return _kD
     */
    public double get_kD() {
        return _kD;
    }

    /**
     * getter
     * @return _kT
     */
    public double get_kT() {
        return _kT;
    }

    /**
     * getter
     * @return _kR
     */
    public double get_kR() {
        return _kR;
    }


    /**
     * getter
     * @return _kS
     */
    public double get_kS() {
        return _kS;
    }

    /**
     * getter
     * @return _nShininess
     */
    public double get_nShininess() {
        return _nShininess;
    }
}
