package me.dags.noise;

import me.dags.noise.func.CellFunc;
import me.dags.noise.func.DistanceFunc;
import me.dags.noise.func.EdgeFunc;
import me.dags.noise.func.Interpolation;
import me.dags.noise.source.*;

/**
 * @author dags <dags@dags.me>
 */
public class Builder {

    public static final int SEED = 1337;
    public static final int OCTAVES = 3;
    public static final float GAIN = 0.5F;
    public static final float POWER = 1F;
    public static final float LACUNARITY = 2F;
    public static final float FREQUENCY = 0.01F;
    public static final float CONST_VALUE = 0F;
    public static final CellFunc CELL_FUNC = CellFunc.CELL_VALUE;
    public static final EdgeFunc EDGE_FUNC = EdgeFunc.DISTANCE_2;
    public static final DistanceFunc DIST_FUNC = DistanceFunc.EUCLIDEAN;
    public static final Interpolation INTERP = Interpolation.QUINTIC;
    public static final Source SOURCE = new Constant(CONST_VALUE);

    private int seed = SEED;
    private int octaves = OCTAVES;
    private float gain = GAIN;
    private float lacunarity = LACUNARITY;
    private float frequency = FREQUENCY;
    private float power = POWER;
    private Module source = SOURCE;
    private CellFunc cellFunc = CELL_FUNC;
    private EdgeFunc edgeFunc = EDGE_FUNC;
    private DistanceFunc distFunc = DIST_FUNC;
    private Interpolation interpolation = INTERP;

    protected Builder() {}

    public int seed() {
        return seed;
    }

    public int octaves() {
        return octaves;
    }

    public float gain() {
        return gain;
    }

    public float power() {
        return power;
    }

    public float frequency() {
        return frequency;
    }

    public float lacunarity() {
        return lacunarity;
    }

    public Interpolation interp() {
        return interpolation;
    }

    public CellFunc cellFunc() {
        return cellFunc;
    }

    public EdgeFunc edgeFunc() {
        return edgeFunc;
    }

    public DistanceFunc distFunc() {
        return distFunc;
    }

    public Module source() {
        return source;
    }

    public Builder seed(int seed) {
        this.seed = seed;
        return this;
    }

    public Builder octaves(int octaves) {
        this.octaves = octaves;
        return this;
    }

    public Builder gain(double gain) {
        this.gain = (float) gain;
        return this;
    }

    public Builder power(double power) {
        this.power = (float) power;
        return this;
    }

    public Builder lacunarity(double lacunarity) {
        this.lacunarity = (float) lacunarity;
        return this;
    }

    public Builder scale(int frequency) {
        this.frequency = 1F / frequency;
        return this;
    }

    public Builder frequency(double frequency) {
        this.frequency = (float) frequency;
        return this;
    }

    public Builder interp(Interpolation interpolation) {
        this.interpolation = interpolation;
        return this;
    }

    public Builder cellFunc(CellFunc cellFunc) {
        this.cellFunc = cellFunc;
        return this;
    }

    public Builder edgeFunc(EdgeFunc cellType) {
        this.edgeFunc = cellType;
        return this;
    }

    public Builder distFunc(DistanceFunc cellDistance) {
        this.distFunc = cellDistance;
        return this;
    }

    public Builder source(Module source) {
        this.source = source;
        return this;
    }

    public Source perlin() {
        return new FastPerlin(this);
    }

    public Source ridge() {
        return new FastRidge(this);
    }

    public Source ridge2() {
        return new FlowRidge(this);
    }

    public Source billow() {
        return new FastBillow(this);
    }

    public Source cubic() {
        return new FastCubic(this);
    }

    public Source cell() {
        return new FastCell(this);
    }

    public Source cellEdge() {
        return new FastCellEdge(this);
    }
}
