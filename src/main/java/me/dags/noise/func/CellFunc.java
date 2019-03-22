package me.dags.noise.func;

import me.dags.noise.Module;
import me.dags.noise.util.NoiseUtil;
import me.dags.noise.util.Vec2f;

/**
 * @author dags <dags@dags.me>
 */
public enum CellFunc {
    CELL_VALUE {
        @Override
        public float apply(int xc, int yc, float distance, int seed, Module lookup) {
            return NoiseUtil.valCoord2D(seed, xc, yc);
        }
    },
    NOISE_LOOKUP {
        @Override
        public float apply(int xc, int yc, float distance, int seed, Module lookup) {
            Vec2f vec = NoiseUtil.CELL_2D[NoiseUtil.hash2D(seed, xc, yc) & 255];
            return lookup.getValue(xc + vec.x, yc + vec.y);
        }
    },
    DISTANCE {
        @Override
        public float apply(int xc, int yc, float distance, int seed, Module lookup) {
            return distance - 1;
        }
    },
    ;

    public abstract float apply(int xc, int yc, float distance, int seed, Module lookup);
}
